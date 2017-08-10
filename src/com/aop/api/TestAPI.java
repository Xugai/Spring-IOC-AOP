package com.aop.api;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAPI {

	static ClassPathXmlApplicationContext context;
	
	@BeforeClass
	public static void beforeclass(){
		context = new ClassPathXmlApplicationContext("Spring-AOP-API.xml");
	}
	@Test
	public void test() {
		BizLogic bl = (BizLogic) context.getBean("bizLogicImpl");
		bl.save();
	}

}
