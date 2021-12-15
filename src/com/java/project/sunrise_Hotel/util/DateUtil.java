package com.java.project.sunrise_Hotel.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil extends Date{
	public static Date convertToDate(String date) {
		try {
			String regexPattern = "(0?[1-9]|[12][0-9]|3[01]){1}\\/(0?[1-9]|1[0-2]){1}\\/(1[0-9]|20[0-9]{2})";
			Pattern pat = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
			Matcher mat = pat.matcher(date);
			if (mat.find()) {
				Date a = new SimpleDateFormat("dd/MM/yyyy").parse(date);
				return a;
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static boolean compareDate(Date date) {
		Date todayDate = new Date();
		if(todayDate.getDay() == date.getDay() && todayDate.getMonth() == date.getMonth() && todayDate.getYear() == date.getYear())
			return true;
		return false;
	}
}
