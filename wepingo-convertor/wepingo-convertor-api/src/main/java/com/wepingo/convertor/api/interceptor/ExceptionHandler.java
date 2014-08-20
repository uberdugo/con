package com.wepingo.convertor.api.interceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

public class ExceptionHandler implements ExceptionMapper<Exception> {

	static final private Logger LOGGER = Logger.getLogger(ExceptionHandler.class);

	@Override
	public Response toResponse(Exception exception) {
		LOGGER.error("Wepingo Error: ", exception);
		if (exception instanceof WebApplicationException)
			return ((WebApplicationException) exception).getResponse();
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
    
}
