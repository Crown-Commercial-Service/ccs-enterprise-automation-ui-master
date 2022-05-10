/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.masterPages;

import com.ccs.masterConfig.DriverManager;
import com.ccs.masterConfig.SessionDataManager;
import com.ccs.masterExceptions.InvalidLocatorException;
import com.ccs.utility.ElementOperations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestNGException;

/*
@Purpose: This class contains functions for managing runtime data
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 27/04/2022
*/
public class ManageSessionData extends ElementOperations {

	private WebDriver driver;
	private WebElement element=null;
	private WebDriverWait wait = null;

	public ManageSessionData() {
		driver = DriverManager.getInstance().getDriver();
		wait = new WebDriverWait(driver, 30);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	public void saveElemText(String accessType, String accessName,String key) throws InvalidLocatorException {
		element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
		String value = element.getText();
		SessionDataManager.getInstance().setSessionData(key,value);
	}
	public void verifyTextWithSavedData(String accessType, String accessName,String key) throws InvalidLocatorException {
		try{
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			Assert.assertEquals(element.getText(),SessionDataManager.getInstance().getSessionData(key));
		}catch (Exception e){
			e.printStackTrace();
			throw new TestNGException("Unable to assert the value with: " + key,
					e);
		}

	}
	public void enterTextWithSavedData(String accessType, String accessName,String key) throws InvalidLocatorException {
		try{
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			element.sendKeys((CharSequence) SessionDataManager.getInstance().getSessionData(key));
		}catch (Exception e){
			e.printStackTrace();
			throw new TestNGException("Unable to enter the value with: " + key,
					e);
		}

	}


}
