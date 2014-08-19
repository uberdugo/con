package com.wepingo.convertor.service;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.wepingo.convertor.common.domain.api.DomainEntity;
import com.wepingo.convertor.common.services.DomainManager;
import com.wepingo.convertor.dao.api.AbstractDao;

public abstract class AbstractManagerImpl<DAO extends AbstractDao<ENTITY_TYPE>, ENTITY_TYPE extends DomainEntity>
		implements DomainManager<ENTITY_TYPE> {
			
	static final Logger LOGGER = Logger.getLogger(AbstractManagerImpl.class);
		
	@Override
	public List<ENTITY_TYPE> addAll(List<ENTITY_TYPE> entities) {
		for (ENTITY_TYPE entity : entities) {			
			getDao().insert(entity);
		}
		return entities;
	}
	
	@Override
	public List<ENTITY_TYPE> modifyAll(List<ENTITY_TYPE> entities) {
		for (ENTITY_TYPE entity : entities) {			
			getDao().update(entity);
		}
		return entities;
	}
	
	@Override
	public ENTITY_TYPE add(ENTITY_TYPE entity) {
		return getDao().insert(entity);
	}

	@Override
	public ENTITY_TYPE modify(ENTITY_TYPE entity) {
		return getDao().update(entity);
	}

	@Override
	public ENTITY_TYPE merge(ENTITY_TYPE entity) {
		return getDao().merge(entity);
	}

	@Override
	public void remove(ENTITY_TYPE entity) {
		getDao().remove(entity);
	}
	

	@Override
	public void forceRemove(ENTITY_TYPE entity) {
		getDao().forceRemove(entity);
	}
		
	@Override
	public ENTITY_TYPE findById(Long id) {
		return getDao().selectById(id);
	}

	@Override
	public ENTITY_TYPE loadComplete(ENTITY_TYPE entity) {
		entity = getDao().merge(entity);
		List<Field> fieldsList = new ArrayList<Field>();
		Field[] fields = entity.getClass().getDeclaredFields();
		fieldsList.addAll(Arrays.asList(fields));
		Class<?> superClass = entity.getClass().getSuperclass();
		
		while(superClass != null){
			fields = superClass.getDeclaredFields();
			fieldsList.addAll(Arrays.asList(fields));
			superClass = superClass.getSuperclass();
		}
		
		for (Field field : fieldsList) {
				if(!field.getType().isPrimitive()){					
					field.setAccessible(true);
					try {
						Hibernate.initialize(field.get(entity));
					} catch (Exception e) {
						LOGGER.error("Error on entity with id : " + entity.getId() + " of type : " + entity.getClass(),e);
					}
				}
		}
		return entity;
	}
	
	@Override
	public ENTITY_TYPE findByLabel(String label) {
		ENTITY_TYPE entity = null;
		List<Field> fields  = new ArrayList<Field>();
		Class<ENTITY_TYPE> clazz = getBusinessEntityClazz();
		Field[] fieldsTab = clazz.getDeclaredFields();
		fields.addAll(Arrays.asList(fieldsTab));
		
		Class<? super ENTITY_TYPE> superClass = clazz.getSuperclass();
		while(superClass != null){
			fieldsTab = superClass.getDeclaredFields();
			fields.addAll(Arrays.asList(fieldsTab));
			superClass = superClass.getSuperclass();
		}
		
		for (Field field : fields) {
				if(field.getType().equals(String.class) && field.getName().equals("label")){				
					field.setAccessible(true);
					try {
						entity = getDao().selectByLabel(label);
						break;
					} catch (Exception e) {
						LOGGER.error("Error on entity with id : " + entity.getId() + " of type : " + entity.getClass(),e);
					}
				}
		}
		return entity;
	}

	@Override
	public ENTITY_TYPE findByName(String name) {
		ENTITY_TYPE entity = null;
		List<Field> fields  = new ArrayList<Field>();
		Class<ENTITY_TYPE> clazz = getBusinessEntityClazz();
		Field[] fieldsTab = clazz.getDeclaredFields();
		fields.addAll(Arrays.asList(fieldsTab));
		
		Class<? super ENTITY_TYPE> superClass = clazz.getSuperclass();
		while(superClass != null){
			fieldsTab = superClass.getDeclaredFields();
			fields.addAll(Arrays.asList(fieldsTab));
			superClass = superClass.getSuperclass();
		}
		
		for (Field field : fields) {
				if(field.getType().equals(String.class) && field.getName().equals("name")){				
					field.setAccessible(true);
					try {
						entity = getDao().selectByName(name);
						break;
					} catch (Exception e) {
						LOGGER.error("Error on entity with id : " + entity.getId() + " of type : " + entity.getClass(),e);
					}
				}
		}
		return entity;
	}
	
	@Override
	public List<ENTITY_TYPE> findAll() {
		return getDao().selectAll();
	}

	abstract protected DAO getDao();
	
	private Class<ENTITY_TYPE> getBusinessEntityClazz() {
		Class<ENTITY_TYPE> businessEntityClass = null ; 
		Class<Type> type = (Class<Type>) this.getClass().getGenericSuperclass().getClass();
		Field field = null ;
		try {
			field = type.getDeclaredField("actualTypeArguments");
			field.setAccessible(true);
			businessEntityClass = (Class<ENTITY_TYPE>) ((Type[])field.get(this.getClass().getGenericSuperclass()))[1];
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return businessEntityClass ;
	}
}
