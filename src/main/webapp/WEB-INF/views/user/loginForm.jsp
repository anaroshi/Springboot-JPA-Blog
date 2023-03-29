<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username</label>
			<input type="text" class="form-control" placeholder="Enter Username" id="username" name="username">
		</div>
		<div class="form-group">
			<label for="password">Password:</label>
			<input type="password" class="form-control" placeholder="Enter Password" id="password" name="password">
		</div>
		<div class="form-group form-check">
			<label class="form-check-label">
			<input class="form-check-input" type="checkbox" name="remember">Remember me</label>
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=b353b3e01d0a3b17503c48af3fb63cf0&redirect_uri=http://localhost:8070/auth/kakao/callback&response_type=code"><img height="38" width="70" alt="" src="/image/kakao_login_button.png"></a>
	</form>	

</div>

<%@ include file="../layout/footer.jsp"%>