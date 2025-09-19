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
  <title>multiFileUploadForm.jsp</title>
  <script>
    'use strict';
    
    function fCheck() {
    	let fName = document.getElementById("fName").value;
    	let ext = "";
    	let fileSize = 0;
    	let maxSize = 1024 * 1024 * 30;	// 최대 30MByte
    	
    	if(fName.trim() == "") {
    		alert("업로드할 파일을 선택하세요");
    		return false;
    	}
    	
    	let fileLength = document.getElementById("fName").files.length;
    	
    	for(let i=0; i<fileLength; i++) {
    		fName = document.getElementById("fName").files[i].name;
    		ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
    		if(ext != 'jpg' && ext != 'gif' && ext != 'png' && ext != 'zip' && ext != 'hwp' && ext != 'doc' && ext != 'ppt' && ext != 'pptx' && ext != 'pdf' && ext != 'txt') {
       		alert("업로드 가능한 파일은 'jpg/gif/png/zip/hwp/doc/ppt/pptx/pdf/txt'파일 입니다.");
       	}
    		fileSize += document.getElementById("fName").files[i].size;
    	}
    	
    	if(fileSize > maxSize) {
    		alert("업로드할 파일의 최대 총용량은 30MByte 이하로 등록하세요");
    	}
    	else {
    		myform.submit();
    	}
    }
    
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>멀티파일 업로드 연습</h2>
  <hr/>
  <form name="myform" method="post" enctype="multipart/form-data">
    <div class="input-group mb-3">
      <div class="input-group-text">올린이</div>
      <input type="text" name="mid" value="admin" class="form-control" />
    </div>
    <div class="input-group mb-3">
      <input type="file" name="fName" id="fName" multiple class="form-control" accept=".jpg,.gif,.png,.zip,.ppt,.pptx,.hwp,.hwpx,.doc,.pdf,.txt"/>
      <input type="button" value="파일전송" onclick="fCheck()" class="btn btn-success"/>
    </div>
  </form>
  <hr/>
  <div class="row">
    <div class="col"><a href="fileUploadForm" class="btn btn-success">싱글파일업로드이동</a></div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>