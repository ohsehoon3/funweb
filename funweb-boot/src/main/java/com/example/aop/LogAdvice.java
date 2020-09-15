package com.example.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

@Aspect
@Component
@Log
public class LogAdvice { // Advice: 여러 주요 로직에 적용될 보조기능 클래스
	
	@Before("execution(public * com.example.service.SampleService.*(*,*))") // '*'은 아무거나..를 의미 //'*,*'은 매개변수 2개를 의미. 갯수 상관없으면 '..' 사용 
	public void logBefore() {
		log.info("================================");
	}
	
	@Before("execution(* com.example.service.SampleService.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1:" + str1);
		log.info("str2:" + str2);
	}
	
	@AfterThrowing(pointcut = "execution(* com.example.service.SampleService.*(..))", throwing = "exception")
	public void logExeception(Exception exception) {
		log.info("Exception...!!!");
		log.info("exception : " + exception);
	}
	
	@Around("execution(* com.example.service.SampleService.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		
		log.info("Target :" + pjp.getTarget());
		log.info("Param : " + Arrays.toString(pjp.getArgs())); // .getArgs()는 Object 타입 리턴
		
		Object result = null;
		
		result = pjp.proceed(); // doAdd() 호출함
		
		long endTime = System.currentTimeMillis();
		
		log.info("method run time : " + (endTime - startTime) + "ms");
		
		return result;
	}
}
