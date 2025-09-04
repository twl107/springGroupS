<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>aopMenu.jsp</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>Service객체를 활용한 AOP 연습</h2>
  <hr/>
  <p>
    <a href="test1" class="btn btn-success me-2">연습1</a>
    <a href="test2" class="btn btn-primary me-2">문제3</a>
    <a href="test3" class="btn btn-secondary me-2">문제2</a>
    <a href="test4" class="btn btn-info me-2">문제4</a>
    <a href="test5" class="btn btn-warning me-2">문제5</a>
  </p>
  <hr/>
  <h2>AOP(Aspect Oriented Program) : 문제를 바라보는 관점을 기준으로 프로그래밍 하는 기법</h2>
  <pre>
  : 핵심관심사(core consern) - 은행의 입금/출금/대출/~~등
  : 공통(횡단)관심사(cross-cutting concern) - 로그, 트랜잭션처리, 보안처리....등..
  - AOP는 핵심코드를 가지고 있는 클래스(부모)는 건드리지 않고, 상속받아 사용하는 자식클래스에서 오버라이드해서 재사용한다.
  - AOP는 적용은 스프링이 런타임시에 프록시를 이용하여 적용시키게 된다.(AOP의 적용을 : 위빙(Weaving))
  
  - AOP의 주요 용어 :
  . 타켓오브젝트(Target Object) : 상속받는 부모(자식)클래스 - AOP를 적용하고 싶은 클래스
  . 조인포인트(JoinPoint) : 타겟오브젝트에 있는 처리해야할 모든 메소드들(재정의해서 사용하게됨)
  . 포인트컷(Pointcut) : 타겟오브젝트에서 실제로 처리해야하는 메소드 - 어드바이스가 적용되는 메소드
  . 어드바이스(Advice) : 타겟오브젝트안에서 사용하고자 하는 공통코드와 그 공통코드가 언제 실행하게 할지에 대해서 처리한것
    어드바이스에는 5가지의 시점으로 보고 있다.
  . 어드바이서(Advisor) : 포인트컷과 어드바이스를 합친것(적용된것)으로 aspect와도 같은 의미로 해석된다.
    수행되는 명령어로는? before advice, after, after-throwing, after-returning, around
  . 위빙(Weaving) : 타겟오브젝트안의 재정의된 메소드중에서 핵심코드에 공통코드가 삽입되는것
  . 애즈팩트(Aspect) : AOP의 핵심으로, 포인트컷과 어드바이스의 결합을 의미한다.
    즉, 해당 포인트컷이 어느시점에 수행되게 하는가의 관점을 말한다.
  </pre>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>