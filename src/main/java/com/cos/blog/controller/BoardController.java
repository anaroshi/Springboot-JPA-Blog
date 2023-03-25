package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 홈
	// public String index(@AuthenticationPrincipal PrincipalDetail principal) { // 컨드롤러에서 세션을 어떻게 찾는지?
	// http://localhost:8070/?page=1
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Direction.DESC) Pageable pageable) { // 컨드롤러에서 세션을 어떻게 찾는지?
		// /WEB-INF/views/index.jsp
		//	System.out.println("......... BoardController 로그인 사용자 아이디 : "+principal.getUsername());
		model.addAttribute("boards", boardService.list(pageable));
		return "index"; // viewResolve
	}

	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.view(id));
		return "board/detail";
	}
	
	// 게시판 글쓰기 폼
	// user 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {		
		return "board/saveForm";
	}
}
