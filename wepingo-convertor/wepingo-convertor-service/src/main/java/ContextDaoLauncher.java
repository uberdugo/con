import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wepingo.convertor.common.domain.EventType;
import com.wepingo.convertor.common.services.EventTypeManager;


public class ContextDaoLauncher {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:ApplicationContextService.xml");
		EventTypeManager eventTypeManager = (EventTypeManager) ctx.getBean("EventTypeManager");
		EventType eventType = eventTypeManager.findById(1L);
		eventType.setName("mouseleave");
		eventTypeManager.modify(eventType);
		ctx.close();
	}
	
}
