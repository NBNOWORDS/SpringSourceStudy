package com.dch.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dch.config.MyConfig;
import com.dch.factory.CarFactory;
import com.dch.pojo.Boss;
import com.dch.pojo.Car;
/**
 * BeanPostPrcessor原理
 * 遍历得到容器中所有的BeanPostProcessor，挨个执行beforeInitialization
 * 一旦返回null,跳出for循环，不会执行后面的BeanPostProcessor.postProcessorsBeforeInitialization
 * 
 * populateBean(beanName,mbd,instanceWrapper)给bean进行属性赋值
 * initializeBean
 * {
 * 	applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName)
 * 	invokeInitMethods(beanName, wrappedBean, mdb);执行自定义初始化(xml配置的initMethod或者@PostConstruct)
 * 	applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName)
 * }
 * 
 * Spring底层对BeanPostProcessor的使用
 * 		bean赋值、注入其他组件，@Autowired、生命周期注解功能
 * 
 * 自定义组件想要使用Spring容器底层的一些组件（ApplicationContext,BeanFactory,xxx）
 * 		自定义组件实现xxxAware，在创建对象的时候，会调用接口规定的方法注入相关组件：Aware：
 * 		把Spring底层一些组件注入到自定义的Bean中
 * 		xxxAware，功能使用xxxProcessor:
 * 				ApplicationContextAware==>ApplicationContextAwareProcessor
 * 				ApplicationContextAwareProcessor调用postProcessBeforeInitialization()方法
 * 				在postProcessBeforeInitialization方法中调用invokeAwareInterfaces()方法
 * 				在invokeAwareInterfaces()方法中调用setApplicationContext()方法获取applicationContext
 * 		可在Red.setApplicationContext处断点查看
 * @author DENGCHENGHAO
 *
 */
public class MyTest {
	public static void main(String[] args) throws Exception {
		ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfig.class);
		Car car = (Car)ac.getBean("car");
		System.out.println(car);
		System.out.println("-----工厂-----");
		CarFactory carFactory = ac.getBean(CarFactory.class);
		Car car2 = (Car) carFactory.getObject();
		System.out.println(car2);
		System.out.println("----Autowired----");
		Boss boss = ac.getBean(Boss.class);
		System.out.println(boss);
	}
}
