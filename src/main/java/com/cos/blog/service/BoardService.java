package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Board> list() {
		System.out.println("boardService .... list");
		return boardRepository.findAll();
	}
	
}