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
  <title>boardSearchList.jsp</title>
  <script>
    'use strict';
    
    function pageSizeCheck() {
    	let pageSize = $("#pageSize").val();
    	location.href = "boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pageSize="+pageSize;
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
	<table class="table table-borderless m-0 p-0">
	  <tr>
	    <td colspan="2"><h2 class="text-center mb-3">게시판 검색 리스트</h2></td>
	  </tr>
	  <tr>
      <td>(<font color="blue"><b>${pageVO.searchStr}</b></font>(으)로 <font color="red"><b>${pageVO.searchString}</b></font>를 검색한결과 <font color="blue"><b>${pageVO.totRecCnt}</b></font>건이 검색되었습니다.)</td>
      <td class="text-end"><a href="boardList" class="btn btn-success btn-sm">돌아가기</a></td>
    </tr>
  </table>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
    	<th>번호</th>
    	<th>글제목</th>
    	<th>글쓴이</th>
    	<th>올린날짜</th>
    	<th>조회수(♥)</th>
    </tr>
    <c:set var="curScrStartNo" value="${pageVO.curScrStartNo}"/>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${curScrStartNo}</td>
        <td class="text-start">
          <c:if test="${vo.openSw == 'NO'}">
            <c:if test="${sMid != vo.mid && sAdmin != 'adminOK'}">(비밀글)</c:if>
            <c:if test="${sMid == vo.mid || sAdmin == 'adminOK'}">
		          <a href="boardContent?idx=${vo.idx}&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}&boardFlag=search&search=${pageVO.search}&searchString=${pageVO.searchString}" class="text-decoration-none text-dark link-primary">
		            <c:if test="${sAdmin == 'adminOK'}"><font color="red">(비밀글)</font></c:if>${vo.title}
		          </a>
		          <c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>
            </c:if>
          </c:if>
          <c:if test="${vo.openSw != 'NO'}">
	          <a href="boardContent?idx=${vo.idx}&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}&boardFlag=search&search=${pageVO.search}&searchString=${pageVO.searchString}" class="text-decoration-none text-dark link-primary">${vo.title}</a>
	          <c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>
          </c:if>
        </td>
        <td>${vo.nickName}</td>
        <td>
          ${vo.date_diff == 0 ? fn:substring(vo.WDate,11,19) : vo.date_diff == 1 ? vo.WDate : fn:substring(vo.WDate,0,10)}
        </td>
        <td>${vo.readNum}
          <c:if test="${vo.good > 0}">(${vo.good})</c:if>
        </td>
      </tr>
      <c:set var="curScrStartNo" value="${curScrStartNo - 1}" />
    </c:forEach>
  </table>
<!-- 블록페이지 시작 -->
	<div class="pagination justify-content-center">
	  <c:if test="${pageVO.pag > 1}"><a href="boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pag=1&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">첫페이지</a></c:if>
	  <c:if test="${pageVO.curBlock > 0}"><a href="boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">이전블록</a></c:if>
	  <c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize)+1}" end="${(pageVO.curBlock*pageVO.blockSize)+pageVO.blockSize}" varStatus="st">
	  	<c:if test="${i <= pageVO.totPage && i == pageVO.pag}"><a href="boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pag=${i}&pageSize=${pageVO.pageSize}" class="page-item page-link active text-decoration-none bg-secondary border-secondary">${i}</a></c:if>
	  	<c:if test="${i <= pageVO.totPage && i != pageVO.pag}"><a href="boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pag=${i}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">${i}</a></c:if>
	  </c:forEach>
	  <c:if test="${pageVO.curBlock < pageVO.lastBlock}"><a href="boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pag=${(pageVO.curBlock+1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">다음블록</a></c:if>
	  <c:if test="${pageVO.pag < pageVO.totPage}"><a href="boardSearchList?search=${pageVO.search}&searchString=${pageVO.searchString}&pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}" class="page-item page-link text-decoration-none text-dark link-primary">마지막페이지</a></c:if>
	</div>
<!-- 블록페이지 끝 -->
<br/>
<!-- 검색기 시작 -->
  <div class="text-center">
    <form name="searchForm" method="get" action="boardSearchList">
      <b>검색  : </b>
      <select name="search" id="search">
        <option value="title" selected>글제목</option>
        <option value="nickName">글쓴이</option>
        <option value="content">글내용</option>
      </select>
      <input type="text" name="searchString" id="searchString" required />
      <input type="submit" value="검색" class="btn btn-secondary btn-sm"/>
    </form>
  </div>
<!-- 검색기 끝 -->

</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>