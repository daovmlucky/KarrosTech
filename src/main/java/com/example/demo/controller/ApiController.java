package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.GPSService;
import com.example.demo.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hs.gpxparser.GPXParser;
import com.hs.gpxparser.modal.GPX;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@MultipartConfig(maxFileSize = 1024*1024*1024, maxRequestSize = 1024*1024*1024)
@RequestMapping("/api")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	private static final List<String> contentTypes = Arrays.asList("application/gpx+xml");
	
	@Autowired
	private GPSService gpsService;
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		String fileContentType = file.getContentType();
    	if(contentTypes.contains(fileContentType)) {
    		ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
    		gpsService.saveGPS(username, stream);
    	}
		return null;
		
	}
}
