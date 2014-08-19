package com.wepingo.convertor.engine.service;

import java.util.List;

public interface UserActionRulable {
	public UserRulable getUser();
	public String getLabel();
	public Double getAbsoluteValue();

	
	public List <UserActionStatisticRulable> getUserActionStatistics();
	public boolean isStatisticAvailable(UserActionStatisticScope userActionStatisticScope);
	public UserActionStatisticRulable getUserActionStatisticRulable(UserActionStatisticScope userActionStatisticScope);
}
