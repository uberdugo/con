package com.wepingo.convertor.dao.hibernate;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wepingo.convertor.common.domain.api.DomainEntity;
import com.wepingo.convertor.dao.api.AbstractDao;

@Transactional(readOnly=false)
public abstract class AbstractDaoHibernateImpl<ENTITY_TYPE extends DomainEntity>
		implements AbstractDao<ENTITY_TYPE> {

	static final private Logger LOGGER = Logger
			.getLogger(AbstractDaoHibernateImpl.class);

	private Class<ENTITY_TYPE> entityClazz;

	@PersistenceContext
	private EntityManager em;

	public AbstractDaoHibernateImpl(Class<ENTITY_TYPE> entityClazz) {
		this.entityClazz = entityClazz;
	}

	public EntityManager getEm() {
		return em;
	}
	
	@Override
	public ENTITY_TYPE insert(ENTITY_TYPE entity) {

		if (null == entity) {
			LOGGER.error("Entity is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Entity is mandatory. Null value is forbidden.");
		}

		em.persist(entity);
		
		return entity;
	}

	@Override
	public List<ENTITY_TYPE> selectAll() {
		List<ENTITY_TYPE> entities = null;

		Query query = buildQuery("from " + entityClazz.getName() + " e", null, entityClazz, true);
		entities = query.getResultList();

		return entities;
	}

	@Override
	public ENTITY_TYPE selectById(Long id) {
		ENTITY_TYPE entity = null;

		if (null == id) {
			LOGGER.error("Id is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Id is mandatory. Null value is forbidden.");
		}

		entity = em.find(entityClazz, id);

		return entity;
	}

	@Override
	public ENTITY_TYPE selectByLabel(String label) {
		ENTITY_TYPE entity = null;
		List<ENTITY_TYPE> entities;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("label", label);
		Class<ENTITY_TYPE> clazz = getDomainEntityClazz();
		entities = executeMultipleResultQuery("from " + clazz.getSimpleName()
				+ " where label = :label", args, clazz, false);

		if (!entities.isEmpty()) {
			entity = entities.iterator().next();
		}

		return entity;
	}

	@Override
	public ENTITY_TYPE selectByName(String name) {
		ENTITY_TYPE entity = null;
		List<ENTITY_TYPE> entities;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", name);
		Class<ENTITY_TYPE> clazz = getDomainEntityClazz();
		entities = executeMultipleResultQuery("from " + clazz.getSimpleName()
				+ " where name = :name", args, clazz,true);

		if (!entities.isEmpty()) {
			entity = entities.iterator().next();
		}

		return entity;
	}
	
	public void refresh(ENTITY_TYPE entity) {
		em.refresh(entity);
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public ENTITY_TYPE update(ENTITY_TYPE entity) {

		if (null == entity) {
			LOGGER.error("Entity is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Entity is mandatory. Null value is forbidden.");
		}
		em.merge(entity);
		
		return entity;
	}

	@Override
	public ENTITY_TYPE merge(ENTITY_TYPE entity) {

		if (null == entity) {
			LOGGER.error("Entity is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Entity is mandatory. Null value is forbidden.");
		}
		return em.merge(entity);
	}

	@Override
	public ENTITY_TYPE persist(ENTITY_TYPE entity) {

		if (null == entity) {
			LOGGER.error("Entity is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Entity is mandatory. Null value is forbidden.");
		}
		em.persist(entity);
		
		return entity;
	}

	@Override
	public void remove(ENTITY_TYPE entity) {
		if (null == entity) {
			LOGGER.error("Entity is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Entity is mandatory. Null value is forbidden.");
		}

		entity = selectById(entity.getId());
		em.remove(entity);
	}
	
	@Override
	public void forceRemove(ENTITY_TYPE entity) {
		if (null == entity) {
			LOGGER.error("Entity is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Entity is mandatory. Null value is forbidden.");
		}
		Class<ENTITY_TYPE> clazz = getDomainEntityClazz();
		String hql = "delete from " + clazz.getSimpleName() + " where id = :id" ;
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("id", entity.getId());
		executeUpdate(hql, args, false);
	}

	public Object executeSingleResultNonTypedQuery(String query,
			Map<String, Object> args, boolean cached) {
		Query nontTypedQuery = buildQuery(query, args, cached);
		return nontTypedQuery.getSingleResult();
	}

	public Object executeMultipleResultNonTypedQuery(String query,
			Map<String, Object> args, boolean cached) {
		Query nontTypedQuery = buildQuery(query, args, cached);
		return nontTypedQuery.getResultList();
	}
	private Query buildQuery(String query, Map<String, Object> args, boolean cached) {
		if (null == query) {
			LOGGER.error("Query is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Query is mandatory. Null value is forbidden.");
		}

		Query nonTypedQuery = getEm().createQuery(query);
//		((org.hibernate.ejb.QueryImpl) nonTypedQuery).getHibernateQuery() .setCacheable(cached);
		nonTypedQuery.setHint("org.hibernate.cacheable", cached);
		populateQueryParameters(args, nonTypedQuery);

		return nonTypedQuery;
	}
	private void populateQueryParameters(Map<String, Object> args,
			Query nonTypedQuery) {
		if (args != null) {
			for (Map.Entry<String, Object> entry : args.entrySet()) {
				nonTypedQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	public ENTITY_TYPE executeSingleResultQuery(String query,
			Map<String, Object> args, Class<ENTITY_TYPE> clazz, boolean cached) {

		TypedQuery<ENTITY_TYPE> typedQuery = buildQuery(query, args, clazz, cached);
		return typedQuery.getSingleResult();
	}

	public List<ENTITY_TYPE> executeMultipleResultQuery(String query,
			Map<String, Object> args, Class<ENTITY_TYPE> clazz, boolean cached) {

		TypedQuery<ENTITY_TYPE> executeTypedQuery = buildQuery(query, args,
				clazz, cached);

		return executeTypedQuery.getResultList();
	}
	
	public Object executeMultipleResultQuery(String query,
			Map<String, Object> args, boolean cached) {

		Query nontTypedQuery = buildQuery(query, args, cached);

		return nontTypedQuery.getResultList();
	}
	
	public int executeUpdate(String query,
			Map<String, Object> args, boolean cached) {

		Query nontTypedQuery = buildQuery(query, args, cached);

		return nontTypedQuery.executeUpdate();
	}
	
	public Object executeMultipleResultQueryNonTyped(String query,
			Map<String, Object> args, int page, int nbEntities, boolean cached) {

		Query executeQuery = buildQuery(query, args, cached);
		
		int beginRow = computeBeginRow(page, nbEntities);
		
		executeQuery.setFirstResult(beginRow) ;
		executeQuery.setMaxResults(nbEntities) ;

		return executeQuery.getResultList();
	}
	
	public List<ENTITY_TYPE> executeMultipleResultQuery(String query,
			Map<String, Object> args, Class<ENTITY_TYPE> clazz, int page, int nbEntities, boolean cached) {

		TypedQuery<ENTITY_TYPE> executeTypedQuery = buildQuery(query, args,
				clazz, cached);
		
		int beginRow = computeBeginRow(page, nbEntities);
		
		executeTypedQuery.setFirstResult(beginRow);
		executeTypedQuery.setMaxResults(nbEntities);

		return executeTypedQuery.getResultList();
	}

	private int computeBeginRow(int page, int nbEntities) {
		int beginRow;
		if(page <= 0){
			throw new IllegalArgumentException("Page must be strictly greater than 0");
		}
		else{
			beginRow = (page - 1) * nbEntities ;
		}
		return beginRow;
	}

	private TypedQuery<ENTITY_TYPE> buildQuery(String query,
			Map<String, Object> args, Class<ENTITY_TYPE> clazz, boolean cached) {
		if (null == query) {
			LOGGER.error("Query is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Query is mandatory. Null value is forbidden.");
		}

		TypedQuery<ENTITY_TYPE> typedQuery = getEm().createQuery(query, clazz);
//		((org.hibernate.ejb.QueryImpl) typedQuery).getHibernateQuery().setCacheable(cached);
		populateQueryParameters(args, typedQuery);

		return typedQuery;
	}

	@Override
	public void lock(ENTITY_TYPE entity) {
		if (null == entity) {
			LOGGER.error("Entity is mandatory. Null value is forbidden.");
			throw new IllegalArgumentException(
					"Entity is mandatory. Null value is forbidden.");
		}
		Session session = (Session) em.getDelegate();
		try {
			session.buildLockRequest(LockOptions.NONE).lock(entity);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private Class<ENTITY_TYPE> getDomainEntityClazz() {
		Class<ENTITY_TYPE> DomainEntityClass = null;
		Class<Type> type = (Class<Type>) this.getClass().getGenericSuperclass()
				.getClass();
		Field field = null;
		try {
			field = type.getDeclaredField("actualTypeArguments");
			field.setAccessible(true);
			DomainEntityClass = (Class<ENTITY_TYPE>) ((Type[]) field.get(this
					.getClass().getGenericSuperclass()))[0];

		} catch (Exception e) {
			e.printStackTrace();
		}
		return DomainEntityClass;
	}
}
