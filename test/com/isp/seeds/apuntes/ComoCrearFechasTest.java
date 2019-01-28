package com.isp.seeds.apuntes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ComoCrearFechasTest {

	public static void main(String[] args) {
		
		Date d;
		d = new Date(2018,6,1);
		
		Calendar c = Calendar.getInstance();  //Este c representa o instante actual
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		
		c.set(2019, Calendar.JANUARY, 01);
		
		c2.set(Calendar.YEAR, 2019);
		c2.set(Calendar.MONTH, Calendar.JANUARY);
		c2.set(Calendar.DAY_OF_MONTH, 1);
		
		d= c.getTime(); // TRANSFORMAR DE CALENDAR A DATE
		
		c3.setTime(d); // TRAMSFORMAR DE DATE A CALENDAR
		
		
		DateFormat df = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
		String fecha= "2020/05/05";
		
		try {
		Date d2 = df.parse(fecha);
		} catch (ParseException pe) {
			
		}
		
		System.out.println(c.getTime());
		
	}
}
