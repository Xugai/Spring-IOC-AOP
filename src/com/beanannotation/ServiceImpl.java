package com.beanannotation;

import org.springframework.stereotype.Service;

//@Service
public class ServiceImpl implements UserService{
	public void say(){
		System.out.println("hello world!");
	}
}
