package com.beanannotation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestBeanAnnotation {

	public static ClassPathXmlApplicationContext context;
	
	@BeforeClass
	public static void beforeClass(){
		context = new ClassPathXmlApplicationContext("BeanAnnotation.xml");
	}
	
	@Test
	public void test() {
		UserService us = (UserService) context.getBean("userService");
	}

}
