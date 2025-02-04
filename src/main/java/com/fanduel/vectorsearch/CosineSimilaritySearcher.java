package com.fanduel.vectorsearch;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

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
