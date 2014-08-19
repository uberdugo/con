package com.wepingo.convertor.common.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wepingo.convertor.common.domain.api.DomainEntity;

@Document(collection="technicalEventLog")
public class TechnicalEventLog implements DomainEntity {
	
	private Long technicalEventId ;
	
	@Override
	public Long getId() {
		return null;
	}

	public Long getTechnicalEventId() {
		return technicalEventId;
	}

	public void setTechnicalEventId(Long technicalEventId) {
		this.technicalEventId = technicalEventId;
	}

}
