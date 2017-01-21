package com.rajesh.lambda;

public class TestVar implements VarArgConsumer {

	@Override
	public String apply(String fmt, Object... args) {
	return	String.format(fmt,args);
	}

}
