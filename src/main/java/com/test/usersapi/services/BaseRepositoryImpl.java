package com.test.usersapi.services;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.test.usersapi.domains.models.BaseModel;


public class BaseRepositoryImpl<T extends BaseModel> extends SimpleJpaRepository<T, String> implements BaseRepository<T> {

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager), entityManager);
    }

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        return super.save(entity);
    }

    @Override
    public Optional<T> findById(String id) {
        return findOne(new Specification<T>() {
			private static final long serialVersionUID = 5698924434733714360L;

			@Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), id)
                );
            }
        });
    }
    
    public boolean exists(String id) {
        return findById(id)!=null;
    }
}
