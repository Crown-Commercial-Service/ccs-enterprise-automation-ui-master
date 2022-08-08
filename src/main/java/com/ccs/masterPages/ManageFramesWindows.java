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
import org.testng.TestNGException;

import java.time.Duration;
import java.util.ArrayList;

/*
@Purpose: This class contains functions for frames and windows
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 27/04/2022
*/
public class ManageFramesWindows extends ElementOperations {

	private WebDriver driver;
	private WebElement element=null;
	private WebDriverWait wait = null;

	public ManageFramesWindows() {
		driver = DriverManager.getInstance().getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	public void switchToWindow(String windowTitle) {
		ArrayList<String> window = new ArrayList<String>(driver.getWindowHandles());
		for (int x = 0; x <= window.size() - 1; x++) {
			driver.switchTo().window(window.get(x));
			if (driver.getTitle().trim().equalsIgnoreCase(windowTitle)) {
				break;
			} else if (x == window.size()) {
				throw new TestNGException("Window with title " + windowTitle + " not found");
			}
		}
	}
	public void switchToParentWindow() {
		try{
			driver.switchTo().defaultContent();
		}catch (Exception e){
			e.printStackTrace();
			throw new TestNGException("Not able to switch to parent window", e);
		}
	}
	public void switchToParentFrame() {
		try{
			driver.switchTo().parentFrame();
		}catch (Exception e){
			e.printStackTrace();
			throw new TestNGException("Not able to switch to parent frame", e);
		}
	}
	public void switchToFrame(String frameName) {
		try{
			driver.switchTo().frame(frameName);
		}catch (Exception e){
			e.printStackTrace();
			throw new TestNGException("Not able to switch to frame:"+frameName, e);
		}
	}



}
