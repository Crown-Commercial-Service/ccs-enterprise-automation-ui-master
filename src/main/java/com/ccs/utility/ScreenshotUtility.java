/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.utility;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.ccs.masterConfig.GlobalVariables;

/*
@Purpose: This class manages screenshot
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 24/04/2022
*/
public class ScreenshotUtility implements GlobalVariables{
	private static final Logger log = Logger.getLogger(ScreenshotUtility.class.getName());
	
	public static String takeScreenShot(WebDriver driver,String testCaseName){
		// Take screenshot and store as a file format
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String screenShotFilePath = screenshotFolder+testCaseName+DateUtility.getStringDate("_ddMMyyyy_HHmmss")+fileFormat;
		try{
			FileUtils.copyFile(src, new File(screenShotFilePath));
		}
		catch(Exception exp){
			log.error("Exception occured in takeScreenShot: "+exp.getMessage());
		}
		return screenShotFilePath;
	}
}
