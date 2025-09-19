<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>mailForm.jsp</title>
  <script>
    'use strict'
    
    function jusorokCheck() {
    	$(".modal-header #cnt").html(${memberCnt});
    	let str = '';
    	str += '<table class="table table-hover text-center">';
    	str += '<tr class="table-secondary">';
    	str += '<th>번호</th><th>아이디</th><th>성명</th><th>메일주소</th>';
    	str += '</tr>';
    	str += '<c:forEach var="vo" items="${memberVos}" varStatus="st">';
    	str += '<tr onclick="javascript:emailView(\'${vo.email}\')">';
    	str += '<td>${st.count}</td>';
    	str += '<td>${vo.mid}</td>';
    	str += '<td>${vo.name}</td>';
    	str += '<td>${vo.email}</td>';
    	str += '</tr>';
    	str += '</c:forEach>';
    	str += '</table>';
    	$(".modal-body #jusorok").html(str);
    }
    
    function emailView(email) {
    	$("#toMail").val(email);
      bootstrap.Modal.getInstance($("#myModal")).hide();
    }
  </script>
  <style>
    th {
      background-color: #eee !important;
      text-align: center;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-3">메 일 보 내 기</h2>
  <div class="text-end">(받는 사람의 메일주소를 정확히 입력하셔야 합니다.)</div>
  <form name="myform" method="post">
    <table class="table table-bordered">
      <tr>
        <th>받는사람</th>
        <td>
          <div class="input-group">
            <input type="text" name="toMail" id="toMail" placeholder="받는사람 메일주소를 입력하세요" autofocus required class="form-control" />
            <!-- <input type="button" value="주소록" onclick="jusorokCheck()" class="btn btn-success"/> -->
            <input type="button" value="주소록" onclick="jusorokCheck()" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#myModal" />
          </div>
        </td>
      </tr>
      <tr>
        <th>메일제목</th>
        <td><input type="text" name="title" id="title" placeholder="메일 제목을 입력하세요" required class="form-control" /></td>
      </tr>
      <tr>
        <th>메일내용</th>
        <td><textarea rows="7" name="content" id="content" placeholder="메일 내용을 입력하세요" required class="form-control"></textarea></td>
      </tr>
      <tr>
        <td colspan="2" class="text-center">
          <input type="submit" value="메일보내기" class="btn btn-success me-2" />
          <input type="reset" value="다시쓰기" class="btn btn-warning" />
        </td>
      </tr>
    </table>
  </form>
  <hr/>
  <pre>
    <h2>메일서버(SMTP/POP3/IMAP)</h2>
    <h4>SMTP(Simple Mail Transfer Protocol)</h4>
    인터넷에서 메일 주고 받기 위해 이용되는 프로토콜(규약).
    RFC2821에 따라 규정한 사용 TCP포트번호는 25번이고, 메일 서버간 송수신 뿐만 아니라 메일 클라에서 메일 서버로 메일을 보낼때에도 사용된다.
    우리가 메일을 보낼때는 바로 상대편의 컴퓨터로 메일을 송신하는것이 아니라, 중간에 메일서버라는 곳을 몇군데 거치게 된다.
    메일서버에 메일이 보관되고 그것을 다시 다른 메일서버에 보내면서 결국 보내고자하는 end-user 에게 전해진다.
    일반적으로 메일서버 간 메일을 주고받을때는 SMTP(simple mail transfer protocol)를 사용한다.
    
    <h4>POP3(Post Office Protocol)</h4>
    받는메일이라고 불리는 POP 서버(version3) 이메일을 받아오는 표준 프로토콜. TCP포트번호는 110번
    메일 서버에서 이메일을 로컬 PC로 수신받을 수 있는 client / server 프로토콜이다.
    메일 서버에 저장되어있는 메일을 로컬 pc로 가져오는 역할.
    pop3는 서버에서 메일을 받아오는 즉시 삭제되도록 만들어 졌지만 서버저장 설정은 가능하다.
    스토리지용량에 제한있는 경우 유리하다.
    
    <h4>IMAP(Internet Message Access Protocol)</h4>
    POP와 같이 메일 서버 종류 중 하나이다. TCP포트번호는 143번를 사용한다.
    POP와는 달리 중앙 서버에서 동기화가 이뤄지기 때문에 모든 장치에서 동일한 이메일 폴더를 확인할 수 있다.
    스마트폰, 태블릿, PC모두 동일한 받은메일/보낸메일/기타폴더 등 모든 이메일 메시지를 볼 수 있다.
    서버에 이메일이 남겨진 상태로 사용자에게 이메일을 보여준다. 그렇게 때문에 사용자는 언제 어디서나 원하는 메일을 열람할 수 있다.
    메일이 서버에 저장되어있기 때문에 로컬pc에 문제가 생겨도 이메일에는 아무 영향을 미치지 않는다.
  </pre>
</div>

<div class="modal fade" id="myModal">
	<div class="modal-dialog modal-dialog-scrollable">
	  <div class="modal-content">
	  	<div class="modal-header">
	  		<h4 class="modal-title">☆ 주 소 록 ☆(건수:<span id="cnt"></span>)</h4>
	  		<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	  	</div>
	  	<div class="modal-body">
	  		<span id="jusorok"></span>
	  	</div>
	  	<div class="modal-footer">
	  		<button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
	  	</div>
	  </div>
	</div>
</div>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>