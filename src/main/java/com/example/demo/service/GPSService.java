package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import com.example.demo.dao.GPSDao;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.GPS;
import com.example.demo.domain.User;
import com.example.demo.dto.GPSDTO;
import com.example.demo.utility.GPXParserUtility;
import com.example.demo.utility.Util;
import com.hs.gpxparser.modal.GPX;
import com.hs.gpxparser.modal.Waypoint;


@Service
public class GPSService {
	
	private static final Logger logger = LoggerFactory.getLogger(GPSService.class);
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GPSDao gpsDao;
	
    @Autowired
    CacheManager cacheManager;
    
    @Autowired
    AmazonClient amazonClient;
	
	
    @Caching(evict = {
            @CacheEvict(value = "findAllCache", allEntries = true),
            @CacheEvict(value = "findByIdCache", allEntries = true)})
	public boolean saveGPS(String username, ByteArrayInputStream in, String path, String name) {
		try {
			User user = userDao.findByUsername(username);
			GPX gpx = GPXParserUtility.parseGPX(in);
			if(gpx != null) {
				List<Waypoint> lstWayPoints = new ArrayList<>();
				gpx.getTracks().stream().forEach(track -> {
					track.getTrackSegments().forEach(trackSegMent ->{
						lstWayPoints.addAll(trackSegMent.getWaypoints());
					});
				});
				
	        
				GPSDTO trackInfoDto = parseTrackInfo(lstWayPoints);
				GPS gpsEntity = convertGPSDtoToGPS(trackInfoDto,path,name,user);
				gpsDao.save(gpsEntity);
				return true;
			}
			amazonClient.deleteFileFromS3Bucket(path);
			return false;
		}catch (Exception e) {
			logger.error("Failed to save gps,exception ",e);
			amazonClient.deleteFileFromS3Bucket(path);
			return false;
		}
	}
	
	@Cacheable(cacheNames = "findAllCache")
	public List<GPS> getlatestTrack(){
		return gpsDao.getLatestGPS();
	}
	
	@Cacheable(cacheNames = "findByIdCache")
	public GPS findById(Long id) {
		return gpsDao.findOne(id);
	}
	
	private GPSDTO parseTrackInfo(List<Waypoint> points) {
		
		Waypoint last = null;
        double totalDist = 0.0;
        double totalTime = 0.0;
        double totalUp = 0;
        double totalDown = 0;
        double maxEle = points.get(0).getElevation();
        double minEle = points.get(0).getElevation();
        double maxSpeed = 0.0;
        double total3dDist = 0.0;
        double total2dDist = 0.0;
        double activeTime = 0.0;
        double stopTime = 0.0;
        double movingDist = 0.0;
        int count = 0;
 
        for(Waypoint point:points){
        	
        	if(point.getElevation() < minEle) {
        		minEle = point.getElevation();
        	}
        	
        	if(point.getElevation() > maxEle) {
        		maxEle = point.getElevation();
        	}
        	
            if(last != null){
            	double dist = Util.distVincentY(point.getLongitude(), point.getLatitude(), last.getLongitude(), last.getLatitude());
                double time = ((point.getTime() == null ? 0 : point.getTime().getTime()) - (last.getTime() == null ? 0 :last.getTime().getTime()))/1000;
                double distEle = point.getElevation() -last.getElevation();
                double dist3d = Math.sqrt(Math.pow(dist,2)+Math.pow(distEle,2));
                double m = 0;
                if(time != 0) {
                	m = (((totalDist + dist3d) - totalDist)/((totalTime + time) - totalTime)) * 3.6;
                }
                
                total3dDist+=dist3d;
                total2dDist+=dist;
                
                totalDist += dist;
                totalTime += time;
                
                if(m > 1){
                    activeTime += time;
                    count++;
                    movingDist += dist;
                    
                    if(time != 0) {
	                    double curSpeed = (dist/time) * 3.6;
	                    
	                    if(curSpeed > maxSpeed) {
	                    	maxSpeed = curSpeed;
	                    }
                    }
                    
                    if(distEle > 0.0){
                    	totalUp += distEle;
                    }
                    else if(distEle < 0.0){
                    	totalDown += distEle;
                    }
                }else {
                	stopTime += time;
                }
            }
            last = point;
        }
        
        return calculateTrackInfo(totalDist,totalTime,movingDist,activeTime,count,maxEle,minEle,totalUp,totalDown,total3dDist,total2dDist,maxSpeed,stopTime,points);
	}
	
