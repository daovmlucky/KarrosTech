package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.dao.JwtBlacklistDao;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.JwtBlacklist;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;

@Service
public class JwtBlacklistService {
	
	@Autowired
	private JwtBlacklistDao jwtBlacklistDao;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public void expiredCurrentToken() {
		String token = jwtTokenUtil.getToken();
		JwtBlacklist jwtBlacklist = new JwtBlacklist();
		jwtBlacklist.setToken(token);
		jwtBlacklistDao.save(jwtBlacklist);
	}
	
	public JwtBlacklist findByTokenEquals(String token) {
		return jwtBlacklistDao.findByTokenEquals(token);
	}
}
