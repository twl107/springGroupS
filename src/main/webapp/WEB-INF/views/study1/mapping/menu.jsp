<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>menu.jsp</title>
  <script>
    'use strict';
    
    function postCheck(flag) {
    	if(flag == '1') myform.action = '${ctp}/study1/mapping/test31';
    	else if(flag == '2') myform.action = '${ctp}/study1/mapping/test32';
    	else if(flag == '3') myform.action = '${ctp}/study1/mapping/test33';
    	else if(flag == '4') myform.action = '${ctp}/study1/mapping/test34';
    	else if(flag == '5') myform.action = '${ctp}/study1/mapping/test35';
    	
    	myform.submit();
    }
  </script>
  <style>
    th {
      text-align: center;
      background-color: #ddd !important;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>Mapping 연습</h2>
  <hr style="border-width:10px; border-color:#ccc;">
  <p><b>Query String방식(?변수=값)</b></p>
  <p>
    <a href="test1?mid=hkd1234&pwd=1234" class="btn btn-success">test1</a>
    <a href="test2?mid=kms1234&pwd=5678" class="btn btn-primary">test2</a>
    <a href="test3?mid=lkj1234&pwd=4321" class="btn btn-secondary">test3</a>
    <a href="test4?mid=snm1234&pwd=8765&sex=2" class="btn btn-info">test4</a>
    <a href="test5?mid=snm1234&pwd=8765&name=소나무&gender=남자&age=20" class="btn btn-dark">test5</a>
  </p>
  <p>
    <a href="test6?mid=ohn1234&pwd=1357&name=오하늘&gender=여자&age=22" class="btn btn-outline-success">test6</a>
    <a href="test7?mid=gid1234&pwd=2468&name=고인돌&gender=남자&age=66" class="btn btn-outline-primary">test7</a>
    <a href="test8?mid=atom1234&pwd=0987&name=아톰&gender=중성&age=12" class="btn btn-outline-secondary">test8</a>
    <a href="test9?mid=btom1234&pwd=5678&name=비톰&gender=남자&age=44" class="btn btn-outline-info">test9</a>
  </p>
  <hr style="border-width:10px; border-color:orange;">
  <p><b>Path Variable방식(/값1/값2)</b></p>
  <p>
  	<a href="test21/hkd1234/1234" class="btn btn-success">test21</a>
  	<a href="test22/kms1234/5678" class="btn btn-primary">test22</a>
  	<a href="1357/test23/lkj1234" class="btn btn-secondary">test23</a>
  	<a href="2468/________/test24/ohn1234" class="btn btn-info">test24</a>
  	<a href="test25/btom1234/5678/비톰/___/남자/44" class="btn btn-outline-dark">test25</a>
  </p>
  <hr style="border-width:10px; border-color:red;">
  <p><b>Post방식</b></p>
  <form name="myform" method="post">
    <table class="table table-bordered">
    	  <tr>
    	    <th>아이디</th>
    	    <td><input type="text" name="mid" id="mid" value="admin" class="form-control"/></td>
    	  </tr>
    	  <tr>
    	    <th>비밀번호</th>
    	    <td><input type="password" name="pwd" id="pwd" value="1234" class="form-control"/></td>
    	  </tr>
    	  <tr>
    	    <th>성명</th>
    	    <td><input type="text" name="name" id="name" value="관리자" class="form-control"/></td>
    	  </tr>
    	  <tr>
    	    <th>별명</th>
    	    <td><input type="text" name="nickName" id="nickName" value="관리맨" class="form-control"/></td>
    	  </tr>
    	  <tr>
    	    <th>나이</th>
    	    <td><input type="number" name="age" id="age" value="22" class="form-control"/></td>
    	  </tr>
    	  <tr>
    	    <th>성별</th>
    	    <td>
    	      <input type="radio" name="strGender" id="gender1" class="me-1" value="남자"/>남자 &nbsp;
    	      <input type="radio" name="strGender" id="gender2" class="me-1" value="여자" checked />여자
    	    </td>
    	  </tr>
    </table>
    <p>
      <input type="button" value="post31" onclick="postCheck(1)" class="btn btn-success"/>
      <input type="button" value="post32" onclick="postCheck(2)" class="btn btn-primary"/>
      <input type="button" value="post33(메세지1)" onclick="postCheck(3)" class="btn btn-secondary"/>
      <input type="button" value="post34(메세지2)" onclick="postCheck(4)" class="btn btn-info"/>
      <input type="button" value="post35(메세지3)" onclick="postCheck(5)" class="btn btn-dark"/>
    </p>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>