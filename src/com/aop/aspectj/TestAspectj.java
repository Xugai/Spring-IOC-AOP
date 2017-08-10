package com.aop.aspectj;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectj {

	static ClassPathXmlApplicationContext context;
	
	@BeforeClass
	public static void beforeclass(){
		context = new ClassPathXmlApplicationContext("Spring-AOP-Aspectj.xml");
	}
	
	@Test
	public void test() {
		MoocBiz mb = (MoocBiz) context.getBean("moocBiz");
		mb.save();
	}

}
