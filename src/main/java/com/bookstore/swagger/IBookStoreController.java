package com.bookstore.swagger;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.entity.Book;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "BookStore" }, description = "Book Store Functionality")
@RequestMapping("/")
public interface IBookStoreController {
	
	@ApiOperation(value = "This API is to add new book to the Book Store", notes = "Add Book")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Book added successfully"),
			@ApiResponse(code = 403, message = "Access Forbbiden"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping("/book/add")
	public Book addBook(@RequestBody Book book);
	
	@ApiOperation(value = "This API enable to search partially on Author", notes = "Search Book By Author")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "API executed successfully"),
			@ApiResponse(code = 403, message = "Access Forbbiden"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/search/author/{text}")
	public List<Book> searchBookByAuthor(@PathVariable String text); 
	
	@ApiOperation(value = "This API enable to search partially on Title", notes = "Search Book By Title")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "API executed successfully"),
			@ApiResponse(code = 403, message = "Access Forbbiden"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/search/title/{text}")
	public List<Book> searchBookByTitle(@PathVariable String text);
	
	@ApiOperation(value = "This API enable to search by ISBN number", notes = "Search Book By ISBN")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "API executed successfully"),
			@ApiResponse(code = 403, message = "Access Forbbiden"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/search/isbn/{isbn}")
	public Book searchBookByISBN(@PathVariable Long isbn);
	
	@ApiOperation(value = "This API enable to search media coverage about a book", notes = "Search Media Coverage")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "API executed successfully"),
			@ApiResponse(code = 403, message = "Access Forbbiden"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/search/media/{isbn}")
	public List<String> searchMediaCoverage(@PathVariable Long isbn);
	
	@ApiOperation(value = "This API enable to buy a book", notes = "Buy Book")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "API executed successfully"),
			@ApiResponse(code = 403, message = "Access Forbbiden"),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping("/book/buy/{isbn}")
	public Book buyBook(@PathVariable Long isbn);
}
