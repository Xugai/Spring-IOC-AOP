/*
 * Spring���𼰼�Ӧ��
 * �Ա�ԭ����ΪҪʹ��ʵ�ֽӿڵ���������޸����еĴ�����й���Ĺ��̣�
 * 1��ʹ��Spring�����������չ�Ա�ǿ������ͨ���޸������ļ����ܹ����в�ͬ�ӿ�ʵ����Ĺ������ã�����
 * 	  ֱ���漰���޸Ĵ����
 * 2������Ŀ��Ķ��Եõ���ߣ�ͬ��Ҳ����Ҫ����������д����Ķ�������ʵ�ֽӿ���ķ�����ֻ���������ļ���
 * 	  ���ܲ鿴����
 */

package com.serviceImpl.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.entity.User;
import com.serviceImpl.UserServiceImpl;


public class UserServiceImplTest{
	User user;
	static ClassPathXmlApplicationContext app;
	@BeforeClass
	//�ڲ��Է�������ִ��ǰ����̬������Ҫ�������ļ�������bean��ʹ��
	public static void BeforeClass(){
		//ͨ��ClassPathXmlApplicationContext��ȡBeans.xml��Ϣ
		app = new ClassPathXmlApplicationContext("Beans.xml");
	}
	
	@Before
	public void setUp() throws Exception{
		System.out.println("this is Before....");
		user = new User();
		user.setName("testName");
	}
	
	@After
	public void after(){
		System.out.println("this is After....");
	}
	
	@Test
	public void testAdd(){
		UserServiceImpl userServiceImpl = (UserServiceImpl) app.getBean("userServiceImpl");
//		System.out.println("ID: "+userServiceImpl.hashCode());
		app.destroy();
		
//		UserServiceImpl userServiceImpl2 = (UserServiceImpl) app.getBean("userServiceImpl");
//		System.out.println(userServiceImpl2.hashCode());
//		userServiceImpl2.add(user);
	}
	/*
	@Test
	public void testModify(){
		UserServiceImpl userServiceImpl = (UserServiceImpl) app.getBean("userServiceImpl");
		System.out.println("ID: "+userServiceImpl.getClass().hashCode());
		userServiceImpl.modify(user);
	}
	*/
}
