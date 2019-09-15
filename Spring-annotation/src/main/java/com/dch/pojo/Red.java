package com.dch.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Red implements ApplicationContextAware, BeanNameAware{
	private ApplicationContext applicationContext;
	
	public Red() {
		System.out.println("red construction.....");
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
		System.out.println("´«ÈëµÄioc" + applicationContext);
	}

	@Override
	public void setBeanName(String name) {
		// TODO Auto-generated method stub
		
	}

}
