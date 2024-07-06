package com.socamaru.literalura.repository;


import com.socamaru.literalura.repository.models.Author;
import com.socamaru.literalura.repository.models.AuthorBook;
import com.socamaru.literalura.repository.models.Book;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorBookRepository extends ListCrudRepository<AuthorBook, Long> {
	boolean existsByAuthorAndBook(Author author, Book book);
}
