package com.dch.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dch.config.MyConfig;
import com.dch.factory.CarFactory;
import com.dch.pojo.Boss;
import com.dch.pojo.Car;
/**
 * BeanPostPrcessorԭ��
 * �����õ����������е�BeanPostProcessor������ִ��beforeInitialization
 * һ������null,����forѭ��������ִ�к����BeanPostProcessor.postProcessorsBeforeInitialization
 * 
 * populateBean(beanName,mbd,instanceWrapper)��bean�������Ը�ֵ
 * initializeBean
 * {
 * 	applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName)
 * 	invokeInitMethods(beanName, wrappedBean, mdb);ִ���Զ����ʼ��(xml���õ�initMethod����@PostConstruct)
 * 	applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName)
 * }
 * 
 * Spring�ײ��BeanPostProcessor��ʹ��
 * 		bean��ֵ��ע�����������@Autowired����������ע�⹦��
 * 
 * �Զ��������Ҫʹ��Spring�����ײ��һЩ�����ApplicationContext,BeanFactory,xxx��
 * 		�Զ������ʵ��xxxAware���ڴ��������ʱ�򣬻���ýӿڹ涨�ķ���ע����������Aware��
 * 		��Spring�ײ�һЩ���ע�뵽�Զ����Bean��
 * 		xxxAware������ʹ��xxxProcessor:
 * 				ApplicationContextAware==>ApplicationContextAwareProcessor
 * 				ApplicationContextAwareProcessor����postProcessBeforeInitialization()����
 * 				��postProcessBeforeInitialization�����е���invokeAwareInterfaces()����
 * 				��invokeAwareInterfaces()�����е���setApplicationContext()������ȡapplicationContext
 * 		����Red.setApplicationContext���ϵ�鿴
 * @author DENGCHENGHAO
 *
 */
public class MyTest {
	public static void main(String[] args) throws Exception {
		ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfig.class);
		Car car = (Car)ac.getBean("car");
		System.out.println(car);
		System.out.println("-----����-----");
		CarFactory carFactory = ac.getBean(CarFactory.class);
		Car car2 = (Car) carFactory.getObject();
		System.out.println(car2);
		System.out.println("----Autowired----");
		Boss boss = ac.getBean(Boss.class);
		System.out.println(boss);
	}
}
