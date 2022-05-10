/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.masterConfig;

import java.lang.reflect.Method;
import java.util.Map;
import org.apache.log4j.Logger;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.ccs.masterActions.ActionsClass;
import com.ccs.masterExceptions.InvalidKeywordException;
import com.ccs.reports.ReportManager;
import com.ccs.utility.ExcelReader;
import com.ccs.utility.ScreenshotUtility;

/*
@Purpose: This class act as master test executor
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 01/04/2022
*/
public class Executor implements GlobalVariables{

	private static final Logger Log = Logger.getLogger(Executor.class.getName());

	public Map<Integer, String> getListOfTestCasesToExecute() {
		Log.info("Getting List of Test Cases to execute.");
		return new ExcelReader().getTestCasesToRun(testDataSheet, runModeColumn, testCaseColumn);
	}

	public void executeTestCase(String testCaseID) throws Exception {
		String keyword;
		String value;
		String stepDesc;
		String selector;
		String[] dataArray = new String[2];
		try {
			int numberOfRows = ExcelReader.getNumberOfRows(testCaseID);
			for (int iRow = 1; iRow < numberOfRows; iRow++){
				keyword = ExcelManager.getInstance().getExcelReader().getCellData(iRow, keywordColumn, testCaseID);
				value = ExcelManager.getInstance().getExcelReader().getCellData(iRow, dataColumn, testCaseID);
				stepDesc = ExcelManager.getInstance().getExcelReader().getCellData(iRow, testStepDescriptionColumn, testCaseID);			
				selector = ExcelManager.getInstance().getExcelReader().getCellData(iRow, selectorCoulumn, testCaseID);
				Log.info("Keyword: "+keyword+" Value: "+ value);
				try {
					executeAction(keyword, value,selector);
				} catch (Exception e) {
					Log.error("Exception Occurred while executing the step:\n", e);					
					String imageFilePath = ScreenshotUtility.takeScreenShot(DriverManager.getInstance().getDriver(), testCaseID);
					ReportManager.getTest().fail(stepDesc+"<br/>Keyword: "+keyword+" | Value: "+ value+"| Element: "+ selector, MediaEntityBuilder.createScreenCaptureFromPath(imageFilePath).build());
					ReportManager.getTest().info(e);
					throw e;
				}
				if(stepDesc !=null && !stepDesc.isEmpty() ) {
					ReportManager.getTest().pass(stepDesc+"<br/>Keyword: "+keyword+" | Value: "+ value+"| Element: "+ selector);
				}
			}
		} catch (Exception e) {
			int testCaseRow = (Integer) SessionDataManager.getInstance().getSessionData("testCaseRow");
			ExcelManager.getInstance().getExcelReader().setCellData(FAIL, testCaseRow, resultColumn, testDataPath, testDataSheet);
			throw e;
		}
	}

	private void executeAction(String keyword, String value, String selector) throws Exception {
		ActionsClass keywordActions = new ActionsClass();
		Method method[] = keywordActions.getClass().getMethods();
		boolean keywordFound = false;
		for(int i = 0;i < method.length;i++){
			if(method[i].getName().equals(keyword)){
				method[i].invoke(keywordActions,value,selector);
				keywordFound = true;
				break;
			}
		}
		if(!keywordFound) {
			throw new InvalidKeywordException("Invalid Keyword "+keyword);
		}
	}
}