	private GPSDTO calculateTrackInfo(double totalDist, double totalTime, double movingDist, double activeTime,
			int count, double maxEle, double minEle, double totalUp, double totalDown, double total3dDist,
			double total2dDist, double maxSpeed, double stopTime, List<Waypoint> points) {
		GPSDTO trackInfoDto = new GPSDTO();
		Pair<Date, Date> startEndDate = parseStartDateAndEndDate(points);
		double totalSpeed = (totalDist/totalTime)*3.6;
        double movingSpeed = (movingDist/activeTime) * 3.6;
        double avgDesti = movingDist / (count / 2);
        trackInfoDto.setMaxElevation(new BigDecimal(maxEle));
        trackInfoDto.setMinElevation(new BigDecimal(minEle));
        trackInfoDto.setUpHill(new BigDecimal(totalUp));
        trackInfoDto.setDownHill(new BigDecimal(Math.abs(totalDown)));
        trackInfoDto.setPointNo(points.size());
        trackInfoDto.setTotalSpeed(new BigDecimal(totalSpeed));
        trackInfoDto.setTotal2dDist(new BigDecimal(total2dDist/1000));
        trackInfoDto.setTotal3dDist(new BigDecimal(total3dDist/1000));
        if(startEndDate != null) {
	        trackInfoDto.setStartTime(dateFormat.format(startEndDate.getFirst()));
	        trackInfoDto.setEndTime(dateFormat.format(startEndDate.getSecond()));
        }
        trackInfoDto.setMaxSpeed(new BigDecimal(maxSpeed * 3.6));
        int hours = (int)Math.round(totalTime) / 3600;
		int minutes = ((int)Math.round(totalTime)  % 3600) / 60;
		int seconds = (int)Math.round(totalTime)  % 60;
		trackInfoDto.setTotalTime(String.format("%02d:%02d:%02d", hours, minutes, seconds));
		int hoursMoving = (int)Math.round(activeTime) / 3600;
		int minutesMoving = ((int)Math.round(activeTime)  % 3600) / 60;
		int secondsMoving = (int)Math.round(activeTime)  % 60;
		trackInfoDto.setTimeMoving(String.format("%02d:%02d:%02d", hoursMoving, minutesMoving, secondsMoving));
		int hoursStop = (int)Math.round(stopTime) / 3600;
		int minutesStop = ((int)Math.round(stopTime)  % 3600) / 60;
		int secondsStop = (int)Math.round(stopTime)  % 60;
		trackInfoDto.setTimeStopped(String.format("%02d:%02d:%02d", hoursStop, minutesStop, secondsStop));
		trackInfoDto.setMovingSpeed(new BigDecimal(movingSpeed));
		trackInfoDto.setAvgDensity(new BigDecimal(avgDesti));
        return trackInfoDto;
	}
	
	private GPS convertGPSDtoToGPS(GPSDTO trackInfoDto, String path, String name, User user) {
		GPS gpsEntity = new GPS();
		gpsEntity.setName(name);
		gpsEntity.setAvgDensity(trackInfoDto.getAvgDensity());
		gpsEntity.setDownHill(trackInfoDto.getDownHill());
		gpsEntity.setEndTime(trackInfoDto.getEndTime());
		gpsEntity.setMaxElevation(trackInfoDto.getMaxElevation());
		gpsEntity.setMaxSpeed(trackInfoDto.getMaxSpeed());
		gpsEntity.setMinElevation(trackInfoDto.getMinElevation());
		gpsEntity.setMovingSpeed(trackInfoDto.getMovingSpeed());
		gpsEntity.setPointNo(trackInfoDto.getPointNo());
		gpsEntity.setStartTime(trackInfoDto.getStartTime());
		gpsEntity.setTimeMoving(trackInfoDto.getTimeMoving());
		gpsEntity.setTimeStopped(trackInfoDto.getTimeStopped());
		gpsEntity.setTotal2dDist(trackInfoDto.getTotal2dDist());
		gpsEntity.setTotal3dDist(trackInfoDto.getTotal3dDist());
		gpsEntity.setTotalSpeed(trackInfoDto.getTotalSpeed());
		gpsEntity.setTotalTime(trackInfoDto.getTotalTime());
		gpsEntity.setUpHill(trackInfoDto.getUpHill());
		gpsEntity.setUrl(path);
		gpsEntity.setUser(user);
		return gpsEntity;
	}
	
	private Pair<Date, Date> parseStartDateAndEndDate(List<Waypoint> list){
		Date startDate = null;
		Date endDate = null;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getTime() != null) {
				startDate = list.get(i).getTime();
				break;
			}
		}
		
		for(int j = list.size() - 1; j > 0; j--) {
			if(list.get(j).getTime() != null) {
				endDate = list.get(j).getTime();
				break;
			}
		}
		
		if(startDate != null && endDate != null) {
			Pair<Date, Date> pairStartEndDate = Pair.of(startDate, endDate);
			return pairStartEndDate;
		}
		return null;
	}
	
	
	public void flushCache() {
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheManager.getCache(cacheName).clear();
        }
    }

}
