<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>memberLogin.jsp</title>
  <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>	<!-- 카카오로그인 js파일 -->
  <script>
	  // 카카오 로그인(자바스크립트 앱키 등록)
	  window.Kakao.init("a257d2d6af6f7865d576b07a9f309156");
	  
	  function kakaoLogin() {
	  	window.Kakao.Auth.login({
	  		scope: 'profile_nickname, account_email',
	  		success:function(autoObj) {
	  			console.log(Kakao.Auth.getAccessToken(), "정상 토큰 발급됨...");
	  			 
	  			window.Kakao.API.request({
	  				url : '/v2/user/me',
	  				success:function(res) {
	  					const kakao_account = res.kakao_account;
	  					//console.log(kakao_account);
	  					
	  					location.href = "${ctp}/member/kakaoLogin?nickName="+kakao_account.profile.nickname+"&email="+kakao_account.email+"&accessToken="+Kakao.Auth.getAccessToken();
	  				}
	  			});
	  			 
	  		}
	  	});
	  }  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
  <form name="myform" method="post">
    <table class="table table-bordered text-center">
      <tr>
        <td colspan="2" class="bg-secondary-subtle"><font size="5">로 그 인</font></td>
      </tr>
      <tr>
        <th class="align-middle">아이디</th>
        <td><input type="text" name="mid" id="mid" value="${mid}" autofocus required class="form-control"/></td>
      </tr>
      <tr>
        <th class="align-middle">비밀번호</th>
        <td><input type="password" name="pwd" id="pwd" value="1234" required class="form-control"/></td>
      </tr>
      <tr>
        <td colspan="2">
          <div class="mb-2">
	          <input type="submit" value="로그인" class="btn btn-success me-2"/>
	          <input type="reset" value="다시입력" class="btn btn-warning me-2"/>
	          <input type="button" value="회원가입" onclick="location.href='${ctp}/member/memberJoin';" class="btn btn-secondary"/>
	          <a href="javascript:kakaoLogin()"><img src="${ctp}/images/kakaoLogin.png" width="145px"/></a>
          </div>
	    		<div style="font-size:0.8em">
	    			<input type="checkbox" name="idSave" checked /> 아이디 저장 /
	    			<a href="memberIdSearch" class="text-decoration-none text-dark link-primary">아이디찾기</a> /
	    			<a href="#" class="text-decoration-none text-dark link-primary">비밀번호찾기</a>
	    		</div>
        </td>
      </tr>
    </table>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>