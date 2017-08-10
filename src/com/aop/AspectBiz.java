package com.aop;

public class AspectBiz {
	public void Biz(){
		System.out.println("biz....");
//		throw new RuntimeException();
	}
	
	public void init(String name,int times){
		System.out.println("my: "+name+" times: "+times);
	}
}
