<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title>userList.jsp</title>
	<script>
		'use strict';
		
		function userDelete(idx) {
			let ans = confirm("현재 선택된 User를 삭제하시겠습니까?");
			if(ans) location.href = "userDelete?idx="+idx;
		}
		
		function userSearch() {
			let mid = document.getElementById("mid").value;
			if(mid.trim() == ""){
				alert("검색할 아이디를 입력하세요.");
				document.getElementById("mid").focus();
			}
			else {
				location.href = "userSearch?mid="+mid;
			}
		}
		// 조건 : 아이디 검색 시 검색한 아이디가 없으면 본문 리스트에는 '검색한 ___아이디가 없습니다.'로 출력하시오.
		
	
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2 class="text-center">User 리스트</h2>
	<div>
		<a href="userInput" class="btn btn-success btn-sm m-2">User등록</a>
	</div>
	<table class="table table-hover text-center">
		<tr class="table-secondary">
			<th>번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>나이</th>
			<th>주소</th>
			<th>비고</th>
		</tr>
		<c:if test="${fn:length(vos) != 0}">
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<tr>
					<td>${vo.idx}</td>
					<td>${vo.mid}</td>
					<td>${vo.name}</td>
					<td>${vo.age}</td>
					<td>${vo.address}</td>
					<td>
						<a href="userUpdate?idx=${vo.idx}" class="badge bg-warning text-decoration-none">수정</a>
						<a href="javascript:userDelete(${vo.idx})" class="badge bg-danger text-decoration-none">삭제</a>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(vos) == 0}">
			<tr>
				<td colspan="6" class="text-center">검색하신 <font color='red'><b>${mid}</b></font>가 없습니다.</td>
			</tr>
		</c:if>
	</table>
	<hr/>
	<div class="input-group">
		<span class="input-group-text">검색 아이디</span>
		<input type="text" name="mid" id="mid" class="form-control" />
		<input type="button" value="검색" onclick="userSearch()" class="btn btn-success" />
	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</body>
</html>