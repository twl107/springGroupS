<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title>randomForm.jsp</title>
	<script>
		'use strict';
		
		let cnt = 0;
		let str = '';
		
		// 랜덤 메소드 사용
		function randomCheck(){
			$.ajax({
				url	: '${ctp}/study2/random/randomCheck',
				type: 'post',
				success: (res) => {
					cnt++;
					str += cnt + "(random) : " + res + "<br/>";
					$("#demo").html(str);
				},
				error : () => alert('전송오류!')
			});
		}
		
		// UUID클래스 사용
		function uuidCheck(){
			$.ajax({
				url	: '${ctp}/study2/random/uuidCheck',
				type: 'post',
				success: (res) => {
					cnt++;
					str += cnt + "(random) : " + res + "<br/>";
					$("#demo").html(str);
				},
				error : () => alert('전송오류!')
			});
		}
		
		// AlphaNumericCheck클래스 사용
		function alphaNumericCheck(){
			$.ajax({
				url	: '${ctp}/study2/random/alphaNumericCheck',
				type: 'post',
				success: (res) => {
					cnt++;
					str += cnt + "(random) : " + res + "<br/>";
					$("#demo").html(str);
				},
				error : () => alert('전송오류!')
			});
		}
		
		
		
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2>무작위수/randomAlphaNumeric</h2>
	<hr/>
	<div>
		<input type="button" value="Numeric" onclick="randomCheck()" class="btn btn-success me-2" />
		<input type="button" value="UUID(16진수)" onclick="uuidCheck()" class="btn btn-success me-2" />
		<input type="button" value="AlphaNumeric" onclick="alphaNumericCheck()" class="btn btn-success me-2" />
	</div>
	<hr/>
	<div>
		<div>출력결과 : </div>
		<span id="demo"></span>
	</div>
	
	
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</body>
</html>