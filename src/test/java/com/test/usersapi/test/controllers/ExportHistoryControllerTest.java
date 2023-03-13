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
//	
//    private ExportHistoryService exportHistoryService = mock(ExportHistoryService.class);
//    private ExportHistoryController exportHistoryController = new ExportHistoryController(exportHistoryService);
//    
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


//package com.test.usersapi.test.controllers;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.test.usersapi.controllers.ExportHistoryController;
//import com.test.usersapi.domains.model.ExportHistory;
//import com.test.usersapi.exceptions.ResourceNotFoundException;
//import com.test.usersapi.services.ExportHistoryRepository;
//import com.test.usersapi.services.ExportHistoryService;
//import com.test.usersapi.services.ExportService;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(MockitoExtension.class)
//public class ExportHistoryControllerTest {
//
//	@Mock
//	private RestTemplate restTemplate;
//	
//	@Mock
//	private ExportHistoryService exportHistoryService;
//
//    @Mock
//    private ExportHistoryRepository exportHistoryRepository;
//    
//    @Mock
//    private ExportService exportService;
//    
//	@InjectMocks
//	private ExportHistoryController exportHistoryController;
//
//	private MockMvc mockMvc;
//
//	public ExportHistoryControllerTest() {
//	}
//
//    @Test
//    public void testListHistory() throws JsonMappingException, JsonProcessingException {
//        String name = "test.pdf";
//
//        ExportHistory exportHistory = new ExportHistory(name);
//        String query = "search=Test";
//		exportHistory.setQuery(query);
//        exportHistoryRepository.save(exportHistory);
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/history", String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        String responseBody = responseEntity.getBody();
//        assertNotNull(responseBody);
//
//        JsonNode root = new ObjectMapper().readTree(responseBody);
//        JsonNode historyNode = root.get("history");
//        assertNotNull(historyNode);
//
//        assertEquals(1, historyNode.size());
//
//        JsonNode historyItemNode = historyNode.get(0);
//        assertNotNull(historyItemNode);
//
//        assertEquals(name, historyItemNode.get("fileName").asText());
//        assertEquals(query, historyItemNode.get("query").asText());
//    }
//    
//    @Test
//    public void testDownloadPdfNotFound() {
//        // Setup
//        given(exportHistoryRepository.findByFileName("test.pdf")).willReturn(null);
//
//        // Execute and verify
//        assertThrows(ResourceNotFoundException.class, () -> exportService.downloadPdf("test.pdf"));
//    }
//
//	@Test
//	public void testGetExportHistory() throws Exception {
//		// Mock the service response
//		List<ExportHistory> histories = new ArrayList<>();
//		histories.add(new ExportHistory("test.pdf", "john.doe@example.com"));
//		Pageable pageable = PageRequest.of(0, 100);
//		PageImpl<ExportHistory> page = new PageImpl<>(histories, pageable, histories.size());
//		when(exportHistoryService.getExportHistory(pageable)).thenReturn(page);
//
//		mockMvc = MockMvcBuilders.standaloneSetup(exportHistoryController).build();
//		mockMvc.perform(get("/export-history")).andExpect(status().isOk())
//				.andExpect(jsonPath("$.content[0].filename").value("test.pdf"))
//				.andExpect(jsonPath("$.content[0].exportedBy").value("john.doe@example.com"));
//	}
//
//	@Test
//	public void testListExportHistory() {
//		// Setup
//		List<ExportHistory> exportHistoryList = new ArrayList<>();
//		exportHistoryList.add(new ExportHistory("test1.pdf", LocalDateTime.now()));
//		exportHistoryList.add(new ExportHistory("test2.pdf", LocalDateTime.now()));
//		Page<ExportHistory> page = new PageImpl<>(exportHistoryList,
//				PageRequest.of(0, 2, Sort.Direction.DESC, "createdAt"), exportHistoryList.size());
//		given(exportHistoryRepository.findAllByOrderByCreatedAtDesc(any(Pageable.class))).willReturn(page);
//
//		// Execute
//		Page<ExportHistory> result = exportHistoryController.getExportHistory(null, null);
//
//		// Verify
//		assertNotNull(result);
//		assertEquals(2, result.getSize());
//		assertEquals(exportHistoryList, result.getContent());
//	}
//
//	@Test
//	public void testListExportHistoryEmpty() {
//		// Setup
//		PageRequest pageRequest = PageRequest.of(0, 10);
//		given(exportHistoryRepository.findAllByOrderByCreatedAtDesc(pageRequest)).willReturn(Page.empty());
//
//		// Execute
//		Page<ExportHistory> result = exportHistoryController.getExportHistory(null, null);
//
//		// Verify
//		assertNotNull(result);
//		assertTrue(result.isEmpty());
//	}
//
//	@Test
//	public void testListExportHistoryWithPageLimit() {
//		// Setup
//		List<ExportHistory> exportHistoryList = new ArrayList<>();
//		exportHistoryList.add(new ExportHistory("test1.pdf", LocalDateTime.now()));
//		exportHistoryList.add(new ExportHistory("test2.pdf", LocalDateTime.now()));
//		exportHistoryList.add(new ExportHistory("test3.pdf", LocalDateTime.now()));
//		exportHistoryList.add(new ExportHistory("test4.pdf", LocalDateTime.now()));
//		given(exportHistoryRepository.findAllByOrderByCreatedAtDesc(any(PageRequest.class))).willReturn(
//				new PageImpl<>(exportHistoryList.subList(0, 2), PageRequest.of(0, 2), exportHistoryList.size()));
//
//		// Execute
//		Page<ExportHistory> result = exportHistoryController.getExportHistory(0, 2);
//
//		// Verify
//		assertNotNull(result);
//		assertEquals(2, result.getSize());
//		assertEquals(exportHistoryList.subList(0, 2), result);
//	}
//
//	@Test()
//	public void testListExportHistoryWithInvalidPageLimit() {
//		// Execute
//		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
//			exportHistoryController.getExportHistory(0, 0);
//		});
//	}
//
//	@Test()
//	public void testListExportHistoryWithPageLimitExceeds() {
//		// Setup
//		given(exportHistoryRepository.findAllByOrderByCreatedAtDesc(any(PageRequest.class)))
//				.willThrow(new ResourceNotFoundException("Page limit exceeds total records"));
//
//		// Execute
//		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//			exportHistoryController.getExportHistory(0, 101);
//		});
//		
//	}
//}
