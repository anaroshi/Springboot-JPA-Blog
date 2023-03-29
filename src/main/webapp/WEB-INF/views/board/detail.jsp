<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container">
	
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.id==principal.user.id}">
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br><br>
	<div>
		글 번호 : <span id="id"><i>${board.id}</i></span>
		작성자 : <span><i>${board.user.username}</i></span>		
	</div>
	<br>
	<div class="form-group">			
		<h3>${board.title}</h3>
	</div>
	<hr>
	<div class="form-group">
		<div>${board.content}</div>
	</div>
	<hr>
	
	<!-- 댓글 -->	
	<div class="card">
		<div class="card-body"><textarea class="form-control" rows="1" cols=""></textarea></div>
		<div class="card-footer"><button class="btn btn-primary">등록</button> </div>
	</div>
	
	<div class="card">
		<div class="card-header">댓글리스트</div>
		<ul class="list-group" id="comment--box">
			<li class="list-group-item d-flex justify-content-between" id="comment--1">
				<div>댓글 내용입니다!!</div>
				<div class="d-flex">
					<div class="font-italic">작성자 : sundor &nbsp;</div>
					<button class="badge"  id="btn-reply" >삭제</button>
				</div>
				
			</li>
		</ul>
	</div>
	
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>
