<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/linkOrange.css">
  <title>pdsList.jsp</title>
  <script>
    'use strict';
    
    function partCheck() {
    	let part = $("#part").val();
      location.href = "pdsList?part="+part+"&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}";
    }
    
    // 다운로드수 증가하기
    function downNumCheck(idx) {
    	$.ajax({
    		url  : '${ctp}/pds/pdsDownNumCheck',
    		type : 'post',
    		data : {idx : idx},
    		success: () => location.reload(),
    		error : () => alert('전송오류')
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
  <c:set var="part" value="${pageVO.part == '' ? '전체' : pageVO.part}" />
  <h2 class="text-center">자 료 실 리 스 트(${part})</h2>
  <br/>
  <table class="table table-borderless m-0 p-0">
    <tr>
      <td>
        <form name="partForm">
          <div class="d-flex justify-content-start">
            <div>
		          <select name="part" id="part" onchange="partCheck()" class="form-select">
		            <option value="" ${pageVO.part=="" ? "selected" : ""}>전체</option>
		            <option ${pageVO.part=="학습" ? "selected" : ""}>학습</option>
		            <option ${pageVO.part=="여행" ? "selected" : ""}>여행</option>
		            <option ${pageVO.part=="음식" ? "selected" : ""}>음식</option>
		            <option ${pageVO.part=="기타" ? "selected" : ""}>기타</option>
		          </select>
	          </div>
          </div>
        </form>
      </td>
      <td class="text-end">
        <a href="pdsInput?part=${pageVO.part}" class="btn btn-success">자료올리기</a>
      </td>
    </tr>
  </table>
  
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th>
      <th>자료제목</th>
      <th>올린이</th>
      <th>올린날짜</th>
      <th>분류</th>
      <th>파일명(크기)</th>
      <th>다운수</th>
      <th>비고</th>
    </tr>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${fn:length(vos) - st.index}</td>
        <td>
          <a href="pdsContent?idx=${vo.idx}&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}&part=${pageVO.part}">${vo.title}</a>
          <c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif"/></c:if>
        </td>
        <td>${vo.nickName}</td>
        <td>
          ${fn:substring(vo.FDate,0,10)}
        </td>
        <td>${vo.part}</td>
        <td>
          <c:set var="fNames" value="${fn:split(vo.FName,'/')}" />
	        <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}" />
	        <c:forEach var="fName" items="${fNames}" varStatus="st">
	          <a href="${ctp}/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})">${fName}</a><br/>
	        </c:forEach>
	        (<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0" />KByte)
        </td>
        <td>${vo.downNum}</td>
        <td>
          <a href="pdsTotalDown?idx=${vo.idx}" class="badge bg-primary text-decoration-none">전체파일다운</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>

<!-- 블록페이지 시작 -->
	<div class="pagination justify-content-center">
	  <c:if test="${pageVO.pag > 1}"><a href="pdsList?pag=1&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">첫페이지</a></c:if>
	  <c:if test="${pageVO.curBlock > 0}"><a href="pdsList?pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">이전블록</a></c:if>
	  <c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize)+1}" end="${(pageVO.curBlock*pageVO.blockSize)+pageVO.blockSize}" varStatus="st">
	  	<c:if test="${i <= pageVO.totPage && i == pageVO.pag}"><a href="pdsList?pag=${i}&pageSize=${pageVO.pageSize}" class="page-item page-link active text-decoration-none bg-secondary border-secondary">${i}</a></c:if>
	  	<c:if test="${i <= pageVO.totPage && i != pageVO.pag}"><a href="pdsList?pag=${i}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">${i}</a></c:if>
	  </c:forEach>
	  <c:if test="${pageVO.curBlock < pageVO.lastBlock}"><a href="pdsList?pag=${(pageVO.curBlock+1)*blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">다음블록</a></c:if>
	  <c:if test="${pageVO.pag < pageVO.totPage}"><a href="pdsList?pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">마지막페이지</a></c:if>
	</div>
<!-- 블록페이지 끝 -->

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>