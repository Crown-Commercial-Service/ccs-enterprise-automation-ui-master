/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.masterActions;

import com.ccs.masterConfig.SessionDataManager;
import com.ccs.masterPages.*;
import org.apache.log4j.Logger;
import com.ccs.masterConfig.GlobalVariables;
import com.ccs.masterExceptions.InvalidLocatorException;
import static com.ccs.runner.TestBase.obj_properties;

/*
@Purpose: This class manages all master actions can be performed by user
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 01/04/2022
*/
public class ActionsClass implements GlobalVariables{
	private static final Logger Log = Logger.getLogger(ActionsClass.class.getName());

	private ManageElements mngElemPage = new ManageElements();
	private ManageNavigations navigationPage = new ManageNavigations();
	private ManageDropdowns mngDropdownPage = new ManageDropdowns();
	private ManageFramesWindows mngFrmWndwPage = new ManageFramesWindows();
	private ManageVerifications mngVerifications = new ManageVerifications();
	private ManageTextFields mngTextFieldPage = new ManageTextFields();
	private ManageSessionData mngSessionData = new ManageSessionData();

	public void NavigateTo(String value, String selector) {
		navigationPage.navigateTo(value);
	}

	public void ClickOnElement(String value, String selector) throws InvalidLocatorException {
		mngElemPage.clickOnElement("xpath",obj_properties.getProperty(selector));
	}

	public void HighlightElement(String value, String selector) throws InvalidLocatorException {
		mngElemPage.elementHighlight("xpath",obj_properties.getProperty(selector));
	}

	public void HoverOnElement(String value, String selector) throws InvalidLocatorException {
		mngElemPage.hoverOnElement("xpath",obj_properties.getProperty(selector));
	}

	public void WaitForElemPresence(String value, String selector) throws InvalidLocatorException {
		mngElemPage.waitForElementPresence("xpath",obj_properties.getProperty(selector));
	}

	public void WaitForElemAbsence(String value, String selector) throws InvalidLocatorException {
		mngElemPage.waitForElementInvisiblity("xpath",obj_properties.getProperty(selector));
	}

	public void WaitForElemClickable(String value, String selector) throws InvalidLocatorException {
		mngElemPage.waitForElementToBeClickable("xpath",obj_properties.getProperty(selector));
	}

	public void GetText(String value, String selector) throws InvalidLocatorException {
		String elemText = mngElemPage.getText("xpath",obj_properties.getProperty(selector));
		SessionDataManager.getInstance().setSessionData(selector,elemText);
	}

	public void GetAttribute(String value, String selector) throws InvalidLocatorException {
		String elemAttr = mngElemPage.getAttribute("xpath",obj_properties.getProperty(selector),value);
		SessionDataManager.getInstance().setSessionData(selector,elemAttr);
	}

	public void GetCount(String value, String selector) throws InvalidLocatorException {
		int elemCount = mngElemPage.getElementCount("xpath",obj_properties.getProperty(selector));
		SessionDataManager.getInstance().setSessionData(selector,elemCount);
	}

	public void ScrollIntoView(String value, String selector) throws InvalidLocatorException {
		mngElemPage.scrollIntoView("xpath",obj_properties.getProperty(selector));
	}

	public void DoubleClick(String value, String selector) throws InvalidLocatorException {
		mngElemPage.doubleClick("xpath",obj_properties.getProperty(selector));
	}

	public void SelectOption(String value, String selector) throws InvalidLocatorException {
		mngDropdownPage.selectOption("xpath",obj_properties.getProperty(selector),value);
	}

	public void SelectContains(String value, String selector) throws InvalidLocatorException {
		mngDropdownPage.selectContains("xpath",obj_properties.getProperty(selector),value);
	}

	public void SwitchToWindow(String value, String selector) throws InvalidLocatorException {
		mngFrmWndwPage.switchToWindow(value);
	}

	public void SwitchToParentWindow(String value, String selector) throws InvalidLocatorException {
		mngFrmWndwPage.switchToParentWindow();
	}

	public void SwitchToParentFrame(String value, String selector) throws InvalidLocatorException {
		mngFrmWndwPage.switchToParentFrame();
	}

	public void SwitchToFrame(String value, String selector) throws InvalidLocatorException {
		mngFrmWndwPage.switchToFrame(value);
	}

	public void VerifyTextEqual(String value, String selector) throws InvalidLocatorException {
		mngVerifications.assertTextEqual("xpath",obj_properties.getProperty(selector),value);
	}

	public void VerifyTextNotEqual(String value, String selector) throws InvalidLocatorException {
		mngVerifications.assertTextNotEqual("xpath",obj_properties.getProperty(selector),value);
	}

	public void VerifyElemPresent(String value, String selector) throws InvalidLocatorException {
		mngVerifications.assertPresenceTrue("xpath",obj_properties.getProperty(selector));
	}

	public void VerifyElemDisabled(String value, String selector) throws InvalidLocatorException {
		mngVerifications.assertElementDisabled("xpath",obj_properties.getProperty(selector));
	}

	public void VerifyElemAttribute(String value, String selector) throws InvalidLocatorException {
		mngVerifications.assertAttributeEqual("xpath",obj_properties.getProperty(selector),value);
	}

	public void VerifyElemAbsent(String value, String selector) throws InvalidLocatorException {
		mngVerifications.assertPresenceFalse("xpath",obj_properties.getProperty(selector));
	}

	public void EnterValue(String value, String selector) throws InvalidLocatorException {
		mngTextFieldPage.enterValue("xpath",obj_properties.getProperty(selector),value);
	}
	public void EnterRandomEmail(String value, String selector) throws InvalidLocatorException {
		mngTextFieldPage.enterRandomEmail("xpath",obj_properties.getProperty(selector),value);
	}
	public void EnterRandomText(String value, String selector) throws InvalidLocatorException {
		mngTextFieldPage.enterRandomText("xpath",obj_properties.getProperty(selector));
	}
	public void ClearValue(String value, String selector) throws InvalidLocatorException {
		mngTextFieldPage.clearValue("xpath",obj_properties.getProperty(selector));
	}

	public void SaveElemText(String value, String selector) throws InvalidLocatorException {
		mngSessionData.saveElemText("xpath",obj_properties.getProperty(selector),selector);
	}

	public void SaveValue(String value, String selector) throws InvalidLocatorException {
		SessionDataManager.getInstance().setSessionData(selector,value);
	}

	public void VerifyWithSavedValue(String value, String selector) throws InvalidLocatorException {
		mngSessionData.verifyTextWithSavedData("xpath",obj_properties.getProperty(selector),value);
	}

	public void EnterSavedValue(String value, String selector) throws InvalidLocatorException {
		mngSessionData.enterTextWithSavedData("xpath",obj_properties.getProperty(selector),value);
	}

}
