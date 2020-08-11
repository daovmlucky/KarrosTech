package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.GPS;

@Repository
public interface GPSDao extends PagingAndSortingRepository<GPS, Long>{
	@Query("SELECT COUNT(gps) FROM GPS gps")
    Integer countItemInGPS();
	
	@Query("SELECT gps FROM GPS gps ORDER BY gps.creationDate DESC")
	List<GPS> getLatestGPS();
	
}
