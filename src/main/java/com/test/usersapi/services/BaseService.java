package com.test.usersapi.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.test.usersapi.domains.models.BaseModel;

@Transactional(readOnly = true)
public abstract class BaseService<T extends BaseModel> extends AbstractBaseService<T>{
    
    @Transactional
    @Override
    public T add(T t) {
        T updated = getJpaRepository().save(t);

        if (null != updated.getId()) {
            return t;
        }
        return null;
    }
    @Transactional
    @Override
    public T update(T t) {
        return getJpaRepository().saveAndFlush(t);
    }
    @Override
    @Transactional
    public Boolean delete(String id) {
        T t = getJpaRepository().findById(id).orElse(null);

        if (null != t) {
            t.setIsDeleted(true);
            getJpaRepository().saveAndFlush(t);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional
    public Boolean delete(T t) {
        if (null != t) {
            t.setIsDeleted(true);
            getJpaRepository().saveAndFlush(t);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    @Override
    public T findById(String id) {

        T t = getJpaRepository().findById(id).orElse(null);
        
        return t;
    }
    @Override
    public Page<T> findAll(Pageable pagination){
    	return getJpaRepository().findAll(pagination);
    }
}
