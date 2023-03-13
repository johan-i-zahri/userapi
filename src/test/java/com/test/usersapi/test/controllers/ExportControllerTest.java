package com.test.usersapi.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.test.usersapi.controllers.ExportController;
import com.test.usersapi.domains.mapping.responses.StringResponse;
import com.test.usersapi.domains.mappings.MapExportPdf;
import com.test.usersapi.exceptions.ExportException;
import com.test.usersapi.services.ExportHistoryRepository;
import com.test.usersapi.services.ExportHistoryService;
import com.test.usersapi.services.ExportService;
import com.test.usersapi.services.FileStorageService;
import com.test.usersapi.services.GithubApiService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ExportControllerTest {
	
    @Mock
    private RestTemplate restTemplate;
    
    @Mock
    private ExportHistoryRepository exportHistoryRepository;

    @Mock
    private ExportHistoryService exportHistoryService;
    
    @Mock
    private FileStorageService fileStorageService;
    
    @Mock
    private ExportService exportService;
    
    @Mock
    private GithubApiService githubApiService;
    
    @InjectMocks
    private ExportController exportController;

    @Test
    public void testExportFound() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        String name = "test.pdf";
//        String requestBody = "{\"name\": \"" + name + "\", \"search\": \"john\"}";
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        byte[] pdfBytes = { 1, 2, 3, 4 };
//
//        when(restTemplate.exchange(eq("http://localhost:8080/export"), eq(HttpMethod.POST), any(HttpEntity.class), eq(byte[].class)))
//                .thenReturn(new ResponseEntity<>(pdfBytes, HttpStatus.OK));
//        
//        when(exportHistoryRepository.save(any(ExportHistory.class))).thenReturn(new ExportHistory(name));
//
//        when(fileStorageService.saveFile(any(byte[].class), any(String.class))).thenReturn(name);
//        
//        when(exportHistoryRepository.findById(anyString())).thenReturn(Optional.of(new ExportHistory(name)));

        String query="qqqq%26location=Indonesia%26per_page=200";
        //String requestBody = "{\"search\": \"" + query + "\"}";
        MapExportPdf exportReturnVal = new MapExportPdf();
        exportReturnVal.setStatus(MapExportPdf.STATUS_OK);
        exportReturnVal.setMessage(String.format(MapExportPdf.MESSAGE_RECORD_FOUND_STRING_FORMAT, 100));
        //when(exportService.downloadPdf(name)).thenReturn(new ByteArrayResource(pdfBytes));
        when(exportService.exportPdf(eq(query))).thenReturn(exportReturnVal);
        ResponseEntity<StringResponse> responseEntity = exportController.export(query);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getMessage(), "PDF export succeeded!");
    }
    

    @Test
    public void testExportNotFound() throws IOException {
        String query="qqzzzxxqq%26location=Indonesia%26per_page=200";
        MapExportPdf exportReturnVal = new MapExportPdf();
        exportReturnVal.setStatus(MapExportPdf.STATUS_OK);
        exportReturnVal.setMessage(MapExportPdf.MESSAGE_RECORD_NOT_FOUND);
        when(exportService.exportPdf(eq(query))).thenReturn(exportReturnVal);
        ResponseEntity<StringResponse> responseEntity = exportController.export(query);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getMessage(), "PDF export succeeded but with no records found!");
    }
    
    @Test
    public void testExportError() throws IOException {
        String query="qqzzzxxqq%26location=Indonesia%26per_page=200";
        when(exportService.exportPdf(eq(query))).thenThrow(new ExportException("Some error"));
        ResponseEntity<StringResponse> responseEntity = exportController.export(query);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getMessage().contains("Some error"));
    }
}