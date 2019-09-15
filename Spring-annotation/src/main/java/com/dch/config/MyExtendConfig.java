package com.dch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.dch.pojo.Boss;
import com.dch.pojo.Red;
//��չԭ��������
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
