package com.socamaru.literalura.repository;

import com.socamaru.literalura.repository.models.Idiom;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomRepository extends ListCrudRepository<Idiom, Long> {
    Optional<Idiom> findByCode(String code);
}
