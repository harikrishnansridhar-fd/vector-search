package com.fanduel.vectorsearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fanduel.vectorsearch.entity.PersonDetailsEntity;
import com.fanduel.vectorsearch.entity.repository.PersonDetailsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseTests {

	@Autowired
	private PersonDetailsRepository personDetailsRepository;

	@Autowired
	private TextEmbedder textEmbedder;

	@Autowired
	private CosineSimilaritySearcher cosineSimilaritySearcher;

	@BeforeAll
	public void setUp() throws URISyntaxException, IOException {
		//createDatabaseEntries();
	}

	private void createDatabaseEntries() throws URISyntaxException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<PersonDetailsEntity> personDetailsEntities =
				objectMapper.readValue( new String( Files.readAllBytes( Paths.get( ClassLoader.getSystemResource( "dataSample.json" ).toURI() ) ) ),
						new TypeReference<List<PersonDetailsEntity>>() {
						} );
		personDetailsEntities.forEach( personDetailsEntity -> {
			try {
				personDetailsEntity.setEmbeddings( objectMapper.writeValueAsString( textEmbedder.embed( personDetailsEntity.getAddress() ) ) );
			} catch ( JsonProcessingException e ) {
				throw new RuntimeException( e );
			}
			personDetailsRepository.save( personDetailsEntity );
		} );
	}

	@Test
	public void givenQuery_whenSearched_thenResultIsReturned() {

		final Map<Long,PersonDetailsEntity> pdes =
				StreamSupport.stream( personDetailsRepository.findAll().spliterator(), false )
						.collect( Collectors.toMap( PersonDetailsEntity::getId, pde -> pde ) );
		var addresses = pdes.values().stream().map( PersonDetailsEntity::getAddress ).collect( Collectors.toList() );
		var query = "11 Lee street";
		var result = cosineSimilaritySearcher.cosineSimilarity( query, addresses);
		assertTrue(result.size() > 2);
		System.out.println("###### Output value #####\n");
		result.stream().sorted( Comparator.comparing( SearchResult::getSimilarity )).filter( res -> res.getSimilarity() >= 0 ).forEach( res -> {
			System.out.println( "Address: " + res.getAddress() + ",Similarity: " + res.getSimilarity());
		} );
		System.out.println("###### EOO #####\n");
	}

}
