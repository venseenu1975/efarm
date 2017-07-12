package com.farm.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
public static void main(String[] args) throws ParseException {
	String dateStr = "Tue Jul 11 10:35:00 IST 2017";
	DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
	Date date = (Date)formatter.parse(dateStr);
	//new Timestamp(new java.text.SimpleDateFormat("MM/dd/yyyy").parse(dateStr).getTime());
	System.out.println(new Timestamp(new java.text.SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(dateStr).getTime()));        

	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
	System.out.println("formatedDate : " + formatedDate);    
}
}
