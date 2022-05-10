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
import com.ccs.masterExceptions.InvalidLocatorException;
import com.ccs.utility.ElementOperations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNGException;
import java.util.List;

/*
@Purpose: This class contains functions for web elements
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 20/04/2022
*/
public class ManageElements extends ElementOperations {

	private WebDriver driver;
	private WebElement element=null;
	private WebDriverWait wait = null;

	public ManageElements() {
		driver = DriverManager.getInstance().getDriver();
		wait = new WebDriverWait(driver, 30);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	public void clickOnElement(String accessType, String accessName) throws InvalidLocatorException {
		try{
			element = wait.until(ExpectedConditions.presenceOfElementLocated(ElementOperations.getElementBy(accessType, accessName)));
			element.click();
		}catch (Exception e){
			throw new TestNGException("Error occured while clicking on " + element.getText(), e);
		}

	}
	public void elementHighlight(String accessType, String accessName) throws InvalidLocatorException {
		element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
		for (int i = 0; i < 5; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: red; border: 3px solid red;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"");
		}
	}
	public void hoverOnElement(String accessType, String accessName) throws InvalidLocatorException {
		try {
			element = wait.until(ExpectedConditions.presenceOfElementLocated(ElementOperations.getElementBy(accessType, accessName)));
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			throw new TestNGException("Error occured while hovering on " + element.getText(), e);
		}
	}
	public void waitForElementPresence(String accessType, String accessName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					30);
			wait.until(ExpectedConditions.presenceOfElementLocated(ElementOperations.getElementBy(accessType, accessName)));
		} catch (Exception e) {
			throw new TestNGException("Element with xpath:"+accessName + " not found in the page.", e);
		}
	}
	public void waitForElementInvisiblity(String accessType, String accessName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					30);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(ElementOperations.getElementBy(accessType, accessName)));
		} catch (Exception e) {
			throw new TestNGException("Element with xpath:"+accessName + " still visible in the page.", e);

		}
	}
	public void waitForElementToBeClickable(String accessType, String accessName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					30);
			wait.until(ExpectedConditions.elementToBeClickable(ElementOperations.getElementBy(accessType, accessName)));
		} catch (Exception e) {
			throw new TestNGException("Element with xpath:"+accessName + " not clickable in the page.", e);
		}
	}
	public String getText(String accessType, String accessName) throws InvalidLocatorException {
		element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
		String textValue = "";
		try {
			waitForElementPresence(accessType, accessName);
			textValue = element.getText();
		} catch (Exception e) {
			throw new TestNGException("Not able to get the text from: " + accessName, e);
		}
		return textValue;
	}
	public String getAttribute(String accessType, String accessName, String attribute) {
		String attrValue = "";
		try {
			element= driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			waitForElementPresence(accessType, accessName);
			attrValue = element.getAttribute(attribute);
		} catch (Exception e) {
			throw new TestNGException("Not able to get the attribute value from " + element.getText(), e);
		}
		return attrValue;
	}
	public Integer getElementCount(String accessType, String accessName) {
		Integer countOfElements = 0;
		try {
			List<WebElement> elements = driver.findElements(ElementOperations.getElementBy(accessType, accessName));
			countOfElements = elements.size();
		} catch (Exception e) {
			throw new TestNGException("Not able to find elements: " + accessName, e);
		}
		return countOfElements;
	}
	public void scrollIntoView(String accessType, String accessName) {
		try {
			waitForElementPresence(accessType, accessName);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(" + true + ");",
					driver.findElement(ElementOperations.getElementBy(accessType, accessName)));
		} catch (Exception e) {
			throw new TestNGException("Not able to find element: " + accessName, e);
		}
	}
	public void doubleClick(String accessType, String accessName) {
		try {
			waitForElementPresence(accessType, accessName);
			Actions actions = new Actions(driver);
			WebElement elementLocator = driver.findElement(ElementOperations.getElementBy(accessType, accessName));
			actions.doubleClick(elementLocator).perform();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestNGException("Not able to double click on: " + accessName, e);
		}
	}

}
