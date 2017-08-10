package com.aop.api;

public class BizLogicImpl implements BizLogic {

	public String save() {
		System.out.println("logic save");
		return "logic save";
	}

}
