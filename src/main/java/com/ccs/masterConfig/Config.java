/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.masterConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/*
@Purpose: This class manages the required configurations
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 20/04/2022
*/
public class Config implements GlobalVariables{	
	private static Properties properties = new Properties();
	private static final Logger Log = Logger.getLogger(Config.class.getName());
	
	public static String getProperty(String key) {
		try {
			InputStream stream = new FileInputStream(new File(configPath));
			properties.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			Log.error("File was Not Found: "+e.getMessage());
		} catch (IOException e) {
			Log.error("There was a IO Exception: ", e);
		} 
		return properties.getProperty(key);
	}
}
