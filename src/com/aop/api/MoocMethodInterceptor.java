package com.aop.api;

import org.aopalliance.intercept.MethodInvocation;

public class MoocMethodInterceptor implements
		org.aopalliance.intercept.MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("MoocMethodInterceptor¡ª¡ªmethod: "
				+ invocation.getMethod().getName() + "  "
				+ invocation.getStaticPart().getClass().getName());
		
		Object obj = invocation.proceed();
		System.out.println("obj: "+obj);
		return obj;
	}

}
