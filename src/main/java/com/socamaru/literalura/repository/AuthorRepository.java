package com.socamaru.literalura.repository;

import com.socamaru.literalura.repository.models.Author;
import com.socamaru.literalura.repository.models.Idiom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends ListCrudRepository<Author, Long> {
	Optional<Author> findByNameIgnoreCase(String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND a.deathYear >= :year")
    List<Author> findAuthorsAliveInYear(@Param("year") int year);
}
