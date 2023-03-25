<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container">

	<form action="/auth/loginProc" method="post">
	
		<div class="form-group">
			<label for="username">Title</label>
			<input type="text" class="form-control" placeholder="Enter Title" id="title" name="title">
		</div>
		
		<div class="form-group">
			<label for="content">Content</label>
			<textarea class="form-control summernote" rows="5" id="content" name="content"></textarea>
		</div>
		
	</form>	
	<button id="btn-save" class="btn btn-primary">글쓰기 완료</button>
	
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>

<script>
$('.summernote').summernote({
  	tabsize: 2,
  	height: 300
});
</script>
