/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.runner;

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
import java.util.Properties;

/*
@Purpose: This class manages execution flow
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 24/04/2022
*/
public class TestBase implements GlobalVariables{
	
	private static final Logger Logs = Logger.getLogger(TestBase.class.getName());
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<WebDriver>();
	public static Properties obj_properties;

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
	@BeforeMethod
	public void initialization() throws MalformedURLException {
		WebDriver driver = launchBrowser();
		DriverManager.getInstance().setDriver(driver);
	}
	
	@AfterMethod
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
	}
	
	private static WebDriver launchBrowser() throws MalformedURLException {
		String browser = Config.getProperty("browser");
		String url = Config.getProperty("url");
		if(browser.equalsIgnoreCase("CHROME")) {
			DesiredCapabilities dr=null;
			if(browser.equals("firefox")){
				FirefoxOptions ffOptions = new FirefoxOptions();
				driver.set(new RemoteWebDriver(new URL("http://localhost:4444/"), ffOptions));
			}else{
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setAcceptInsecureCerts(true);
				chromeOptions.setExperimentalOption("useAutomationExtension", false);
				chromeOptions.addArguments("--no-sandbox");
				chromeOptions.addArguments("--disable-dev-shm-usage");
				driver.set(new RemoteWebDriver(new URL("http://localhost:4444/"), chromeOptions));
			}

		}
		driver.get().manage().window().maximize();
		driver.get().navigate().to(url);
		return driver.get();
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
