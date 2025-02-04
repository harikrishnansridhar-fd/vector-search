package com.fanduel.vectorsearch;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TextEmbedder {

	private final EmbeddingModel embeddingModel;

	public float[] embed( String text ) {
		return embeddingModel.embed( text );
	}
}
