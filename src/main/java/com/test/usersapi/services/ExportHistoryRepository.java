package com.test.usersapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.test.usersapi.domains.models.ExportHistory;

@Repository
public interface ExportHistoryRepository extends BaseRepository<ExportHistory> {
	Page<ExportHistory> findAllByOrderByCreatedAtDesc(Pageable pageable);
    ExportHistory findByFileName(String name);
}
