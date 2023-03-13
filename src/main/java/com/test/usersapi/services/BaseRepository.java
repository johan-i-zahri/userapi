package com.test.usersapi.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.test.usersapi.domains.models.BaseModel;

@NoRepositoryBean
public interface BaseRepository<T extends BaseModel> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
    Optional<T> findById(String id);
    Page<T> findAll(Pageable pagination);
}