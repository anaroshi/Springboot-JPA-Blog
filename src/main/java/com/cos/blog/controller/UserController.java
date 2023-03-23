package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	// 회원가입폼
	@GetMapping("/user/joinForm")
	public String joinForm() {
		// /WEB-INF/views/joinForm.jsp
		return "user/joinForm";
	}

	// 로그인폼
	@GetMapping("/user/loginForm")
	public String login() {
		// /WEB-INF/views/loginForm.jsp
		return "user/loginForm";
	}

}
