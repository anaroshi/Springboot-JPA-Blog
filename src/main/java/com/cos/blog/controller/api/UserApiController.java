package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("save.. user : "+ user);
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		user.setRole(RoleType.USER);
		userService.save(user);		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	// 전통 로그인 방식
	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) { // session 만들기
	public ResponseDto<Integer> login(@RequestBody User user) { // @Autowired로 session을 상단에 선언해 놓으면 생략가능
		System.out.println("login.. user : "+user);
		User principal = userService.login(user); // principal(접근주체)
		if(principal != null) {
			session.setAttribute("principal", principal);
			System.out.println("login.. principal : "+principal);
			return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
		}
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
}
