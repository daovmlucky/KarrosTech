package com.example.demo.dto;

import java.math.BigDecimal;

public class GPSDTO {
	private BigDecimal total2dDist;
	private BigDecimal total3dDist;
	private BigDecimal totalSpeed;
	private BigDecimal movingSpeed;
	private BigDecimal maxSpeed;
	private String totalTime;
	private String timeStopped;
	private String timeMoving;
	private String startTime;
	private String endTime;
	private Integer pointNo;
	private BigDecimal avgDensity;
	private BigDecimal upHill;
	private BigDecimal downHill;
	private BigDecimal maxElevation;
	private BigDecimal minElevation;
	
	public GPSDTO() {
		super();
	}

	public BigDecimal getTotal2dDist() {
		return total2dDist;
	}

	public void setTotal2dDist(BigDecimal total2dDist) {
		this.total2dDist = total2dDist;
	}

	public BigDecimal getTotal3dDist() {
		return total3dDist;
	}

	public void setTotal3dDist(BigDecimal total3dDist) {
		this.total3dDist = total3dDist;
	}

	public BigDecimal getTotalSpeed() {
		return totalSpeed;
	}

	public void setTotalSpeed(BigDecimal totalSpeed) {
		this.totalSpeed = totalSpeed;
	}

	public BigDecimal getMovingSpeed() {
		return movingSpeed;
	}

	public void setMovingSpeed(BigDecimal movingSpeed) {
		this.movingSpeed = movingSpeed;
	}

	public BigDecimal getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(BigDecimal maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getTimeStopped() {
		return timeStopped;
	}

	public void setTimeStopped(String timeStopped) {
		this.timeStopped = timeStopped;
	}

	public String getTimeMoving() {
		return timeMoving;
	}

	public void setTimeMoving(String timeMoving) {
		this.timeMoving = timeMoving;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getPointNo() {
		return pointNo;
	}

	public void setPointNo(Integer pointNo) {
		this.pointNo = pointNo;
	}

	public BigDecimal getAvgDensity() {
		return avgDensity;
	}

	public void setAvgDensity(BigDecimal avgDensity) {
		this.avgDensity = avgDensity;
	}

	public BigDecimal getUpHill() {
		return upHill;
	}

	public void setUpHill(BigDecimal upHill) {
		this.upHill = upHill;
	}

	public BigDecimal getDownHill() {
		return downHill;
	}

	public void setDownHill(BigDecimal downHill) {
		this.downHill = downHill;
	}

	public BigDecimal getMaxElevation() {
		return maxElevation;
	}

	public void setMaxElevation(BigDecimal maxElevation) {
		this.maxElevation = maxElevation;
	}

	public BigDecimal getMinElevation() {
		return minElevation;
	}

	public void setMinElevation(BigDecimal minElevation) {
		this.minElevation = minElevation;
	}

	
	
	
}
