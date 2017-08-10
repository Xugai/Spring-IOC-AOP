package com.aop.api;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class MoocAfterReturningAdvice implements AfterReturningAdvice {

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		System.out.println("MoocAfterReturning¡ª¡ªmethod: " + method.getName()
				+ "  target: " + target.getClass().getName() + " returnValue: "
				+ returnValue);
	}

}
