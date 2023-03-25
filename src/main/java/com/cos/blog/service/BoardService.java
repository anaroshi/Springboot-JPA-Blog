package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 게시판 글쓰기
	@Transactional  // import org.springframework.transaction.annotation.Transactional;
	public void save(Board board, User user) {
		System.out.println("boardService .... save.. board : "+ board);
		System.out.println("boardService .... save.. user : "+ user);
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	// 게시판 글 목록
	@Transactional(readOnly = true)
	public Page<Board> list(Pageable pageable) {
		System.out.println("boardService .... list");
		return boardRepository.findAll(pageable);
	}
	
	// 게시판 글 상세 보기
	@Transactional(readOnly = true)
	public Board view(int id) {
		return boardRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("글 상세보기 실패 - id : "+id+"를 찾을수 없습니다.");
		});
	}
	
	// 게시판 글삭제
	@Transactional
	public void deleteById(int id) {
		System.out.println("boardService .... deleteById : "+id);
		boardRepository.deleteById(id);
	}
	
}
