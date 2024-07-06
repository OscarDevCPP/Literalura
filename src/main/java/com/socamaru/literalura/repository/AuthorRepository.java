package com.socamaru.literalura.repository;

import com.socamaru.literalura.repository.models.Author;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends ListCrudRepository<Author, Long> {
	Optional<Author> findByNameIgnoreCase(String name);
	boolean existsByNameContainsIgnoreCase(String name);
}
