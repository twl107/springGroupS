<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<div>
  <a href="kakaomap" class="btn btn-danger">HOME</a>
  <a href="kakaoEx1" class="btn btn-success">지도정보획득</a>
  <a href="kakaoEx2" class="btn btn-primary">클릭한위치에마커표시</a>
  <a href="kakaoEx3" class="btn btn-info">MyDB에 저장된 지명검색</a>
  <a href="kakaoEx4" class="btn btn-warning">KakaoDB에 저장된 지명검색</a>
</div>
