<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>.jsp</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-3">성적 자료 리스트</h2>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th>
      <th>성명</th>
      <th>국어</th>
      <th>영어</th>
      <th>수학</th>
      <th>종점</th>
      <th>평균</th>
      <th>학점</th>
    </tr>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${st.count}</td>
        <td>${vo.name}</td>
        <td>${vo.kor}</td>
        <td>${vo.eng}</td>
        <td>${vo.mat}</td>
        <td>${vo.tot}</td>
        <td><fmt:formatNumber value="${vo.avg}" pattern="0.0" /></td>
        <td>${vo.grade}</td>
      </tr>
    </c:forEach>
  </table>
  <p class="text-center"><a href="xmlMenu" class="btn btn-success">돌아가기</a></p>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>