package com.example.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.models.Url;

public interface UrlMongo extends MongoRepository<Url, Long>{
	
	public Url findByNewPath(String path);
	

}
