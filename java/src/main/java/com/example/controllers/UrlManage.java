package com.example.controllers;

import ch.qos.logback.classic.Logger;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.net.URI;
import java.util.List;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.data.UrlMongo;
import com.example.models.DatabaseSequence;
import com.example.models.Url;

@Controller
@RequestMapping
@EnableCaching
public class UrlManage {

	@Autowired
	UrlMongo urlMongo;
	
	@Autowired
	MongoOperations mongoOperations;
	
	Logger log = (Logger) LoggerFactory.getLogger(UrlManage.class);
	
	@GetMapping("/")
	public String get(Model model){
		model.addAttribute("url", new Url());
		return "index.html";
	}
	
	@PostMapping(path = "/url", produces = "application/json")
	public ResponseEntity<Url> postUrl(@RequestBody Url url){
		url.setNewPath(createString());
		url.setId(generateSequence("ddcfcf"));
		url = urlMongo.save(url);
		ResponseEntity<Url> res = new ResponseEntity<Url>(url, HttpStatus.CREATED);
		return res;
	}
	
	@GetMapping
	public ResponseEntity<List<Url>> getAll(){
		return new ResponseEntity<List<Url>>(urlMongo.findAll(), HttpStatus.CREATED);
	}
	
	private String createString() {
		String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcbefghijklmnopqrstuvwxyz";
		char[] charray = new char[5];
		for (int i = 0; i < 5; i++) {
			int randomNum = (int)(Math.random() * model.length());
			charray[i] = model.charAt(randomNum);
		}
		return String.copyValueOf(charray);
	}
	
	public long generateSequence(String seqName) {
		DatabaseSequence counter = mongoOperations.findAndModify(
				query(where("_id").is(seqName)),
				new Update().inc("seq", 1),
				options().returnNew(true).upsert(true),
				DatabaseSequence.class);
		
		System.out.println("Got id as " + counter.seq);
		return counter.seq;
	}
	
	@GetMapping("/{urlString}")
	private ResponseEntity<Void> redirectToUrl(@PathVariable String urlString){
		Url url = getUrl(urlString);
		System.out.println(urlString);
		return ResponseEntity.status(HttpStatus.FOUND)
				.location(URI.create(url.getPath()))
				.build();
	}
	
	@Cacheable(value = "paths")
	private Url getUrl(String url) {
		return urlMongo.findByNewPath(urlString);
	}
	
	
}













