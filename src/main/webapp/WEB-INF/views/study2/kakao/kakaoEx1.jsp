<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=158c673636c9a17a27b67c95f2c6be5c"></script>
  <title>kakaoEx1.jsp</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>지도 정보 획득</h2>
  <div id="map" style="width:100%;height:500px;"></div>
  <hr class="border border-dark">
  <jsp:include page="kakaoMenu.jsp" />
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = { 
	    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	    level: 3 // 지도의 확대 레벨
	};
	
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	
	//일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
	var mapTypeControl = new kakao.maps.MapTypeControl();
	
	//지도 타입 컨트롤을 지도에 표시합니다
	map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
	
	function getInfo() {
	// 지도의 현재 중심좌표를 얻어옵니다 
	var center = map.getCenter(); 
	
	// 지도의 현재 레벨을 얻어옵니다
	var level = map.getLevel();
	
	// 지도타입을 얻어옵니다
	var mapTypeId = map.getMapTypeId(); 
	
	// 지도의 현재 영역을 얻어옵니다 
	var bounds = map.getBounds();
	
	// 영역의 남서쪽 좌표를 얻어옵니다 
	var swLatLng = bounds.getSouthWest(); 
	
	// 영역의 북동쪽 좌표를 얻어옵니다 
	var neLatLng = bounds.getNorthEast(); 
	
	// 영역정보를 문자열로 얻어옵니다. ((남,서), (북,동)) 형식입니다
	var boundsStr = bounds.toString();
	
	
	var message = '지도 중심좌표는 위도 ' + center.getLat() + ', <br>';
	message += '경도 ' + center.getLng() + ' 이고 <br>';
	message += '지도 레벨은 ' + level + ' 입니다 <br> <br>';
	message += '지도 타입은 ' + mapTypeId + ' 이고 <br> ';
	message += '지도의 남서쪽 좌표는 ' + swLatLng.getLat() + ', ' + swLatLng.getLng() + ' 이고 <br>';
	message += '북동쪽 좌표는 ' + neLatLng.getLat() + ', ' + neLatLng.getLng() + ' 입니다';
	
	// 개발자도구를 통해 직접 message 내용을 확인해 보세요.
	console.log(message);
}
</script>
</body>
</html>