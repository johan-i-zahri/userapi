package com.test.usersapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.test.usersapi.domains.models.BaseModel;

public abstract class AbstractBaseService<T extends BaseModel> {

    protected abstract BaseRepository<T> getJpaRepository();

    public abstract T add(T t);

    public abstract T update(T t);

    public abstract Boolean delete(String id);

    public abstract T findById(String id);
    
    public abstract Page<T> findAll(Pageable pagination);

    public static Sort getSortBy(String sortBy, String direction) {
        if ("desc".equalsIgnoreCase(direction!=null?direction.trim():"")) {
            return Sort.by(Sort.Direction.DESC, sortBy);
        } else {
            return Sort.by(Sort.Direction.ASC, sortBy);
        }
    }
}
