package com.fanduel.vectorsearch.secondary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fanduel.vectorsearch.secondary.entity.PersonDetailsEntity;

public interface PersonDetailsRepository extends CrudRepository<PersonDetailsEntity, Long> {

	@Query(value = "SELECT id,first_name, last_name,city,state,zip,birth_date,address,ssn4,ssn9, embeddings,  (1- (embeddings <=> cast(:embeddings "
			+ "as vector)))"
			+ " * "
			+ "100 as similarity_score FROM person_details_mini_llm order by embeddings <=> cast(:embeddings as vector) limit :n", nativeQuery =
			true)
	List<PersonDetailsEntity> findNNearstNeighbours( @Param("embeddings") float[] embeddings, @Param("n") int n );

	@Query(value = "SELECT id,first_name, last_name,city,state,zip,birth_date,address,ssn4,ssn9, embeddings,(1- (embeddings <=> cast(:embeddings as "
			+ "vector)))"
			+ " * "
			+ "100 as similarity_score FROM person_details_mini_llm where ((1-(embeddings <=> cast(:embeddings as vector)))*100) > :range",
			nativeQuery = true)
	List<PersonDetailsEntity> findRangeItems( @Param("embeddings") float[] embeddings, @Param("range") int range );
}
