package com.socamaru.literalura.repository.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
		name = "book_author",
		joinColumns = @JoinColumn(name = "author_id"),
		inverseJoinColumns = @JoinColumn(name = "book_id")
	)
	private List<Book> books; ;
}
