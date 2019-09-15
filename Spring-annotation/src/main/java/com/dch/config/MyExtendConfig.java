package com.dch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.dch.pojo.Boss;
import com.dch.pojo.Red;
//扩展原理配置类
@ComponentScan("com.dch.extend")
@Configuration
public class MyExtendConfig {
	@Bean
	public Red red() {
		return new Red();
	}
	
	@Bean
	public Boss boss() {
		return new Boss();
	}
}
