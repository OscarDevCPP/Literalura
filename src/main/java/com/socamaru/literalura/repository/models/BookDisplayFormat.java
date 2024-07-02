package com.socamaru.literalura.repository.models;

import jakarta.persistence.*;

@Entity
@Table(name = "book_display_formats")
public class BookDisplayFormat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "url")
	private String url;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "display_format_id")
	private DisplayFormat displayFormat;
}
