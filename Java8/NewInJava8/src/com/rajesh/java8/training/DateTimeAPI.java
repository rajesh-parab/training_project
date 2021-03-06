package com.rajesh.java8.training;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.Test;

public class DateTimeAPI {

	@Test
	public void period() {
		Period tenDays = Period.between(LocalDate.of(2014, 3, 8), LocalDate.of(2014, 3, 18));
		System.out.println(tenDays.getDays());

	}

	@Test
	public void duration() {
		Duration threeMinutes = Duration.ofMinutes(3);
		System.out.println(threeMinutes.getSeconds());

	}

	@Test
	public void localeDate() {
		LocalDate date = LocalDate.of(2016, 8, 15);
		System.out.println(date.getMonth());
		System.out.println(date.getDayOfWeek());

		date = date.plusYears(2);
		System.out.println(date);
		System.out.println(date.getMonth());
		System.out.println(date.getDayOfWeek());
	}

	@Test
	public void formatDate() {
		LocalDate date = LocalDate.of(2016, 8, 15);

		String strdate = date.format(DateTimeFormatter.ISO_DATE);
		System.out.println(strdate);

		DateTimeFormatter french = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.FRANCE);
		LocalDate date1 = LocalDate.of(2014, 3, 18);
		String formattedDate = date1.format(french);
		System.out.println(formattedDate);
		
		

	}
	@Test
	public void otherCalandar() {
	System.out.println(HijrahDate.now());
	}

}
