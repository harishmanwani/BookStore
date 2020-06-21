package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bookstore.client.MediaCoverageClient;
import com.bookstore.dao.BookStoreDao;
import com.bookstore.entity.Book;
import com.bookstore.enums.PartialSearchType;
import com.bookstore.exception.BookNotAvailableException;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.DuplicateRecordException;

@RunWith(MockitoJUnitRunner.class)
public class BookStoreServiceTest {
	
	@Mock
	private BookStoreDao bookStoreDao;
	
	@Mock
	private MediaCoverageClient mediaCoverageClient;
	
	@InjectMocks
	private BookStoreService bookStoreService;
	
	private Book input;
	private Book alreadyStoredRecord;
	
	@Before
	public void setup() {
		
		initializeInputs();
	}
	
	@Test
	public void testAddBookForNewBookScenario() {
		
		Mockito.when(bookStoreDao.searchByISBN(input.getIsbn())).thenReturn(null);
		Mockito.when(bookStoreDao.addOrUpdateBook(input)).thenReturn(input);
		
		Book response = bookStoreService.addBook(input);
		
		assertEquals(input, response);
	}
	
	@Test
	public void testAddBookForExistingBookWithProperDetails() {
		
		Mockito.when(bookStoreDao.searchByISBN(input.getIsbn())).thenReturn(input);
		Mockito.when(bookStoreDao.addOrUpdateBook(input)).thenReturn(input);
		
		Book response = bookStoreService.addBook(input);
		
		assertEquals(input.getCount(), response.getCount());
		
		input.setCount(1);
	}
	
	@Test(expected = DuplicateRecordException.class)
	public void testAddBookForExistingBookWithNoProperDetails() {
		
		alreadyStoredRecord.setAuthor("Test Another Author");
		Mockito.when(bookStoreDao.searchByISBN(input.getIsbn())).thenReturn(alreadyStoredRecord);
		
		Book response = bookStoreService.addBook(input);
		
		assertEquals(input.getCount(), response.getCount());
		
		input.setCount(1);
		alreadyStoredRecord.setAuthor("Test Author");
	}
	
	@Test
	public void testPartialSearchForAuthor() {
		
		List<Book> list = new ArrayList<>();
		list.add(input);
		String inputText = "Test";
		PartialSearchType inputSearchType = PartialSearchType.AUTHOR;
		
		Mockito.when(bookStoreDao.partailSearch(inputText, inputSearchType)).thenReturn(list);
		
		List<Book> response = bookStoreService.partialSearch(inputText, inputSearchType);
		
		assertEquals(list, response);
	}
	
	@Test
	public void testPartialSearchForTitle() {
		
		List<Book> list = new ArrayList<>();
		list.add(input);
		String inputText = "Test";
		PartialSearchType inputSearchType = PartialSearchType.TITLE;
		
		Mockito.when(bookStoreDao.partailSearch(inputText, inputSearchType)).thenReturn(list);
		
		List<Book> response = bookStoreService.partialSearch(inputText, inputSearchType);
		
		assertEquals(list, response);
	}
	
	@Test
	public void testSearchByISBN() {
		
		Long inputText = 1L;
		
		Mockito.when(bookStoreDao.searchByISBN(inputText)).thenReturn(input);
		
		Book response = bookStoreService.searchViaISBN(inputText);
		
		assertEquals(input, response);
	}
	
	@Test
	public void testSearchMediaCoverageForExistingBook() {
		
		Long inputISBN = 1L;
		List<String> outputFromMediaCoverage = new ArrayList<>();
		outputFromMediaCoverage.add("Test Media Title");
		
		
		Mockito.when(bookStoreDao.searchByISBN(inputISBN)).thenReturn(input);
		Mockito.when(mediaCoverageClient.checkforMedia(input.getTitle())).thenReturn(outputFromMediaCoverage);
		
		List<String> response = bookStoreService.searchMediaCoverage(inputISBN);
		
		assertEquals(outputFromMediaCoverage, response);
	}
	
	@Test(expected = BookNotFoundException.class)
	public void testSearchMediaCoverageForNonExistingBook() {
		
		Long inputISBN = 1L;
		
		Mockito.when(bookStoreDao.searchByISBN(inputISBN)).thenReturn(null);
		
		bookStoreService.searchMediaCoverage(inputISBN);
	}
	
	@Test
	public void testBuyBookForExistingBook() {
		
		Long inputISBN = 1L;
		int expectedCount = input.getCount() - 1;
		
		Mockito.when(bookStoreDao.searchByISBN(inputISBN)).thenReturn(input);
		
		Book response = bookStoreService.buyBook(inputISBN);
		
		assertEquals(expectedCount, response.getCount());
		input.setCount(1);
	}
	
	@Test (expected = BookNotFoundException.class)
	public void testBuyBookForNonExistingBook() {
		
		Long inputISBN = 1L;
		
		Mockito.when(bookStoreDao.searchByISBN(inputISBN)).thenReturn(null);
		
		bookStoreService.buyBook(inputISBN);
	}
	
	@Test (expected = BookNotAvailableException.class)
	public void testBuyBookForExistingBookWithCountZero() {
		
		Long inputISBN = 1L;
		input.setCount(0);
		
		Mockito.when(bookStoreDao.searchByISBN(inputISBN)).thenReturn(input);
		
		bookStoreService.buyBook(inputISBN);
		
		input.setCount(1);
	}
	
	private void initializeInputs() {
		
		input = new Book();
		input.setId(1L);
		input.setIsbn(1L);
		input.setAuthor("Test Author");
		input.setPrice(1.0);
		input.setTitle("Test Title");
		input.setCount(1);
		
		alreadyStoredRecord = new Book();
		alreadyStoredRecord.setId(1L);
		alreadyStoredRecord.setIsbn(1L);
		alreadyStoredRecord.setAuthor("Test Author");
		alreadyStoredRecord.setPrice(1.0);
		alreadyStoredRecord.setTitle("Test Title");
		alreadyStoredRecord.setCount(1);
	}

}
