package com.aop.aspectj;

import org.springframework.stereotype.Service;

@Service
public class MoocBiz {
	
	@MoocMethod("moocmethod....")
	public String save(){
		System.out.println("save successfully!!!");
//		throw new RuntimeException("save failed!!!");
		return "save successfully!!!";
	}
}
