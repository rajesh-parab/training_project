package com.training.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FilterExample {

	public static void main(String[] args) {
	 List<String> list = new ArrayList<>() ;
	 list.add("ahamdabad");
	 list.add("mumbai");
	 list.add("pune");
	 list.add("patna");
	 list.add("kolkotta");
	 
	 
 	 StringPredicate p = c ->  {  boolean retValue = c.length() > 5;
	                                 return retValue;
	 								};
	 								
 	// Consumer<String>	 c = city -> {  System.out.println(city);}	;				
	 
	 list.stream().
	   filter(c-> c.length() > 5).forEach(  city ->System.out.println(city)); 
	 
	/* Predicate<String> p =  
			 	s    -> {
				    boolean reValue = false;
					 
						if(s.length()<5){
							reValue= true;
							 
						}
						 
				 
					return reValue;
				
				};
	 
			for(String item : list ){
				if(p.test(item)==true){
					c.apply(item);
				}
			} */
 
	 
	   	
	  
	}

}
