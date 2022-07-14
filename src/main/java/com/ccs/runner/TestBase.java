/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.runner;

import com.browserstack.local.Local;
import com.ccs.masterConfig.*;
import com.ccs.reports.ReportManager;
import com.ccs.utility.ExcelReader;
import com.ccs.utility.Log;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import javax.mail.MessagingException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
@Purpose: This class manages execution flow
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 24/04/2022
*/
public class TestBase implements GlobalVariables{
	
	private static final Logger Logs = Logger.getLogger(TestBase.class.getName());
	public static WebDriver driver;
	public static Properties obj_properties;
	private static Local l;

	@BeforeSuite
	public void configurations(ITestContext context) {
		DOMConfigurator.configure("log4j.xml");
		Logs.info("Setting up Test data Excel sheet");
		Directory directory = new Directory();
		directory.clearFolder(screenshotFolder);
		directory.clearFolder(htmlReportPath);
		ExcelReader.setExcelFile(testDataPath);
		ExcelReader.clearColumnData(testDataSheet, resultColumn, testDataPath);
		obj_properties = propertyLoader(objectRepository);
	}
	@AfterSuite
	public void sendEmail() throws MessagingException {
		//EmailSender.sendAsHtml("mibin.boban@crowncommercial.gov.uk",
				//"Test Automation Report",
				//"<h3>Hi,<br>Please find attached the test automation execution report</br></h3><p>Best regards,<br>Admin</br></p>");
		obj_properties.clear();
	}
	@BeforeMethod(alwaysRun = true)
	@org.testng.annotations.Parameters(value = { "config", "environment" })
	public void initialization(String config_file, String environment) throws Exception {
		WebDriver driver = launchBrowser(config_file,environment);
		DriverManager.getInstance().setDriver(driver);
	}
	
	@AfterMethod(alwaysRun = true)
	public void updateStatus(ITestResult result) throws Exception {
		String status = SKIP;
		if(result.getStatus() == ITestResult.FAILURE) {
			status = FAIL;								
		}
		if(result.getStatus() == ITestResult.SUCCESS) {
			status = PASS;
		}
		Logs.info("Closing all the browser.");
		String testCaseName = (String) SessionDataManager.getInstance().getSessionData("testCaseName");
		ReportManager.endTest();
		DriverManager.getInstance().getDriver().quit();
		Log.endTestCase(testCaseName+" "+status);
		if (l != null) {
			l.stop();
		}
	}
	
	private static WebDriver launchBrowser(String config_file, String environment) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/browserstack.config.json"));
		JSONObject envs = (JSONObject) config.get("environments");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
		Iterator it = envCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
		}

		Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
		it = commonCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (capabilities.getCapability(pair.getKey().toString()) == null) {
				capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
			}
		}

		String username = System.getenv("BROWSERSTACK_USERNAME");
		if (username == null) {
			username = (String) config.get("user");
		}

		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		if (accessKey == null) {
			accessKey = (String) config.get("key");
		}

		if (capabilities.getCapability("browserstack.local") != null
				&& capabilities.getCapability("browserstack.local") == "true") {
			l = new Local();
			Map<String, String> options = new HashMap<String, String>();
			options.put("key", accessKey);
			l.start(options);
		}

		driver = new RemoteWebDriver(
				new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
		return driver;
	}

	public static Properties propertyLoader(String directory){
		obj_properties = new Properties();
		BufferedReader reader;
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		Logs.info("Obj repository:"+directory);
		Logs.info("Obj repository:"+listOfFiles.length);
		for (File file:listOfFiles) {
			try {
				reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
				try {
					obj_properties.load(reader);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("failed to load properties file "+ file.getAbsolutePath());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("properties file not found at " + file.getAbsolutePath());
			}
		}
		return obj_properties;
	}

}
