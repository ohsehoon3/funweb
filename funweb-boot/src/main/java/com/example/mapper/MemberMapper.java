package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.example.domain.MemberVo;

public interface MemberMapper {
	// 회원가입 메소드 
	// SQL문이 길 때 주로 사용
	// 애노테이션(@)이 없으면 같은 경로의 같은 이름의
	// xml 매퍼파일을 찾아서 해당 sql구문을 실행함
	int insert(MemberVo vo);

	@Select("SELECT passwd FROM member where id = #{id} ")
	String getPasswdById(String id);
	
	@Select("SELECT COUNT(*) FROM member WHERE id = #{id} ")
	int countMemberById(String id);
	
	@Select("SELECT * FROM member Order By id ASC ")
	List<MemberVo> getMembers();
	
	@Select("SELECT * FROM member WHERE id = #{id} ")
	MemberVo getMemberById(String id);
	
	// @ 생략
	int update(MemberVo vo);
	
	@Delete("DELETE FROM member WHERE id = #{id}")
	int deleteById(String id);
	
	@Delete("DELETE FROM member")
	void deleteAll();
	
	@Select("SELECT COUNT(*) FROM member")
	int getCount();
	
}
