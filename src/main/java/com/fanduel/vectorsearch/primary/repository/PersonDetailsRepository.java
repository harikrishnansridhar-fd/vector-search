package com.fanduel.vectorsearch.primary.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fanduel.vectorsearch.primary.entity.PersonDetailsEntity;

@Repository
public interface PersonDetailsRepository extends CrudRepository<PersonDetailsEntity, Long> {
}
