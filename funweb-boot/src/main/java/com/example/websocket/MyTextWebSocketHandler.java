package com.example.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.service.MemberService;

import lombok.extern.java.Log;

// 소켓 서버 역할 클래스
@Component
@Log
public class MyTextWebSocketHandler extends TextWebSocketHandler {
	// MyTextWebSocketHandler는 서버 역할
	
	@Autowired
	private MemberService memberService; // 나중에 로그인 기반으로 만들 때 필요
	
	private JSONParser jsonParser = new JSONParser();
	
	private Map<String, WebSocketSession> sessionMap = new HashMap<>(); // 웹 소켓 세션을 담아둘 맵
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// HttpSession에 있는 attributes 값 가져오기
		Map<String, Object> attrsMap = session.getAttributes();
		String loginId = (String) attrsMap.get("id");
		log.info("loginId : " + loginId);
		log.info("웹 소켓 클라이언트와 연결됨");
		// ===================================================
		
		// 클라이언트 웹 소켓 세션 관리를 위해 Map컬렉션에 저장
		sessionMap.put(session.getId(), session);
		
		// 세션아이디를 클라이언트로 보내기
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "getId"); // 세션아이디를 데이터로 가진다는 표시
		jsonObject.put("sessionId", session.getId());
		log.info("");
		
		session.sendMessage(new TextMessage(jsonObject.toJSONString()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonStr = message.getPayload();
		
		JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonStr); // 중간 작업을 하기위한 파싱작업
		
		Set<String> keySet = sessionMap.keySet();
		
		// 모든 채팅 참여자에게 브로드캐스트하기
		for (String key : keySet) {
			WebSocketSession wss = sessionMap.get(key);
			wss.sendMessage(new TextMessage(jsonObj.toJSONString()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 웹 소켓 세션 연결이 종료되면(사용자가 접속 종료하면) 호출됨.
		log.info("웹 소켓 클라이언트와 연결이 끊김");
		sessionMap.remove(session.getId());
	}
	
}
