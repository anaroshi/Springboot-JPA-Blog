package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

//인증이 안된 사용자들이 출입할 수 있는 경로는 /auth/** 허용  ==> 인증이 필요없는 곳에 /auth/ 경로를 붙힘 

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	// 게시판 글쓰기
	@PostMapping("/api/boardProc")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("save.. board : "+ board);
		System.out.println("save.. board User : "+ principal.getUser());
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		boardService.save(board, principal.getUser());		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	// 게시판 글삭제
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		System.out.println("deleteById.. id : "+ id);
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		boardService.deleteById(id);		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	//  게시판 글 수정
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		System.out.println("update.. id : "+ id);
		System.out.println("update.. board : "+ board);
		boardService.updateById(id, board);		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	// 게시판 댓글 등록	
//	@PostMapping("/api/board/{boardId}/reply")
//	public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
//		System.out.println("replySave.. boardId : "+ boardId);
//		System.out.println("replySave.. Reply : "+reply);
//		System.out.println("replySave.. User : "+ principal.getUser());
//
//		boardService.replySave(principal.getUser(), boardId, reply);
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
	
	// 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
	// 게시판 댓글 등록	
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto reply) {
		System.out.println("replySave.. ReplySaveRequestDto : "+reply);
		boardService.replySave(reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	// 게시판 댓글 삭제
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
		System.out.println("replyDelete.. replyId : "+replyId);
		boardService.replyDelete(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
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
