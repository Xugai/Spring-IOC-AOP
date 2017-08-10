package com.daoImpl;

import com.dao.UserDao;
import com.entity.User;

public class UserDaoImpl2 implements UserDao{
	public void save(User user){
		System.out.println(user.getName()+" save已被调用！--->UserDaoImpl2");
	}
	public void update(User user){
		System.out.println(user.getName()+" add已被调用！--->UserDaoImpl2");
	}
}
