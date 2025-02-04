## WHat
This repository provides implementations for fast similarity search on high-dimensional vector embeddings. 
The similarity search is performed with the help of vector embeddings and vector search. The vector embeddings are generated using a ML model.
The ML model used is [`sentence-transformers/all-MiniLM-L6-v2`](https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2)
The model is used in spring boot's [Transformers (ONNX)](https://docs.spring.io/spring-ai/reference/api/embeddings/onnx.html) to generate embeddings and 
run the similarity search using cosine similarity.
### How
Inorder to run the model in spring boot, the model needs to be exported to ONNX format.

#### Pre requisites:
python3

* The model can be exported to ONNX format using the following command
    ```shell
    pip install optimum onnx onnxruntime sentence-transformers
    optimum-cli export onnx --model sentence-transformers/all-MiniLM-L6-v2 /Users/harikrishnan.sridhar/IdeaProjects/vector-search/src/main/resources/model
    ```
once we have the model locally we can load the model as bean as below and can be used to create embeddings.
```java
@Configuration
public class Config {

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
```
* Run the tests to verify the embeddings are generated correctly.
