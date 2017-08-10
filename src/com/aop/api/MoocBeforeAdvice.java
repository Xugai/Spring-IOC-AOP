package com.aop.api;

import java.lang.reflect.Method;
import org.springframework.aop.MethodBeforeAdvice;

public class MoocBeforeAdvice implements MethodBeforeAdvice {

	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("MoocBeforeAdvice¡ª¡ªmethod: " + method.getName() + "  target: "
				+ target.getClass().getName());
	}

}
