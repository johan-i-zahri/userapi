package com.test.usersapi.controllers;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.usersapi.domains.mappings.MapPdfBytes;
import com.test.usersapi.domains.models.ExportHistory;
import com.test.usersapi.services.ExportHistoryService;

@RestController
public class ExportHistoryController extends BaseController {
    private static final Logger logger = Logger.getLogger(ExportHistoryController.class.getName());

	@Autowired
	private ExportHistoryService exportHistoryService;

	@GetMapping("/export-history")
	public Page<ExportHistory> getExportHistory(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize) {
		if (pageNo == 0 && pageSize == 0)
			throw new IllegalArgumentException();
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
		return exportHistoryService.getExportHistory(pageable);
	}
	
    @GetMapping("/download-pdf/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String id) throws IOException {
        MapPdfBytes response = exportHistoryService.getPdfBytes(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(response.getPdfBytes().length);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(response.getFileName()).build());

        return new ResponseEntity<>(response.getPdfBytes(), headers, HttpStatus.OK);
    }

}
