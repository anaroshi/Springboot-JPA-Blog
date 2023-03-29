package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;
	
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
	
	// 게시판 글수정
	@Transactional
	public void updateById(int id, Board requestBoard) {
		System.out.println("boardService .... updateById : "+id);
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 - id : "+id+"를 찾을수 없습니다.");
		}); // 영속화
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될 때)에 트랜잭션이 종료됩니다. 이때 더티체킹이 일어나면서 자동 업데이트가 됨. db flush 따로 저장명령어를 넣어줄 필요가 없다. 
		// @PutMapping
	}
	
	// 게시판 댓글 등록
	@Transactional
	public void replySave(User user, int boardId, Reply requestReply) {
		System.out.println(" ---------------- boardService ----------------");
		System.out.println("boardService .... replySave.. user : "+user);
		System.out.println("boardService .... replySave.. boardId : "+ boardId);
		System.out.println("boardService .... replySave.. requestReply : "+ requestReply);
		
		Board board = boardRepository.findById(boardId).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 - 게시글 id : "+boardId+"를 찾을수 없습니다.");
		});		
		requestReply.setUser(user);
		requestReply.setBoard(board);
		replyRepository.save(requestReply);		
	}
	
}