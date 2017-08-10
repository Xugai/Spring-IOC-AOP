package com.aop;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAOP {

	private static ClassPathXmlApplicationContext context;
	
	@BeforeClass
	public static void beforeclass(){
		context = new ClassPathXmlApplicationContext("Spring-AOP.xml");
	}
	
	@Test
	public void test() {
		Fit fit = (Fit) context.getBean("aspectBiz");
		fit.say();
	}

}
