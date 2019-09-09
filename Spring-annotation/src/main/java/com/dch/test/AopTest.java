package com.dch.test;

import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
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
 * 					创建internalAutoProxyCreator(AnnotationAwareAspectJAutoProxyCreator)的BeanPostProcessor  doCreateBean()方法中
 * 					1)、创建Bean的实例
 * 					2)、populateBean:给Bean的各种属性赋值
 * 					3)、initializeBean:初始化bean:
 * 							1)、invokeAwareMethods():处理Aware接口的方法回调
 * 							2)、applyBeanPostProcessorsBeforeInitialization():应用后置处理器postProcessBeforeInitialization()
 * 							3)、invokeInitMethods():执行自定义初始化方法
 * 							4)、applyBeanPostProcessorsAfterInitialization():执行后置处理器postProcessorsAfterInitialization()
 * 					4)、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功:-->aspectJAdvisorsBuilder
 * 			7)、把BeanPostProcessor注册到BeanFactory中
 * 					beanFactory.addBeanPostProcessor(postProcessor);
 * =======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程=======
 * 						AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 * 		4)、finishBeanFactoryInitialization(beanFactory);完成BeanFactory的初始化工作;创建剩下的单实例Bean
 * 			1)、遍历获取容器中所有的Bean、依次创建对象getBean(beanName)
 * 					getBean()->doGetBean()->getSingleton()
 * 			2)、创建bean
 * 					【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截 InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation()】
 * 					1)、先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建
 * 							只要创建好的Bean都会被缓存起来
 * 					2)、createBean();创建bean AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先尝试返回bean的实例
 * 							【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 * 							【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 * 							1)、resolveBeforeInstantiation(beanName, mbd);解析BeforeInstantitation
 * 									希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续走4.2.2.2
 * 									1)、后置处理器先尝试返回对象，
 * 											bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName)
 * 													拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor;
 * 													就执行postProcessBeforeInstantiation
 * 											if (bean != null) {
														bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
												}
 * 											
 * 							2)、doCreateBean(beanName, mdbToUse, args);真正的去创建一个bean实例，和3.6流程一样
 * AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】的作用
 * 1)、每一个bean创建之前，调用postProcessBeforeInstantiation()方法
 * 		关心MathCalculator和LogAspect的创建
 * 		1)、判断当前bean是否在advisedBeans中（保存了所有需要增强的bean）
 * 		2)、判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean
 * 				或者是切面(@Aspect)
 * 		3)、是否需要跳过
 * 				1)、获取候选的增强器(切面里面的通知方法)【List<Advisor> candidateAdvisors】
 * 							每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor
 * 							判断每一个增强器是否是AspectJPointcutAdvisor类型的：返回true
 * 				2)、永远返回false
 * 2)、创建对象
 * postProcessAfterInitialization：
 * 			return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
 * 			1)、获取当前bean的所有增强器（通知方法）Object[] specificInterceptors
 * 					1、找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法）
 * 					2、获取到能在bean使用的增强器
 * 					3、给增强器排序
 * 			2)、保存当前bean在advisedBeans中：
 * 			3)、如果当前bean需要增强，创建当前bean的代理对象
 * 					1)、获取所有增强器（通知方法）
 * 					2)、保存到proxyFactory
 * 					3)、创建代理对象：Spring自动决定
 * 							JdkDynamicAopProxy(config);jdk动态代理
 * 							objenesisCglibAopProxy(config);cglib动态代理
 * 			4)、给容器中返回当前组件使用cglib增强了的对象代理
 * 			5)、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象会窒息感通知方法的流程
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
