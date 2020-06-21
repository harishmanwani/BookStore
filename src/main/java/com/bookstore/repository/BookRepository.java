package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findByTitleContaining(String text);
	List<Book> findByAuthorContaining(String text);
	Book findByIsbn(Long isbn);
	
}
