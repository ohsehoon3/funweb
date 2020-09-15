package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.interceptor.MemberLoginCheckInterceptor;
import com.example.interceptor.MemberStayLoggedInInterceptor;


@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private MemberLoginCheckInterceptor memberLoginCheckInterceptor;
	@Autowired
	private MemberStayLoggedInInterceptor memberStayLoggedInInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		InterceptorRegistration reg = registry.addInterceptor(memberLoginCheckInterceptor);
		reg.addPathPatterns("/board/file*");
		reg.excludePathPatterns("/board/fileList");
		
		registry.addInterceptor(memberStayLoggedInInterceptor)
		.addPathPatterns("/*");
	}
	
}
