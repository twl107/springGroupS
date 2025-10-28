<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>jsoup.jsp</title>
  <script>
    'use strict';
    
    function crawling1() {
    	let url = document.getElementById("url").value;
    	let selector = document.getElementById("selector").value;
    	
    	$.ajax({
    		url  : "${ctp}/study2/crawling/jsoup",
    		type : "post",
    		data : {
    			url : url,
    			selector : selector
    		},
    		success: (res) => {
    			console.log(res);
    			if(res != '') {
    				let str = '';
    				for(let i=0; i<res.length; i++) {
    					str += res[i] + "<br/>";
    				}
    				$("#demo").html(str);
    			}
    			else $("#demo").html("<b>검색된 자료가 없습니다.</b>");
    		},
    		error : () => alert("전송오류!")
    	});
    }
    
    function crawling2() {
    	$.ajax({
    		url  : "${ctp}/study2/crawling/jsoup2",
    		type : "post",
    		success: (vos) => {
    			if(vos != '') {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>번호</th><th>제목</th><th>사진</th><th>언론사</th></tr>';
    				for(let i=0; i<vos.length; i++) {
    					str += '<tr>';
    					str += '<td>'+(i+1)+'</td>';
    					str += '<td>'+vos[i].item1+'</td>';
    					str += '<td>'+vos[i].item2+'</td>';
    					str += '<td>'+vos[i].item3+'</td>';
    					str += '</tr>';
    				}
    				str += '</table>';
    				$("#demo").html(str);
    			}
    			else $("#demo").html("<b>검색된 자료가 없습니다.</b>");
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
  <h2>jsoup를 이용한 크롤링 연습</h2>
  <div>클롤링할 웹주소</div>
  <div class="input-group">
	  <select name="url" id="url" class="form-control">
	    <option value="">URL선택</option>
	    <option value="https://news.naver.com/">1.네이버 뉴스검색(title)</option>
	    <option value="https://news.naver.com/">2.네이버 뉴스검색(thumb)</option>
	  </select>
	  <select name="selector" id="selector" onchange="crawling1()" class="form-control">
	    <option value="">selector선택</option>
	    <option>strong.cnf_news_title</option>
	    <option>div.cnf_news_thumb</option>
	  </select>
  </div>
  <hr class="border-1 border-dark">
  <div class="mb-3"><input type="button" value="크롤링2(네이버 헤드라인뉴스)한번에가져오기" onclick="crawling2()" class="btn btn-primary"/></div>
  <hr class="border-1 border-dark">
  <div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>