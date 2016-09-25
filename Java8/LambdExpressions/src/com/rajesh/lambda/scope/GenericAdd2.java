package com.rajesh.lambda.scope;
@FunctionalInterface
public interface GenericAdd2 {
	<T> void print(T x,T y);
}
