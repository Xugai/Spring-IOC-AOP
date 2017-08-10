package com.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MoocAspect {
	
	//matching method
	@Pointcut("execution(* com.aop.aspectj.*Biz.*(..))")
	public void pointcut(){
		
	}
	
	//matching class
	@Pointcut("within(com.aop.aspectj.*)")
	public void bizPointcut(){
		
	}
	
//	@Before("pointcut()")
//	public void before(){
//		System.out.println("before....");
//	}
	
//	@Before("pointcut() && args(obj)")
//	public void before(Object obj){
//		System.out.println("before....args: "+obj);
//	}
//	
	
	@Before("pointcut() && @annotation(moocMethod)")
	public void before(MoocMethod moocMethod){
		System.out.println("before....@annotation: "+moocMethod.value());
	}
	
	
	
	@AfterReturning(pointcut="pointcut()",returning="Value")
	public void afterReturning(String Value){
		System.out.println("afterReturning...."+Value);
	}
	
	@AfterThrowing(pointcut="pointcut()",throwing="ex")
	public void afterThrowing(Exception ex){
		System.out.println("afterThrowing...."+ex.getMessage());
	}
	
	@After("pointcut()")
	public void after(){
		System.out.println("after....");
	}
	
	@Around("pointcut()")
	public void around(ProceedingJoinPoint pjp){
		try {
			System.out.println("around  1....");
			Object obj;
			obj = pjp.proceed();
			System.out.println("around  2....");
			System.out.println("obj: "+obj+" type: "+obj.getClass().getName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
