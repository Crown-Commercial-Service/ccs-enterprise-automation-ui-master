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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNGException;

import java.time.Duration;
import java.util.List;

/*
@Purpose: This class contains functions for dropdowns
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 20/04/2022
*/
public class ManageDropdowns extends ElementOperations {

	private WebDriver driver;
	private WebElement element=null;
	private WebDriverWait wait = null;

	public ManageDropdowns() {
		driver = DriverManager.getInstance().getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	public void selectOption(String accessType, String accessName, String selectOption) {
		try {
			Select select = new Select(driver.findElement(ElementOperations.getElementBy(accessType, accessName)));
			List<WebElement> test = select.getOptions();
			int optionIndex = 0;
			for (int x = 0; x <= test.size() - 1; x++) {
				if (test.get(x).getText().equalsIgnoreCase(selectOption)) {
					optionIndex = x;
					break;
				}
			}
			select.selectByIndex(optionIndex);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("Not able to select the option " + selectOption + " in: " + accessName,
					e);
		}
	}
	public void selectContains(String accessType, String accessName, String selectOption) {

		try {
			Select select = new Select(driver.findElement(ElementOperations.getElementBy(accessType, accessName)));
			List<WebElement> test = select.getOptions();
			for (WebElement Option : test) {
				if (Option.getText().contains(selectOption)) {
					System.out.println("found");
					select.selectByVisibleText(Option.getAttribute("displayvalue"));
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("Not able to select the option " + selectOption + " in :" + accessName,
					e);
		}
	}

}
