package com.serviceImpl.test;

import org.junit.Before;
import org.junit.After;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
public class UnitTestBase {
	private ClassPathXmlApplicationContext context;
	
	private String springXmlPath;
	
	public UnitTestBase(){
		
	}
	
	public UnitTestBase(String springXmlPath){
		this.springXmlPath = springXmlPath;
	}
	
	@Before
	public void before(){
		if(StringUtils.isEmpty(springXmlPath)){
			//�˴�Ϊ·��ƥ�䣬Ҫ�����õ�xml�ļ���������ͬ��������ʽ
			springXmlPath = "classpath*:Beans*.xml";
		}try{
			context = new ClassPathXmlApplicationContext(springXmlPath.split("[,\\s]+"));
			context.start();
		}catch(BeansException ex){
			ex.printStackTrace();
		}
	}
	
	@After
	public void after(){
		context.destroy();
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getBean(String beanId){
		try{
			return (T)context.getBean(beanId);
		}catch(BeansException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	protected <T extends Object> T getBean(Class<T> clazz){
		try{
			return (T)context.getBean(clazz);
		}catch(BeansException ex){
			ex.printStackTrace();
			return null;
		}
	}
}
