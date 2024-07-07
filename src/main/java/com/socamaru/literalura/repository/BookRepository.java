package com.socamaru.literalura.repository;

import com.socamaru.literalura.repository.models.Book;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {
	Optional<Book> findByGutenbergId(Long gutenbergId);
}
