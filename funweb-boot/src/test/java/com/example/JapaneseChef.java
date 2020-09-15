package com.example;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary // @Component 겹칠때 우선순위를 의미함.
@Component
public class JapaneseChef implements Chef {

	@Override
	public void doCook() {
		System.out.println("일본 음식을 요리합니다.");
	}

}
