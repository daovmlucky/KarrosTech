package com.example.demo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    @JsonIgnore
    private String password;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
        		cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GPS> gpss;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Set<GPS> getGpss() {
		return gpss;
	}

	public void setGpss(Set<GPS> gpss) {
		this.gpss = gpss;
		for (GPS gps : gpss) {
			gps.setUser(this);
		}
	}
    
    

}
