package com.fanduel.vectorsearch;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResult {
	private String address;
	private Long similarity;
}
