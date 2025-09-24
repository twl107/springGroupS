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
  <title>validatorForm.jsp</title>
  <script>
  'use strict';
  
  function fCheck() {
	  let query = {
			  mid			:	$("#mid").val(),
			  name		:	$("#name").val(),
			  age			:	$("#age").val(),
			  address	:	$("#address").val()
	  }
	  
	  $.ajax({
  		type  : "post",
  		url   : "validatorForm",
  		data  : query,
  		success:function(res) {
	  				alert(res);
	  				location.reload();
  		},
  		error : function() {
  			alert("전송오류!");
  		}
  	});
  }
  
  function userDelete(idx) {
	 	let ans = confirm("선택된 회원을 삭제하시겠습니까?");
		if(!ans) return false;
		
		$.ajax({
			type  : "post",
			url   : "validatorDeleteOk",
			data  : {idx : idx},
			success:function(res) {
				if(res != 0) {
						alert("회원이 삭제 되었습니다.");
						location.reload();
				}
				else alert("회원 삭제실패~~");
			},
			error : function() {
				alert("전송 오류!");
			}
		});	  
	  
  }
  
  
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>valicator체크(유효성검사)-BackEnd 체크</h2>
  <hr class="border border-dark">
  <h2 class="text-center m-3">User 등록하기</h2>
  <form method="post">
	  <table class="table table-bordered">
	    <tr>
	      <th>아이디</th>
	      <td><input type="text" name="mid" id="mid" class="form-control" autofocus required /></td>
	    </tr>
	    <tr>
	      <th>성명</th>
	      <td><input type="text" name="name" id="name" class="form-control" required /></td>
	    </tr>
	    <tr>
	      <th>나이</th>
	      <td><input type="number" name="age" id="age" value="20" class="form-control" /></td>
	    </tr>
	    <tr>
	      <th>주소</th>
	      <td><input type="text" name="address" id="address" class="form-control" /></td>
	    </tr>
	    <tr>
	      <td colspan="2" class="text-center">
	        <input type="button" value="회원가입" onclick="fCheck()" class="btn btn-success" />
	        <input type="reset" value="다시입력" class="btn btn-warning" />
	      </td>
	    </tr>
	  </table>
  </form>
  <hr class="border border-dark">
  
  <c:if test="${fn:length(vos) != 0}">
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
	  </table>
  </c:if>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>