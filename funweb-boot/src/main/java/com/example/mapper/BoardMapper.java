package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.domain.BoardVo;

public interface BoardMapper {
	
	@Select ("select ifnull(max(num), 0) + 1 as max_num from board ")
	int getBoardNum();
	
	int insertBoard(BoardVo vo);
	
	int getTotalCount(@Param("category") String category, @Param("search") String search);
	
	// 매퍼 메소드의 매개변수가 2개 이상일때는
	// @Param 애노테이션 값으로 sql문에 배치.
	List<BoardVo> getBoards(@Param("startRow") int startRow, 
							@Param("pageSize") int pageSize, 
							@Param("category") String category, 
							@Param("search") String search);
	
	BoardVo getBoardByNum(int num);
	
	void updateReadcount(int num);
	
	void update(BoardVo vo);
	
	@Delete ("DELETE FROM board WHERE num = #{num} ")
	void deleteByNum(int num);
	
	int updateReSeqByReRef(@Param("reRef") int reRef, @Param("reSeq") int reSeq);
	
	// board와 attachfile 테이블 조인해서 select하기
	BoardVo getBoardAndAttachfilesByNum(int num);
	
	void updateReplycnt(@Param("bno") int bno, @Param("amount") int amount);
	
	@Delete ("DELETE FROM board ")
	void deleteAll();
	
	@Select("SELECT COUNT(*) FROM board ")
	int getCount();
}
