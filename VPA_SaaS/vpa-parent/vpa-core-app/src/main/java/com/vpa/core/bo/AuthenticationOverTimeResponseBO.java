
package com.vpa.core.bo;

public class AuthenticationOverTimeResponseBO {

	private int year;
	private String monthName;
	private int monthNo;
	private int weekOfMonth;
	private String date;
	private String weekDateRange;
	private int noOfAuthentication;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public int getMonthNo() {
		return monthNo;
	}

	public void setMonthNo(int monthNo) {
		this.monthNo = monthNo;
	}

	public int getWeekOfMonth() {
		return weekOfMonth;
	}

	public void setWeekOfMonth(int weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNoOfAuthentication() {
		return noOfAuthentication;
	}

	public void setNoOfAuthentication(int noOfAuthentication) {
		this.noOfAuthentication = noOfAuthentication;
	}

	public String getWeekDateRange() {
		return weekDateRange;
	}

	public void setWeekDateRange(String weekDateRange) {
		this.weekDateRange = weekDateRange;
	}

}

