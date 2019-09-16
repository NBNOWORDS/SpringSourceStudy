package com.dch.servlet;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.HandlesTypes;

import com.dch.service.HelloService;
//����������ʱ��ʱ��Ὣ@HandlesTypesָ��������������������(ʵ���ࡢ�ӽӿڵ�)���ݹ���
//�������Ȥ������
@HandlesTypes(value= {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer{

	/**
	 * Ӧ��������ʱ�򣬻�����onStartup����
	 * 
	 * Set<Class<?>> arg0 ����Ȥ�����͵�����������
	 * ServletContext arg1:����ǰwebӦ�õ�ServletContext��һ��webӦ��һ��
	 * 
	 * 1)��ʹ��ServletContextע��Wen���(Servlet��Filter��Listener)
	 * 2)��ʹ�ñ���ķ�ʽ������Ŀ������ʱ���ServletContext����������
	 * 		��������Ŀ������ʱ�������
	 *
	 */
	@Override
	public void onStartup(Set<Class<?>> arg0, ServletContext sc) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("����Ȥ������:");
		for (Class<?> clazz : arg0) {
			System.out.println(clazz);
		}
		//ע�����
		Dynamic servlet=sc.addServlet("userServlet", new UserServlet());
		//����servlet��ӳ����Ϣ
		servlet.addMapping("/user");
		
		//ע��Listener
		sc.addListener(UserListener.class);
		
		//ע��Filter
		FilterRegistration.Dynamic filter = sc.addFilter("userFilter", UserFilter.class);
		//����filter��ӳ����Ϣ
		filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
		
		
	}

}
