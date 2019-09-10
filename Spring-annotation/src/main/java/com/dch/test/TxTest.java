package com.dch.test;

import java.lang.annotation.Annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

import com.dch.config.MyTxConfig;
import com.dch.service.RoleService;
/**
 *  原理
 *  1)、@EnableTransactionManagement
 *  					利用TransactionManagementConfigurationSelector给容器中导入组件
 *  					导入两个组件
 *  					AutoProxyRegistrar、
 *  					ProxyTransactionManagementConfiguration
 *  2)、AutoProxyRegistrar：
 *  					给容器中注册一个InfrastructureAdvisorAutoProxyCreator组件
 *  					InfrastructureAdvisorAutoProxyCreator
 *  					利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 *  					
 *  3)、ProxyTransactionManagementConfiguration
 *  					1、给容器中注册事务增强器：
 *  							事务增强器要事务注解的信息：AnnotationTransactionAttributeSource解析事务注解
 *  
 * @author a
 *
 */
public class TxTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyTxConfig.class);
		RoleService  roleService = ac.getBean(RoleService.class);
		roleService.insertRole();
	}
}
