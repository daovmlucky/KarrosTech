package com.example.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataDTO {
	private AuthorDTO author;
	private BoundDTO bound;
	private String desc;
	private String keyWord;
	private String name;
	private Date time;
	private ObjectMapper mapper;
	
	public MetadataDTO() {
		super();
		mapper = new ObjectMapper();
	}
	public MetadataDTO(AuthorDTO author, BoundDTO bound, String desc, String keyWord, String name, Date time) {
		super();
		this.author = author;
		this.bound = bound;
		this.desc = desc;
		this.keyWord = keyWord;
		this.name = name;
		this.time = time;
		mapper = new ObjectMapper();
	}
	public AuthorDTO getAuthor() {
		return author;
	}
	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	public BoundDTO getBound() {
		return bound;
	}
	public void setBound(BoundDTO bound) {
		this.bound = bound;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public ObjectMapper getMapper() {
		return mapper;
	}
	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	public String serialize() throws Exception{
        return mapper.writeValueAsString(this);
    }
	
}
