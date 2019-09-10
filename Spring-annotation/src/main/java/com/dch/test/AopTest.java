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
 * 			5)、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
 * 3)、目标方法执行
 * 		容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象，xxx）
 * 		1)、CglibAopProxy.intercept();拦截目标方法的执行
 * 		2)、根据ProxyFactory对象获取将要执行的目标方法的拦截器链
 * 			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 * 			1)、List<Object> interceptorList 保存所有拦截器 5个
 * 					一个默认的ExposeInvocationInterceptor和4个增强器
 * 			2)、遍历所有的增强器，将其转为Interceptor：
 * 					registry.getInterceptors(advisor)
 * 			3)、将增强器转为List<MethodInterceptor>
 * 					如果是MethodInterceptor，直接加入到集合中
 * 					如果不是，使用AdvisorAdapter转为MethodInterceptor
 * 		3)、如果没有拦截器链，直接执行目标方法
 * 			拦截器(每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制)
 * 		4)、如果有拦截器链，把需要执行的目标对象，目标方法、拦截器链等信息传入创建一个CglibMethodInvocation对象，
 * 				并调用它的proceed()方法，
 * 				retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
 * 				proceed()方法为拦截器链的触发过程
 * 		5)、拦截器链的触发过程
 * 				1)、如果没有拦截器执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器，执行目标方法）
 * 				2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行
 * 						拦截器链的机制，保证通知方法与目标方法的顺序
 * 
 * 	总结
 * 			1)、 @EnableAspectJAutoProxy 开启aop功能
 * 			2)、 @EnableAspectJAutoProxy 会给容器中注册一个组件AnnotationAwareAspectJAutoProxyCreator
 * 			3)、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器
 * 			4)、容器的创建流程：
 * 					1)、registerBeanPostProcessors()注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator对象
 * 					2)、finishBeanFactoryInitialization()初始化剩下的单实例bean
 * 						1)、创建业务逻辑组件切面组件
 * 						2)、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建
 * 						3)、组件创建完之后，判断组件是否需要增强
 * 							是，切面的通知方法，包装成增强器(Advisor)；给业务逻辑组件创建一个代理对象(cglib)
 * 			5)、执行目标方法
 * 					1)、代理对象执行目标方法
 * 					2)、CglibAopProxy.intercept()
 * 						1)、得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 * 						2)、利用拦截器的链式机制，依次进入每一个拦截器进行执行
 * @author DENGCHENGHAO
 *
 */
public class AopTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAop.class);
		MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
		mathCalculator.div(2, 1);
		applicationContext.close();
	}

}
