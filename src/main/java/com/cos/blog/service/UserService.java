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
	
	// 회원 정보 수정
	@Transactional
	public void update(User requestUser) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화 된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로 가져오는 이유는 영속화를 하기 위해서!!
		// 영속화된 오브젝트를 변경하면 자동으로 더티체킹이 일어나면서 DB에 update문을 날려준다. db flush 따로 저장명령어를 넣어줄 필요가 없다. 
		// 람다식
		User user = userRepository.findById(requestUser.getId()).orElseThrow(()->{ return new IllegalArgumentException("해당 사용자는 없습니다. id : "+requestUser.getId()); });
		String rawPassword = requestUser.getPassword();
		user.setPassword(encoder.encode(rawPassword)); // 해쉬코드로 암호화한다.
		user.setEmail(requestUser.getEmail());
		// 회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 된다.
		// 영속화된 user 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
	}
	
	// 로그인 - 전통 로그인 방식
//	@Transactional(readOnly = true) // Select 할 때 트랜잭션을 시작, 서비스 종료시에 트랜잭션 종료(정합성 유지)
//	public User login(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
}
