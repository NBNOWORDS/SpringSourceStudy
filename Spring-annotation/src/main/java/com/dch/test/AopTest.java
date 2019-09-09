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
 * 
 * 	����
 * 		1)�����������࣬����ioc����
 * 		2)��ע�������࣬����refresh()��ˢ������
 * 		3)��registerBeanPostProcessors(beanFactory);ע��bean�ĺ��ô���������������bean�Ĵ�����
 * 			1)���Ȼ�ȡioc�������Ѿ������˵���Ҫ�������������BeanPostProcessor
 * 			2)�������������һЩ���BeanPostProcessor
 * 			3)������ע��ʵ����PriorityOrdered�ӿڵ�BeanPostProcessor
 * 			4)���ٸ�������ע��ʵ����Ordered�ӿڵ�BeanPostProcessor
 * 			5)��ע��ûʵ�����ȼ��ӿڵ�BeanPostProcessor
 * 			6)��ע��BeanPostProcessor��ʵ���Ͼ��Ǵ���BeanPostProcessor���󣬱�����������
 * 					����internalAutoProxyCreator(AnnotationAwareAspectJAutoProxyCreator)��BeanPostProcessor  doCreateBean()������
 * 					1)������Bean��ʵ��
 * 					2)��populateBean:��Bean�ĸ������Ը�ֵ
 * 					3)��initializeBean:��ʼ��bean:
 * 							1)��invokeAwareMethods():����Aware�ӿڵķ����ص�
 * 							2)��applyBeanPostProcessorsBeforeInitialization():Ӧ�ú��ô�����postProcessBeforeInitialization()
 * 							3)��invokeInitMethods():ִ���Զ����ʼ������
 * 							4)��applyBeanPostProcessorsAfterInitialization():ִ�к��ô�����postProcessorsAfterInitialization()
 * 					4)��BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)�����ɹ�:-->aspectJAdvisorsBuilder
 * 			7)����BeanPostProcessorע�ᵽBeanFactory��
 * 					beanFactory.addBeanPostProcessor(postProcessor);
 * =======�����Ǵ�����ע��AnnotationAwareAspectJAutoProxyCreator�Ĺ���=======
 * 						AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 * 		4)��finishBeanFactoryInitialization(beanFactory);���BeanFactory�ĳ�ʼ������;����ʣ�µĵ�ʵ��Bean
 * 			1)��������ȡ���������е�Bean�����δ�������getBean(beanName)
 * 					getBean()->doGetBean()->getSingleton()
 * 			2)������bean
 * 					��AnnotationAwareAspectJAutoProxyCreator������bean����֮ǰ����һ������ InstantiationAwareBeanPostProcessor�������postProcessBeforeInstantiation()��
 * 					1)���ȴӻ����л�ȡ��ǰbean������ܻ�ȡ����˵��bean��֮ǰ���������ģ�ֱ��ʹ�ã������ٴ���
 * 							ֻҪ�����õ�Bean���ᱻ��������
 * 					2)��createBean();����bean AnnotationAwareAspectJAutoProxyCreator�����κ�bean����֮ǰ�ȳ��Է���bean��ʵ��
 * 							��BeanPostProcessor����Bean���󴴽���ɳ�ʼ��ǰ����õġ�
 * 							��InstantiationAwareBeanPostProcessor���ڴ���Beanʵ��֮ǰ�ȳ����ú��ô��������ض���ġ�
 * 							1)��resolveBeforeInstantiation(beanName, mbd);����BeforeInstantitation
 * 									ϣ�����ô������ڴ��ܷ���һ�������������ܷ��ش�������ʹ�ã�������ܾͼ�����4.2.2.2
 * 									1)�����ô������ȳ��Է��ض���
 * 											bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName)
 * 													�õ����к��ô������������InstantiationAwareBeanPostProcessor;
 * 													��ִ��postProcessBeforeInstantiation
 * 											if (bean != null) {
														bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
												}
 * 											
 * 							2)��doCreateBean(beanName, mdbToUse, args);������ȥ����һ��beanʵ������3.6����һ��
 * AnnotationAwareAspectJAutoProxyCreator��InstantiationAwareBeanPostProcessor��������
 * 1)��ÿһ��bean����֮ǰ������postProcessBeforeInstantiation()����
 * 		����MathCalculator��LogAspect�Ĵ���
 * 		1)���жϵ�ǰbean�Ƿ���advisedBeans�У�������������Ҫ��ǿ��bean��
 * 		2)���жϵ�ǰbean�Ƿ��ǻ������͵�Advice��Pointcut��Advisor��AopInfrastructureBean
 * 				����������(@Aspect)
 * 		3)���Ƿ���Ҫ����
 * 				1)����ȡ��ѡ����ǿ��(���������֪ͨ����)��List<Advisor> candidateAdvisors��
 * 							ÿһ����װ��֪ͨ��������ǿ����InstantiationModelAwarePointcutAdvisor
 * 							�ж�ÿһ����ǿ���Ƿ���AspectJPointcutAdvisor���͵ģ�����true
 * 				2)����Զ����false
 * 2)����������
 * postProcessAfterInitialization��
 * 			return wrapIfNecessary(bean, beanName, cacheKey);//��װ�����Ҫ�������
 * 			1)����ȡ��ǰbean��������ǿ����֪ͨ������Object[] specificInterceptors
 * 					1���ҵ���ѡ�����е���ǿ��������Щ֪ͨ��������Ҫ���뵱ǰbean������
 * 					2����ȡ������beanʹ�õ���ǿ��
 * 					3������ǿ������
 * 			2)�����浱ǰbean��advisedBeans�У�
 * 			3)�������ǰbean��Ҫ��ǿ��������ǰbean�Ĵ������
 * 					1)����ȡ������ǿ����֪ͨ������
 * 					2)�����浽proxyFactory
 * 					3)�������������Spring�Զ�����
 * 							JdkDynamicAopProxy(config);jdk��̬����
 * 							objenesisCglibAopProxy(config);cglib��̬����
 * 			4)���������з��ص�ǰ���ʹ��cglib��ǿ�˵Ķ������
 * 			5)���Ժ������л�ȡ���ľ����������Ĵ������ִ��Ŀ�귽����ʱ�򣬴���������Ϣ��֪ͨ����������
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
