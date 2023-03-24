package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	// 회원가입폼
	@GetMapping("/joinForm")
	public String joinForm() {
		// /WEB-INF/views/joinForm.jsp
		return "user/joinForm";
	}

	// 로그인폼
	@GetMapping("/loginForm")
	public String login() {
		// /WEB-INF/views/loginForm.jsp
		return "user/loginForm";
	}
	
	@PostMapping("/userForm")
	public String save(@RequestBody User user ) {
		System.out.println("save.. user : "+ user);
		// DB에서 select를 하고 아래에서 return이 되면 된다.
		
		userService.userInfo(user);
		return "user/userForm";
	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout() {
		// /WEB-INF/views/logout.jsp
		return "user/logout";
	}
}
