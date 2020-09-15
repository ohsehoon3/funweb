package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

//@Component 계열 애노테이션 이름: @Component, @Configuration, @Controller, @RestController, @Service, @Repository 등
@SpringBootApplication  // @Component 계열 애노테이션이 붙은 클래스들을 스캔해서 스프링 빈으로 등록
@MapperScan("com.example.mapper") // 해당 패키지의 매퍼 인터페이스를 구현한 객체를 만들고 스프링 빈으로 등록 
@EnableAspectJAutoProxy // 프로젝트 내의 @Aspect 붙은 클래스들을 스캔해서 처리
//@EnableScheduling  // 프로젝트 내의 @Scheduled 붙은 클래스들을 스캔해서 처리
public class FunwebBootApplication {

	public static void main(String[] args) {
		// http://localhost:8083/
		SpringApplication.run(FunwebBootApplication.class, args);
	}
}
