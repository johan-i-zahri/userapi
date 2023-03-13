package com.test.usersapi.test.controllers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.test.usersapi.controllers.ExportHistoryController;
import com.test.usersapi.domains.mappings.MapPdfBytes;
import com.test.usersapi.domains.models.ExportHistory;
import com.test.usersapi.services.ExportHistoryRepository;
import com.test.usersapi.services.ExportHistoryService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ExportHistoryControllerTest {
	
	@Mock
	private ExportHistoryRepository exportHistoryRepository;
	  
	@Mock
	private ExportHistoryService exportHistoryService;
  
	@InjectMocks
	private ExportHistoryController exportHistoryController;

    @Test
    public void testGetExportHistory() {
        List<ExportHistory> exportHistoryList = new ArrayList<>();
        ExportHistory exportHistory = new ExportHistory();
        exportHistoryList.add(exportHistory);

        when(exportHistoryService.getExportHistory(any())).thenReturn(new PageImpl<>(exportHistoryList));
        
        exportHistoryController.getExportHistory(0, 100);
        
        // verify the service method is called with the correct arguments
        verify(exportHistoryService).getExportHistory(PageRequest.of(0, 100, Sort.by("createdAt").descending()));
    }
    
    @Test
    public void testDownloadPdf() throws Exception {
        byte[] pdfBytes = "Test PDF".getBytes();
        String fileName = UUID.randomUUID().toString() + ".pdf";
        
        MapPdfBytes mapPdfBytes = new MapPdfBytes();
        mapPdfBytes.setPdfBytes(pdfBytes);
        mapPdfBytes.setFileName(fileName);

        when(exportHistoryService.getPdfBytes(any())).thenReturn(mapPdfBytes);
        
        ResponseEntity<byte[]> response = exportHistoryController.downloadPdf("1");
        
        // verify the service method is called with the correct argument
        verify(exportHistoryService).getPdfBytes("1");
        
        // verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals(pdfBytes.length, response.getHeaders().getContentLength());
        assertEquals(fileName, response.getHeaders().getContentDisposition().getFilename());
        assertArrayEquals(pdfBytes, response.getBody());
    }
}