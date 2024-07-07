package com.socamaru.literalura.repository;

import com.socamaru.literalura.repository.models.Idiom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomRepository extends ListCrudRepository<Idiom, Long> {
    Optional<Idiom> findByCode(String code);

    @Query("SELECT i FROM Idiom i LEFT JOIN FETCH i.books WHERE i.code = :code")
    Optional<Idiom> findByCodeWithBooks(@Param("code") String code);
}
