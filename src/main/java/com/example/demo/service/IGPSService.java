package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

public interface IGPSService {
	void saveGPS(String username,ByteArrayInputStream in);
}
