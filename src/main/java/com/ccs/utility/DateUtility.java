/**
 * Copyright (C) 2021 Crown Commercial Service. All rights reserved This Test Automation Solution is the confidential
 * and proprietary information of Crown Commercial Service. You shall not disclose such confidential information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Crown Commercial Service.
 * Mentor: Anne Vaudrey-McVey, CCS Enterprise Test Manager
 * Author: Mibin Boban, CCS Senior QAT Analyst
 * Development period: March-May, 2022
 */
package com.ccs.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
@Purpose: This class manages execution flow
@Author: Mibin Boban, CCS Senior QAT Analyst
@Creation: 24/04/2022
*/
public class DateUtility {
	
	public static String getStringDate(String dateFormat) {
		SimpleDateFormat dtf = new SimpleDateFormat(dateFormat);  
	    Date localDate = new Date();  
		return dtf.format(localDate).toString();
	}
}
