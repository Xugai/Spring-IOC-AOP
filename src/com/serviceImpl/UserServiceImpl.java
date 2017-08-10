package com.serviceImpl;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.dao.UserDao;
import com.entity.User;

public class UserServiceImpl implements ApplicationContextAware{
	private UserDao userDao;
	
	public void add(User user){
//		userDao = new UserDaoImpl1();  //UserDaoImpl1ʵ�ֽӿ�
		userDao.save(user);
	}

	public void modify(User user){
//		userDao = new UserDaoImpl1();  //UserDaoImpl1ʵ�ֽӿ�
		userDao.update(user);
	}
	

	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		Resource resource = applicationContext.getResource("file:C:\\Users\\rabbit\\Workspaces\\MyEclipse 10\\MySQL_Operation\\src\\note.txt");
		try {
			System.out.println("��Դ�ļ������ǣ�"+resource.contentLength());
			System.out.println("��Դ����"+resource.getFilename());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
