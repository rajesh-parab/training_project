/**
 * 
 */
package com.vpa.saas.formatters;


/**
 * @author PD42694
 *
 */
public class CurrencyFormatter {
	
	private static char[] c = new char[]{'k', 'm', 'b', 't'};
	
	private CurrencyFormatter(){
		
	}
	/**
	 * This method is used to format the currency
	 * @param n
	 * @param iteration
	 * @return formatted currency
	 */
	public static String currencyFormatter(double n, int iteration) {
	    return "$" +format(n, iteration);
	}	
	
	/**
	 * This method is used to format the currency
	 * @param n
	 * @param iteration
	 * @return formatted currency
	 */
	public static String format(double n, int iteration) {
		   double d = ((long) n / 100) / 10.0;
		   	 
		    boolean isRound = (Double.doubleToRawLongBits((d * 10) %10.0d) == 0L);//true if the decimal part is equal to 0 (then it's trimmed anyway)
		   return  (d < 1000? //this determines the class, i.e. 'k', 'm' etc
			        ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
			         (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
			         ) + "" + c[iteration]) 
			        : format(d, iteration+1));
	}
	

}
