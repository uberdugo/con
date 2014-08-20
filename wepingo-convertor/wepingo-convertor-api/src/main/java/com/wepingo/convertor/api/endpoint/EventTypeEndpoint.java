package com.wepingo.convertor.api.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wepingo.convertor.common.domain.EventType;
import com.wepingo.convertor.common.services.EventTypeManager;

@CrossOriginResourceSharing(allowAllOrigins = true, allowHeaders = {
		"Content-Type", "Accept", "X-Requested-With", "Session", "shop_key",
		"id_guest", "origin" })
@Consumes("application/json")
@Produces("application/json")
@Service
public class EventTypeEndpoint {

	@Autowired
	private EventTypeManager eventTypeManager;

	@GET
	@Path("/")
	public EventType getFeed() {
		EventType eventType = eventTypeManager.findById(1L);
		return eventType;
	}

}
