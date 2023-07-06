package com.wolfhack.cloud.product.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

	@Pointcut("@annotation(com.wolfhack.cloud.product.annotations.AopLog)")
	public void monitor() { }

	@Bean
	public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
		return new PerformanceMonitorInterceptor(true);
	}

	@Bean
	public Advisor performanceMonitorAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("com.wolfhack.cloud.product.config.AopConfiguration.monitor()");
		return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
	}

	@AfterThrowing("@annotation(com.wolfhack.cloud.product.annotations.AopLog))")
	public void AfterThrowing(JoinPoint joinPoint) {
		log.info("{}.{} Throws exception", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
	}

	@Around("@annotation(com.wolfhack.cloud.product.annotations.AopLog)")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		//Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		//Measure method execution time
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		//Log method execution time
		log.info("{}.{} Execution time :: {} ms", className, methodName, stopWatch.getTotalTimeMillis());

		return result;
	}

}
