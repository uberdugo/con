package com.wepingo.convertor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wepingo.convertor.common.domain.EventType;
import com.wepingo.convertor.common.services.EventTypeManager;
import com.wepingo.convertor.dao.api.EventTypeDao;


@Service("EventTypeManager")
@Scope("prototype")
public class EventTypeManagerImpl extends AbstractManagerImpl<EventTypeDao, EventType> implements EventTypeManager{

	@Autowired
	private EventTypeDao dao ;

	@Override
	public EventTypeDao getDao() {
		return dao ;
	}
}