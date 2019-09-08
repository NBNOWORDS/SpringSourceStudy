package com.dch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.dch.aop.LogAspects;
import com.dch.aop.MathCalculator;
//启用AspectJ自动代理
@EnableAspectJAutoProxy
@Configuration
public class MyConfigOfAop {
	@Bean
	public MathCalculator calculator() {
		return new MathCalculator();
	}
	
	@Bean
	public LogAspects logAspects() {
		return new LogAspects();
	}
}
