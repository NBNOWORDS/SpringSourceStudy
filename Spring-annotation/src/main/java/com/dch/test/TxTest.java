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
 *  							1)、事务增强器要事务注解的信息：AnnotationTransactionAttributeSource解析事务注解
 *  							2)、事务拦截器：
 *  								TransactionInterceptor:保存了事务属性信息，事务管理器
 *  								他是一个MethodInterceptor
 *  								在目标方法执行的时候：
 *  									执行拦截器链
 *  									事务拦截器
 *  										1)、获取事务相关属性
 *  										2)、再获取PlatformTransactionManager，如果事先没有添加指定任何transactionManager
 *  											最终会从容器中按照类型获取一个PlatformTransactionManager
 *  										3)、执行目标方法
 *  											如果异常，获取到事务管理器，利用事务管理器回滚操作
 *  											如果正常，利用事务管理器提交事务
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
