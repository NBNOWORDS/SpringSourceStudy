package com.dch;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.dch.config.AppConfig;
import com.dch.config.RootConfig;

//web����������ʱ�򴴽����󣬵��÷�������ʼ�������Լ�ǰ�˿�����
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	//��ȡ�����������ࣺ(Spring�������ļ�)  ������
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {RootConfig.class};
	}

	//��ȡ������������(SpringMVC�����ļ�) ������
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {AppConfig.class};
	}

	//��ȡDispatcherServlet��ӳ����Ϣ
	// /:������������(������̬��Դ(xx.js,xx.png))�����ǲ�����*.jsp
	// /*������������ ��*.jspҳ�涼���أ�jsp��tomcat��jsp���������
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}

}
