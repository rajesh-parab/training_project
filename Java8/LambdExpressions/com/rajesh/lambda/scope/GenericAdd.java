package com.rajesh.lambda.scope;
@FunctionalInterface
public interface GenericAdd<T> {
	T add(T x,T y);
}
