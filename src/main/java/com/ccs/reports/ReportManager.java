/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.reports;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.ccs.masterConfig.Config;
import com.ccs.masterConfig.GlobalVariables;

/*
@Purpose: This class manages report generation
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 01/04/2022
*/
public class ReportManager implements GlobalVariables{
	private static ExtentReports extent;
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	private static ExtentReports createInstance() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(htmlReportPath+htmlFileName);
		htmlReporter.loadXMLConfig(extentConfigFilePath);
		//htmlReporter.loadXMLConfig(new File(extentConfigFilePath));
		extent = new ExtentReports();
		extent.setSystemInfo("Framework: ", "CCS Enterprise Automation-Web");
		extent.setSystemInfo("Author: ", "Mibin Boban");
		extent.setSystemInfo("Executed By: ", System.getProperty("user.name"));
		extent.setSystemInfo("URL: ", Config.getProperty("url"));
		extent.attachReporter(htmlReporter); 
		return extent;
	}

	private static ExtentReports getInstance() {
		if (extent == null) {
			createInstance();
		}
		return extent;
	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void endTest() {
		getInstance().flush();
	}

	public static synchronized ExtentTest startTest(String testName) {
		ExtentTest test = getInstance().createTest(testName);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}

}