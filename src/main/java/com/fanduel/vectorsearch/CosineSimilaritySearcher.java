package com.fanduel.vectorsearch;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fanduel.vectorsearch.primary.entity.PersonDetailsEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CosineSimilaritySearcher {
	private final TextEmbedder textEmbedder;

	public List<SearchResult> cosineSimilarity( String query, List<String> addresses ) {
		float[] queryVector = textEmbedder.embed( query );
		return addresses.stream().map( address -> SearchResult.builder( ).address( address ).similarity( getCosineProduct( queryVector,
				textEmbedder.embed( address ) ) ).build() ).collect( Collectors.toList() );
	}

	public List<SearchResult> cosineSimilarityAddress( String query, Map<Long, PersonDetailsEntity> users) {
		Time start = Time.valueOf("00:00:00");
		Time end = Time.valueOf("00:00:00");
		start.setTime( System.currentTimeMillis() );
		float[] queryVector = textEmbedder.embed( query );
		end.setTime( System.currentTimeMillis() );
		System.out.println("Time took to create embedding for single user: " + (end.getTime() - start.getTime()) + "ms");
		return users.values().stream().map( user -> SearchResult.builder( ).id( user.getId() ).address( user.getAddress() ).similarity( getCosineProduct( queryVector,
				textEmbedder.embed( user.getAddress() ) ) ).build() ).collect( Collectors.toList() );
	}

	private Long getCosineProduct( final float[] v1, final float[] v2 ) {
		double dot = 0, normA = 0, normB = 0;
		for ( int i = 0; i < v1.length; i++ ) {
			dot += v1[i] * v2[i];
			normA += Math.pow( v1[i], 2 );
			normB += Math.pow( v2[i], 2 );
		}
		return Math.round( ( dot / ( Math.sqrt( normA ) * Math.sqrt( normB ) ) ) * 100 );
	}
}
