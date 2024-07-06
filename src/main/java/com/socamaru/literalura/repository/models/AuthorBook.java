package com.socamaru.literalura.repository.models;

import jakarta.persistence.*;

@Entity
public class AuthorBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public AuthorBook() {
	}

	public AuthorBook(Author author, Book book) {
		this.author = author;
		this.book = book;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
