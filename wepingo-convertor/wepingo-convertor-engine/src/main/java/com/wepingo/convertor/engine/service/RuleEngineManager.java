package com.wepingo.convertor.engine.service;

import java.util.List;
import java.util.Map;

public class RuleEngineManager {
	
	private Map<String, UserBusinessState> mCurrentBusinessStates;
	
	public void addBusinessState(UserBusinessState userBusinessState){
			mCurrentBusinessStates.put(userBusinessState.getLabel(), userBusinessState);
	}
	
	public RuleEngineManager() {
	}
	public boolean FireUserAction(UserActionRulable userActionRulable){
		return false;
	}
	public List<BusinessActionRulable> getComputedBusinessActions(){
		return null;
	}
	
	
}
