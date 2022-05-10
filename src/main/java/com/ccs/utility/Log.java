/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.utility;

import org.apache.log4j.Logger;

/*
@Purpose: This class manages log creation
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 24/04/2022
*/
public class Log {

	//Initialize Log4j logs
	private static final Logger Log = Logger.getLogger(Log.class.getName());//

	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	public static synchronized void startTestCase(String testCaseName){
		Log.info("********************************CCS Enterprise Test Automation***************************************");
		Log.info("****************************************************************************************");
		Log.info("\t\t\t\t"+testCaseName.toUpperCase()+ " START");
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
	}
	
	public static synchronized void endTestCase(String testCaseName){
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("\t\t\t\t"+testCaseName.toUpperCase());
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
	}

}
