<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container">

<c:forEach items="${boards.content}" var="board">
	<div class="card m-2">	 
	  <div class="card-body">
	  
	    <h4 class="card-title">${board.title}</h4>
	    <div class="d-flex">
	    	<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
	    	<h5 class="card-title ml-auto">${board.user.username}</h5>
		</div>
 
	  </div>
	</div>
</c:forEach>

<ul class="pagination justify-content-center">
	<c:choose>
		<c:when test="${boards.first }">
			<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
		</c:otherwise>
	</c:choose>
	<c:forEach var="i" begin="0" end="${boards.totalPages-1 }">		
			<li class="page-item <c:if test="${boards.number == i}">active</c:if>"><a class="page-link" href="?page=${i}">${i+1}</a></li>
	</c:forEach>
	<c:choose>
		<c:when test="${boards.last }">
			<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
		</c:otherwise>
  	</c:choose>
</ul>	

</div>

<%@ include file="layout/footer.jsp" %>
