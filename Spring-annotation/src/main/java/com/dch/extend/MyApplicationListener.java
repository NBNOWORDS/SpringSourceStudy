package com.dch.extend;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent>{

	//�������з������¼��Ժ󣬷�������
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		System.out.println("�յ��¼� " + event);
	}

}
