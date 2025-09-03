package com.spring.springGroupS.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Aspect
@Component
public class AspectTest {

	// 문제1 : getAopServiceTest1()메소드 '전/후'에 메세지를 출력하시오.
	@Around("execution(* com.spring.springGroupS.service.Study1Service.getAopServiceTest1(..))")
	public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("\n 이곳은 aroundAdvice()메소드입니다. 핵심관심사 수행전~~~~ 입니다. \n");
		
		joinPoint.proceed();
		
		System.out.println("\n 이곳은 aroundAdvice()메소드입니다. 핵심관심사 수행후!!!! 입니다. \n");
	}
	
	// 문제2 : getAopServiceTest3()메소드 '전'에 메세지 출력하시오.
	@Before("execution(* com.spring.springGroupS.service.Study1Service.getAopServiceTest3(..))")
	public void beforeAdvice() {
		System.out.println("\n 이곳은 beforeAdvice()메소드입니다. 핵심관심사 수행전~~~~ 입니다. \n");
	}
	
	// 문제3 : getAopServiceTest2()메소드 수행결과 메소드에서의 반환값을 콘솔에 출력하시오.
	@AfterReturning(value="execution(* com.spring.springGroupS.service.Study1Service.getAopServiceTest2(..))", returning="result")
	public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
		System.out.println("\n 이곳은 afterReturningAdvice()메소드 입니다. \n");
		System.out.println("   핵심코드에서 반환된 값 : " + result.toString() + "\n");
	}
	
	// 문제4 : getAopServiceTest2()메소드, getAopServiceTest3()메소드, 모든 메소드가 수행하는데 걸리는 시간?
	long startTime, endTime;
	@Pointcut("execution(* com.spring.springGroupS.service.Study1Service.getAopServiceTest5*(..))")
	private void cut() {}
	
	@Around("cut()")
	public void arroundAdvice2(ProceedingJoinPoint joinPoint) throws Throwable {
		startTime = System.nanoTime();
		
		System.out.println("arroundAdvice2()메소드입니다. (전) : " + joinPoint);
		joinPoint.proceed();
		System.out.println("arroundAdvice2()메소드입니다. (후) : " + joinPoint);
		
		endTime = System.nanoTime();
		
		long res = endTime - startTime;
		System.out.println("수행시간 : " + res);
	}
	
  // 문제4 : Study1Service객체안의 모든 메소드의 수행시간을 해당 메소드 이름과 함께 출력하시오.
}
