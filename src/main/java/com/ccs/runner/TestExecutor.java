/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */

package com.ccs.runner;

import com.ccs.masterConfig.ExcelManager;
import com.ccs.masterConfig.Executor;
import com.ccs.masterConfig.SessionDataManager;
import com.ccs.reports.ReportManager;
import com.ccs.utility.DateUtility;
import com.ccs.utility.ExcelReader;
import com.ccs.utility.Log;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.Map.Entry;

/*
@Purpose: This class manages test selection and execution
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 15/04/2022
*/
public class TestExecutor extends TestBase{

	@Test (dataProvider = "testCasesList")
	public void testCasesExecutor(int testRow, String testCase) throws Exception {
		SessionDataManager.getInstance().setSessionData("testStartTime", DateUtility.getStringDate("hh.mm.ss aaa"));
		ExcelReader reader = new ExcelReader();
		ExcelManager.getInstance().setExcelReader(reader);
		Executor executor = new Executor();		
		SessionDataManager.getInstance().setSessionData("testCaseName", testCase);
		SessionDataManager.getInstance().setSessionData("testCaseRow", testRow);
		Log.startTestCase(testCase);
		ReportManager.startTest(testCase);
		executor.executeTestCase(testCase);
		ExcelManager.getInstance().getExcelReader().setCellData(PASS, testRow, resultColumn, testDataPath, testDataSheet);
	}

	@DataProvider (name = "testCasesList", parallel = true)
	public Object[][] getTestCaseList(){
		Executor executor = new Executor();
		Map<Integer, String> mapOfTestCases = executor.getListOfTestCasesToExecute();
		Object[][] testCaseList = new Object[mapOfTestCases.size()][2];
		int i=0;
		for (Entry<Integer, String> mapIterator: mapOfTestCases.entrySet()) {
			testCaseList[i][0] = mapIterator.getKey(); 
			testCaseList[i][1] = mapIterator.getValue(); 
			i++;
		}			
		return testCaseList;
	}
}
