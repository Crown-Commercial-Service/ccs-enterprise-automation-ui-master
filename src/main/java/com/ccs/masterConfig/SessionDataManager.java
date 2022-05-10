/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.masterConfig;

import java.util.HashMap;

/*
@Purpose: This class manages the session data at runtime
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 28/04/2022
*/
public class SessionDataManager {

	private static SessionDataManager SESSION_DATA_MANAGER;
	private static ThreadLocal<HashMap<String,Object>> tSessionData = new ThreadLocal<HashMap<String, Object>>() {
		 @Override
		    protected HashMap<String, Object> initialValue() {
		        return new HashMap<>();
		    }
	};
	
	private SessionDataManager() {
		
	}
	
	public static SessionDataManager getInstance(){
		if(SESSION_DATA_MANAGER == null) {
			synchronized (SessionDataManager.class) {
				if(SESSION_DATA_MANAGER == null) {
					SESSION_DATA_MANAGER  = new SessionDataManager();
				}
			}			
		}
		return SESSION_DATA_MANAGER;
	}
	
	public synchronized void setSessionData (String key, Object value) {
		tSessionData.get().put(key, value);
	}

	public synchronized Object getSessionData (String key) {
		return tSessionData.get().get(key);
	}

}
