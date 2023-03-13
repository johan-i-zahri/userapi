package com.test.usersapi.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;
import com.test.usersapi.domains.mappings.MapExportPdf;
import com.test.usersapi.domains.mappings.MapRenderPdf;
import com.test.usersapi.domains.models.ExportHistory;
import com.test.usersapi.exceptions.FileStorageException;
import com.test.usersapi.exceptions.GithubApiException;
import com.test.usersapi.util.ExportPdfGenerator;

@Service
public class ExportService {

	@Autowired
	ExportPdfGenerator exportPdfGenerator;
	
	@Autowired
	private GithubApiService githubApiService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private ExportHistoryService exportHistoryService;

	@Transactional
	public MapExportPdf exportPdf(String searchQuery) throws ExportException {
		MapExportPdf exportResult = new MapExportPdf();
		try {
			String userListJson = githubApiService.searchUsers(searchQuery);
			MapRenderPdf renderResult = exportPdfGenerator.renderPdf(searchQuery, userListJson);
			exportResult.setRenderPdf(renderResult);

			String candidateFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS.pdf", new Date());
			String uniqueFilename = fileStorageService.saveFile(renderResult.getPdfBytes(), candidateFileName);
			ExportHistory exportHistory = new ExportHistory(uniqueFilename, searchQuery);
			this.exportHistoryService.add(exportHistory);
			exportResult.setFileName(uniqueFilename);

			exportResult.setStatus(MapExportPdf.STATUS_OK);
            if(exportResult.getRenderPdf().getSearchResult().getMapItems().size()>0)
            	exportResult.setMessage(String.format(MapExportPdf.MESSAGE_RECORD_FOUND_STRING_FORMAT, 
            			exportResult.getRenderPdf().getSearchResult().getMapItems().size()));
            else
            	exportResult.setMessage(String.format(MapExportPdf.MESSAGE_RECORD_NOT_FOUND));
		} catch (GithubApiException | IOException | DocumentException e) {
			throw new ExportException("Export failed with the following message: " + e.getMessage(), e);
		}
		return exportResult;
	}

	// Method to download PDF file
	public Resource downloadPdf(String fileName) throws FileStorageException, FileNotFoundException {
		// Load file from file system
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// If file exists, return it
		if (resource != null) {
			return resource;
		}

		// If file doesn't exist, throw exception
		throw new FileNotFoundException("File not found: " + fileName);
	}

}
