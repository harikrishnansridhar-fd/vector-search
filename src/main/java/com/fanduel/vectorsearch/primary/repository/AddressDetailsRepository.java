package com.fanduel.vectorsearch.primary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fanduel.vectorsearch.primary.entity.PersonDetailsEntity;

@Repository
public interface AddressDetailsRepository extends JpaRepository<PersonDetailsEntity, Long> {

	@Query(value="SELECT id, address_line1 FROM address WHERE id BETWEEN :idStart AND :idEnd", nativeQuery=true)
	List<PersonDetailsEntity> findEntriesForRangeOfUsers( @Param("idStart")  int idStart, @Param("idEnd") int idEnd );
}
