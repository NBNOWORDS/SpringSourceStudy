package com.dch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.dch.factory.CarFactory;
import com.dch.pojo.Car;
import com.dch.pojo.Red;

@Configuration
@ComponentScan("com.dch.bean,com.dch.pojo")
public class MyConfig {
	public MyConfig() {
		System.out.println("MyConfig constructor");
	}
	@Bean
	public Car car() {
		return new Car();
	}
	
	@Bean
	public CarFactory carFarcoty() {
		return new CarFactory("benz");
	}
	
	@Bean Red red() {
		return new Red();
	}
}
