package com.wepingo.convertor.common.services;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wepingo.convertor.common.domain.api.DomainEntity;

@Transactional(readOnly = false)
public interface DomainManager<ENTITY_TYPE extends DomainEntity> {

    @Transactional(propagation = Propagation.REQUIRED)
    public abstract ENTITY_TYPE add(ENTITY_TYPE entity);

    @Transactional(propagation = Propagation.REQUIRED)
    public abstract ENTITY_TYPE modify(ENTITY_TYPE entity);

    @Transactional(propagation = Propagation.REQUIRED)
    public abstract ENTITY_TYPE merge(ENTITY_TYPE entity);

    @Transactional(propagation = Propagation.SUPPORTS)
    public abstract ENTITY_TYPE findById(Long id);

    @Transactional(propagation = Propagation.SUPPORTS)
    public abstract ENTITY_TYPE findByLabel(String label);

    @Transactional(propagation = Propagation.SUPPORTS)
    public abstract ENTITY_TYPE findByName(String name);

    @Transactional(propagation = Propagation.SUPPORTS)
    public abstract List<ENTITY_TYPE> findAll();

    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(ENTITY_TYPE entity);

    @Transactional(propagation = Propagation.SUPPORTS)
    public ENTITY_TYPE loadComplete(ENTITY_TYPE entity);

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ENTITY_TYPE> addAll(List<ENTITY_TYPE> entities);

    @Transactional(propagation = Propagation.REQUIRED)
    List<ENTITY_TYPE> modifyAll(List<ENTITY_TYPE> entities);

    @Transactional(propagation = Propagation.REQUIRED)
    void forceRemove(ENTITY_TYPE entity);

}