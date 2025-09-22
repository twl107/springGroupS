<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>pdsInput.jsp</title>
  <script>
    'use strict';
    
    // 폼체크, 파일사이즈체크, 확장자체크
    function fCheck() {
    	let fName = document.getElementById("fName").value;
    	let title = $("#title").val();
    	let ext = "";
    	let fileSize = 0;
    	let maxSize = 1024 * 1024 * 30;	// 최대 30MByte
    	
    	//if(fName.trim() == "") {
    	//	alert("업로드할 파일을 선택하세요");
    	//	return false;
    	//}
    	
    	let fileLength = document.getElementById("fName").files.length;
    	if(fileLength < 1) {
       	alert("업로드할 파일을 선택하세요");
       	return false;
    	}
    	else if(title.trim() == "") {
    		alert("제목을 선택하세요");
    		$("#title").focus()
       	return false;
    	}
    	
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
    		myform.fSize.value = fileSize;
    		myform.submit();
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">자 료 올 리 기</h2>
  <br/>
  <form name="myform" method="post" class="was-validated" enctype="multipart/form-data">
		<div>
      <!-- <input type="button" value="파일박스추가" onclick="fileBoxAppend()" class="btn btn-primary mb-1" /> -->
    	<!-- input태그의 file속성에서 사용하는 name의 변수명은 VO에 있는 필드명과 같아서는 안된다.(400에러발생) -->
    	<input type="file" name="file" id="fName" multiple class="form-control mb-1" />
    </div>
    <!-- <div id="fileBox"></div> -->
    <div class="mt-3 mb-3">
      올린이 : ${sNickName}
    </div>
    <div class="mb-3">
      제목 : <input type="text" name="title" id="title" placeholder="자료의 제목을 입력하세요" class="form-control" required />
    </div>
    <div class="mb-3">
      내용 : <textarea rows="4" name="content" id="content" placeholder="자료의 상세내역을 입력하세요" class="form-control"></textarea>
    </div>
    <div class="mb-3">
      분류 :
			<select name="part" id="part" class="form-control">
        <option ${part=="학습" ? "selected" : ""}>학습</option>
        <option ${part=="여행" ? "selected" : ""}>여행</option>
        <option ${part=="음식" ? "selected" : ""}>음식</option>
        <option ${part=="기타" ? "selected" : ""}>기타</option>
      </select>
    </div>
    <div class="mb-3">
      공개여부 :
      <input type="radio" name="openSw" value="공개" checked/>공개 &nbsp; &nbsp;
      <input type="radio" name="openSw" value="비공개"/>비공개
    </div>
    <div class="row text-center">
      <div class="col"><input type="button" value="자료올리기" onclick="fCheck()" class="btn btn-success"/></div>
      <div class="col"><input type="reset" value="다시쓰기" class="btn btn-warning"/></div>
      <div class="col"><input type="button" value="돌아가기" onclick="location.href='pdsList?part=${part}';" class="btn btn-info"/></div>
    </div>
    <input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}" />
    <input type="hidden" name="mid" value="${sMid}" />
    <input type="hidden" name="nickName" value="${sNickName}" />
    <input type="hidden" name="fSize" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>