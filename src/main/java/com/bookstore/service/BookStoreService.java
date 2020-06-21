package com.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.client.MediaCoverageClient;
import com.bookstore.constant.ExceptionConstant;
import com.bookstore.dao.BookStoreDao;
import com.bookstore.entity.Book;
import com.bookstore.enums.PartialSearchType;
import com.bookstore.exception.BookNotAvailableException;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.DuplicateRecordException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookStoreService {
	
	@Autowired
	private BookStoreDao bookDao;
	
	@Autowired
	private MediaCoverageClient mediaCoverageClient;
	
	public Book addBook(Book book) {
		
		Book existingBook = bookDao.searchByISBN(book.getIsbn());
		Book savedBook = null;
		
		if(null == existingBook) {
			
			savedBook = bookDao.addOrUpdateBook(book);
			
		}else if(checkBookDetails(existingBook, book)){
			
			existingBook.setCount(existingBook.getCount() + book.getCount());
			existingBook.setPrice(book.getPrice());
			
			savedBook = bookDao.addOrUpdateBook(existingBook);
		}else {
			
			throw new DuplicateRecordException(ExceptionConstant.BOOK_DEATILS_NOT_MATCHED + " With ISBN :" + book.getIsbn());
		}
		
		return savedBook;
	}
	
	public List<Book> partialSearch(String text, PartialSearchType type){
		
		return bookDao.partailSearch(text, type);
	}
	
	public Book searchViaISBN(Long isbn) {
		
		return bookDao.searchByISBN(isbn);
	}
	
	public List<String> searchMediaCoverage(Long isbn){
		
		Book book = searchViaISBN(isbn);
		
		if(null == book) {
			throw new BookNotFoundException(ExceptionConstant.BOOK_NOT_FOUND +" With ISBN  : "+ isbn);
		}
		
		String title = book.getTitle();
		
		return mediaCoverageClient.checkforMedia(title);
	}

	public Book buyBook(Long isbn) {
		
		Book book = searchViaISBN(isbn);
		
		if(null == book) {
			throw new BookNotFoundException(ExceptionConstant.BOOK_NOT_FOUND +" With ISBN  : "+ isbn);
		}
		
		if(book.getCount() == 0) {
			throw new BookNotAvailableException(ExceptionConstant.BOOK_NOT_AVAILABLE + "With ISBN : "+ isbn);
		}
		
		book.setCount(book.getCount() - 1);
		bookDao.addOrUpdateBook(book);
		
		log.info("Book sold!!!");
		
		return book;
	}
	
	private boolean checkBookDetails(Book exitingBook, Book newBook) {
		
		if(exitingBook.getTitle().equals(newBook.getTitle()) 
				&& exitingBook.getAuthor().equals(newBook.getAuthor())) {
			return true;
		}
		
		return false;
	}
}