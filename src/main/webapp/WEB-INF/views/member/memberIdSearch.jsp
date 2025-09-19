<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberIdSearch.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    function idSearch() {
    	let email = document.getElementById("email").value;
    	
    	if(email.trim() == "") {
    		alert("이메일 주소를 입력하세요.");
    		docuemnt.getElementById("email").focus();
    		myform.mid.focus();
    		return false;
    	}
    	
			$.ajax({
				url  : "${ctp}/member/memberIdSearch",
				type : "post",
				data : {email : email},
				success: (res) => {
					let str = '';
					if(res != '') {
						for(let i=0; i<res.length; i++) {
							for(let j=0; j<res[i].mid.length; j+=2) {
							  str += res[i].mid.substring(j,j+1);
							  if(res[i].mid.length > j+1) str += "*";
							}
							str += '<br/>';
						}
					}
					else {
						str = '검색하신 이메일 주소가 없습니다. 확인후 다시 검색하세요';
						document.getElementById("email").focus();
					}
					demo.innerHTML = str;
				},
				error : () => alert("전송 오류!")
			});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>아이디 찾기</h2>
  <div>(회원 가입시에 등록한 이메일 주소를 입력하세요)</div>
  <div class="input-group">
    <div class="input-group-text">이메일</div>
    <input type="text" name="email" id="email" placeholder="이메일 주소를 입력하세요" class="form-control" autofocus />
    <input type="button" value="아이디찾기" onclick="idSearch()" class="btn btn-success"/>
  </div>
  <hr class="border-1 border-dark">
  <div id="demo"></div>
  <hr class="border-1 border-success opacity-75">
  <div>
    <a href="memberLogin" class="btn btn-primary me-2">Login</a>
    <a href="#" class="btn btn-info">비밀번호찾기</a>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>