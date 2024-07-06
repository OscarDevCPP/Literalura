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

	@Transient
	private List<String> languages;

	public Book() {
	}

	public Book(Long gutenbergId, String title, List<String> languages) {
		this.setGutenbergId(gutenbergId);
		this.setTitle(title);
		this.setLanguages(languages);
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

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

}
