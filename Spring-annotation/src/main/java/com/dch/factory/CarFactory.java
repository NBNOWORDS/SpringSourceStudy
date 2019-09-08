package com.dch.factory;

import org.springframework.beans.factory.FactoryBean;

import com.dch.pojo.Car;
//使用工厂模式
public class CarFactory implements FactoryBean<Car>{
	private String carName;
	public CarFactory(String carName) {
		System.out.println("CarFactory constructor with parameters...");
		this.carName = carName;
	}
	@Override
	public Car getObject() throws Exception {
		// TODO Auto-generated method stub
		return new Car(carName);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Car.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
