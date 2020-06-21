package com.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(
		strategy = GenerationType.AUTO
	)
	private Long id;
	@NotNull
	@Column(unique=true)
	private Long isbn;
	@NotNull
	private String title;
	@NotNull
	private String author;
	@NotNull
	private Double price;
	@NotNull
	private Integer count;
}
