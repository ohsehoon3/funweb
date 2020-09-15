package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
	Spring Web MVC 모듈에서
	@Component를 상속받는 애노테이션 종류
	- @Controller : 컨트롤러 역할을 하는 클래스에 사용
	- @Service    : 트랜잭션 처리를 수행하는 클래스에 사용
	- @Repository : (저장소) DAO클래스에 사용
*/

@Component
public class Restaurant {

	@Autowired
	private Chef chef/* = new Chef() */;

	public void makeDish() {
		chef.doCook();
	}
}
