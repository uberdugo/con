package com.wepingo.convertor.engine.service;

import java.util.List;

public interface UserActionRulable {
	public UserRulable getUser();
	public String getName();
	public List <UserActionStatisticRulable> getUserActionStatistics();
}
