package com.wepingo.convertor.engine.service;

public interface UserActionStatisticRulable {
	public UserActionStatisticScope getUserActionStatisticScope();
	public UserActionStatisticType getActionStatisticType();
	public Double getAverage();
	public Double getStandardDeviation();
}
