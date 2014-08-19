package com.wepingo.convertor.engine.core;

import java.util.ArrayList;
import java.util.List;
import com.wepingo.convertor.engine.service.UserActionRulable;

public class RuleTermManager {
	public static boolean isUserActionManaged(UserActionRulable userActionRulable){
		if(managedUserActions.contains(userActionRulable.getLabel()))
			return true;
		else 
			return false;
	}
	public static List<String> managedUserActions = new ArrayList<String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 8844994000739490024L;

		{
		    add("WebsiteExit");
		    add("ProductHover");
		    add("AddToChart");
		    add("IgnoreRetentionPopup");
		    add("IgnorePromotionPopup");
		}
	};
	
	public static List<String> managedBusinessUserStates = new ArrayList<String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 8844994000739490024L;

		{
		    add("RetentionAffect");
		    add("PromotionAffect");
		    add("ProductAffect");
		    add("WebsiteAffect");
		}
	};
	//Utiliser Queue --> Linked List pour ranger les bitswise numbers par userstate type

}
