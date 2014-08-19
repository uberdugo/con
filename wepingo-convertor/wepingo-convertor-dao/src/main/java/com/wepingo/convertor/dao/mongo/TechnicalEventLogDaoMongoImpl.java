package com.wepingo.convertor.dao.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.wepingo.convertor.common.domain.TechnicalEventLog;
import com.wepingo.convertor.dao.api.TechnicalEventLogDao;

@Repository("TechnicalEventLogDao")
public class TechnicalEventLogDaoMongoImpl implements TechnicalEventLogDao{

	@Autowired
	private MongoOperations mongoOps;
	

	@Override
	public TechnicalEventLog insert(TechnicalEventLog entity) {
		this.mongoOps.save(entity);
		return entity;
	}

	@Override
	public TechnicalEventLog update(TechnicalEventLog entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TechnicalEventLog persist(TechnicalEventLog entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TechnicalEventLog merge(TechnicalEventLog entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(TechnicalEventLog entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TechnicalEventLog selectById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TechnicalEventLog selectByLabel(String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TechnicalEventLog selectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TechnicalEventLog> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lock(TechnicalEventLog entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forceRemove(TechnicalEventLog entity) {
		// TODO Auto-generated method stub
		
	}
	
}
