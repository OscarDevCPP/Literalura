package com.socamaru.literalura.repository;

import com.socamaru.literalura.repository.models.Author;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends ListCrudRepository<Author, Long> {
	Optional<Author> findByNameIgnoreCase(String name);
    List<Author> findByBirthYearGreaterThanEqualAndDeathYearLessThanEqual(int birthYear, int deathYear);
}
