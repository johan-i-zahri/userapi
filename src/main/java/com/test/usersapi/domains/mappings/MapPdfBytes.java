package com.test.usersapi.domains.mappings;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class MapPdfBytes extends BaseMap{
	@JsonProperty("pdf_bytes")
	private byte[] pdfBytes;
	@JsonProperty("file_name")
	private String fileName;
}
