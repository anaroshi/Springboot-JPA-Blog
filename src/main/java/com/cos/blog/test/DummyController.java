package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController // html 파일이 아니라 data를 리턴해주는 controller이다.
public class DummyController {
	
	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository; 

	// 회원 가입
	// http://localhost:8070/blog/dummy/join (요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value(약속된 규칙)
		System.out.println("username : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user); // 회원가입 DB처리
		
		return "회원가입이 완료되었습니다.";
	}
	
	/*
	 * @PostMapping("/dummy/join") 
	 * public String join(String username, String password, String email) { // key=value(약속된 규칙
	 *     System.out.println("username : "+username);
	 *     System.out.println("password : "+password);
	 *     System.out.println("email : "+email); return "회원가입이 완료되었습니다."; 
	 * }
	 */
	
	// {id} 주소로 파라메터를 전달 받을 수 있음.
	// http://localhost:8070/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될것 아냐?
		// 그럼 return null이 리턴되면 프로그램에 문제가 되지
		// Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
		// User user = userRepository.findById(id); // 반환값이 User가 아닌 Optional<User>이다.
		
/*
		   	User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		}); // 반환값이 User가 아닌 Optional<User>이다.
*/		
		// 람다식
		User user = userRepository.findById(id).orElseThrow(()->{ return new IllegalArgumentException("해당 유저는 없습니다. id : "+id); });
		
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저로 이해할 수 있는 데이터) -> json (Gson라이브러리) => 스프링
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
	
	// user 전체 조회
	// http://localhost:8070/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 페이지별 조회 ( 한페이지에 2건의 데이터를 리턴, id 기준으로 내림차순, 페이지 정보 포함)
	// http://localhost:8070/blog/dummy/pageInfoUsers
	// http://localhost:8070/blog/dummy/pageInfoUsers?page=0
	// http://localhost:8070/blog/dummy/pageInfoUsers?page=1	
	@GetMapping("/dummy/pageInfoUsers")
	public Page<User> pageInfoUsersList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<User> users =  userRepository.findAll(pageable);
		return users;
	}
	
	// 페이지별 조회 ( 한페이지에 2건의 데이터를 리턴, id 기준으로 내림차순)
	// http://localhost:8070/blog/dummy/pageUsers
	// http://localhost:8070/blog/dummy/pageUsers?page=0
	// http://localhost:8070/blog/dummy/pageUsers?page=1	
	@GetMapping("/dummy/pageUsers")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<User> pagingUsers =  userRepository.findAll(pageable);
		
		if (pagingUsers.isFirst()) {
			System.out.println(".............. First Page");
		} else if (pagingUsers.isLast()) {																																																																																																																	
			System.out.println(".............. Last Page");
		}
		
		List<User> users = pagingUsers.getContent();
		return users;
	}
	
	// 수정
	// save함수는 id를 전달하지 않으면 insert를 해주고
	// id를 전달하여 해당 id에 대한 데이타가 있으면 update를 해주고
	// id를 전달하여 해당 id에 대한 데이타가 없으면 insert를 한다.
	// email, password 수정
	// http://loc																																																																																													alhost:8070/blog/dummy/user/1
	@Transactional // 선언하면 save 함수를 호출하지 않아도 자동으로 save된다.(더티채킹) // 함수 종료시 자동 commit된다.
	@PutMapping("/dummy/user/{id}")	
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		// @RequestBody : json 데이터를 요청 => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아준다.)
		System.out.println("id : "+id);
		System.out.println("password : "+ requestUser.getPassword());
		System.out.println("email : "+ requestUser.getEmail());
		
		// 람다식
		User user = userRepository.findById(id).orElseThrow(()->{ return new IllegalArgumentException("수정에 실패하였습니다. id : "+id); });
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// 더티채킹
		// @Transactional을 선언하면 save 함수를 호출하지 않아도 자동으로 save된다.
		// userRepository.save(user); // 수정을 행한다.
		
		return user;
	}
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																													
	// 삭제
	// http://localhost:8070/blog/dummy/user/1
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {			
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}		
		return "삭제되었습니다. id : "+id;
	}
}
