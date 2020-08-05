package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gps_detail")
public class GPSDetail {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;
	
	@Column(name = "metadata",nullable = true)
    private String metadata;
	
	@Column(name = "waypoint",nullable = true)
    private String waypoint;
	
	@Column(name = "track",nullable = true)
    private String track;
	
	@OneToOne(mappedBy = "gps_detail")
	private GPS gps;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getWaypoint() {
		return waypoint;
	}

	public void setWaypoint(String waypoint) {
		this.waypoint = waypoint;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public GPSDetail(String metadata, String waypoint, String track) {
		super();
		this.metadata = metadata;
		this.waypoint = waypoint;
		this.track = track;
	}
	
	
}
