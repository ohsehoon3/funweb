package com.example.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// member 테이블의 레코드(행) 한 개를 표현하는 클래스
//@Getter
//@Setter
//@ToString
@Data
@NoArgsConstructor // MemberVo() 기본생성자
@AllArgsConstructor // MemberVo(String, String, String, String, LocalDateTime, String, String, String) 전체인자를 받는 생성자
public class MemberVo implements Cloneable {
	private String id;
	private String passwd;
	private String name;
	private String email;
	private LocalDateTime regDate;
	private String address;
	private String tel;
	private String mtel;
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
