package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class MoocAspect {
	public void before(){
		System.out.println("before....");
	}
	
	public void afterReturning(){
		System.out.println("after Returning....");
	}
	
	public void afterThrowing(){
		System.out.println("after Throwing....");
	}
	
	public void after(){
		System.out.println("after....");
	}
	
	//»·ÈÆÍ¨Öª(Around advice)
	public Object around(ProceedingJoinPoint pjp){
		Object obj = null;
		try {
			System.out.println("around 1");
			obj = pjp.proceed();
			System.out.println("around 2");
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public Object aroundInit(ProceedingJoinPoint pjp,String name,int times){
//		System.out.println(name+" "+times);
		Object obj = null;
		try {
			System.out.println("aroundInit 1");
			obj = pjp.proceed();
			System.out.println("aroundInit 2");
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
}
