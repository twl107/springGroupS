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
  <title>ajaxObjectForm.jsp</title>
  <script>
    'use strict';
    
    // 일반 배열처리
    function dodoCheck() {
    	let dodo = document.getElementById("dodo").value;
    	if(dodo.trim() == "") {
    		alert("지역을 선택하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study1/ajax/ajaxObject1",
    		type : "post",
    		data : {dodo : dodo},
    		success:function(res) {
    			//console.log(res);
    			//$("#demo1").html(res);
    			let str = '<option>도시선택</option>';
    			for(let i=0; i<res.length; i++) {
    				if(res[i] == null) break;
    				str += '<option>'+res[i]+'</option>';
    			}
    			$("#city").html(str);
    		},
    		error : () => alert("전송오류")
    	});
    }
    
    function fCheck() {
    	let dodo = document.getElementById("dodo").value;
    	let city = document.getElementById("city").value;
    	
    	if(dodo.trim() == "" || city.trim() == "") {
    		alert("지역 선택후 클릭해 주세요");
    		return false;
    	}
    	
    	let str = "선택하신 지역은? " + dodo + " / " + city;
    	$("#demod2").html(str);
    }
    
    // 객체 배열처리(ArrayList)
    function dodoCheck2() {
    	let dodo = document.getElementById("dodo2").value;
    	if(dodo.trim() == "") {
    		alert("지역을 선택하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study1/ajax/ajaxObject2",
    		type : "post",
    		data : {dodo : dodo},
    		success:function(res) {
    			//console.log(res);
    			//$("#demo1").html(res);
    			let str = '<option>도시선택</option>';
    			for(let i=0; i<res.length; i++) {
    				if(res[i] == null) break;
    				str += '<option>'+res[i]+'</option>';
    			}
    			$("#city2").html(str);
    		},
    		error : () => alert("전송오류")
    	});
    }
    
    function fCheck2() {
    	let dodo = document.getElementById("dodo2").value;
    	let city = document.getElementById("city2").value;
    	
    	if(dodo.trim() == "" || city.trim() == "") {
    		alert("지역 선택후 클릭해 주세요");
    		return false;
    	}
    	
    	let str = "선택하신 지역은? " + dodo + " / " + city;
    	$("#demo2").html(str);
    }
    
    // 객체 배열처리(Map)
    function dodoCheck3() {
    	let dodo = document.getElementById("dodo3").value;
    	if(dodo.trim() == "") {
    		alert("지역을 선택하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study1/ajax/ajaxObject3",
    		type : "post",
    		data : {dodo : dodo},
    		success:function(res) {
    			//console.log(res);
    			//$("#demo1").html(res);
    			let str = '<option>도시선택</option>';
    			for(let i=0; i<res.city.length; i++) {
    				str += '<option>'+res.city[i]+'</option>';
    			}
    			$("#city3").html(str);
    		},
    		error : () => alert("전송오류")
    	});
    }
    
    function fCheck3() {
    	let dodo = document.getElementById("dodo3").value;
    	let city = document.getElementById("city3").value;
    	
    	if(dodo.trim() == "" || city.trim() == "") {
    		alert("지역 선택후 클릭해 주세요");
    		return false;
    	}
    	
    	let str = "선택하신 지역은? " + dodo + " / " + city;
    	$("#demo2").html(str);
    }
    
    // 동기식 처리
    function midCheck1() {
    	let mid = document.getElementById("mid").value;
    	if(mid.trim() == "") alert("검색할 아이디를 입력하세요");
    	else myform4.submit();
    }
    
    // vo검색처리
    function midCheck2() {
    	let mid = document.getElementById("mid").value;
    	if(mid.trim() == "") {
    		alert("검색할 아이디를 입력하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study1/ajax/ajaxObject5",
    		type : "post",
    		data : {mid : mid},
    		success:function(vo) {
    			//console.log(res);
    			//$("#demo1").html(res);
    			let str = '';
    			str += '고유번호 : ' + vo.idx + "<br/>";
    			str += '아이디 : ' + vo.mid + "<br/>";
    			str += '성 명 : ' + vo.name + "<br/>";
    			str += '나 이 : ' + vo.age + "<br/>";
    			str += '주소  : ' + vo.address + "<br/>";
    			$("#demo1").html(str);
    		},
    		error : () => alert("전송오류")
    	});
    }
    
    // vos검색(완전일치)
    function midCheck3() {
    	let mid = document.getElementById("mid").value;
    	if(mid.trim() == "") {
    		alert("검색할 아이디를 입력하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study1/ajax/ajaxObject6",
    		type : "post",
    		data : {mid : mid},
    		success:function(res) {
    			//console.log(res);
    			//$("#demo1").html(res);
    			let str = '';
    			str += '<table class="table table-hover text-center">';
    			str += '<tr class="table-secondary">';
    			str += '<th>번호</th><th>아이디</th><th>성명</th><th>나이</th><th>주소</th>';
    			str += '</tr>';
    			for(let i=0; i<res.length; i++) {
    				str += '<tr>';
    				str += '<td>'+res[i].idx+'</td>';
    				str += '<td>'+res[i].mid+'</td>';
    				str += '<td>'+res[i].name+'</td>';
    				str += '<td>'+res[i].age+'</td>';
    				str += '<td>'+res[i].address+'</td>';
    				str += '</tr>';
    			}
    			str += '</table>';
    			$("#demo1").html(str);
    		},
    		error : () => alert("전송오류")
    	});
    }
    
    // vos검색(부분일치 - like연산자 사용)
    function midCheck4() {
    	let mid = document.getElementById("mid").value;
    	if(mid.trim() == "") {
    		alert("검색할 아이디를 입력하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study1/ajax/ajaxObject7",
    		type : "post",
    		data : {mid : mid},
    		success:function(res) {
    			//console.log(res);
    			//$("#demo1").html(res);
    			let str = '<h4>vos로 전송된 자료 출력</h4>';
    			
    			if(res != '') {
	    			str += '<table class="table table-hover text-center">';
	    			str += '<tr class="table-secondary">';
	    			str += '<th>번호</th><th>아이디</th><th>성명</th><th>나이</th><th>주소</th>';
	    			str += '</tr>';
	    			for(let i=0; i<res.length; i++) {
	    				str += '<tr>';
	    				str += '<td>'+res[i].idx+'</td>';
	    				str += '<td>'+res[i].mid+'</td>';
	    				str += '<td>'+res[i].name+'</td>';
	    				str += '<td>'+res[i].age+'</td>';
	    				str += '<td>'+res[i].address+'</td>';
	    				str += '</tr>';
	    			}
	    			str += '</table>';
    			}
    			else {
    				str += "<b>찾고자 하는 자료가 없습니다.</b>";
    			}
    			$("#demo1").html(str);
    		},
    		error : () => alert("전송오류")
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>문자 배열 처리</h2>
  <hr/>
  <form name="myform1">
    <h3>1.도시를 선택하세요(일반배열처리)</h3>
    <select name="dodo" id="dodo" onchange="dodoCheck()">
      <option value="">선택</option>
      <option>서울</option>
      <option>경기</option>
      <option>충북</option>
      <option>충남</option>
    </select>
    <select name="city" id="city">
      <option>도시선택</option>
    </select>
    <input type="button" value="선택" onclick="fCheck()" class="btn btn-success me-3"/>
    <input type="button" value="돌아가기" onclick="location.href='${ctp}/study1/ajax/ajaxForm'" class="btn btn-warning me-3"/>
  </form>
  <hr/>
  <form name="myform2">
    <h3>2.도시를 선택하세요(객체배열(ArrayList)처리)</h3>
    <select name="dodo" id="dodo2" onchange="dodoCheck2()">
      <option value="">선택</option>
      <option>서울</option>
      <option>경기</option>
      <option>충북</option>
      <option>충남</option>
    </select>
    <select name="city" id="city2">
      <option>도시선택</option>
    </select>
    <input type="button" value="선택" onclick="fCheck2()" class="btn btn-success me-3"/>
    <input type="button" value="돌아가기" onclick="location.href='${ctp}/study1/ajax/ajaxForm'" class="btn btn-warning me-3"/>
  </form>
  <hr/>
  <form name="myform3">
    <h3>3.도시를 선택하세요(객체배열(Map)처리)</h3>
    <select name="dodo" id="dodo3" onchange="dodoCheck3()">
      <option value="">선택</option>
      <option>서울</option>
      <option>경기</option>
      <option>충북</option>
      <option>충남</option>
    </select>
    <select name="city" id="city3">
      <option>도시선택</option>
    </select>
    <input type="button" value="선택" onclick="fCheck3()" class="btn btn-success me-3"/>
    <input type="button" value="돌아가기" onclick="location.href='${ctp}/study1/ajax/ajaxForm'" class="btn btn-warning me-3"/>
  </form>
  <hr/>
  <h3>아이디 검색</h3>
  <form method="post" name="myform4" action="ajaxObject4">
	  <div class="input-group">
		  <input type="text" name="mid" id="mid" value="hkd1234" class="form-control"/>
		  <input type="button" value="검색1(일반-DB)" onclick="midCheck1()" class="btn btn-success"/>
		  <input type="button" value="검색2(ajax-vo)" onclick="midCheck2()" class="btn btn-primary"/>
		  <input type="button" value="검색3(ajax-vos)" onclick="midCheck3()" class="btn btn-info"/>
		  <input type="button" value="검색4(부분일치)" onclick="midCheck4()" class="btn btn-warning"/>
	  </div>
  </form>
  <hr/>
  <div id="demo1"></div>
  <div id="demo2"></div>
  <c:if test="${fn:length(vos) != 0}">
	  <hr/>
	  <table class="table table-hover text-center">
	    <tr>
	      <th>번호</th>
	      <th>아이디</th>
	      <th>성명</th>
	      <th>나이</th>
	      <th>주소</th>
	    </tr>
	    <c:forEach var="vo" items="${vos}" varStatus="st">
	      <tr>
	        <td>${vo.idx}</td>
	        <td>${vo.mid}</td>
	        <td>${vo.name}</td>
	        <td>${vo.age}</td>
	        <td>${vo.address}</td>
	      </tr>
	    </c:forEach>
	  </table>
  </c:if>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>