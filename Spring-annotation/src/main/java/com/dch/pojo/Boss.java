package com.dch.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //Ĭ�ϼ���ioc�����е��������������������޲ι��������������ڽ��г�ʼ����ֵ�Ȳ���
public class Boss {
	public Boss(){
		System.out.println("Boss constructor....");
	}
	private Car car;

	public Car getCar() {
		return car;
	}

	//��ע�ڷ����ϣ�Spring����������ǰ���󣬾ͻ���÷�������ɸ�ֵ
	//����ʹ�õĲ������Զ������͵�ֵ��ioc�����л�ȡ
	@Autowired
	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "Boss [car=" + car + "]";
	}
	
}
