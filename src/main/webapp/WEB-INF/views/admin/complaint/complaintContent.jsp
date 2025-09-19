<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("LF", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>complaintContent.jsp</title>
  <style>
    th {
      background-color: #eee !important;
      text-align: center;
    }
    th, td {
      padding: 10px !important;
    }
  </style>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-3">상세보기</h2>
  <table class="table table-bordered">
  <tr>
    <th>제목</th>
    <td colspan="3">${vo.title}</td>
  </tr>
  <tr>
    <th>분류</th>
    <td>${vo.part}</td>
    <th>고유번호</th>
    <td>${vo.idx}</td>
  </tr>
  <tr>
    <th>진행상황</th>
    <td>${vo.progress}</td>
    <th>글쓴이</th>
    <td>${vo.mid}</td>
  </tr>
  <tr>
    <th>글제목</th>
    <td colspan="3">${vo.title}</td>
  </tr>
  <tr>
  </tr>
  <tr>
    <th>신고자</th>
    <td>${vo.cpMid}</td>
    <th>신고날짜</th>
    <td>${vo.cpDate}</td>
  </tr>
  <tr>
    <th>신고내역</th>
    <td colspan="3">${fn:replace(vo.cpContent, LF, '<br/>')}</td>
  </tr>
  <tr>
    <th>글내용</th>
    <td colspan="3" style="height:400px">${fn:replace(vo.content, LF, '<br/>')}</td>
  </tr>
  </table>
  <div class="text-center"><a href="javascript:history.back()" class="btn btn-success">돌아가기</a></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>