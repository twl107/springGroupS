package com.spring.springGroupS.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.spring.springGroupS.vo.BmiVO;
import com.spring.springGroupS.vo.SungjukVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
@Service
public class Study1Service {

	public void getAopServiceTest1() {
		log.info("getAopServiceTest1 메소드입니다.\n");
	}
	
	public int getAopServiceTest2() {
		//log.info("getAopServiceTest2 메소드입니다.\n");
		
		int tot = 0;
		for(int i=1; i<=100; i++) {
			tot += i;
		}
		log.info("getAopServiceTest2 메소드(for문)1~100 : "+tot+"\n");
		return tot;
	}

	public void getAopServiceTest3() {
		//log.info("getAopServiceTest3 메소드입니다.\n");
		
		int i = 0, tot = 0;
		while(i < 100) {
			i++;
			tot += i;
		}
		log.info("getAopServiceTest3 메소드(while문)1~100 : "+tot+"\n");
	}
	
	public void getAopServiceTest52() {
		//log.info("getAopServiceTest52 메소드입니다.\n");
		
		int tot = 0;
		for(int i=1; i<=100; i++) {
			tot += i;
		}
		log.info("getAopServiceTest52 메소드(for문)1~100 : "+tot+"\n");
		//return tot;
	}
	
	public void getAopServiceTest53() {
		//log.info("getAopServiceTest53 메소드입니다.\n");
		
		int i = 0, tot = 0;
		while(i < 100) {
			i++;
			tot += i;
		}
		log.info("getAopServiceTest53 메소드(while문)1~100 : "+tot+"\n");
	}

	// 성적 계산하기
	//public SungjukVO getSungjukCalc(SungjukVO vo) {
	public void getSungjukCalc(SungjukVO vo) {
		int tot = vo.getKor() + vo.getEng() + vo.getMat();
		double avg = tot / 3.0;
		String grade = "";
		if(avg >= 90) grade = "A";
		else if(avg >= 80) grade = "B";
		else if(avg >= 70) grade = "C";
		else if(avg >= 60) grade = "D";
		else grade = "F";
		
		vo.setTot(tot);
		vo.setAvg(avg);
		vo.setGrade(grade);
		//System.out.println("vo(서비스) : " + vo);
		
		//return vo;
	}

	// 비만도 판별하기
	/*
  비만도(BMI) 계산하는 프로그램을 만드시오.
  공식 : 몸무게 / (키백분율 * 키백분율)
  예) 키:170Cm, 몸무게:65Kg
     비만지수 구하는 공식 : 65 / (1.7 * 1.7)
     비만지수에 대한 판정?
     저체중: 18.5미만, 표준 : 18.5~23 , 과체중:23~25, 비만:25초과
   
   데이터의 주입은 xml을 통해서 처리한다.(입력값 : 비만지수값, 개인별 성명/키/몸무게)
  */
	//public BmiVO getBmiCalc(BmiVO vo) {
	public void getBmiCalc(BmiVO vo) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml/bmi.xml");
		BmiVO bVo = (BmiVO) ctx.getBean("bmi");
		//BmiVO bVo = ctx.getBean("bmi", BmiVO.class);
		
		double dblHeight = vo.getHeight() / 100.0;
		double bmi = vo.getWeight() / (dblHeight * dblHeight);
		
		//String result = "성명 : "+vo.getName()+" , 키 : "+vo.getHeight()+"cm , 몸무게 : "+vo.getWeight()+"kg<br>";
		String result = "";
		String bigo = vo.getName() + "님의 비만지수는 "+String.format("%.2f", bmi)+"로 ";
		
		if(bmi < bVo.getLow()) {
			result = "저체중";
			bigo += result + " 입니다.<br> 음식을 가리지 말고 많이 많이 드세요";
		}
		else if(bmi <= bVo.getNormal()) {
			result = "정상";
			bigo += result + " 입니다.<br> 꾸준히 건강관리 잘 하세요.";
		}
		else if(bmi <= bVo.getHigh()) {
			result = "과체중";
			bigo += result + " 입니다.<br> 지금부터라도 체중관리 잘 하세요.";
		}
		else {
			result = "비만";
			bigo += result + " 입니다.<br> 음식보기를 돌같이 하라~~ 꾸준히 노력하세요.";
		}
		
		vo.setBmi(bmi);
		vo.setResult(result);
		vo.setBigo(bigo);
		
		ctx.close();
		//return vo;
	}
	
}
