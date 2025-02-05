package com.fanduel.vectorsearch;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fanduel.vectorsearch.secondary.entity.PersonDetailsEntity;
import com.fanduel.vectorsearch.secondary.repository.PersonDetailsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostGresSQLDatabaseTests {
	@Autowired
	private PersonDetailsRepository personDetailsRepository;

	@Autowired
	private TextEmbedder textEmbedder;

	@BeforeAll
	public void setUp() throws URISyntaxException, IOException {
		createDatabaseEntries();
	}

	private void createDatabaseEntries() throws URISyntaxException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<PersonDetailsEntity> personDetailsEntities =
				objectMapper.readValue( new String( Files.readAllBytes( Paths.get( ClassLoader.getSystemResource( "dataSample.json" ).toURI() ) ) ),
						new TypeReference<List<PersonDetailsEntity>>() {
						} );
		personDetailsEntities.forEach( personDetailsEntity -> {
			personDetailsEntity.setEmbeddings( textEmbedder.embed( ContextCreater.context( personDetailsEntity ) ) );
			personDetailsRepository.save( personDetailsEntity );
		} );
	}

	@Test
	public void test_own_jacky_smith() {
		/*
		 user =     {
        "Firstname": "Jacky",
        "Lastname": "Smith",
        "City": "Hoboken",
        "State": "NJ",
        "Zip": "7101",
        "Birthdate": "04/10/1980",
        "Address": "11 Lee street",
        "SSN4": "2017",
        "SSN9": "351732017"
    }
		 */

		PersonDetailsEntity jackySmithPerson = PersonDetailsEntity.builder()
				.firstName( "Jacky" )
				.lastName( "Smith" )
				.city( "Hoboken" )
				.state( "NJ" )
				.zip( "7101" )
				.address( "11 Lee street" )
				.ssn4( "2017" )
				.ssn9( "351732017" )
				.birthDate( "04/10/1980" )
				.build();
		var query = textEmbedder.embed( ContextCreater.context( jackySmithPerson ) );
		List<PersonDetailsEntity> nearestNeighbours = personDetailsRepository.findNNearstNeighbours( query, 10 );
		List<PersonDetailsEntity> ranges = personDetailsRepository.findRangeItems( query, 75 );
		assertTrue( nearestNeighbours.size() == 10 );
		assertTrue( ranges.size() > 0 );

	}

}
