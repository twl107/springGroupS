<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("CRLF", "\r\n"); %>
<% pageContext.setAttribute("LF", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>complaintList.jsp</title>
  <script>
    'use strict';
    
    // 신고내역 모달로 확인
    //function modalCheck(mid, title, content, nickName) {
    function modalCheck(mid, title, nickName) {
    	$("#myModal #modalMid").text(mid);
    	$("#myModal #modalTitle").text(title);
    	//$("#myModal #modalContent").html(content);
    	$("#myModal #modalNickName").text(nickName);
    }
    
    // 신고내역 처리(해제/감추기/삭제)
    function complaintProcess(idx, part, partIdx, complaintSw) {
    	let ans = '';
    	if(complaintSw == 'S') {
    		ans = confirm("현 게시물의 신고를 해제 하시겠습니까?");
    		if(!ans) return false;
    	}
    	else if(complaintSw == 'H') {
    		ans = confirm("현 게시물을 감출까요?");
    		if(!ans) return false;
    	}
    	else if(complaintSw == 'D') {
    		ans = confirm("현 게시물을 삭제할까요?");
    		if(!ans) return false;
    	}
    	
    	let query = {
    			idx        : idx,
    			part       : part,
    			partIdx    : partIdx,
    			complaintSw: complaintSw
    	}
    	
    	$.ajax({
    		url  : 'complaintProcess',
    		type : 'post',
    		data : query,
    		success:function(res) {
    			if(res != 0) {
    				alert("처리 완료되었습니다.");
    				location.reload();
    			}
    			else alert("처리 실패~~");
    		},
    		error : function() { alert("전송오류!!"); }
    	});
    }
    
    $(document).ready(function() {
   	  // 메뉴 버튼 클릭
   	  $(".menu-btn").on("click", function(e) {
   	    e.stopPropagation(); 		// 이벤트 버블링 방지
   	    $(".menu-list").hide();	// 모든 메뉴 닫기
   	    $(this).siblings(".menu-list").toggle();	// 현재 버튼에 해당하는 메뉴만 토글
   	  });

   	  // 문서 아무 곳이나 클릭하면 메뉴 닫기
   	  $(document).on("click", function() {
   	    $(".menu-list").hide();
   	  });

   	  // 메뉴 안쪽 클릭 시 닫히지 않도록
   	  $(".menu-list").on("click", function(e) {
   	    e.stopPropagation();
   	  });
   	});
    
    // 분류별 보여주기
    function partCheck() {
    	let part = $("#part").val();
    	location.href = "complaintList?part="+part;
    }
  </script>
  <link rel="stylesheet" type="text/css" href="${ctp}/css/linkOrange.css">
  <style>
    .comment-menu {
		  position: relative;
		  display: inline-block;
		}
		.menu-btn {
		  background: none;
		  border: none;
		  cursor: pointer;
		  font-size: 16px;
		}
		.menu-list {
		  display: none;               /* 기본은 숨김 */
		  position: absolute;
		  right: -24px;
		  top: 100%;
		  background: #777;
		  color: white;
		  list-style: none;
		  padding: 0;
		  margin: 0;
		  border-radius: 5px;
		  box-shadow: 0 2px 6px rgba(0,0,0,0.3);
		  min-width: 80px;
		  z-index: 100;
		}
		.menu-list li {
		  padding: 5px;
		  cursor: pointer;
		}
		.menu-list li:hover {
		  background: #444;
		}
  </style>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2 class="text-center">신 고 글 리 스 트</h2>
  <div class="d-flex justify-content-end mb-1">
    <div>
	    <select name="part" id="part" onchange="partCheck()" class="form-select bg-secondary-subtle">
	      <option value='' ${pageVO.part == '' ? 'selected' : ''}>전체보기</option>
	      <option ${pageVO.part == '신고접수'    ? 'selected' : ''}>신고접수</option>
	      <option ${pageVO.part == '처리중(H)'  ? 'selected' : ''}>처리중(H)</option>
	      <option ${pageVO.part == '처리완료(S)' ? 'selected' : ''}>처리완료(S)</option>
	      <option ${pageVO.part == '처리완료(D)' ? 'selected' : ''}>처리완료(D)</option>
	    </select>
    </div>
  </div>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th>
      <th>분류</th>
      <th>글제목</th>
      <th>글쓴이</th>
      <th>신고자</th>
      <th class="text-start ps-5">신고내역</th>
      <th>신고날짜</th>
      <th>진행상황</th>
    </tr>
    <c:set var="curScrStartNo" value="${pageVO.curScrStartNo}"/>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${curScrStartNo}</td>
        <td>${vo.part}</td>
        <td>
          <c:set var="content" value="${fn:replace(fn:replace(vo.content, CRLF, '<br/>'),LF, '<br/>')}"/>
          <%-- <a href="#" onclick="modalCheck('${vo.mid}','${vo.title}','${content}','${vo.nickName}')" data-bs-toggle='modal' data-bs-target='#myModal'>${vo.title}</a> --%>
          <a href="#" onclick="modalCheck('${vo.mid}','${vo.title}','${vo.nickName}')" data-bs-toggle='modal' data-bs-target='#myModal'>${vo.title}</a>
        </td>
        <td>${vo.nickName}</td>
        <td>${vo.cpMid}</td>
        <td class="text-start">
          <a href="complaintContent?partIdx=${vo.partIdx}">${fn:replace(vo.cpContent, LF,"<br/>")}</a>
        </td>
        <td>${vo.cpDate}</td>
        <td>
          <c:if test="${vo.progress == '신고접수'}">
            <a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','S')" class="badge bg-primary">신고해제</a><br/>
            <a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','H')" class="badge bg-warning">감추기</a><br/>
            <a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','D')" class="badge bg-danger">글삭제</a>
          </c:if>
          <c:if test="${vo.progress != '신고접수'}">
            <c:if test="${vo.progress == '처리중(H)'}">
						  <div class="comment-text">${vo.progress}</div>
						  <div class="comment-menu">
						    <button class="menu-btn">⬇️</button>
						    <ul class="menu-list">
						      <li><a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','S')">신고취소</a></li>
						      <li><a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','D')">글삭제</a></li>
						    </ul>
						  </div>
            </c:if>
            <c:if test="${vo.progress != '처리중(H)'}">${vo.progress}</c:if>
          </c:if>
        </td>
      </tr>
      <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
    </c:forEach>
  </table>
</div>

<!-- 모달에 회원 상세정보 출력하기 -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">Modal Heading</h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          작성자 아이디 : <span id="modalMid"></span><hr/>
          글제목 : <span id="modalTitle"></span><br/>
          글내용 : <span id="modalContent"></span><hr/>
          작성자 닉네임 : <span id="modalNickName"></span><br/>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

<!-- 블록페이지 시작 -->
	<div class="pagination justify-content-center">
	  <c:if test="${pageVO.pag > 1}"><a href="complaintList?pag=1&pageSize=${pageVO.pageSize}&part=${pageVO.part}" class="page-item page-link text-decoration-none text-dark link-primary">첫페이지</a></c:if>
	  <c:if test="${pageVO.curBlock > 0}"><a href="complaintList?pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}&part=${pageVO.part}" class="page-item page-link text-decoration-none text-dark link-primary">이전블록</a></c:if>
	  <c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize)+1}" end="${(pageVO.curBlock*pageVO.blockSize)+pageVO.blockSize}" varStatus="st">
	  	<c:if test="${i <= pageVO.totPage && i == pageVO.pag}"><a href="complaintList?pag=${i}&pageSize=${pageVO.pageSize}&part=${pageVO.part}" class="page-item page-link active text-decoration-none bg-secondary border-secondary">${i}</a></c:if>
	  	<c:if test="${i <= pageVO.totPage && i != pageVO.pag}"><a href="complaintList?pag=${i}&pageSize=${pageVO.pageSize}&part=${pageVO.part}" class="page-item page-link text-decoration-none text-dark link-primary">${i}</a></c:if>
	  </c:forEach>
	  <c:if test="${pageVO.curBlock < pageVO.lastBlock}"><a href="complaintList?pag=${(pageVO.curBlock+1)*blockSize + 1}&pageSize=${pageVO.pageSize}&part=${pageVO.part}" class="page-item page-link text-decoration-none text-dark link-primary">다음블록</a></c:if>
	  <c:if test="${pageVO.pag < pageVO.totPage}"><a href="complaintList?pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}&part=${pageVO.part}" class="page-item page-link text-decoration-none text-dark link-primary">마지막페이지</a></c:if>
	</div>
<!-- 블록페이지 끝 -->

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>