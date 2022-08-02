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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/*
@Purpose: This class manages execution flow
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 24/04/2022
*/
public class TestBase implements GlobalVariables{

	private static final Logger Logs = Logger.getLogger(TestBase.class.getName());
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<WebDriver>();
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
	@BeforeMethod
	@org.testng.annotations.Parameters(value = { "config", "environment" })
	public void initialization(String config_file, String environment) throws Exception {
		String mode = Config.getProperty("mode");
		if (mode.equalsIgnoreCase("LOCAL")){
			WebDriver driver = launchBrowser();
			DriverManager.getInstance().setDriver(driver);
		}
		else{
			WebDriver driver = launchBrowser(config_file,environment);
			DriverManager.getInstance().setDriver(driver);
		}

	}

	@AfterMethod
	public void updateStatus(ITestResult result) throws Exception {
		String status = SKIP;
		JavascriptExecutor jse = (JavascriptExecutor)driver.get();
		if(result.getStatus() == ITestResult.FAILURE) {
			status = FAIL;
			jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Test Failed, Check details\"}}");
		}
		if(result.getStatus() == ITestResult.SUCCESS) {
			status = PASS;
			jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test Successful\"}}");

		}
		Logs.info("Closing all the browser.");
		String testCaseName = (String) SessionDataManager.getInstance().getSessionData("testCaseName");
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\":\""+testCaseName+"\" }}");

		ReportManager.endTest();
		DriverManager.getInstance().getDriver().quit();
		Log.endTestCase(testCaseName+" "+status);

		if (l != null) {
			l.stop();
		}
	}

	private static WebDriver launchBrowser(String config_file, String environment) throws Exception {
		String url = Config.getProperty("url");
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/"+ config_file));
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
		if (capabilities.getCapability("browser")!=null){
			SessionDataManager.getInstance().setSessionData("envName", capabilities.getCapability("browser"));
		}
		if (capabilities.getCapability("os")!=null){
			SessionDataManager.getInstance().setSessionData("osName", capabilities.getCapability("os"));
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(cal.getTime());
		if (capabilities.getCapability("build")!=null) {
			capabilities.setCapability("build",capabilities.getCapability("build")+strDate);
		}
		/**String username = System.getenv("BROWSERSTACK_USERNAME");
		if (username == null) {
			username = (String) config.get("user");
		}*/

		String username = System.getProperty("bs.user");
		String accessKey = System.getProperty("bs.key");
		if (username == null) {
			username = (String) config.get("user");
		}
		if (accessKey == null) {
			accessKey = (String) config.get("key");
		}

		/**String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		if (accessKey == null) {
			accessKey = (String) config.get("key");
		}*/

		if (capabilities.getCapability("browserstack.local") != null
				&& capabilities.getCapability("browserstack.local") == "true") {
			l = new Local();
			Map<String, String> options = new HashMap<String, String>();
			options.put("key", accessKey);
			l.start(options);
		}

		capabilities.setCapability("browserstack.idleTimeout", "240");
		Logs.info("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub");
		driver.set(new RemoteWebDriver(
				new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities));
		driver.get().manage().window().maximize();
		driver.get().navigate().to(url);
		return driver.get();
	}
	private static WebDriver launchBrowser() throws Exception {
		String browser = Config.getProperty("browser");
		String url = Config.getProperty("url");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setAcceptInsecureCerts(true);
		chromeOptions.setExperimentalOption("useAutomationExtension", false);
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		driver.set(new RemoteWebDriver(new URL("http://localhost:4444/"), chromeOptions));
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

