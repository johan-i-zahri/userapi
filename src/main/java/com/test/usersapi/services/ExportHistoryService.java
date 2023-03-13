package com.test.usersapi.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.test.usersapi.domains.mappings.MapPdfBytes;
import com.test.usersapi.domains.models.ExportHistory;

@Service
@Transactional
public class ExportHistoryService extends BaseService<ExportHistory>{
	
    @Autowired
    private ExportHistoryRepository exportHistoryRepository;
    
    @Autowired
    private FileStorageService fileStorageService;

	@Override
	protected BaseRepository<ExportHistory> getJpaRepository() {
		return this.exportHistoryRepository;
	}
	
    public Page<ExportHistory> getExportHistory(Pageable pageable) {
        return exportHistoryRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public MapPdfBytes getPdfBytes(String id) throws IOException {
        Optional<ExportHistory> exportHistory = exportHistoryRepository.findById(id);
        if (exportHistory.isPresent()) {
        	MapPdfBytes response = new MapPdfBytes();
            ExportHistory history = exportHistory.get();
            response.setFileName(history.getFileName());
            response.setPdfBytes(fileStorageService.getPdfBytes(history.getFileName()));
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Export history not found");
        }
    }

    public ExportHistory getExportHistoryById(String id) {
        return exportHistoryRepository.findById(id).orElse(null);
    }

}
