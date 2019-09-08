package com.dch.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspects {
	//��ȡ�е�
	@Pointcut("execution(public int com.dch.aop.MathCalculator.*(..))")
	public void pointCut() {
		
	}
	@Before("pointCut()")
	//@Before("public int com.dch.aop.MathCalculator.*(..)")
	public void logStart(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println(""+joinPoint.getSignature().getName()+"�������У������б���{}"+Arrays.asList(args));
	}
	@After("pointCut()")
	public void logEnd() {
		System.out.println("��������");
	}
	//returning="result"��÷���ֵ
	@AfterReturning(value="pointCut()",returning="result")
	public void logReturn(Object result) {
		System.out.println("������������ֵ" + result);
	}
	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(Exception exception) {
		System.out.println("�����쳣 �쳣��Ϣ" + exception.getMessage());
	}
}
