package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller // 파일을 반환해준다.
public class TempController {

	// http://localhost:8070/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일 리턴 기본경로 : src/main/resources/static => 부라우저가 인식하는 정적파일을 모아 놓는다.
		// 리턴명: /home.html
		// 풀경로 : 파일 리턴 기본경로+리턴명 => src/main/resources/static/home.html
		return "/home.html";
	}
	
	// http://localhost:8070/blog/temp/img	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/dice6.jpg";
	}
	
	// http://localhost:8070/blog/temp/jsp	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//	 prefix: /WEB-INF/views/
		// suffix: .jsp
		// 풀경로 :  /WEB-INF/views/test.jsp
		return "test";
	}
}
