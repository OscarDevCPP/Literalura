package com.socamaru.literalura.repository.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class DisplayFormat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mimeType;

	@OneToMany(mappedBy = "displayFormat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<BookDisplayFormat> bookDisplayFormats;
}
