package com.fanduel.vectorsearch;

import java.sql.Time;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fanduel.vectorsearch.primary.entity.PersonDetailsEntity;
import com.fanduel.vectorsearch.primary.repository.AddressDetailsRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddressSimilaritySearchTest {

	@Autowired
	private AddressDetailsRepository addressDetailsRepository;

	@Autowired
	private CosineSimilaritySearcher cosineSimilaritySearcher;

	private Map<Long,PersonDetailsEntity> getAddressesForUser(int start, int end) {
		return addressDetailsRepository.findEntriesForRangeOfUsers( start, end ).stream()
				.collect( Collectors.toMap( PersonDetailsEntity::getId, pde -> pde ) );
	}

	@Test
	public void givenQuery_whenSearched_thenResultIsReturned() {
		final int firstUser = 1;
		final int lastUser = 1;
		Time initialStart = Time.valueOf("00:00:00");
		Time initialEnd = Time.valueOf("00:00:00");
		Time start = Time.valueOf("00:00:00");
		Time end = Time.valueOf("00:00:00");
		initialStart.setTime( System.currentTimeMillis() );
		start.setTime( System.currentTimeMillis() );
		var pdes = getAddressesForUser(firstUser, lastUser);
		end.setTime( System.currentTimeMillis() );
		System.out.println("Time took to get addresses for " + (lastUser - firstUser + 1) + " users: " + (end.getTime() - start.getTime()) + "ms");
		start.setTime( System.currentTimeMillis() );
		var query = getAddressesForUser( 356, 356 ).get( 356L ).getAddress();
		end.setTime( System.currentTimeMillis() );
		System.out.println("Time took to get address for user 356: " + (end.getTime() - start.getTime()) + "ms");
		start.setTime( System.currentTimeMillis() );
		var result = cosineSimilaritySearcher.cosineSimilarityAddress( query, pdes);
		end.setTime( System.currentTimeMillis() );
		initialEnd.setTime( System.currentTimeMillis() );
		System.out.println("Time took to calculate cosine similarity for " + (lastUser - firstUser + 1) + " users: " + (end.getTime() - start.getTime()) + "ms");
		System.out.println("Total time took to get addresses, query and calculate cosine similarity for " + (lastUser - firstUser + 1) + " users: " + (initialEnd.getTime() - initialStart.getTime()) + "ms");
		System.out.println("###### Output value #####\n");
		result.stream().sorted( Comparator.comparing( SearchResult::getSimilarity ).reversed()).filter( res -> res.getSimilarity() >= 80 ).forEach( res -> {
			System.out.println( res.getId() + "," + res.getSimilarity() + "," + res.getAddress() );
		} );
		System.out.println("###### EOO #####\n");
	}

}
