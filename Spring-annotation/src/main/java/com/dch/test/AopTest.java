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
 * Aopԭ��[����������ע����ʲô��������ʲôʱ������������ʲô��]
 * 		@EnableAspectJAutoProxy
 * 1��@EnableAspectJAutoProxy��ʲô
 * 		@Import(AspectJAutoProxyRegistrar.class)���������е���AspectJAutoProxyRegister.class
 * 			����AspectJAutoProxyRegister�Զ����������ע��bean
 * 			internalAutoProxyCreator=AnnotationAwareAspetJAutoProxyCreator
 * 			��������ע��һ��AnnotationAwareAspectJAutoProxyCreator
 * 2��AnnotationAwareAspectJAutoProxyCreator
 * 		AnnotationAwareAspectJAutoProxyCreator
 * 			->AspectJAwareAdvisorAutoProxyCreator
 * 				->AbstractAdvisorAutoProxyCreator
 * 					->AbstractAutoProxyCreator
 * 						implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 					��ע���ô�������bean��ʼ�����ǰ�������飩���Զ�װ��BeanFactory
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator.�к��ô������߼�
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
