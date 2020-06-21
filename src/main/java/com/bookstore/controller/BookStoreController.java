package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entity.Book;
import com.bookstore.enums.PartialSearchType;
import com.bookstore.service.BookStoreService;
import com.bookstore.swagger.IBookStoreController;

@RestController
public class BookStoreController implements IBookStoreController{

	@Autowired
	private BookStoreService bookService;
	
	@Override
	public Book addBook (Book book) {
		
		return bookService.addBook(book);
	}

	@Override
	public List<Book> searchBookByAuthor(String text) {
		
		return bookService.partialSearch(text, PartialSearchType.AUTHOR);
	}

	@Override
	public List<Book> searchBookByTitle(String text) {
		
		return bookService.partialSearch(text, PartialSearchType.TITLE);
	}

	@Override
	public Book searchBookByISBN(Long isbn) {
		
		return bookService.searchViaISBN(isbn);
	}

	@Override
	public List<String> searchMediaCoverage(Long isbn) {
	
		return bookService.searchMediaCoverage(isbn);
	}

	@Override
	public Book buyBook(Long isbn) {
		
		return bookService.buyBook(isbn);
	}
}
