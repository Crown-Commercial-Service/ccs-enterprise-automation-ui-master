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
import org.testng.Assert;
import org.testng.TestNGException;

import java.time.Duration;

/*
@Purpose: This class contains functions for verifications
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 25/04/2022
*/
public class ManageVerifications extends ElementOperations {

	private WebDriver driver;
	private WebElement element=null;
	private WebDriverWait wait = null;

	public ManageVerifications() {
		driver = DriverManager.getInstance().getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	public void assertTextEqual(String accessType, String accessName, String expected) {
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			String actual = element.getText();
			Assert.assertEquals(expected,actual);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("The expected and actual are not matching for: " + accessName,
					e);
		}
	}
	public void assertAttributeEqual(String accessType, String accessName, String attribute) {
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			String actual = element.getAttribute(attribute.split(";")[0]);
			Assert.assertEquals(attribute.split(";")[1],actual);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("The expected and actual attribute values are not matching for: " + accessName,
					e);
		}
	}
	public void assertTextNotEqual(String accessType, String accessName, String expected) {
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			String actual = element.getText();
			Assert.assertNotEquals(expected,actual);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("The expected and actual are matching for: " + accessName,
					e);
		}
	}
	public void assertPresenceTrue(String accessType, String accessName) {
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			boolean actual = element.isDisplayed();
			Assert.assertTrue(actual);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("The presence of element is not detected for: " + accessName,
					e);
		}
	}
	public void assertPresenceFalse(String accessType, String accessName) {
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			boolean actual = element.isDisplayed();
			Assert.assertFalse(actual);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("The presence of element is detected for: " + accessName,
					e);
		}
	}
	public void assertElementDisabled(String accessType, String accessName) {
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			boolean actual = element.isEnabled();
			Assert.assertFalse(actual);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("The element is enabled: " + accessName,
					e);
		}
	}
	public void assertCheckboxSelected(String accessType, String accessName,String value) {
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			boolean actual = element.isSelected();
			boolean expected = Boolean.parseBoolean(value);
			Assert.assertEquals(actual, expected);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("The assertion failed: " + accessName,
					e);
		}
	}

}
