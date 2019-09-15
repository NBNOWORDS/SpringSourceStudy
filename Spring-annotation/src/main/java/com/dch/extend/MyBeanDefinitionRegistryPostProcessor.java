package com.dch.extend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import com.dch.pojo.Car;
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("MyBeanDefinitionRegistryPostProcessor..bean������" + beanFactory.getBeanDefinitionCount());
	}

	//BeanDefinitionRegistry Bean������Ϣ�ı������ģ��Ժ�BeanDefinitionRegistry���汣���ÿһ��bean������Ϣ����beanʵ��
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("postProcessBeanDefinitionRegistry...bean������" + registry.getBeanDefinitionCount());
		RootBeanDefinition beanDefinition = new RootBeanDefinition(Car.class);
		registry.registerBeanDefinition("hello", beanDefinition);
	}

}
