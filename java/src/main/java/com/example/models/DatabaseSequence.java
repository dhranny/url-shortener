package com.example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "char_seq")
public class DatabaseSequence {
	
	@Id
	public String id;
	public long seq;

}
