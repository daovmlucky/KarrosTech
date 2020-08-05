package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WayPointDTO {
	private Double ageOfGPSData;
	private String cmt;
	private String desc;
	private Integer gpsStationId;
	private Double elevation;
	private Double geoIdHeight;
	private Double hDop;
	private Double lat;
	private List<LinkDTO> links;
	private Double lon;
	private Double magnetic;
	private String name;
	private Double pDop;
	private Integer sat;
	private String src;
	private String sym;
	private Date time;
	private String type;
	private Double vDop;
	private String fix;
	private ObjectMapper mapper;
	
	public WayPointDTO() {
		super();
		mapper = new ObjectMapper();
	}
	public WayPointDTO(Double ageOfGPSData, String cmt,String desc, Integer gpsStationId, Double elevation, Double geoIdHeight,
			Double hDop, Double lat, List<LinkDTO> links, Double lon, Double magnetic, String name, Double pDop,
			Integer sat, String src, String sym, Date time, String type, Double vDop, String fix) {
		super();
		this.ageOfGPSData = ageOfGPSData;
		this.cmt = cmt;
		this.gpsStationId = gpsStationId;
		this.elevation = elevation;
		this.geoIdHeight = geoIdHeight;
		this.hDop = hDop;
		this.lat = lat;
		this.links = links;
		this.lon = lon;
		this.magnetic = magnetic;
		this.name = name;
		this.pDop = pDop;
		this.sat = sat;
		this.src = src;
		this.sym = sym;
		this.time = time;
		this.type = type;
		this.vDop = vDop;
		this.fix = fix;
		this.desc = desc;
		mapper = new ObjectMapper();
	}
	public Double getAgeOfGPSData() {
		return ageOfGPSData;
	}
	public void setAgeOfGPSData(Double ageOfGPSData) {
		this.ageOfGPSData = ageOfGPSData;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public Integer getGpsStationId() {
		return gpsStationId;
	}
	public void setGpsStationId(Integer gpsStationId) {
		this.gpsStationId = gpsStationId;
	}
	public Double getElevation() {
		return elevation;
	}
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}
	public Double getGeoIdHeight() {
		return geoIdHeight;
	}
	public void setGeoIdHeight(Double geoIdHeight) {
		this.geoIdHeight = geoIdHeight;
	}
	public Double gethDop() {
		return hDop;
	}
	public void sethDop(Double hDop) {
		this.hDop = hDop;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public List<LinkDTO> getLinks() {
		return links;
	}
	public void setLinks(List<LinkDTO> links) {
		this.links = links;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getMagnetic() {
		return magnetic;
	}
	public void setMagnetic(Double magnetic) {
		this.magnetic = magnetic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getpDop() {
		return pDop;
	}
	public void setpDop(Double pDop) {
		this.pDop = pDop;
	}
	public Integer getSat() {
		return sat;
	}
	public void setSat(Integer sat) {
		this.sat = sat;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getSym() {
		return sym;
	}
	public void setSym(String sym) {
		this.sym = sym;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getvDop() {
		return vDop;
	}
	public void setvDop(Double vDop) {
		this.vDop = vDop;
	}
	public String getFix() {
		return fix;
	}
	public void setFix(String fix) {
		this.fix = fix;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String serialize() throws Exception{
        return mapper.writeValueAsString(this);
    }
}
