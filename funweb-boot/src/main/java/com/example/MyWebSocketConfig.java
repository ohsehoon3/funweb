package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.example.websocket.MyTextWebSocketHandler;

@Configuration // @Component 계열
@EnableWebSocket // 웹 소켓 서버 기능 활성화하기
public class MyWebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private MyTextWebSocketHandler webSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// "/chatting"는 소켓 연결을 위한 주소로서 http 연결이 아닌 ws 연결이 되야 함!
		registry.addHandler(webSocketHandler, "/chatting")
				.addInterceptors(new HttpSessionHandshakeInterceptor()) 
				.setAllowedOrigins("*");
		// 클라이언트와 서버가 채팅으로 들어오기전엔 http로 통신하다가 채팅연결되면 웹 소켓으로 통신함.
		// http로 통신할때 HttpSession에 있는 attributes 값들을 
		// HttpSessionHandshakeInterceptor()가 WebSocetSession으로 복사해줌.
	}
	
	@Bean // 다른 사람이 생성한 메서드일 경우 @Bean 사용
	public TaskScheduler taskScheduler() { // Quartz 작동 안될 때 사용 
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

	    scheduler.setPoolSize(2);
	    scheduler.setThreadNamePrefix("scheduled-task-");
	    scheduler.setDaemon(true);

	    return scheduler;
	}

}
