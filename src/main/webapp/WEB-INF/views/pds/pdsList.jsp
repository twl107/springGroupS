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
  <title>pdsList.jsp</title>
  <script>
    'use strict';
    
    function partCheck() {
    	let part = $("#part").val();
      location.href = "pdsList?part="+part+"&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}";
    }
    
    // 모달 출력
    function fCheck1(title) {
    	$("#modal-title").text(title);
    }
    
    function fCheck2(title,nickName,fDate,part) {
    	$("#myModal2 .modal-title").text(title);
    	$("#modal-part").text(part);
    	$("#modal-nickName").text(nickName);
    	$("#modal-fDate").text(fDate);
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">자 료 실 리 스 트(${pageVO.part})</h2>
  <br/>
  <table class="table table-borderless m-0 p-0">
    <tr>
      <td>
        <form name="partForm">
          <select name="part" id="part" onchange="partCheck()">
            <option ${pageVO.part=="전체" ? "selected" : ""}>전체</option>
            <option ${pageVO.part=="학습" ? "selected" : ""}>학습</option>
            <option ${pageVO.part=="여행" ? "selected" : ""}>여행</option>
            <option ${pageVO.part=="음식" ? "selected" : ""}>음식</option>
            <option ${pageVO.part=="기타" ? "selected" : ""}>기타</option>
          </select>
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
          <a href="#" onclick="fCheck2('${vo.title}','${vo.nickName}','${vo.FDate}','${vo.part}')" data-bs-toggle="modal" data-bs-target="#myModal2">${vo.title}</a>
          <%-- <a href="#" onclick="fCheck1('${vo.title}')" data-bs-toggle="modal" data-bs-target="#myModal1">${vo.title}</a> --%>
          <%-- <a href="PdsContent.pds?idx=${vo.idx}&part=${part}&pag=${pag}&pageSize=${pageSize}">${vo.title}</a> --%>
          <c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif"/></c:if>
        </td>
        <td>${vo.nickName}</td>
        <td>
          ${vo.FDate}
        </td>
        <td>${vo.part}</td>
        <td>
        	${vo.FName}(${vo.FSize})
        </td>
        <td>${vo.downNum}</td>
        <td><!-- 파일 올린이와 관리자만 삭제처리가능 -->
          <a href="javascript:pdsDeleteCheck()" class="badge bg-danger">삭제</a><br/>
          <a href="#" class="badge bg-primary">전체파일다운</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>

<!-- The Modal1 -->
<div class="modal fade" id="myModal1">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title" id="modal-title"></h4>
        <div>(소제목)</div>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <!-- Modal body -->
      <div class="modal-body">
        <h5>이곳은 본문입니다.</h5>
        <div>정보1 : ___</div>
        <div><input type="text" name="msg" value="반갑습니다." class="form-control"/></div>
      </div>
      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- The Modal2 -->
<div class="modal fade" id="myModal2">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title"></h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <!-- Modal body -->
      <div class="modal-body">
        <div>분류 : <span id="modal-part"></span></div>
        <div>올린이 : <span id="modal-nickName"></span></div>
        <div>올린날짜 : <span id="modal-fDate"></span></div>
      </div>
      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
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