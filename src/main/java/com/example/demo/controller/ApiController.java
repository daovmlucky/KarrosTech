package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.domain.GPS;
import com.example.demo.service.AmazonClient;
import com.example.demo.service.GPSService;
import com.example.demo.service.JwtBlacklistService;
import com.example.demo.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hs.gpxparser.GPXParser;
import com.hs.gpxparser.modal.GPX;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@MultipartConfig(maxFileSize = 1024*1024*1024, maxRequestSize = 1024*1024*1024)
@RequestMapping("/api")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    private static final DecimalFormat twoDForm = new DecimalFormat("#.##");
	private static final List<String> contentTypes = Arrays.asList("application/gpx+xml");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private GPSService gpsService;
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private JwtBlacklistService jwtBlacklistService;
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
		ObjectNode jsonResponse = JsonResponse.getDefaultResponse();
		try {
			String path = this.amazonClient.uploadFile(file);
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    	if(contentTypes.contains(file.getContentType())) {
	    		ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
	    		if(gpsService.saveGPS(username, stream, path,name)) {
	    			jsonResponse.put(JsonResponse.KEY_MESSAGE, "Successful to upload file");
		            return new ResponseEntity<>(jsonResponse,HttpStatus.OK);
	    		}else {
	    			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    		}
	    	}else {
	    		jsonResponse.put(JsonResponse.KEY_ERROR_MESSAGE, "Only upload file (*.gpx)");
	            return new ResponseEntity<>(jsonResponse,HttpStatus.NO_CONTENT);
	    	}
		}catch (Exception e) {
			logger.error("Failed to uploead file,exception: ",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
    @RequestMapping(value = "/latestTrack", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ObjectNode> getlatestTrackGPS() {
    	try {
	        List<GPS> allGPS = gpsService.getlatestTrack();
	        if (allGPS.isEmpty()) {
	        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        ObjectNode dataObject = JsonNodeFactory.instance.objectNode();
	        ArrayNode gpsArray = JsonNodeFactory.instance.arrayNode();
	        for (GPS gps : allGPS) {
	        	ObjectNode gpsObj = JsonNodeFactory.instance.objectNode();
	        	gpsObj.put("id", gps.getId());
	        	gpsObj.put("name", gps.getName() != null ? gps.getName() : "");
	        	gpsObj.put("created_date",dateFormat.format(gps.getCreationDate()));
//	        	String[] arr = gps.getUrl().split("\\/");
//	        	ByteArrayOutputStream byteArray = this.amazonClient.downloadFile(arr[arr.length - 1]);
	        	gpsObj.put("link", gps.getUrl());
//	        	gpsObj.put("data", byteArray.toByteArray());
	        	gpsObj.put("author", gps.getUser().getUsername());
	        	gpsArray.add(gpsObj);
			}
	        dataObject.set("latestTrack", gpsArray);
	        return new ResponseEntity<>(dataObject, HttpStatus.OK);
    	}catch (Exception e) {
    		logger.error("Failed to get latest track,exception: ",e);
    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping("/track/{id}")
    public ResponseEntity<ObjectNode> getDetailTrackById(@PathVariable("id") long id) {
    	try {
	    	GPS gpsData = gpsService.findById(id);
	    	if(gpsData == null) {
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}else {
	    		ObjectNode dataObject = buildTrackDetail(gpsData);
	    		return new ResponseEntity<>(dataObject, HttpStatus.OK);
	    	}
    	}catch (Exception e) {
    		logger.error("Failed to get track with id {},exception: ",id,e);
    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
    	try {
	    	jwtBlacklistService.expiredCurrentToken();
	    	return new ResponseEntity<>("Logout successful",HttpStatus.OK);
    	}catch (Exception e) {
    		logger.error("Failed to logout,exception: ",e);
    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    private ObjectNode buildTrackDetail(GPS gpsData) {
    	ObjectNode ruleObj = JsonNodeFactory.instance.objectNode();
        ruleObj.put("created_date", dateFormat.format(gpsData.getCreationDate()));
        ruleObj.put("author", gpsData.getUser().getUsername());
        ruleObj.put("link", gpsData.getUrl());
        ObjectNode trackInfo = JsonNodeFactory.instance.objectNode();
        trackInfo.put("2dLength", twoDForm.format(gpsData.getTotal2dDist()));
        trackInfo.put("3dLength", twoDForm.format(gpsData.getTotal3dDist()));
        trackInfo.put("total_speed", twoDForm.format(gpsData.getTotalSpeed()));
        trackInfo.put("moving_speed", twoDForm.format(gpsData.getMovingSpeed()));
        trackInfo.put("total_time", gpsData.getTotalTime());
        trackInfo.put("max_speed", twoDForm.format(gpsData.getMaxSpeed()));
        trackInfo.put("time_stopped", gpsData.getTimeStopped());
        trackInfo.put("time_moving", gpsData.getTimeMoving());
        trackInfo.put("start_time", gpsData.getStartTime());
        trackInfo.put("end_time", gpsData.getEndTime());
        trackInfo.put("point_no", gpsData.getPointNo());
        trackInfo.put("avg_density", twoDForm.format(gpsData.getAvgDensity()));
        trackInfo.put("uphill", twoDForm.format(gpsData.getUpHill()));
        trackInfo.put("downhill", twoDForm.format((gpsData.getDownHill())));
        trackInfo.put("max_elevation", twoDForm.format((gpsData.getMaxElevation())));
        trackInfo.put("min_elevation", twoDForm.format((gpsData.getMinElevation())));
        ruleObj.set("track_info", trackInfo);
		return ruleObj;
	}
}
