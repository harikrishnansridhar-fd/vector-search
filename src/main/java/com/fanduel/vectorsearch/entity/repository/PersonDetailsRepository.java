package com.fanduel.vectorsearch.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fanduel.vectorsearch.entity.PersonDetailsEntity;

@Repository
public interface PersonDetailsRepository extends CrudRepository<PersonDetailsEntity, Long> {
}
