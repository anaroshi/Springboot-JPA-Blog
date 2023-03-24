package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	// 회원가입
	@Transactional  // import org.springframework.transaction.annotation.Transactional;
	public void save(User user) {
		System.out.println("userService .... save.. user : "+ user);
		user.setPassword(encoder.encode(user.getPassword())); // 비밀번호 해쉬 처리
		user.setRole(RoleType.USER); // user의 권한 지정
//		try {
//			userRepository.save(user);
//			return 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("UserService : 회원가입() : "+e.getMessage());
//		}
//		return -1;
		userRepository.save(user);
	}
	
//	// 로그인 - 전통 로그인 방식
//	@Transactional(readOnly = true) // Select 할 때 트랜잭션을 시작, 서비스 종료시에 트랜잭션 종료(정합성 유지)
//	public User login(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
	// 회원조회 by id
	@Transactional(readOnly = true)
	public User userInfo(User user) {
		// 람다식
		User userInfo = userRepository.findById(user.getId()).orElseThrow(()->{ return new IllegalArgumentException("해당 사용자는 없습니다. id : "+user.getId()); });
		return userInfo;
	}
	
}
