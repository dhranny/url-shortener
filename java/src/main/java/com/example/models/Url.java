package com.example.models;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
public class Url {

	@org.springframework.data.annotation.Id
	private long id;
	public void setId(long id) {
		this.id = id;
	}
	public long getId(){
		return id;
	}
	
	private String path;
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath(){
		return path;
	}
	

	private String newPath;
	public void setNewPath(String newPath) {
		this.newPath = newPath;
	}
	
	public String getNewPath(){
		return newPath;
	}
	
	
}
