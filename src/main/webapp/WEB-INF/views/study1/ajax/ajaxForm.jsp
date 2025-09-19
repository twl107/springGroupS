<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>ajaxForm.jsp</title>
  <script>
    'use strict';
    
    // 값전달(일반형식)
    function ajaxTest1(item) {
    	location.href = "${ctp}/study1/ajax/ajaxTest1?item="+item;
    }
    
    // 값(숫자) 전달(AJax처리)
    function ajaxTest2(item) {
    	$.ajax({
    		url  : '${ctp}/study1/ajax/ajaxTest2',
    		type : 'post',
    		data : {item : item},
    		success:function(res) {
    			$('#demo1').html(res);
    		},
    		error : () => alert("전송오류!")
    	});
    }
    
    // 값(문자) 전달(AJax처리)
    function ajaxTest3(item) {
    	$.ajax({
    		url  : '${ctp}/study1/ajax/ajaxTest3',
    		type : 'post',
    		data : {item : item},
    		success:function(res) {
    			$('#demo1').html(res);
    		},
    		error : () => alert("전송오류!")
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>AJax 연습</h2>
  <hr/>
  <div>기본 값 전달 :<br/>
    <a href="javascript:ajaxTest1(10)" class="btn btn-secondary me-2 mb-2">값(숫자)전달(일반)</a>
    <a href="javascript:ajaxTest2(10)" class="btn btn-success me-2 mb-2">값(숫자)전달2</a>
    <a href="javascript:ajaxTest3('atom')" class="btn btn-primary me-2 mb-2">값(문자)전달3</a>
  </div>
  <hr/>
  <div>객체 전달 :<br/>
    <a href="ajaxObjectForm" class="btn btn-success me-2 mb-2">객체전달연습</a>
  </div>
  <hr/>
  <div id="demo1"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>