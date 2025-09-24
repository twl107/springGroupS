<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>calendar.jsp</title>
  <style>
    td.today {
      background-color: pink;
      color: #fff;
      font-weight: bolder;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <div>
    <button type="button" onclick="location.href='calendar?yy=${yy-1}&mm=${mm}'" title="이전년도" class="btn btn-secondary btn-sm">◁◁</button>
    <button type="button" onclick="location.href='calendar?yy=${yy}&mm=${mm-1}'" title="이전월" class="btn btn-secondary btn-sm">◀</button>
    &nbsp; <font size="5">${yy}년 ${mm+1}월</font> &nbsp;
    <button type="button" onclick="location.href='calendar?yy=${yy}&mm=${mm+1}'" title="다음월" class="btn btn-secondary btn-sm">▶</button>
    <button type="button" onclick="location.href='calendar?yy=${yy+1}&mm=${mm}'" title="다음년도" class="btn btn-secondary btn-sm">▶</button>
    <button type="button" onclick="location.href='calendar'" title="오늘날짜" class="btn btn-secondary btn-sm">♥</button>
  </div>
  <div style="height:450px">
    <table class="table table-bordered text-center" style="height:100%">
      <tr>
        <th style="color:red;width:13%;vertical-align:middle">일</th>
        <th style="width:13%;vertical-align:middle">월</th>
        <th style="width:13%;vertical-align:middle">화</th>
        <th style="width:13%;vertical-align:middle">수</th>
        <th style="width:13%;vertical-align:middle">목</th>
        <th style="width:13%;vertical-align:middle">금</th>
        <th style="color:blue;width:13%;vertical-align:middle">토</th>
      </tr>
      <tr>
        <!-- 달력 1줄(7일)을 체크하기위한 변수 : cnt -->
        <c:set var="cnt" value="1" />
        <c:forEach var="preDay" begin="${preLastDay - (startWeek-2)}" end="${preLastDay}" varStatus="st">
          <td style="font-size:0.7em">
            ${prevYear}-${prevMonth + 1}-${preDay}
          </td>
          <c:set var="cnt" value="${cnt + 1}" />
        </c:forEach>
        
         
         <!-- 달력출력 -->
        <c:forEach var="i" begin="${1}" end="${lastDay}" varStatus="st">
          <c:set var="todaySw" value="${yy==toYear && mm==toMonth && st.count==toDay ? 1 : 0}"/>
          <td ${todaySw == 1 ? 'class=today' : ''}>
            <c:choose>
              <c:when test="${cnt % 7 == 1}"><font color="red">${st.count}</font></c:when>
              <c:when test="${cnt % 7 == 0}"><font color="blue">${st.count}</font></c:when>
              <c:otherwise>${st.count}</c:otherwise>
            </c:choose>
          </td>
          <c:if test="${cnt % 7 == 0}">
            </tr><tr>
          </c:if>
          <c:set var="cnt" value="${cnt + 1}"/>
        </c:forEach>
       
       	<%-- <c:if test="${nextStartWeek != 7}"> --%>
	       	<c:forEach begin="${nextStartWeek}" end="7" varStatus="st">
	          <td style="font-size:0.7em">
	            ${nextYear}-${nextMonth + 1}-${st.count}
	          </td>
	          <c:set var="cnt" value="${cnt + 1}" />
	        </c:forEach>
        <%-- </c:if> --%>
      </tr>
    </table>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>