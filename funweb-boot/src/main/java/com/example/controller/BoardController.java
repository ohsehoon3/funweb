package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.BoardVo;
import com.example.domain.PageDto;
import com.example.service.BoardService;

import lombok.extern.java.Log;

@Log
@RequestMapping("/board/*")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list")
	public String list(
			@RequestParam(defaultValue = "1") int pageNum,			
			@RequestParam(defaultValue = "") String category,
			@RequestParam(defaultValue = "") String search,
			Model model) {
		log.info("list() 호출됨");
		
		// 전체 글 갯수
		int totalCount = boardService.getTotalCount(category, search);
		
		// ======================================
		// 한페이지에 해당하는 글목록 구하기 작업
		// ======================================
		
		// 한 페이지에 15개씩 가져오기
		int pageSize = 15;
		// 시작행 인덱스번호 구하기(수식)
		int startRow = (pageNum - 1) * pageSize;
		
		// 원하는 페이지의 글을 가져오는 메소드
		List<BoardVo> list = null;
		if (totalCount > 0) {
			list = boardService.getBoards(startRow, pageSize, category, search);
		}
		
		// ======================================
		// 페이지 블록 관련정보 구하기 작업
		// ======================================
		
		// 총 페이지 수 구하기
		/*int pageCount = (totalCount / pageSize) + (totalCount % pageSize == 0 ? 0 : 1);*/ // 삼항연산자 활용가능
		int pageCount = (totalCount / pageSize);
		if (totalCount % pageSize > 0) {
			pageCount += 1;
		}
		
		// 화면에 보여줄 페이지 번호의 갯수 설정
		int pageBlock = 10;
		
		// 페이지블록의 시작페이지 구하기!
		int startPage = ((pageNum / pageBlock) - (pageNum % pageBlock == 0 ? 1 : 0 )) * pageBlock + 1;
		
		// 페이지블록의 끝페이지
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) { // 글 없는 페이지 번호 안주기 
			endPage = pageCount;
		}
		
		// 페이지블록 관련 정보를 PageDTO에 저장(Map 컬렉션 사용 가능)
//		Map<String, Object> pageMap = new HashMap<>();
//		pageMap.put("totalCount", totalCount);
//		pageMap.put("pageCount", pageCount);
//		pageMap.put("pageBlock", pageBlock);
//		pageMap.put("startPage", startPage);
//		pageMap.put("endPage", endPage);
//		pageMap.put("category", category);
//		pageMap.put("search", search);
		
		PageDto pageDto = new PageDto();
		pageDto.setTotalCount(totalCount);
		pageDto.setPageCount(pageCount);
		pageDto.setPageBlock(pageBlock);
		pageDto.setStartPage(startPage);
		pageDto.setEndPage(endPage);
		pageDto.setCategory(category);
		pageDto.setSearch(search);
		
		// 뷰(jsp)에서 사용할 데이터를 model 객체에 저장해놓으면
		// 스프링 프론트컨트롤러가  request 영역객체로 옮겨줌
		model.addAttribute("boardList", list);
		model.addAttribute("pageDto",	pageDto);
		//model.addAttribute("pageMap", pageMap);
		model.addAttribute("pageNum", pageNum);
		
		return "center/notice";
	} // list()
	
	@GetMapping("/write")
	public String write() {
		return "center/writeForm";
	}
	
	@PostMapping("/write")
	public String write(BoardVo boardVo, HttpServletRequest request) {
		// ip주소  작성일자 값 저장
		boardVo.setIp(request.getRemoteAddr());
		boardVo.setRegDate(LocalDateTime.now());
		
		// 주글 한개 등록
		boardService.insert(boardVo);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/content")
	public String content(int num, @ModelAttribute("pageNum") String pageNum, Model model) {
		// 조회수 1증가
		boardService.updateReadcount(num);
		// 글 한 개 가져오기
		BoardVo vo = boardService.getBoardByNum(num);
		// 내용에서 엔터키 줄바꿈 \r\n -> <br> 바꾸기
		String content = "";
		if (vo.getContent() != null) {
			content = vo.getContent().replace("\r\n", "<br>");
			vo.setContent(content);
		}
		
		// 뷰(jsp)에서 사용할 데이터를 model 객체에 저장해놓으면
		// 스프링 프론트컨트롤러가  request 영역객체로 옮겨줌
		model.addAttribute("boardVo", vo);
		//model.addAttribute("pageNum", pageNum); // @ModelAttribute 사용시 필요 없음. 파라미터 배달 용도일 때 사용
		
		return "center/content";
	}
	
	@GetMapping("/reply")
	public String reply(BoardVo boardVo, 
			@ModelAttribute("pageNum") String pageNum) {
		// BoardVo에 reRef, reLev, reSeq 파라미터 설정됨
		// 스프링 프론트컨트롤러는 우리 메소드 파라미터에 VO형식 클래스가 선언되어 있으면 
		// VO 객체를 항상 model 객체에 추가 해주기 때문에 뷰(JSP)에서 사용 가능 (@ModelAttribute 필요 없음)
		// return "redirect:...."; 할 경우에는 안한다.
		
		return "center/replyWriteForm";
	}
	
	@PostMapping("/reply")
	public String reply(BoardVo boardVo, 
			String pageNum,
			HttpServletRequest request,
			RedirectAttributes rttr) {
		// ip주소  작성일자 값 저장
		boardVo.setIp(request.getRemoteAddr());
		boardVo.setRegDate(LocalDateTime.now());
		
		// 답글 한 개 등록
		boardService.replyInsert(boardVo);
		
		// [return "redirect:/board/list?pageNum=" + pageNum;] 대신 RedirectAttributes 사용
		// rttr.addAttribute 여러개 가능
		rttr.addAttribute("pageNum", pageNum);
		return "redirect:/board/list";
	}
}
