package com.test.usersapi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.itextpdf.text.DocumentException;
import com.test.usersapi.domains.mappings.MapItem;
import com.test.usersapi.domains.mappings.MapRenderPdf;
import com.test.usersapi.domains.mappings.MapSearchResult;

@Component
public class ExportPdfGenerator {

	public MapRenderPdf renderPdf(String query, String userListJson) throws IOException, DocumentException {
		if (StringUtils.isBlank(userListJson)) {
			throw new IllegalArgumentException("User list JSON cannot be null or blank");
		}

		MapRenderPdf renderResult = new MapRenderPdf();
		renderResult.setSearchQuery(query);

		ObjectMapper mapper = new ObjectMapper();
		MapSearchResult searchResult = mapper.readValue(userListJson, MapSearchResult.class);
		List<MapItem> users = searchResult.getMapItems();
		renderResult.setSearchResult(searchResult);

		String templateSource = loadTemplate();
		Handlebars handlebars = new Handlebars();
		handlebars.registerHelper("listSize", CustomHelpers.listSizeHelper);
		Template template = handlebars.compileInline(templateSource);

		Context context = Context.newBuilder(searchResult).combine("searchQuery", query).build();
		String html = template.apply(context);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(html);
		renderer.layout();
		renderer.createPDF(out);
		renderResult.setPdfBytes(out.toByteArray());

		return renderResult;
	}

	private static String loadTemplate() throws IOException {
		ClassLoader loader = ExportPdfGenerator.class.getClassLoader();
		return IOUtils.toString(loader.getResourceAsStream("templates/pdf_template.html"), StandardCharsets.UTF_8);
	}
}
