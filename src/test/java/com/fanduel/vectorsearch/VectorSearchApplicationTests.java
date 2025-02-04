package com.fanduel.vectorsearch;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VectorSearchApplicationTests {

	@Autowired
	private CosineSimilaritySearcher cosineSimilaritySearcher;

	@Test
	void givenAddresses_whenSearched_ThencosineSmilarityWorks() {
		// given
		String query = "123 Main St, New York, NY 10001";
		String address1 = "123 Main St, New York, NY 10001";
		String address2 = "456 Elm St, New York, NY 10001";
		String address3 = "789 Oak St, New York, NY 10001";

		// when
		List<SearchResult> results= cosineSimilaritySearcher.cosineSimilarity( query, List.of( address1, address2, address3 ) );
		System.out.println(results);
		// then
		SearchResult max = results.stream().max( Comparator.comparing( SearchResult::getSimilarity ) ).orElseThrow();
		System.out.println("###### Output value #####\n" + max + "\n");
		// no exception is thrown
		assertThat( max.getAddress() ).isEqualTo( query );
	}

}
