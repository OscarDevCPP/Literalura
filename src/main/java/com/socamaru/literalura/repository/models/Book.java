package com.socamaru.literalura.repository.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private Long gutenbergId;

    @ManyToMany
    @JoinTable(
        name = "book_idiom",
        joinColumns = @JoinColumn(name = "idiom_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
	private List<Idiom> idioms;

    @ManyToMany
    @JoinTable(
        name = "author_book",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

	public Book() {
	}

	public Book(Long gutenbergId, String title) {
		this.setGutenbergId(gutenbergId);
		this.setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getGutenbergId() {
		return gutenbergId;
	}

	public void setGutenbergId(Long gutenbergId) {
		this.gutenbergId = gutenbergId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Idiom> getIdioms() {
		return idioms;
	}

	public void setIdioms(List<Idiom> idioms) {
		this.idioms = idioms;
	}

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
