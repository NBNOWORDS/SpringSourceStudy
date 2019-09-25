package com.dch.pojo;

public class Car {
	private String carName;
	public Car() {
		System.out.println("car constructor....");
	}
	
	public Car(String carName) {
		this.carName = carName;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	@Override
	public String toString() {
		return "Car [carName=" + carName + "]";
	}
	
}
