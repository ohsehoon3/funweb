package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.domain.MailDto;
import com.example.mail.MyMailSender;

import lombok.extern.java.Log;

@Controller
@Log
public class HomeController {
	
	@Autowired
	private MyMailSender mailSender;
	
	@GetMapping("/")
	public String index() {
		log.info("index() 호출됨");
		return "index"; // 디스패치방식 호출
	}
	
	@GetMapping("/company/welcome")
	public void welcome() {
		log.info("welcome() 호출됨");
		// 리턴타입이이 String일때
		//return "company/welcome"; 
		// 리턴타입이 void면
		// url요청 주소 경로("/company/welcome")를 jsp 경로로 사용함.
	}
	
	@GetMapping("/company/history")
	public void history() {
		log.info("history() 호출됨");
	}
	
	@GetMapping("/mail")
	public String mail() {
		return "contact/mail";
	}
	
	@PostMapping("/mail")
	public String sendMail(MailDto mailDto) {
		log.info("mailDto :" + mailDto);
		
		mailSender.sendHtmlMail(mailDto);
		return "redirect:/mail";
	}
}
