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
    
    // '네이버' 검색어로 검색결과 가져오기
    function crawling3() {
    	let searchString = document.getElementById("searchString").value;
    	let page = document.getElementById("page").value;
    	if(searchString.trim() == "") {
    		alert("검색어를 입력하세요");
    		document.getElementById("searchString").focus();
    		return false;
    	}
    	if(page.trim() == "") page = 2;
    	
    	let search = "https://search.naver.com/search.naver?nso=&page="+page+"&query="+searchString+"&sm=tab_pge&start="+((page-2)*15+1)+"&where=web";
    	let searchSelector = "span.sds-comps-text-content";
    	
    	$.ajax({
    		url  : "${ctp}/study2/crawling/jsoup3",
    		type : "post",
    		data : {
    			search : search,
    			searchSelector : searchSelector
    		},
    		success: (vos) => {
    			if(vos != '') {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>번호</th><th>제목</th><th>언론사</th><th>사진</th></tr>';
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
    		error : () => {
    			alert("전송오류!");
    		}
    	});
    }
    
    // '네이버' 검색어로 검색결과 가져오기2
    function crawling4() {
    	let searchString = document.getElementById("searchString2").value;
    	let page = document.getElementById("page2").value;
    	if(searchString.trim() == "") {
    		alert("검색어를 입력하세요");
    		document.getElementById("searchString").focus();
    		return false;
    	}
    	if(page.trim() == "") page = 1;
    	
    	let search = "https://search.naver.com/search.naver?nso=&page="+page+"&query="+searchString+"&sm=tab_pge&start="+(page*15+1)+"&where=web";
    	let searchSelector = "div.sds-comps-vertical-layout div.sds-comps-vertical-layout";
    	
    	$.ajax({
    		url  : "${ctp}/study2/crawling/jsoup4",
    		type : "post",
    		data : {
    			search : search,
    			searchSelector : searchSelector
    		},
    		success: (vos) => {
    			$("#demo").html(vos);
    		},
    		error : () => {
    			alert("전송오류!");
    		}
    	});
    }
    
    // '다음' 연예계 소식
    function crawling5() {
    	$.ajax({
    		url  : "${ctp}/study2/crawling/jsoup5",
    		type : "post",
    		success:function(vos) {
    			if(vos != "") {
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
    				str += '<tr><td colspan="4" class="p-0 m-0"></td></tr>';
    				str += '</table>';
	    			$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
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
  <div class="input-group mb-3">
    <div class="input-group-text">네이버 검색어로 검색1</div>
    <input type="text" name="searchString" id="searchString" value="케이팝 데몬 헌터스" class="form-control"/>
    <input type="number" name="page" id="page" value="2" min="1" class="form-control"/>
    <input type="button" value="네이버검색" onclick="crawling3()" class="btn btn-info"/>
  </div>
  <hr class="border-1 border-dark">
  <div class="input-group mb-3">
    <div class="input-group-text">네이버 검색어로 검색2</div>
    <input type="text" name="searchString2" id="searchString2" value="케이팝 데몬 헌터스" class="form-control"/>
    <input type="number" name="page2" id="page2" value="2" min="1" class="form-control"/>
    <input type="button" value="네이버검색2" onclick="crawling4()" class="btn btn-warning"/>
  </div>
  <hr class="border-1 border-dark">
  <div class="mb-3"><input type="button" value="크롤링5(다음 엔터테인먼트)" onclick="crawling5()" class="btn btn-secondary"/></div>
  <hr class="border-1 border-danger">
  <div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>