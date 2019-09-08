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
 * 
 * 	流程
 * 		1)、传入配置类，创建ioc容器
 * 		2)、注册配置类，调用refresh()，刷新容器
 * 		3)、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建：
 * 			1)、先获取ioc容器中已经定义了的需要创建对象的所有BeanPostProcessor
 * 			2)、给容器中添加一些别的BeanPostProcessor
 * 			3)、优先注册实现了PriorityOrdered接口的BeanPostProcessor
 * 			4)、再给容器中注册实现了Ordered接口的BeanPostProcessor
 * 			5)、注册没实现优先级接口的BeanPostProcessor
 * 			6)、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中
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
