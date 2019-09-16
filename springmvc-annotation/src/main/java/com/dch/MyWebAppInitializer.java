package com.dch;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.dch.config.AppConfig;
import com.dch.config.RootConfig;

//web容器启动的时候创建对象，调用方法来初始化容器以及前端控制器
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	//获取容器的配置类：(Spring的配置文件)  父融资
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {RootConfig.class};
	}

	//获取容器的配置类(SpringMVC配置文件) 子容器
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {AppConfig.class};
	}

	//获取DispatcherServlet的映射信息
	// /:拦截所有请求(包括静态资源(xx.js,xx.png))，但是不包括*.jsp
	// /*拦截所有请求； 连*.jsp页面都拦截，jsp是tomcat的jsp引擎解析的
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}

}
