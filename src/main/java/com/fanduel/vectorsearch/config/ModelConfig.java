package com.fanduel.vectorsearch.config;

import java.util.Map;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

	@Bean
	public EmbeddingModel embeddingModel() throws Exception {
		TransformersEmbeddingModel model =  new TransformersEmbeddingModel();
		model.setTokenizerResource( "classpath:model/tokenizer.json" );
		model.setModelResource( "classpath:model/model.onnx" );
		model.setTokenizerOptions( Map.of("padding", "true"));
		model.afterPropertiesSet();
		return model;
	}

}
