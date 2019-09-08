package com.dch.test;

import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

import com.dch.aop.MathCalculator;
import com.dch.config.MyConfigOfAop;
/**
 * Aop原理[看给容器中注册了什么组件，组件什么时候工作，功能是什么，]
 * 		@EnableAspectJAutoProxy
 * 1、@EnableAspectJAutoProxy是什么
 * 		@Import(AspectJAutoProxyRegistrar.class)，给容器中导入AspectJAutoProxyRegister.class
 * 			利用AspectJAutoProxyRegister自定义给容器中注册bean
 * 			internalAutoProxyCreator=AnnotationAwareAspetJAutoProxyCreator
 * 			给容器中注册一个AnnotationAwareAspectJAutoProxyCreator
 * 2、AnnotationAwareAspectJAutoProxyCreator
 * 		AnnotationAwareAspectJAutoProxyCreator
 * 			->AspectJAwareAdvisorAutoProxyCreator
 * 				->AbstractAdvisorAutoProxyCreator
 * 					->AbstractAutoProxyCreator
 * 						implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 					关注后置处理器（bean初始化完成前后做事情）、自动装配BeanFactory
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator.有后置处理器逻辑
 * 
 * AbstractAdvisorAutoProxyCreator.setBeanFactory() ->initBeanFactory()
 * 
 * AspectJAwareAdvisorAutoProxyCreator.initBeanFactory()
 * @author DENGCHENGHAO
 *
 */
public class AopTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAop.class);
		MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
		mathCalculator.div(2, 1);
	}

}
