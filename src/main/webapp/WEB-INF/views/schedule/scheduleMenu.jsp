<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>scheduleMenu.jsp</title>
  <script>
    'use strict';
    
    // 일정 등록하기
    function scheduleInput() {
    	let part = scheduleForm.part.value;
    	let content = scheduleForm.content.value;
    	if(content == "") {
    		alert("일정을 입력하세요!");
    		return false;
    	}
    	let query = {
    			mid   : '${sMid}',
    			ymd   : '${ymd}',
    			part  : part,
    			content : content
    	}
    	
    	$.ajax({
    		type  : "post",
    		url   : "${ctp}/schedule/scheduleInputOk",
    		data  : query,
    		success:function(res) {
    			if(res != 0) {
	  				alert("일정이 등록되었습니다.");
	  				location.reload();
    			}
    			else alert("일정 등록실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 스케줄 수정하기(동적폼)
    function updateCheck(idx, part, content) {
    	let scheduleInput = '<div id="scheduleUpdateForm'+idx+'">';
    	scheduleInput += '<form name="updateForm'+idx+'">';
    	scheduleInput += '<table class="table table-bordered">';
    	scheduleInput += '<tr><th>일정분류</th><td>';
    	scheduleInput += '<select name="part" id="part'+idx+'" class="form-control">';
    	scheduleInput += '<option value="모임">모임</option>';
    	scheduleInput += '<option value="업무">업무</option>';
    	scheduleInput += '<option value="학습">학습</option>';
    	scheduleInput += '<option value="여행">여행</option>';
    	scheduleInput += '<option value="기타">기타</option>';
    	scheduleInput += '<option value="'+part+'" selected>'+part+'</option>';
    	scheduleInput += '</select>';
    	scheduleInput += '</td></tr>';
    	scheduleInput += '<tr><th>내용</th><td>';
    	scheduleInput += '<textarea name="content" id="content'+idx+'" rows="4" class="form-control">'+content.replaceAll("<br/>","\n")+'</textarea>';
    	scheduleInput += '</td></tr>';
    	scheduleInput += '<tr><td colspan="2" class="text-center">';
    	scheduleInput += '<span class="row">';
    	scheduleInput += '<span class="col"><input type="button" value="일정수정" onclick="scheduleUpdateOk('+idx+')" class="btn btn-success form-control"/></span>';
    	scheduleInput += '<span class="col"><input type="button" value="수정창닫기" onclick="scheduleUpdateClose('+idx+')" class="btn btn-warning form-control"/></span>';
    	scheduleInput += '</span>';
    	scheduleInput += '</td></tr>';
    	scheduleInput += '</table>';
    	scheduleInput += '</form></div>';
    	
    	$("#scheduleUpdateOpen"+idx).hide();
    	$("#updateDemo"+idx).slideDown(500);
    	$("#updateDemo"+idx).html(scheduleInput);
    }
    
    // 수정창 닫기
    function scheduleUpdateClose(idx) {
    	$("#scheduleUpdateOpen"+idx).show();
    	$("#scheduleUpdateForm"+idx).slideUp(500);
    }
    
    // 일정 수정하기
    function scheduleUpdateOk(idx) {
    	let part = $("#part"+idx).val();
    	let content = $("#content"+idx).val();
    	let query = {
    			idx  : idx,
    			part : part,
    			content : content
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/schedule/scheduleUpdateOk",
    		data : query,
    		success:function(res) {
    			if(res != 0) {
  					alert("수정완료!!");
  					location.reload();
    			}
    			else alert("수정실패~~");
    		},
    		error : function() {
    			alert("전송 실패~~");
    		}
    	});
    }
    
    // 스케줄 삭제처리
    function delCheck(idx) {
    	let ans = confirm("선택된 일정을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type  : "post",
    		url   : "${ctp}/schedule/scheduleDeleteOk",
    		data  : {idx : idx},
    		success:function(res) {
    			if(res != 0) {
  					alert("일정이 삭제 되었습니다.");
  					location.reload();
    			}
    			else alert("일정 삭제실패~~");
    		},
    		error : function() {
    			alert("전송 오류!");
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
  <h4>${ymd} 일정입니다.</h4>
  <p>(오늘의 일정은 총 ${scheduleCnt}건이 있습니다.)</p>
  <hr/>
  <div>
    <!-- ymd : '2025-09-24' , nowDate : '2025-09-25' -->
    <%-- <% Date now = new Date(); %> --%>
    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate var="nowDate" value="${now}" pattern="yyyy-MM-dd"/>
    <c:if test="${ymd >= nowDate}">
    	<a href="#" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#myModalInput">일정등록</a>
    </c:if>
    <a href="schedule" class="btn btn-primary">돌아가기</a>
  </div>
  <hr class="border border-dark">
  
  <c:if test="${scheduleCnt != 0}">
    <table class="table table-hover text-center">
      <tr class="table-dark text-dark">
        <th>번호</th>
        <th>간단 내역</th>
        <th>분류</th>
        <th>비고</th>
      </tr>
      <c:forEach var="vo" items="${vos}" varStatus="st">
        <tr>
          <td>${st.count}</td>
          <td>
          	<a href="#" onclick="modalView('${vo.part}','${fn:replace(vo.content,newLine,'<br/>')}')" data-bs-toggle="modal" data-bs-target="#myModal">
	            <c:if test="${fn:indexOf(vo.content,newLine) != -1}">${fn:substring(vo.content,0,fn:indexOf(vo.content,newLine))}</c:if>
	            <c:if test="${fn:indexOf(vo.content,newLine) == -1}">${fn:substring(vo.content,0,20)}</c:if>
            </a>
          </td>
          <td>${vo.part}</td>
          <td>
            <c:if test="${ymd >= nowDate}">
              <input type="button" value="수정" onclick="updateCheck('${vo.idx}','${vo.part}','${fn:replace(vo.content,newLine,'<br/>')}')" id="scheduleUpdateOpen${vo.idx}" class="btn btn-warning btn-sm"/>
            </c:if>
            <input type="button" value="삭제" onclick="delCheck(${vo.idx})" class="btn btn-danger btn-sm"/>
          </td>
        </tr>
        <tr><td colspan="4" class="m-0 p-0"><div id="updateDemo${vo.idx}"></div></td></tr>
      </c:forEach>
      <tr><td colspan="4" class="m-0 p-0"></td></tr>
    </table>
  </c:if>
</div>

<!-- 일정 등록을 위한 모달 -->
<div class="modal fade" id="myModalInput">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">>> 일정 등록하기</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
				<form name="scheduleForm">
		    	<table class="table table-bordered">
		    	  <tr>
			    	  <th>일정분류</th>
			    	  <td>
					    	<select name="part" id="part" class="form-select">
						    	<option>모임</option>
						    	<option>업무</option>
						    	<option>학습</option>
						    	<option>여행</option>
						    	<option>기타</option>
			    			</select>
			    		</td>
			    	</tr>
		    	  <tr>
		    	    <th>내용</th>
		    	    <td>
		    				<textarea name="content" id="content" rows="4" class="form-control"></textarea>
		    			</td>
		    		</tr>
		    		<tr>
			    		<td colspan="2" class="text-center">
					    	<input type="button" value="일정등록" onclick="scheduleInput()" class="btn btn-success form-control"/>
			    		</td>
			    	</tr>
		    	</table>
	    	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- 내용클릭시 모달창을 통해서 상세내역 출력하기 -->
<div class="modal fade" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title"><b>${ymd}</b></h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
        <table class="table">
          <tr><th>분류 :</th><td><span id="part"></span></td></tr>
          <tr><th>내용 :</th><td><span id="content"></span></td></tr>
          <tr><th>작성자 :</th><td>${sMid}</td></tr>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
      
    </div>
  </div>
</div>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>