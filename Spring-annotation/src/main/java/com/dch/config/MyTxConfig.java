package com.dch.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * ����ʽ����
 * @author DengChenghao
 *
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.dch.dao,com.dch.service")
public class MyTxConfig {
	//����Դ
	@Bean
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("19960802");
		druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		druidDataSource.setUrl("jdbc:mysql://localhost:3306/ssm");
		return druidDataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}
	
	//���������
	@Bean
	public PlatformTransactionManager dataSourceTransactonManager() {
		DataSourceTransactionManager dataSourceTransactonManager = new DataSourceTransactionManager(dataSource());
		return dataSourceTransactonManager;
		
	}
}
