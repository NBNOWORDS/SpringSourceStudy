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
	//抽取切点
	@Pointcut("execution(public int com.dch.aop.MathCalculator.*(..))")
	public void pointCut() {
		
	}
	@Before("pointCut()")
	//@Before("public int com.dch.aop.MathCalculator.*(..)")
	public void logStart(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println(""+joinPoint.getSignature().getName()+"除法运行，参数列表是{}"+Arrays.asList(args));
	}
	@After("pointCut()")
	public void logEnd() {
		System.out.println("除法结束");
	}
	//returning="result"获得返回值
	@AfterReturning(value="pointCut()",returning="result")
	public void logReturn(Object result) {
		System.out.println("除法正常返回值" + result);
	}
	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(Exception exception) {
		System.out.println("除法异常 异常信息" + exception.getMessage());
	}
}
