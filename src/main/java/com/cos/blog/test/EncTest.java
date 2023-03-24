package com.cos.blog.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {
	
	@Test
	public void HashPwd() {
		String encPassword = new BCryptPasswordEncoder().encode("1234");
		System.out.println("해쉬 암호화 테스트 : "+encPassword);
	}
}