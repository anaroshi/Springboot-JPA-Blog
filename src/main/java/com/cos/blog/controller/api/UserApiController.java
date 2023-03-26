package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

//인증이 안된 사용자들이 출입할 수 있는 경로는 /auth/** 허용  ==> 인증이 필요없는 곳에 /auth/ 경로를 붙힘 

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// 회원 가입
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("save.. user : "+ user);
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.		
		userService.save(user);		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}

	// 회원정보 수정
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		System.out.println("User update .. user : "+ user);
		userService.update(user);
		
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해주어야 한다.
		// 세션 등록 ***********
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	/*
	// 전통 로그인 방식
	@PostMapping("/api/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) { // session 만들기
	public ResponseDto<Integer> login(@RequestBody User user) { // @Autowired로 private HttpSession session;을 상단에 선언해 놓으면 생략가능
		System.out.println("login.. user : "+user);
		User principal = userService.login(user); // principal(접근주체)
		if(principal != null) {
			session.setAttribute("principal", principal);
			System.out.println("login.. principal : "+principal);
			return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
		}
		return new ResponseDto<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	*/
	
	// Security 로그인은 필요없음	
	
}
