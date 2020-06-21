package com.bookstore.model;

import lombok.Data;

@Data
public class Media {
	
	private Long userId;
	private Long id;
	private String title;
	private String body;

}
