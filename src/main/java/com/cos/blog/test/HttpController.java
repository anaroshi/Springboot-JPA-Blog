package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpController {
	
	private static final String TAG = "HttpController : ";
	
	// http://localhost:8070/http/get (select)
	/*
	 * @GetMapping("/http/get") public String getTest(@RequestParam int
	 * id, @RequestParam String username) { return
	 * "get 요청 id:"+id+", username:"+username; }
	 */

	// http://localhost:8070/blog/http/get?id=1&username=anaroshi&password=badami
	@GetMapping("/http/get")
	public String getTest(Member m) { // MessageConverter(스프링부트)		
		return "get 요청 id:"+m.getId()+", username:"+m.getUsername()+", password:"+m.getPassword();
	}
	
	// http://localhost:8070/blog/http/lombokTest?id=1&username=anaroshi&password=badami
	@GetMapping("/http/lombokTest")
	public String lombokTest(Member m) {
		System.out.println(TAG+"getter : "+m.getId());
		m.setId(5000);
		System.out.println(TAG+"getter : "+m.getId());		
		Member m1 = new Member(1,"sundor","kemon","sundor@hanmail.net");
		System.out.println(TAG+"m1  : "+m1 );
		Member m2 = new Member();
		m2.setId(5000);
		m2.setUsername("anaroshi");
		m2.setPassword("kemon");
		m2.setEmail("anaroshi@naver.com");
		System.out.println(TAG+"m2  : "+m2 );
		Member m3 = Member.builder().username("Ann").password("kemon").email("Ann@hanmail.net").build();
		m3.setId(5);
		System.out.println(TAG+"m3  : "+m3 );
		return "lombokTest m1:"+m1+", m2:"+m2+", m3:"+m3;
	}
	
	// http://localhost:8070/http/post (insert)
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { // MessageConverter(스프링부트)
		return "post 요청 id:"+m.getId()+", username:"+m.getUsername()+", password:"+m.getPassword()+", email:"+m.getEmail();
	}
//  raw/application/json
//	{
//	    "id":1,
//	    "username":"anaroshi",
//	    "password":1234,
//	    "email":"sundor@hanmail.net"
//	}
	
//	@PostMapping("/http/post") // raw/text/plain, raw/application/json
//	public String postTest(@RequestBody String text) {
//		return "post 요청 text:"+text;
//	}

	// http://localhost:8070/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 id:"+m.getId()+", username:"+m.getUsername()+", password:"+m.getPassword()+", email:"+m.getEmail();		
	}

	// http://localhost:8070/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest(@RequestBody Member m) {
		return "delete 요청 id:"+m.getId()+", username:"+m.getUsername()+", password:"+m.getPassword()+", email:"+m.getEmail();
	}

}
