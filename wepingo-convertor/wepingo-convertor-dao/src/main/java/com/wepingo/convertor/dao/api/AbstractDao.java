package com.wepingo.convertor.dao.api;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wepingo.convertor.common.domain.api.DomainEntity;

@Transactional(readOnly=false)
public interface AbstractDao<ENTITY_TYPE extends DomainEntity> {

	@Transactional(propagation=Propagation.REQUIRED)
	public abstract ENTITY_TYPE insert(ENTITY_TYPE entity);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public abstract ENTITY_TYPE update(ENTITY_TYPE entity);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public abstract ENTITY_TYPE persist(ENTITY_TYPE entity);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public abstract ENTITY_TYPE merge(ENTITY_TYPE entity);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public abstract void remove(ENTITY_TYPE entity);
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public abstract ENTITY_TYPE selectById(Long id);

	@Transactional(propagation=Propagation.SUPPORTS)
	public abstract ENTITY_TYPE selectByLabel(String label);

	@Transactional(propagation=Propagation.SUPPORTS)
	public abstract ENTITY_TYPE selectByName(String name);

	@Transactional(propagation=Propagation.SUPPORTS)
	public abstract List<ENTITY_TYPE> selectAll();

	@Transactional(propagation=Propagation.REQUIRED)
	public abstract void lock(ENTITY_TYPE entity);

	@Transactional(propagation=Propagation.REQUIRED)
	public abstract void forceRemove(ENTITY_TYPE entity);
	
}