<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>memberMain.jsp</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>${sNickName} 회원 전용방</h2>
  <hr/>
  <c:if test="${sLevel == 3}">
    정회원 등업 조건 : 방명록에 3회이상 글쓰기, 회원로그인 4일 이상<br/>
    (포인트는 정회원 이상일경우 1회방문 10포인트증정, 1일 최대 50포인트증정)
  </c:if>
  <hr/>
  <div class="row">
  <div class="col">
    현재 회원 등급 : ${strLevel}<br/>
    총 방문횟수 : ${mVo.visitCnt}<br/>
    오늘 방문횟수 : ${mVo.todayCnt}<br/>
    현재 가용 포인트 : ${mVo.point}<br/>
    최종 방문일 : ${sLastDate}<br/>
  </div>
  <div class="col">
    <img src="${ctp}/member/${mVo.photo}" width="200px"/>
  </div>
  </div>
  <hr/>
  <h4>활동내역</h4>
	<div>
	  방명록 올린 글수 : ${guestCnt}<br/>
	</div>  
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>