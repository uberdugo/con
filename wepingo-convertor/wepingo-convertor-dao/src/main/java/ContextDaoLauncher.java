import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wepingo.convertor.common.domain.EventType;
import com.wepingo.convertor.common.domain.TechnicalEventLog;
import com.wepingo.convertor.dao.api.EventTypeDao;
import com.wepingo.convertor.dao.api.TechnicalEventLogDao;


public class ContextDaoLauncher {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:ApplicationContextDao.xml");
		TechnicalEventLogDao technicalEventLogDao = (TechnicalEventLogDao) ctx.getBean("TechnicalEventLogDao");
		EventTypeDao eventTypeDao = (EventTypeDao) ctx.getBean("EventTypeDao");
		TechnicalEventLog eventLog = new TechnicalEventLog();
		eventLog.setTechnicalEventId(1L);
		technicalEventLogDao.insert(eventLog);
		EventType eventType = eventTypeDao.selectById(1L);
		eventType.setName("hover");
		eventTypeDao.merge(eventType);
		ctx.close();
	}
	
}
