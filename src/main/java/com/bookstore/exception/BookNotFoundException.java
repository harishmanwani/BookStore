package com.bookstore.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookNotFoundException extends RuntimeException{

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException(String message) {
		super(message);
		log.error(message);
	}
}
