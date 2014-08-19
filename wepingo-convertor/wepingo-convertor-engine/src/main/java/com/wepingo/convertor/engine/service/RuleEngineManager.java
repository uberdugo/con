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
		// 1) Computing relative user action value 
		UserActionStatisticScope relativeScope;
		Double relativeValue = this.computeUserActionRelativeValue(userActionRulable);
		
		// 2) Ask for all Business states
		// 3) 
		
		
		
		return false;
	}
	private Double computeUserActionRelativeValue(
			UserActionRulable userActionRulable) {
		
		
		return .0;
	}

	public List<BusinessActionRulable> getComputedBusinessActions(){
		return null;
	}
	
}
