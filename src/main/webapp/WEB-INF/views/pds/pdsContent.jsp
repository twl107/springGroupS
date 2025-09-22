<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("LF", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/linkOrange.css">
  <title>pdsContent.jsp</title>
  <script>
    'use strict';
    
    function downNumCheck(idx) {
    	$.ajax({
    		url  : '${ctp}/pds/pdsDownNumCheck',
    		type : 'post',
    		data : {idx : idx},
    		success: () => location.reload(),
    		error : () => alert('전송오류')
    	});
    }
    
    // 자료실 내역 삭제하기(파일삭제 + DB의 내역삭제)
    function deleteCheck() {
    	let ans = confirm("선택하신 자료를 삭제하시겠습니까?");
			if(!ans) return false;
			
			$.ajax({
	    		url  : '${ctp}/pds/pdsDeleteCheck',
	    		type : 'post',
	    		data : {
	    			idx : ${vo.idx},
	    			fSName : '${vo.FSName}'
    			},
	    		success: (res) => {
	    			if(res != 0){
	    				alert("자료가 삭제되었습니다.");
		    			location.href = "pdsList";
	    			}
	    			else alert("자료 삭제 실패~~");
	    		},
	    		error : () => alert('전송오류')
	    	});
	    }
    
    
  </script>
  <style>
    th {
      background-color: #eee !important;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-3">자료실 내용 상세보기</h2>
  <table class="table table-bordered text-center">
    <tr>
      <th>올린이</th>
      <td>${vo.nickName}</td>
      <th>올린날짜</th>
      <td>${fn:substring(vo.FDate,0,19)}</td>
    </tr>
    <tr>
      <th>파일명</th>
      <td>
        <c:set var="fNames" value="${fn:split(vo.FName,'/')}" />
        <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}" />
        <c:forEach var="fName" items="${fNames}" varStatus="st">
          <a href="${ctp}/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})">${fName}</a><br/>
        </c:forEach>
        (<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0"/> KByte)
      </td>
      <th>다운횟수</th>
      <td>${vo.downNum}</td>
    </tr>
    <tr>
      <th>분류</th>
      <td>${vo.part}</td>
      <th>접속IP</th>
      <td>${vo.hostIp}</td>
    </tr>
    <tr>
      <th>제목</th>
      <td colspan="3" class="text-start">${vo.title}</td>
    </tr>
    <tr>
      <th>상세내역</th>
      <td colspan="3" class="text-start" style="height:250px">${fn:replace(vo.content, LF, "<br/>")}</td>
    </tr>
  </table>
  <div class="row">
    <div class="col text-start"><a href="pdsList?pag=${pageVO.pag}&pageSize=${pageVO.pageSize}&part=${pageVO.part}" class="btn btn-success">돌아가기</a></div>
    <c:if test="${vo.mid == sMid || sLevel == 0}">
	    <div class="col text-end">
	      <c:if test="${vo.mid == sMid || sLevel != 0}">
	      	<a href="#" class="btn btn-warning">수정</a>
	      </c:if>
	      <a href="javascript:deleteCheck()" class="btn btn-danger">삭제</a>
	    </div>
    </c:if>
  </div>
  <!-- 별점 부여 및 후기 등록/리스트 -->
  
  
  
  
  <!-- 자료실 파일 형식(그림파일은 그림 출력) -->
  <br/>
  <div class="text-center">
	  <c:set var="fNames" value="${fn:split(vo.FName,'/')}" />
	  <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}" />
	  <c:forEach var="fName" items="${fNames}" varStatus="st">
	  	자료 파일명 : ${fName} :
		  <c:set var="fNameArray" value="${fn:split(fName,'.')}"/>
		 	<c:set var="extName" value="${fn:toLowerCase(fNameArray[fn:length(fNameArray)-1])}"/>
		 	<c:if test="${extName == 'zip'}">압축파일</c:if>
		 	<c:if test="${extName == 'hwp'}">한글문서파일</c:if>
		 	<c:if test="${extName == 'doc'}">Word파일</c:if>
		 	<c:if test="${extName == 'ppt' || extName == 'pptx'}">파워포인트파일</c:if>
		 	<c:if test="${extName == 'pdf'}">pdf문서파일</c:if>
		 	<c:if test="${extName == 'txt'}">텍스트문서파일</c:if>
	 		<br/><br/>
		 	<c:if test="${extName == 'jpg' || extName == 'gif' || extName == 'png'}">
		 	  <img src='${ctp}/pds/${fSNames[st.index]}' width="90%"/>
		 	  <br/>
		 	</c:if>
	  </c:forEach>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>