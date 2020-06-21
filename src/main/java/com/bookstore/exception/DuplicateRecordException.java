package com.bookstore.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicateRecordException extends RuntimeException{

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateRecordException(String message) {
		super(message);
		log.error(message);
	}
}
