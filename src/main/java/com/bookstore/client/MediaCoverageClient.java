package com.bookstore.client;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bookstore.model.Media;

@Component
public class MediaCoverageClient {
	
	private final String URL = "https://jsonplaceholder.typicode.com/posts";
	
	@Autowired
	private RestTemplate restTemplate;
	
	private List<Media> mediaList;
	
	synchronized public List<String> checkforMedia(String title) {
		
		if(null == mediaList) {
			
			ResponseEntity<Media[]> responseEntity = restTemplate.getForEntity(URL, Media[].class);
			
			mediaList = Arrays.asList(responseEntity.getBody());
		}
		
		return mediaList.parallelStream()
				.filter(x -> x.getTitle().contains(title) || x.getBody().contains(title))
				.map(a -> a.getTitle())
				.collect(Collectors.toList());
	}

}
