package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.example.demo.dao.GPSDao;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.BoundDTO;

import com.example.demo.dto.LinkDTO;
import com.example.demo.dto.MetadataDTO;
import com.example.demo.dto.MovingAverage;
import com.example.demo.dto.TimeHolder;
import com.example.demo.dto.TrackInfoDto;
import com.example.demo.dto.WayPointDTO;
import com.example.demo.utility.GPXParserUtility;
import com.example.demo.utility.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hs.gpxparser.GPXParser;
import com.hs.gpxparser.modal.GPX;
import com.hs.gpxparser.modal.Link;
import com.hs.gpxparser.modal.Metadata;
import com.hs.gpxparser.modal.Person;
import com.hs.gpxparser.modal.Track;
import com.hs.gpxparser.modal.TrackSegment;
import com.hs.gpxparser.modal.Waypoint;
import com.hs.gpxparser.type.Fix;





@Service
public class GPSService implements IGPSService{
	
	private static final Logger logger = LoggerFactory.getLogger(GPSService.class);
    private static final DecimalFormat twoDForm = new DecimalFormat("#.##");
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private ObjectMapper mapper = new ObjectMapper(); 
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GPSDao gpsDao;
	
	@Override
	public void saveGPS(String username, ByteArrayInputStream in) {
		try {
			User user = userDao.findByUsername(username);

			//GPXParser p = new GPXParser();
			GPX gpx = GPXParserUtility.parseGPX(in);
			
			if(gpx != null) {
				
				Pair<Date, Date> startEndDate = parseStartDateAndEndDate(gpx);
				
				List<Waypoint> lstWayPoints = new ArrayList<>();
				gpx.getTracks().stream().forEach(track -> {
					track.getTrackSegments().forEach(trackSegMent ->{
						lstWayPoints.addAll(trackSegMent.getWaypoints());
					});
				});
		        
				TrackInfoDto trackInfoDto = parseTrackInfo(lstWayPoints,startEndDate);
		        TimeHolder timeHolder = TimeComputer.generateTimes(lstWayPoints);

			}


		}catch (Exception e) {
			logger.error("Failed to save gps,exception ",e);
		}
	}
	
	private TrackInfoDto parseTrackInfo(List<Waypoint> points,Pair<Date, Date> startEndDate) {
		TrackInfoDto trackInfoDto = new TrackInfoDto();
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
                double time = (point.getTime().getTime()-last.getTime().getTime())/1000;
                double distEle = point.getElevation() -last.getElevation();
                double dist3d = Math.sqrt(Math.pow(dist,2)+Math.pow(distEle,2));
                double m = (((totalDist + dist3d) - totalDist)/((totalTime + time) - totalTime)) * 3.6;
                
                total3dDist+=dist3d;
                total2dDist+=dist;
                
                totalDist += dist;
                totalTime += time;
                
                if(m > 1){
                    activeTime += time;
                    count++;
                    movingDist += dist;
                    
                    double curSpeed = (dist/time) * 3.6;
                    
                    if(curSpeed > maxSpeed) {
                    	maxSpeed = curSpeed;
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
        
        double totalSpeed = (totalDist/totalTime)*3.6;
        double movingSpeed = (movingDist/activeTime) * 3.6;
        double avgDesti = movingDist / (count / 2);
        trackInfoDto.setMaxElevation(maxEle);
        trackInfoDto.setMinElevation(minEle);
        trackInfoDto.setUpHill(totalUp);
        trackInfoDto.setDownHill(Math.abs(totalDown));
        trackInfoDto.setPointNo(points.size());
        trackInfoDto.setTotalSpeed(totalSpeed);
        trackInfoDto.setTotal2dDist((total2dDist/1000));
        trackInfoDto.setTotal3dDist((total3dDist/1000));
        trackInfoDto.setStartTime(dateFormat.format(startEndDate.getFirst()));
        trackInfoDto.setEndTime(dateFormat.format(startEndDate.getSecond()));
        trackInfoDto.setMaxSpeed(maxSpeed * 3.6);
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
		trackInfoDto.setMovingSpeed(movingSpeed);
		trackInfoDto.setAvgDensity(avgDesti);
        return trackInfoDto;
	}
	
	private Pair<Date, Date> parseStartDateAndEndDate(GPX gpx){
		Date startDate = gpx.getTracks().stream().findFirst().get().getTrackSegments().get(0).getWaypoints().get(0).getTime();
		
		Track[] trackArr = gpx.getTracks().toArray(new Track[gpx.getTracks().size()]);
		int numTracks = gpx.getTracks().size()-1;
        int numSegmentsInLastTrack = trackArr[numTracks].getTrackSegments().size()-1;
        List<Waypoint> lstWayPoints = trackArr[numTracks].getTrackSegments().get(numSegmentsInLastTrack).getWaypoints();
        int pointNo = lstWayPoints.size();
        int numPtsInLastSegment = pointNo -1;
        Date endDate = lstWayPoints.get(numPtsInLastSegment).getTime();
        
        Pair<Date, Date> pairStartEndDate = Pair.of(startDate, endDate);
        
        return pairStartEndDate;
	}
	
	

}
