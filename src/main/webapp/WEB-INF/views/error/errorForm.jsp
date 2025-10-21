<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title>errorForm.jsp</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2>에러발생에 대한 대처 연습</h2>
	<hr/>
	<div><a href="${ctp}/error/errorMessage" class="btn btn-success mb-2">오류발생 시 호출할 에러 페이지</a></div>
	<div><a href="${ctp}/error/errorJsp" class="btn btn-primary mb-2">JSP에러처리</a></div>
	<div><a href="${ctp}/error/errorTest400?idx=abc" class="btn btn-info mb-2">400오류</a></div>
	<div><a href="${ctp}/error/errorTest000" class="btn btn-secondary mb-2">404오류</a></div>
	<div><a href="${ctp}/error/errorTest405" class="btn btn-warning mb-2">405오류</a></div>
	<div><a href="${ctp}/error/errorTest500" class="btn btn-danger mb-2">500오류</a></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>