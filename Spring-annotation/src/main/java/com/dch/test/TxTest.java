package com.dch.test;

import java.lang.annotation.Annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

import com.dch.config.MyTxConfig;
import com.dch.service.RoleService;
/**
 *  ԭ��
 *  1)��@EnableTransactionManagement
 *  					����TransactionManagementConfigurationSelector�������е������
 *  					�����������
 *  					AutoProxyRegistrar��
 *  					ProxyTransactionManagementConfiguration
 *  2)��AutoProxyRegistrar��
 *  					��������ע��һ��InfrastructureAdvisorAutoProxyCreator���
 *  					InfrastructureAdvisorAutoProxyCreator
 *  					���ú��ô����������ڶ��󴴽��Ժ󣬰�װ���󣬷���һ�����������ǿ�������������ִ�з������������������е���
 *  					
 *  3)��ProxyTransactionManagementConfiguration
 *  					1����������ע��������ǿ����
 *  							������ǿ��Ҫ����ע�����Ϣ��AnnotationTransactionAttributeSource��������ע��
 *  
 * @author a
 *
 */
public class TxTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyTxConfig.class);
		RoleService  roleService = ac.getBean(RoleService.class);
		roleService.insertRole();
	}
}
