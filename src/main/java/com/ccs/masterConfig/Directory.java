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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

/*
@Purpose: This class manages the directory structure and folders
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 15/04/2022
*/
public class Directory {
	private static final Logger log = Logger.getLogger(Directory.class.getName());

	public boolean create(String folderPath) {
		Path path = Paths.get(folderPath);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				log.info("Directory was created\n"+folderPath);
				return true;
			} catch (IOException exp) {
				log.error(exp.getMessage(), exp);
				return false;
			}
		}
		return true;
	}

	public void delete(String folderPath) {
		File folder = new File(folderPath);
		if(folder.exists()) {
			for(String file: folder.list()){
				File currentFile = new File(folder.getPath(),file);
				currentFile.delete();
			}
		}
	}

	public void clearFolder(String folderPath) {
		log.info("Clearing content in below folder: \n"+folderPath);
		delete(folderPath);
		create(folderPath);
	}
}
