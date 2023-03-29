package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> Java(다른언어) Object -> 테이블 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="board")
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;	
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인 됨.
	
//	@ColumnDefault("0")
	private int count; // 조회수
	
	// (fetch = FetchType.EAGER) 게시글 한건에 하나의 user가 오므로 셀렉트할때 무조건 user의 정보를 가져온다.
	@ManyToOne (fetch = FetchType.EAGER)// Many = Many, User = One (연관관계 지어줌)
	@JoinColumn(name="userId") // Foreign Key로 사용되어질 이름
	private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
	
	// mappedBy 연관관계의 주인이 아니다(난 FK가 아니에요) DB 칼럼을 만들지 마세요. 나는 그냥 board를 셀렉트할때 조인문을 통해서 값을 얻기 위해서 있다.
	// mappedBy = "board" 의 board명은 Reply.java의 private Board board; 명이다.
	// (fetch = FetchType.LAZY) 게시글 한건에 여러개의 reply가 오므로 셀렉트할때 필요할때 가져오게 한다.(FetchType.LAZY)
	// (fetch = FetchType.EAGER) 게시글 한건에 여러개의 reply가 오는데 셀렉트할때 다 가져오게 한다.(FetchType.EAGER)
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 여러의 답변글이 한개의 게시글에 달릴 수 있다.
	@JsonIgnoreProperties({"board"}) // 무한 참조를 방지하기 위함(Reply model에서 board 정보를 가져오기때문)
	@OrderBy("id desc") // 정렬
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
