package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 인증이 안된 사용자들이 출입할 수 있는 경로는 /auth/** 허용  ==> 인증이 필요없는 곳에 /auth/ 경로를 붙힘 
// 그냥 주소가 /이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

	// 회원가입폼
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		// /WEB-INF/views/user/joinForm.jsp
		return "user/joinForm";
	}

	// 로그인폼
	@GetMapping("/loginForm")
	public String loginForm() {
		// /WEB-INF/views/user/loginForm.jsp
		return "user/loginForm";
	}
	
	// 회원정보
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
}
