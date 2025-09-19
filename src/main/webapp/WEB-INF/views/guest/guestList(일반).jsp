<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>guestList_일반.jsp</title>
  <script>
  	'use strict';
  	
  	function guestDelete(idx) {
  		let ans = confirm("현재 게시글을 삭제하시겠습니까?");
  		if(!ans) return false;
  		
  		$.ajax({
  			url		: 'GuestDelete.gu',
  			type	: 'post',
  			data	: {idx : idx},
  			success: function(res) {
					if(res != "0") {
						alert('게시글이 삭제 되었습니다.');
						location.reload();
					}
					else alert("게시글 삭제 실패~~");
					}
  			});
	  	}
  	
  </script>
  <style>
    th {
      background-color: #eee !important;
      text-align: center;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">방명록 리스트</h2>
  <table class="table table-borderless m-0 p-0">
    <tr>
      <td><a href="guestInput" class="btn btn-success btn-sm">글쓰기</a></td>
      <td class="text-end">
      	<c:if test="${sAdmin != 'adminOK'}"><a href="admin" class="btn btn-primary btn-sm">관리자</a></c:if>
      	<c:if test="${sAdmin == 'adminOK'}"><a href="adminOut" class="btn btn-primary btn-sm">관리자 로그아웃</a></c:if>
      </td>
    </tr>
  </table>
  
  <c:forEach var="vo" items="${vos}" varStatus="st">
    <table class="table table-borderless m-0 p-0">
	    <tr>
	      <%-- <td>번호 : ${vo.idx} --%>
	      <td>번호 : ${fn:length(vos) - st.index}		<!-- 페이징 처리 안 했을 때 -->
	      	<c:if test="${sAdmin == 'adminOK'}"><a href="javascript:guestDelete(${vo.idx})" class="btn btn-danger btn-sm">삭제</a></c:if>
	      	<c:if test="${sAdmin != 'adminOK' && sNickName == vo.name}">
	      		<a href="#" class="btn btn-warning btn-sm">수정</a>
	      		<a href="javascript:guestDelete(${vo.idx})" class="btn btn-danger btn-sm">삭제</a>
	      	</c:if>
	      </td>
	      <td class="text-end">방문IP : ${vo.hostIp}</td>
	    </tr>
	  </table>
    <table class="table table-bordered">
	    <tr>
	      <th>성명</th>
	      <td>${vo.name}</td>
	      <th>방문일자</th>
	      <td>${vo.VDate}</td>
	    </tr>
	    <tr>
	      <th>이메일</th>
	      <td colspan="3">
	      	<c:if test="${empty vo.email || fn:length(vo.email)<6 || fn:indexOf(vo.email,'@')==-1 || fn:indexOf(vo.email,'.')==-1}">- 없음 -</c:if>
	      	<c:if test="${!empty vo.email && fn:length(vo.email)>=6 && fn:indexOf(vo.email,'@')!=-1 && fn:indexOf(vo.email,'.')!=-1}">${vo.email}</c:if>
	      </td>
	    </tr>
	    <tr>
	      <th>홈페이지</th>
	      <td colspan="3">
	        <c:if test="${empty vo.homePage || fn:length(vo.homePage)<10 || fn:indexOf(vo.email,'.')==-1}">- 없음 -</c:if>
	        <c:if test="${!empty vo.homePage && fn:length(vo.homePage)>=10 && fn:indexOf(vo.email,'.')!=-1}"><a href="${vo.homePage}" target="_blank">${vo.homePage}</a></c:if>
	      </td>
	    </tr>
	    <tr>
	      <th>방문소감</th>
	      <td colspan="3" style="height:150px">${fn:replace(vo.content, newLine, "<br/>")}</td>
	    </tr>
	  </table>
	  <br/>
  </c:forEach>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>