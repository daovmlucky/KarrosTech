package com.example.demo.domain;

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
public class GPS {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;
	
	@Column(name = "name",nullable = true)
    private String name;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
	
	@Column(name = "metadata",nullable = true)
    private String metadata;
	
	@Column(name = "waypoint",nullable = true)
    private String waypoint;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="user_id", referencedColumnName = "id")
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true, name ="gps_detail_id")
	private GPSDetail gps_detail;

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

	public GPSDetail getGpsDetail() {
		return gps_detail;
	}

	public void setGpsDetail(GPSDetail gpsDetail) {
		this.gps_detail = gpsDetail;
	}

	public GPS(String name, Date creationDate, GPSDetail gpsDetail) {
		super();
		this.name = name;
		this.creationDate = creationDate;
		this.gps_detail = gpsDetail;
		this.gps_detail.setGps(this);
	}
	
	
	
}
