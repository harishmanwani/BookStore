package com.bookstore.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.entity.Book;
import com.bookstore.enums.PartialSearchType;
import com.bookstore.repository.BookRepository;

@Component
public class BookStoreDao {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book addOrUpdateBook(Book newBook) {
		
		return bookRepository.save(newBook);
	}

	public List<Book> partailSearch(String text, PartialSearchType type){
		
		List<Book> bookList = null;
		
		switch(type) {
		
		case AUTHOR :
			bookList = bookRepository.findByAuthorContaining(text);
			break;
		case TITLE : 
			bookList = bookRepository.findByTitleContaining(text);
			break;
		}
		
		return bookList;
	}
	
	public Book searchByISBN(Long isbn) {
		
		return bookRepository.findByIsbn(isbn);
	}

}
