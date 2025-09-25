<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title>dataApiForm1.jsp</title>
	<script>
		'use strict';
		
		const API_KEY = 'a0e39309cec2f4c16d3c72a83cfa8ba2e3601d9b1067289f1e5af3bb5f77e33c';
	
		async function crimeCheck() {
			let year = $("#year").val();
			let apiYear = '';
			
			if(year == 2015) apiYear = '/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669';
			else if(year == 2016) apiYear = '/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff';
			else if(year == 2017) apiYear = '/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116';
			else if(year == 2018) apiYear = '/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7';
			else if(year == 2019) apiYear = '/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0';
			else if(year == 2020) apiYear = '/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e';
			else if(year == 2021) apiYear = '/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2';
			else if(year == 2022) apiYear = '/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c';
			else if(year == 2023) apiYear = '/15084592/v1/uddi:18a0493e-32bb-433d-b291-aedadffe1027';
			else if(year == 2024) apiYear = '/15084592/v1/uddi:ae3e551f-e743-4833-a566-b3315cc354b0';
			
			let url = 'https://api.odcloud.kr/api';
			url += apiYear + '?serviceKey=' + API_KEY + '&page=1&perPage=300';
			
			let response = await fetch(url);
			console.log("response : ", response);
			
			let res = await response.json();
			console.log("res : ", res);
			/* 
			let str = '';
			res.data.forEach((item, i) => {
				console.log(item, i);
				str += item + '<br/>';
			});
			 */
			 
			/* 
			let str = '';
			str += res.data.map((item, i)=>
				i + ':' + item.발생년도 + "," + item.경찰서 + '<br/>'
			).join('');
			 */
			 
    	let str = '<table class="table table-hover table-hover text-center">';
    	str += '<tr class="table-secondary">';
    	str += '<th>번호</th><th>발생년도</th><th>경찰서/지역</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th>';
    	str += '</tr>';
    	str += res.data.map((item, i) =>
				'<tr><td>' + (i+1) + '</td>'
				+ '<td>' + item.발생년도 + '년</td>'
				+ '<td>' +  item.경찰서 + '건</td>'
				+ '<td>' + item.강도 + '건</td>'
				+ '<td>' + item.절도 + '건</td>'
				+ '<td>' + item.살인 + '건</td>'
				+ '<td>' + item.폭력 + '건</td></tr>'
			).join('');
			
			$("#demo").html(str);
		}
		
		// 검색한 자료를 DB에 저장하기
		async function saveCrimeCheck() {
			let year = $("#year").val();
			let apiYear = '';
			
			if(year == 2015) apiYear = '/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669';
			else if(year == 2016) apiYear = '/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff';
			else if(year == 2017) apiYear = '/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116';
			else if(year == 2018) apiYear = '/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7';
			else if(year == 2019) apiYear = '/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0';
			else if(year == 2020) apiYear = '/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e';
			else if(year == 2021) apiYear = '/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2';
			else if(year == 2022) apiYear = '/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c';
			else if(year == 2023) apiYear = '/15084592/v1/uddi:18a0493e-32bb-433d-b291-aedadffe1027';
			else if(year == 2024) apiYear = '/15084592/v1/uddi:ae3e551f-e743-4833-a566-b3315cc354b0';
			
			let url = 'https://api.odcloud.kr/api';
			url += apiYear + '?serviceKey=' + API_KEY + '&page=1&perPage=300';
			
			let response = await fetch(url);
			console.log("response : ", response);
			
			let res = await response.json();
			console.log("res : ", res);
			
    	let str = '<table class="table table-hover table-hover text-center">';
    	str += '<tr class="table-secondary">';
    	str += '<th>번호</th><th>발생년도</th><th>경찰서/지역</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th>';
    	str += '</tr>';
    	str += res.data.map((item, i) =>
				'<tr><td>' + (i+1) + '</td>'
				+ '<td>' + item.발생년도 + '년</td>'
				+ '<td>' +  item.경찰서 + '건</td>'
				+ '<td>' + item.강도 + '건</td>'
				+ '<td>' + item.절도 + '건</td>'
				+ '<td>' + item.살인 + '건</td>'
				+ '<td>' + item.폭력 + '건</td></tr>'
			).join('');
			$("#demo").html(str);
			// 위쪽으로는 브라우저에 출력처리, 아래쪽은 DB에 저장처리
			
			let query = '';
			
			for(let i=0; i<res.data.length; i++) {
				if(res.data[i].경찰서 != null) {
					query = {
							year			: year,
							police		: res.data[i].경찰서,
							robbery		: res.data[i].강도,
							theft		: res.data[i].절도,
							murder		: res.data[i].살인,
							violence		: res.data[i].폭력
					}
				}
				
				$.ajax({
					url			: 'saveCrimeCheck',
					type		: 'post',
					data		: query,
					error		: () => alert('전송오류!')					
				});
			}
			alert(year + "년도 자료가 DB에 저장되었습니다.");
		}
		
		// 검색한 자료를 DB에서 삭제하기
		async function deleteCrimeCheck() {
			let year = $("#year").val();
			let ans = confirm(year + "년도 자료를 삭제하시겠습니가?");
			if(!ans) return false;
		
			$.ajax({
				url			: 'deleteCrimeCheck',
				type		: 'post',
				data		: {year : year},
				success	: () => alert(year + "년도 자료가 DB에서 삭제되었습니다."),
				error		: () => alert('전송오류!')					
			});
		}
	
    // 선택한 년도의 자료를 DB에서 가져와서 화면에 출력하기
    function dbListCrimeCheck() {
    	let year = $("#year").val();
    	
    	$.ajax({
				url   : 'dbListCrimeCheck',
				type  : 'post',
				data  : {year : year},
				success: (vos) => {
					let str = '';
					if(vos.length != 0) {
						str += '<table class="table table-hover text-center">';
						str += '<tr class="table-secondary"><th>번호</th><th>발생년도</th><th>경찰서/지역</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th></tr>';
						for(let i=0; i<vos.length; i++) {
							str += '<tr>';
							str += '<td>'+(i+1)+'</td>';
							str += '<td>'+vos[i].year+'</td>';
							str += '<td>'+vos[i].police+'</td>';
							str += '<td>'+vos[i].robbery+'</td>';
							str += '<td>'+vos[i].theft+'</td>';
							str += '<td>'+vos[i].murder+'</td>';
							str += '<td>'+vos[i].violence+'</td>';
							str += '</tr>';
						}
						str += '</table>';
					}
					else alert(year + "년도 자료는 DB에 없습니다.");
					$("#demo").html(str);
				},
				error : () => alert('전송오류!')
			});
    }
    
    // 년도별 + 경찰서 지역별 DB자료 출력처리호출하기
    function yearPoliceCheck() {
    	policeForm.action = "${ctp}/study2/dataApi/dataApiForm1";
    	policeForm.submit();
    }
    
    // 화면 끝에서 위쪽으로 부드럽게 이동하기
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
  <h2>경찰청 강력범죄 발생 현황 자료 리스트</h2>
  <hr class="border-1 border-dark">
  <form name="policeForm" method="post">
		<div class="input-group mb-3">
		  <select name="year" id="year" class="form-select me-1">
		    <c:forEach var="year" begin="2015" end="2024" step="1" varStatus="st">
		    	<option value="${year}">${year}년도</option>
		    </c:forEach>
		  </select>
		  <input type="button" value="검색" onclick="crimeCheck()" class="btn btn-success me-1"/>
		  <input type="button" value="DB저장" onclick="saveCrimeCheck()" class="btn btn-primary me-1"/>
		  <input type="button" value="DB삭제" onclick="deleteCrimeCheck()" class="btn btn-danger me-1"/>
		  <input type="button" value="DB출력" onclick="dbListCrimeCheck()" class="btn btn-info"/>
		</div>
		<div class="input-group">
	    <div class="input-group-prepend"><span class="input-group-text">경찰서 지역명</span></div>
	    <select name="policeZone" class="mr-3">
	      <option ${policeZone=='서울' ? 'selected' : ''}>서울</option>
	      <option ${policeZone=='경기' ? 'selected' : ''}>경기</option>
	      <option ${policeZone=='강원' ? 'selected' : ''}>강원</option>
	      <option ${policeZone=='충북' ? 'selected' : ''}>충북</option>
	      <option ${policeZone=='충남' ? 'selected' : ''}>충남</option>
	      <option ${policeZone=='전북' ? 'selected' : ''}>전북</option>
	      <option ${policeZone=='전남' ? 'selected' : ''}>전남</option>
	      <option ${policeZone=='경북' ? 'selected' : ''}>경북</option>
	      <option ${policeZone=='경남' ? 'selected' : ''}>경남</option>
	      <option ${policeZone=='제주' ? 'selected' : ''}>제주</option>
	    </select>
	    <input type="button" value="년도/경찰서별출력" onclick="yearPoliceCheck()" class="btn btn-secondary mr-2" />
		</div>
  </form>
  <hr class="border-1 border-dark">
  <div id="demo"></div>
  <hr class="border-1 border-dark">
  <c:if test="${!empty vos}">
  	<h3>${year}년 ${policeZone}지역 범죄 분석 통계</h3>
  	<table class="table table-hover">
  	  <tr class="table-secondary">
  	    <th>구분</th>
  	    <th>경찰서</th>
  	    <th>강도</th>
  	    <th>살인</th>
  	    <th>절도</th>
  	    <th>폭력</th>
  	  </tr>
  	  <c:forEach var="vo" items="${vos}" varStatus="st">
  	    <tr>
  	      <td>${st.count}</td>
  	      <td>${vo.police}</td>
  	      <td>${vo.robbery}</td>
  	      <td>${vo.theft}</td>
  	      <td>${vo.murder}</td>
  	      <td>${vo.violence}</td>
  	    </tr>
  	  </c:forEach>
  	  <tr><td colspan="6"></td></tr> 
  	  <tr class="table-info">
  	    <td>총계</td>
  	    <td>${policeZone}</td>
  	    <td>${analyzeVO.totRobbery}</td>
  	    <td>${analyzeVO.totMurder}</td>
  	    <td>${analyzeVO.totTheft}</td>
  	    <td>${analyzeVO.totViolence}</td>
  	  </tr>
  	  <tr class="table-warning">
  	    <td>평균</td>
  	    <td>${policeZone}</td>
  	    <td>${analyzeVO.avgRobbery}</td>
  	    <td>${analyzeVO.avgMurder}</td>
  	    <td>${analyzeVO.avgTheft}</td>
  	    <td>${analyzeVO.avgViolence}</td>
  	  </tr>
  	</table>
  </c:if>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
<h6 id="topBtn" class="text-end me-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동"/></h6>
</body>
</html>