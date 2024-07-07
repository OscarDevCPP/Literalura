package com.socamaru.literalura.repository.models;

import java.util.List;

public record BookDTO(
	Long id,
	String title,
	List<AuthorDTO> authors,
	List<String> languages,
	Long downloadCount
){

	@Override
	public String toString() {
		return String.format("%d_%s_%d", id, title, downloadCount);
	}

}

