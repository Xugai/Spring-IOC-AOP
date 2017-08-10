package com.dao;
import com.entity.User;

public interface UserDao {
	public void save(User user);
	public void update(User user);
}
