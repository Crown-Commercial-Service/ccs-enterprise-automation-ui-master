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
import com.ccs.utility.ElementOperations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
@Purpose: This class contains functions for web navigations
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 20/04/2022
*/
public class ManageNavigations extends ElementOperations {

	private WebDriver driver;
	private WebElement element=null;
	private WebDriverWait wait = null;

	public ManageNavigations() {
		driver = DriverManager.getInstance().getDriver();
		wait = new WebDriverWait(driver, 30);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	public void navigateTo(String url)
	{
		driver.get(url);
	}

}
