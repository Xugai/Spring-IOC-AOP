package com.daoImpl;

import com.dao.UserDao;
import com.entity.User;

public class UserDaoImpl1 implements UserDao {
	public void save(User user){
		System.out.println(user.getName()+" save �ѱ����ã�--->UserDaoImpl1");
	}
	public void update(User user){
		System.out.println(user.getName()+" update �ѱ����ã�--->UserDaoImpl1");
	}
}
