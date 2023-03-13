package com.test.usersapi.domains.mappings;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MapRenderPdf {
    @JsonProperty("search_query")
    private String searchQuery;
    @JsonProperty("pdf_bytes")
    private byte[] pdfBytes;
	@JsonProperty("search_result")
	private MapSearchResult searchResult;
}
