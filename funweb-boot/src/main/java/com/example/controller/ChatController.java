package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Log
@RequestMapping("/chat/*")
@Controller
public class ChatController {
	
	@GetMapping("/chat")
	public void chat() { // void일 경우: 위쪽 RequestMapping의 "/chat/*"을 기본 경로로 지정함.
		log.info("chat..............");
		//return "chat/chat"; // String일 경우 코드 사용 
	}
}
