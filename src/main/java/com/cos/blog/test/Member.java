package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 전체 생성자 => Member m1 = new Member(1,"sundor","kemon","sundor@hanmail.net");
@NoArgsConstructor // 빈 생성자 => Member m2 = new Member();
@Builder // 필요한 생성자만 생성, 순서상관없음 => Member m3 = Member.builder().username("Ann").email("Ann@hanmail.net").password("kemon").build();
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	
}
