package com.test.usersapi.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.usersapi.domains.mapping.responses.BaseResponse;
import com.test.usersapi.domains.mapping.responses.ObjectResponse;
import com.test.usersapi.domains.mapping.responses.StringResponse;
import com.test.usersapi.domains.mappings.MapExportPdf;
import com.test.usersapi.exceptions.UserapiException;
import com.test.usersapi.services.ExportService;
import com.test.usersapi.util.ResponseUtil;

@RestController
public class ExportController extends BaseController {
    private static final Logger logger = Logger.getLogger(ExportController.class.getName());

    private static StringResponse stringResponse = new StringResponse("","");    
    @SuppressWarnings("unused")
	private static ObjectResponse objectResponse = new ObjectResponse("","");
    @SuppressWarnings({ "unused", "rawtypes" })
	private static BaseResponse response = new BaseResponse();
    
	@Autowired
    private ExportService exportService;

    @GetMapping("/export")
    public ResponseEntity<StringResponse> export(@RequestParam(name = "search") String searchQuery) {
        try {
            MapExportPdf response = exportService.exportPdf(searchQuery);
            if(MapExportPdf.STATUS_OK.equals(response.getStatus()) && 
            		MapExportPdf.MESSAGE_RECORD_NOT_FOUND.equals(response.getMessage()))
            	return ResponseUtil.handleOk(stringResponse, "PDF export succeeded but with no records found!", response.getFileName());
            else if(MapExportPdf.STATUS_OK.equals(response.getStatus()))
            	return ResponseUtil.handleOk(stringResponse, "PDF export succeeded!", response.getFileName());
            else
            	throw new UserapiException("Should not reach this point!");
        } catch (Exception e) {
        	return ResponseUtil.handleException(stringResponse, "PDF export failed:", e, logger);
        }
    }
    @Autowired
    private ApplicationContext context;

    @GetMapping("/shutdown-app")
    public void shutdownApp() {

        int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
        System.exit(exitCode);
    }
}
