package com.aop.api;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class MoocThrowsAdvice implements ThrowsAdvice {
	public void afterThrowing(Exception ex) throws Throwable {
		System.out.println("afterThrowing....");
	}

	public void afterThrowing(Exception ex, Method method, Object[] args,
			Object target) throws Throwable {
		System.out.println("afterThrowng method: " + method.getName() + "  target: "
				+ target.getClass().getName());
	}
}
