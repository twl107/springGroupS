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
  <title>kakaoEx3.jsp</title>
  <script>
  	// MyDB에 저장된 위치 검색하기
    function addressSearch() {
    	let address = addressForm.address.value;
    	if(address.trim() == "") {
    		alert("검색할 지점을 선택하세요");
    		return false;
    	}
    	addressForm.submit();    		
    }
  	
  	// MyDB에 저장된 지점 삭제하기
    function addressDelete() {
    	let address = addressForm.address.value;
    	if(address == "") {
    		alert("삭제할 지점을 선택하세요");
    		return false;
    	}
    	let ans = confirm("검색한 지점명을 MyDB에서 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "kakaoAddressDelete",
    		type : "post",
    		data : {address : address},
    		success:function(res) {
    			if(res != 0) {
    				alert("선택한 지점이 MyDB에 삭제 되었습니다.");
    				location.href = "kakaoEx3";
    			}
    			else alert("삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
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
  <h2>MyDB에 저장된 목록으로 바로가기</h2>
  <hr class="border border-dark">
  <form name="addressForm">
    <div class="input-group mb-1">
	    <select name="address" id="address" class="form-select">
	      <option value="">지역선택</option>
	      <c:forEach var="aVO" items="${addressVos}" varStatus="st">
	      	<option value="${aVO.address}" <c:if test="${aVO.address == vo.address}">selected</c:if>>${aVO.address}</option>
	      </c:forEach>
	    </select>
	    <input type="button" value="지점선택" onclick="addressSearch()" class="btn btn-success btn-sm me-1"/>
	    <input type="button" value="지점삭제" onclick="addressDelete()" class="btn btn-danger btn-sm me-1"/>
    </div>
  </form>
  <div id="map" style="width:100%;height:500px;"></div>
  
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=158c673636c9a17a27b67c95f2c6be5c"></script>
	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(36.63508163115122, 127.45948750459904), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

		
		// 앞에서 넘어온 위도와 경도로 위치생성
	  var coords = new kakao.maps.LatLng(${vo.latitude}, ${vo.longitude});
		
		
		
		
		// 2.지도를 클릭한 위치에 표출할 마커입니다(클릭한 위치에 마커 표시하기)
		var marker = new kakao.maps.Marker({ 
		    // 지도 중심좌표에 마커를 생성합니다 
		    map: map,
		    position: coords
		}); 
		
		
		// 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
		    //position : iwPosition, 
		    content : '<div style="width:150px;text-align:center;padding:6px 0;">${vo.address}</div>' 
		});
		// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
		infowindow.open(map, marker); 

	  map.setCenter(coords);
	</script>
  <hr class="border border-dark">
  <jsp:include page="kakaoMenu.jsp" />
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>