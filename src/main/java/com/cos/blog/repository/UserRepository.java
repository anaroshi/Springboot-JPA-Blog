package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// DAO
// 자동으로 Bean 등록이 된다.
// @Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer>{
	
	// JPA Naming 쿼리
	// SELECT * FROM user WHERE username = ?1 AND password = ?2;	
	User findByUsernameAndPassword(String username, String password);
	
	// 위와 같은 역활을 한다. 쿼리를 생성하는 2가지 방법	
	//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
	//	User login(String username, String password);
}
