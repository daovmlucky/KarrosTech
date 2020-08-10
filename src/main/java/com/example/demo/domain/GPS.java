package com.example.demo.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "gps")
public class GPS{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;
	
	@Column(name = "name",nullable = true)
    private String name;
	
    @Column(name = "creation_date", nullable = true)
    private Date creationDate;
	
	@Column(name = "total2d_dist",nullable = true)
	private BigDecimal total2dDist;
	
	@Column(name = "total3d_dist",nullable = true)
	private BigDecimal total3dDist;
	
	@Column(name = "total_speed",nullable = true)
	private BigDecimal totalSpeed;
	
	@Column(name = "moving_speed",nullable = true)
	private BigDecimal movingSpeed;
	
	@Column(name = "max_speed",nullable = true)
	private BigDecimal maxSpeed;
	
	@Column(name = "total_time",nullable = true)
	private String totalTime;
	
	@Column(name = "time_stopped",nullable = true)
	private String timeStopped;
	
	@Column(name = "time_moving",nullable = true)
	private String timeMoving;
	
	@Column(name = "start_time",nullable = true)
	private String startTime;
	
	@Column(name = "end_time",nullable = true)
	private String endTime;
	
	@Column(name = "url",nullable = true)
	private String url;
	
	@Column(name = "point_no",nullable = true)
	private Integer pointNo;
	
	@Column(name = "avg_density",nullable = true)
	private BigDecimal avgDensity;
	
	@Column(name = "up_hill",nullable = true)
	private BigDecimal upHill;
	
	@Column(name = "down_hill",nullable = true)
	private BigDecimal downHill;
	
	@Column(name = "max_elevation",nullable = true)
	private BigDecimal maxElevation;
	
	@Column(name = "min_elevation",nullable = true)
	private BigDecimal minElevation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="user_id", referencedColumnName = "id")
	private User user;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		user.getGpss().add(this);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GPS() {
		super();
	}

	public GPS(String name, Date creationDate, BigDecimal total2dDist, BigDecimal total3dDist, BigDecimal totalSpeed, BigDecimal movingSpeed,
			BigDecimal maxSpeed, String totalTime, String timeStopped, String timeMoving, String startTime,
			String endTime, Integer pointNo, BigDecimal avgDensity, BigDecimal upHill, BigDecimal downHill,
			BigDecimal maxElevation, BigDecimal minElevation, User user,String url) {
		super();
		this.name = name;
		this.creationDate = creationDate;
		this.total2dDist = total2dDist;
		this.total3dDist = total3dDist;
		this.totalSpeed = totalSpeed;
		this.movingSpeed = movingSpeed;
		this.maxSpeed = maxSpeed;
		this.totalTime = totalTime;
		this.timeStopped = timeStopped;
		this.timeMoving = timeMoving;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pointNo = pointNo;
		this.avgDensity = avgDensity;
		this.upHill = upHill;
		this.downHill = downHill;
		this.maxElevation = maxElevation;
		this.minElevation = minElevation;
		this.user = user;
		this.url = url;
	}
	
}
