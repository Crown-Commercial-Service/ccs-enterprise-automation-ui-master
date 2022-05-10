/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.masterConfig;

import org.openqa.selenium.WebDriver;

/*
@Purpose: This class manages all master actions can be performed by user
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 25/04/2022
*/
public class DriverManager {

	private static DriverManager DRIVER_MANAGER;
	private static ThreadLocal<WebDriver> tDriver = new ThreadLocal<>();

	private DriverManager() {
		
	}
	
	public static DriverManager getInstance(){
		if(DRIVER_MANAGER == null) {
			synchronized (DriverManager.class) {
				if(DRIVER_MANAGER == null) {
					DRIVER_MANAGER  = new DriverManager();
				}
			}			
		}
		return DRIVER_MANAGER;
	}
	
	public synchronized void setDriver (WebDriver driver) {
		tDriver.set(driver);
	}

	public synchronized WebDriver getDriver () {
		WebDriver driver = tDriver.get();
		if (driver == null) {
			throw new IllegalStateException("Driver should have not been null!!");
		}
		return driver;
	}

}
