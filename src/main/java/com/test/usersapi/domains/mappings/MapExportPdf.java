package com.test.usersapi.domains.mappings;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class MapExportPdf extends BaseMap{
	public static final String MESSAGE_RECORD_FOUND_STRING_FORMAT = "%d record found!";
	public static final String MESSAGE_RECORD_NOT_FOUND = "No record was found!";
	@JsonProperty("render_pdf")
	private MapRenderPdf renderPdf;
	@JsonProperty("file_name")
	private String fileName;
}
