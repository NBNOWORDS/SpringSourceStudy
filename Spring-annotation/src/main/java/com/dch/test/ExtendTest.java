package com.dch.test;

import java.util.EventListener;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import com.dch.config.MyExtendConfig;

/**
 * ��չԭ��
 *1�� BeanPostProcessor��bean���ô�������bean���������ʼ��ǰ��������ع���
 * BeanFactoryPostProcessor��beanFactory���ô�����
 * 		��BeanFactory��׼��ʼ֮����ã����е�bean�����Ѿ�������ص�beanFactory����bean��ʵ����δ����
 * 1)��ioc��������
 * 2)��invokeBeanFactoryPostProcessors(beanFactory);ִ��BeanFactoryPostProcessor
 * 		����ҵ����е�BeanFactoryPostProcessor��ִ�����ǵķ���
 * 			1)��ֱ����BeanFactory���ҵ�����������BeanFactoryPostProcessor���������ִ�����ǵķ���
 * 			2)���ڳ�ʼ�������������ǰ��ִ��
 * 
 * 
 * =======================================================================
 * 2��BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 * 		postProcessBeanDefinitionRegistry();
 * 		������bean������Ϣ��Ҫ�����أ�beanʵ����δ������
 * 		������BeanFactoryPostProcessorִ�У�
 * 		����BeanDefinitionRegistryPostProcessor���������ٶ������һЩ���
 * ԭ��	
 * 1)��ioc��������
 * 2)��refresh() -> invokeBeanFactoryPostProcessors(beanFactory)
 * 3)���������л�ȡ�����е�BeanDefinitionRegistryPostProcessor�����
 * 		1)�����δ������е�postProcessBeanDefinitionRegistry()����
 * 		2)�������������е�postProcessBeanFactory()����BeanFactoryPostProcessor
 * 4)���������������ҵ�BeanFactoryPostProcessor�����Ȼ�����δ���postProcessBeanFactory()����
 * =======================================================================
 * 
 * 
 * 3��ApplicationListener:���������з�����ʱ�䣬ʱ������ģ�Ϳ���
 * 		public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 * 			����ApplicationEvent������������¼�
 * 	����:
 * 		1)��дһ��������������ĳ���¼�(ApplicationEvent��������)
 * 		2)���Ѽ��������뵽����
 * 		3)��ֻҪ������������¼��ķ��������Ǿ��ܼ���������¼�
 * 				ContextRefreshedEvent;����ˢ����ɣ�����bean����ȫ�������ᷢ������¼�
 * 				ContextClosedEvent;�ر������ᷢ������¼�
 * 		4)������һ���¼�
 * 
 * 	ԭ��
 * 		ContextRefreshedEvent��ExtendTest$1[source=�ҷ������¼�]��ContextClosedEvent
 * 	1)��ContextRefreshedEvent�¼�
 * 		1)��������������refresh();
 * 		2)��finishRefresh();����ˢ����ɻᷢ��ContextRefreshedEvent�¼�
 * 	2)���Է����¼�
 * 	3)�������رջ�ر�ContextClosedEvent�¼�
 * 		���¼��������̡���
 * 		3)��publishEvent(new ContextRefreshedEvent(this))
 * 				1)����ȡ�¼��Ķಥ��(�ɷ���)��getApplicationEventMulticaster()
 * 				2)��multicastEvent()�ɷ��¼�
 * 				3)����ȡ�����е�AppicationListener
 * 					for (final ApplicationListener<?> listener : getApplicationListeners(event))
 * 					1)�������Executor�������ʹ��Executor�����첽�ɷ�
 * 						Executor executor = getTaskExecutor()
 * 					2)������ͬ���ķ�ʽֱ��ִ��listener����
 * 					�õ�listener�ص�onApplicationEvent����
 * 
 * 	���¼��Ķಥ�����ɷ�������
 * 		1)��������������:refresh();
 * 		2)��initApplicationEventMulticaster()����;��ʼ��ApplicationEventMulticaster
 * 			1)����ȥ��������û��id="applicationEventMulticaster"�����
 * 			2)�����û��this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 * 				���Ҽ��뵽�����У����ǾͿ������������Ҫ�ɷ��¼����Զ�ע�����applicationEventMulticaster
 * 	
 * ������������Щ��������
 * 		1)��������������:refresh();
 * 		2)��registerListeners();
 * 			���������õ����еļ�������������ע�ᵽapplicationEventMulticaster�У�
 * 			String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
			for (String lisName : listenerBeanNames) {
				getApplicationEventMulticaster().addApplicationListenerBean(lisName);
			}
 * 	=======================================================================
 * 	SmartInitializingSingleton ԭ��
 * 		1)��ioc������������refresh()
 * 		2)��finishBeanFactoryInitialization(beanFactory);��ʼ��ʣ�µĵ�ʵ��bean
 * 			1)���ȴ������еĵ�ʵ��bean��getBean()
 * 			2)����ȡ���д����õĵ�ʵ��bean���ж��Ƿ���SmartInitializingSingleton���͵�
 * 				����Ǿ͵���afterSingletonsInstantiated()����
 * 			
 * @author DENGCHENGHAO
 *
 */
public class ExtendTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyExtendConfig.class);
		//�����¼�
		ac.publishEvent(new ApplicationEvent(new String("�ҷ������¼�")) {
		});
		
		ac.close();
	}
}
