<%@ page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>adScheduleInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    $(document).ready(function(){
    	$("#scheduleInputHideBtn").hide();
    	$("#scheduleInputBox").hide();
    });
    
    // 일정 등록창 열기
    function scheduleInputView() {
    	$("#scheduleInputViewBtn").hide();
    	$("#scheduleInputHideBtn").show();
    	$("#scheduleInputBox").slideDown(300);
    }
    
    // 일정 등록창 닫기
    function scheduleInputHidden() {
    	$("#scheduleInputViewBtn").show();
    	$("#scheduleInputHideBtn").hide();
    	$("#scheduleInputBox").slideUp(300);
    }
    
    // 관리자 일정등록
    function adScheduleInput() {
    	let sDate = scheduleForm.sDate.value;
    	let part = scheduleForm.part.value;
    	let content = scheduleForm.content.value;
    	if(part == "") {
    		alert("공지사항을 입력하세요!");
    		scheduleForm.part.focus();
    		return false;
    	}
    	let query = {
    			mid   : '${sMid}',
    			sDate  : sDate,
    			part  : part,
    			content  : content,
    	}
    	
    	$.ajax({
    		type  : "post",
    		url   : "${ctp}/admin/schedule/adScheduleInput",
    		data  : query,
    		success:function(res) {
    			if(res != 0) {
	  				alert("일정이 등록되었습니다.");
	  				location.reload();
    			}
    			else alert("일정 등록 실패~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    // 관리자 일정삭제
    function adScheduleDelete(idx) {
    	let ans = confirm("선택하신 일정을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type  : "post",
    		url   : "${ctp}/schedule/scheduleDeleteOk",
    		data  : {idx : idx},
    		success:function(res) {
    			if(res != 0) {
	  				alert("일정이 삭제되었습니다.");
	  				location.reload();
    			}
    			else alert("일정 삭제 실패~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
  <style>
    a {text-decoration: none}
  </style>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-3">관리자 년간 일정 리스트</h2>
  <div class="mb-2">
	  <input type="button" value="일정등록" onclick="scheduleInputView()" id="scheduleInputViewBtn" class="btn btn-primary"/>
	  <input type="button" value="등록창닫기" onclick="scheduleInputHidden()" id="scheduleInputHideBtn" class="btn btn-info"/>
  </div>
  <div id="scheduleInputBox">
	  <h4 class="text-center">관리자 년간 일정 등록</h4>
	  <form name="scheduleForm">
	    <div class="input-group">
	      <div class="input-group-text p-0" style="width:80%">
			    <table class="table table-bordered border-secondary-subtle m-0">
			      <tr>
			        <th class="text-center align-middle table-secondary">날짜선택</th>
			        <td><input type="date" name="sDate" id="sDate" value="<%=LocalDate.now()%>" class="form-control"/></td>
			      </tr>
			      <tr>
			        <th class="text-center align-middle table-secondary">공지내역</th>
			        <td><input type="text" name="content" id="content" class="form-control"/></td>
			      </tr>
			    </table>
	    	</div>
	      <input type="button" value="공지내역등록" onclick="adScheduleInput()" class="btn btn-success form-control" style="width:20%"/>
	    </div>
	    <!-- <div><input type="button" value="공지내역등록" onclick="adScheduleInput()" class="btn btn-success form-control" /></div> -->
	    <input type="hidden" name="part" value="공지"/>
	  </form>
	  <hr class="border border-dark">
  </div>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th><th>날짜</th><th>행사내역</th><th>비고</th>
    </tr>
    <c:forEach var="vo" items="${scheduleVos}" varStatus="st">
      <tr>
        <td>${st.count}</td>
        <td>${fn:substring(vo.SDate,0,10)}</td>
        <td>${vo.content}</td>
        <td>
          <a href="#" class="badge bg-warning">수정</a>
          <a href="javascript:adScheduleDelete(${vo.idx})" class="badge bg-danger">삭제</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
<p><br/></p>
</body>
</html>