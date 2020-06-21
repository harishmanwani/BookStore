package com.bookstore.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookNotAvailableException extends RuntimeException {

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = 1L;

	public BookNotAvailableException(String message) {
		super(message);
		log.error(message);
	}
}
