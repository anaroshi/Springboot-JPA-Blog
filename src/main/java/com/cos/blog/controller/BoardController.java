package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	// 홈
	// public String index(@AuthenticationPrincipal PrincipalDetail principal) { // 컨드롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"","/"})
	public String index() { // 컨드롤러에서 세션을 어떻게 찾는지?
		// /WEB-INF/views/index.jsp
		//	System.out.println("......... BoardController 로그인 사용자 아이디 : "+principal.getUsername());
		return "index";
	}

	// user 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		
		return "board/saveForm";
	}
}
