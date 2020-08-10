package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.JwtBlacklist;

@Repository
public interface JwtBlacklistDao extends CrudRepository<JwtBlacklist, String>{
	JwtBlacklist findByTokenEquals(String token);
}
