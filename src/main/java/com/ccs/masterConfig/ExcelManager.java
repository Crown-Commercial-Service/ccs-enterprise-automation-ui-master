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
import java.util.Map;
import com.ccs.utility.ExcelReader;

/*
@Purpose: This class manages excel file operations
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 01/04/2022
*/
public class ExcelManager {

	private static ExcelManager excelReader;
	static Map<Integer, ExcelReader> readerMap = new HashMap<Integer, ExcelReader>();
	
	private ExcelManager() {
		
	}
	
	public static ExcelManager getInstance() {
		if(excelReader == null) {
			excelReader = new ExcelManager();
		}
		return excelReader;
	}
	
	public synchronized void setExcelReader (ExcelReader reader) {
		readerMap.put((int) (long) (Thread.currentThread().getId()), reader);
	}

	public synchronized ExcelReader getExcelReader () {		
		return readerMap.get((int) (long) (Thread.currentThread().getId()));
	}

}
