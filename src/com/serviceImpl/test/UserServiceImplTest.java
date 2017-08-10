/*
 * Spring部署及简单应用
 * 对比原来因为要使用实现接口的类而必须修改其中的代码进行管理的过程，
 * 1、使用Spring管理，程序的扩展性变强，仅需通过修改配置文件便能够进行不同接口实现类的管理及调用，不用
 * 	  直接涉及到修改代码层
 * 2、程序的可阅读性得到提高，同样也不需要进入代码层进行代码阅读、查找实现接口类的方法，只需在配置文件中
 * 	  便能查看明了
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
	//在测试方法真正执行前，静态加载需要的配置文件，用于bean的使用
	public static void BeforeClass(){
		//通过ClassPathXmlApplicationContext获取Beans.xml信息
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
