/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.masterConfig;

/*
@Purpose: This class handles all global variables
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 01/04/2022
*/
public interface GlobalVariables {

	String baseDirectory = System.getProperty("user.dir");
	String configPath = baseDirectory+"/config.properties";

	// Path to test data sheet with test cases to run and test case details
	String testDataPath = baseDirectory+"/src/test/resources/data/TestDataSheet.xlsx";
	String objectRepository = baseDirectory+"/src/test/resources/objectRepository/";
	String testDataSheet = "TestCases";
	int testCaseColumn = 0;
	int testCaseDescriptionColumn = 1;
	int runModeColumn = 2;
	int resultColumn = 3;

	// Columns in Test Case sheet
	int testStepsColumn = 0;
	int testStepDescriptionColumn = 1;
	int keywordColumn = 2;
	int dataColumn = 3;
	int selectorCoulumn = 4;

	String PASS = "PASS";
	String FAIL = "FAIL";
	String SKIP = "SKIP";

	// Wait times
	int implicitWaitTime = 10;
	long objectWaitTime = 30;

	// Screenshot folder and file details
	String screenshotFolder = baseDirectory+"/screenshots/";
	String fileFormat = ".png";
	
	// Report related details 
	String extentConfigFilePath = baseDirectory+"/extent-config.xml";
	String htmlReportPath = baseDirectory+"/target/html-report/";
	String htmlFileName = "index.html";
}
