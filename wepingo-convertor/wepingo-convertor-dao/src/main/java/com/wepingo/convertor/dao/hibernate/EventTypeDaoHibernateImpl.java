package com.wepingo.convertor.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wepingo.convertor.common.domain.EventType;
import com.wepingo.convertor.dao.api.EventTypeDao;

@Transactional(readOnly=false)
@Repository("EventTypeDao")
public class EventTypeDaoHibernateImpl extends AbstractDaoHibernateImpl<EventType>  implements EventTypeDao  {

	static final private Logger LOGGER = Logger.getLogger(EventTypeDaoHibernateImpl.class);

	public EventTypeDaoHibernateImpl() {
		super(EventType.class);
	}
	
}
