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
    
    // 처음접속시는 '리뷰보이기'버튼 감추고, '리뷰가리기'버튼과 '리뷰박스'를 보여준다.
    $(function(){
    	$("#reviewShowBtn").hide();
    	$("#reviewHideBtn").show();
    	$("#reviewBox").show();
    });
    
    // 리뷰 보이기
    function reviewShow() {
    	$("#reviewShowBtn").hide();
    	$("#reviewHideBtn").show();
    	$("#reviewBox").show();
    }
    
    // 리뷰 가리기
    function reviewHide() {
    	$("#reviewShowBtn").show();
    	$("#reviewHideBtn").hide();
    	$("#reviewBox").hide();
    }
    
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
    			if(res != 0) {
    				alert("자료가 삭제되었습니다.");
    				location.href = "pdsList";
    			}
    			else alert("자료 삭제 실패~~");
    		},
    		error : () => alert('전송오류')
    	});
    }
    
    // 별점과 리뷰를 입력후 등록처리하기
    function reviewCheck() {
    	let star = starForm.star.value;
    	let review = $("#review").val();
    	
    	if(star == "") {
    		alert("별점을 부여해 주세요");
    		return false;
    	}
    	
    	let query = {
    			part    : 'pds',
    			partIdx : ${vo.idx},
    			mid     : '${sMid}',
    			nickName: '${sNickName}',
    			star    : star,
    			content : review
    	}
    
			$.ajax({
				url  : "${ctp}/review/reviewInputOk",
				type : "post",
				data : query,
				success: (res) => {
					if(res != 0) {
						alert("리뷰가 등록되었습니다.");
						location.reload();
					}
					else alert("리뷰 등록 실패~");
				},
				error : () =>	alert("전송오류!")
			});
		}

    // 작성된 리뷰 삭제처리
    function reviewDelete(idx) {
    	let ans = confirm("리뷰를 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "${ctp}/review/reviewDelete",
    		type : "post",
    		data : {idx : idx},
    		success: (res) => {
    			if(res != 0) {
    				alert('리뷰가 삭제되었습니다.');
    				location.reload();
    			}
    			else alert("리뷰 삭제 실패~~");
    		},
    		error : () => alert("전송오류!")
    	});
    }
    
    // 리뷰의 댓글을 달기위한 모달창 출력하기
    function reviewReply(idx, nickName, content) {
    	$("#myModal #reviewIdx").val(idx);
    	$("#myModal #reviewReplyNickName").text(nickName);
    	$("#myModal #reviewReplyContent").html(content);
    }
    
    // 리뷰 댓글 등록하기
    function reviewReplyCheck() {
    	let replyContent = reviewReplyForm.replyContent.value;
    	let reviewIdx = reviewReplyForm.reviewIdx.value;
    	
    	if(replyContent.trim() == "") {
    		alert("리뷰 댓글을 입력하세요");
    		return false;
    	}
    	
    	let query = {
    			reviewIdx : reviewIdx,
    			replyMid  : '${sMid}',
    			replyNickName : '${sNickName}',
    			replyContent  : replyContent
    	}
    	
    	$.ajax({
    		url  : "${ctp}/review/reviewReplyInputOk",
    		type : "post",
    		data : query,
    		success:function(res) {
    			if(res != 0) {
    				alert("댓글이 등록되었습니다.");
    				location.reload();
    			}
    			else alert("댓글 등록 실패~~");
    		},
    		error : function() {
    			alert("전송 오류!");
    		}
    	});
    }
    
    // 리뷰 댓글 삭제하기
    function reviewReplyDelete(replyIdx) {
    	let ans = confirm("현 리뷰의 댓글을 삭제합니다.");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "${ctp}/review/reviewReplyDelete",
    		type : "post",
    		data : {replyIdx : replyIdx},
    		success: (res) => {
    			if(res != 0) {
    				alert("리뷰 댓글이 삭제되었습니다.");
    				location.reload();
    			}
    			else alert("리뷰댓글 삭제 실패~~");
    		},
    		error : () => alert("전송 오류!")
    	});
    }
    
	$(window).scroll(function(){
		if($(this).scrollTop() > 100) {
			$("#topBtn").addClass("on");
		}
		else {
			$("#topBtn").removeClass("on");
		}
		
		$("#topBtn").click(function(){
			window.scrollTo({top:0, behavior: "smooth"});
		});
	});
    
  </script>
  <style>
    th {
      background-color: #eee !important;
    }
    
    /* rtl : right-to-left : 배열을 오른쪽에서 왼쪽으로... */
    #starForm fieldset {
      direction: rtl;
    }
    
    /* 라디오버튼 감추기 */
    #starForm input[type=radio] {
      display: none;
    }
    
    /* 별의 색/크기 지정 */
    #starForm label {
      font-size: 1.6em;
      color: transparent;
      text-shadow: 0 0 0 #f0f0f0;
    }
    
    /* 별위에 마우스 올리면 노랑색으로 출력처리 */
    #starForm label:hover {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    
    /* 형제선택자(~)을 이용하여 hover시 같은 형제의 label은 같이 노란색으로 처리 */
    #starForm label:hover ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    
    /* 라이오버튼을 선택하면(checked), 해당 별과 그 앞의 별들을 노랑색으로 유지시킴 */
    #starForm input[type=radio]:checked ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    
    /* 화살표 부드럽게 위로 이동하는 CSS(2개) */
    h6 {
      position: fixed;
      right: 1rem;
      bottom: -50px;
      transition: 0.7s ease;
    }
    .on {
      opacity: 0.8;
      cursor: pointer;
      bottom: 0;
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
        (<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0" />KByte)
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
  <hr class="border border-dark">
  <!-- 별점부여및 후기 등록/리스트 -->
  <div>
    <form name="starForm" id="starForm">
      <fieldset style="border:0px;">
        <div class="text-start m-0 b-0">
					<input type="radio" name="star" value="5" id="star1"><label for="star1">★</label>
          <input type="radio" name="star" value="4" id="star2"><label for="star2">★</label>
          <input type="radio" name="star" value="3" id="star3"><label for="star3">★</label>
          <input type="radio" name="star" value="2" id="star4"><label for="star4">★</label>
          <input type="radio" name="star" value="1" id="star5"><label for="star5">★</label>
          : 별점을 선택해 주세요 ■
        </div>
      </fieldset>
      <div class="m-0 p-0">
        <textarea rows="3" name="review" id="review" class="form-control mb-1" placeholder="별점 후기를 남겨주시면 100포인트를 지급합니다."></textarea>
      </div>
      <div>
        <input type="button" value="별점/리뷰등록" onclick="reviewCheck()" class="btn btn-primary btn-sm form-control"/>
      </div>
    </form>
  </div>
	<hr class="border border-dark">
  <div class="row">
    <div class="col">
      <input type="button" value="리뷰보이기" id="reviewShowBtn" onclick="reviewShow()" class="btn btn-success"/>
      <input type="button" value="리뷰가리기" id="reviewHideBtn" onclick="reviewHide()" class="btn btn-warning"/>
    </div>
    <div class="col text-end">
      <b>리뷰평점 : <fmt:formatNumber value="${reviewAvg}" pattern="#,##0.0" /></b>
    </div>
  </div>
  <div id="reviewBox">
  	<hr class="border border-dark">
  	<c:set var="imsiIdx" value="0"/>
    <c:forEach var="vo" items="${reviewVos}" varStatus="st">
      <c:if test="${imsiIdx != vo.idx}">
	      <div class="row mt-3">
	        <div class="col ms-2">
	          <b>${vo.nickName}</b>
	          <span style="font-size:11px">${fn:substring(vo.RDate, 0, 10)}</span>
	          <c:if test="${vo.mid == sMid || sLevel == 0}"><a href="javascript:reviewDelete(${vo.idx})" title="리뷰삭제" class="badge bg-danger" style="font-size:8px">x</a></c:if>
	          <a href="#" onclick="reviewReply('${vo.idx}','${vo.nickName}','${fn:replace(vo.content,newLine,'<br>')}')" title="댓글달기" data-bs-toggle="modal" data-bs-target="#myModal" class="badge bg-secondary" style="font-size:8px">▤</a>
	        </div>
	        <div class="col text-end me-2">
	        	<c:forEach var="i" begin="1" end="${vo.star}" varStatus="iSt">
	        	  <font color="gold">★</font>
	        	</c:forEach>
	        	<c:forEach var="i" begin="1" end="${5 - vo.star}" varStatus="iSt">☆</c:forEach>
	        </div>
	      </div>
	      <div class="row border m-1 p-2" style="border-radius:5px">
	        ${fn:replace(vo.content, newLine, '<br/>')}
	      </div>
      </c:if>
      <c:set var="imsiIdx" value="${vo.idx}"/>
      <c:if test="${!empty vo.replyContent}">
	      <div class="d-flex text-secondary">
	        <div class="mt-2 ms-3">└─▶ </div>
	        <div class="mt-2 ms-2">${vo.replyNickName}
	          <span style="font-size:11px">${fn:substring(vo.replyRDate,0,10)}</span>
	          <c:if test="${vo.replyMid == sMid || sLevel == 0}"><a href="javascript:reviewReplyDelete(${vo.replyIdx})" title="리뷰댓글삭제" class="badge bg-danger" style="font-size:8px">x</a></c:if>
	          <br/>${vo.replyContent}
	        </div>
	      </div>
      </c:if>
    </c:forEach>
  </div>
  
  
  <hr class="border border-dark">
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
			  <br/><br/>
			</c:if>
    </c:forEach>
  </div>
</div>

<!-- 댓글 달기위한 모달창 -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">>> 리뷰에 댓글달기</h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <form name="reviewReplyForm" id="reviewReplyForm" class="was-vilidated">
            <table class="table table-bordered">
              <tr>
                <th style="width:25%">원본글작성자</th>
                <td style="width:75%"><span id="reviewReplyNickName"></span></td>
              </tr>
              <tr>
                <th>원본글</th>
                <td><span id="reviewReplyContent"></span></td>
              </tr>
            </table>
            <hr/>
            댓글 작성자 : ${sNickName}<br/>
            댓글 내용 : <textarea rows="3" name="replyContent" id="replyContent" class="form-control" required></textarea><br/>
            <input type="button" value="리뷰댓글등록" onclick="reviewReplyCheck()" class="btn btn-success form-control"/>
            <input type="hidden" name="reviewIdx" id="reviewIdx"/>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동"/></h6>
</body>
</html>