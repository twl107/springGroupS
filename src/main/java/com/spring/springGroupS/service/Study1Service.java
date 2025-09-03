package com.spring.springGroupS.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
@Service
public class Study1Service {

	public void getAopServiceTest1() {
		log.info("getAopServiceTest1 메소드입니다.\n");
	}
	
	public int getAopServiceTest2() {
		log.info("getAopServiceTest2 메소드입니다.\n");
		
		int tot = 0;
		for(int i=1; i<=100; i++) {
			tot += i;
		}
		log.info("getAopServiceTest2 메소드(for문)1~100 : "+tot+"\n");
		return tot;
	}

	public void getAopServiceTest3() {
		log.info("getAopServiceTest3 메소드입니다.\n");
		
		int i = 0, tot = 0;
		while(i < 100) {
			i++;
			tot += i;
		}
		log.info("getAopServiceTest3 메소드(while문)1~100 : "+tot+"\n");
	}
	
	public void getAopServiceTest52() {
		log.info("getAopServiceTest52 메소드입니다.\n");
		
		int tot = 0;
		for(int i=1; i<=100; i++) {
			tot += i;
		}
		log.info("getAopServiceTest52 메소드(for문)1~100 : "+tot+"\n");
		//return tot;
	}
	
	public void getAopServiceTest53() {
		log.info("getAopServiceTest53 메소드입니다.\n");
		
		int i = 0, tot = 0;
		while(i < 100) {
			i++;
			tot += i;
		}
		log.info("getAopServiceTest53 메소드(while문)1~100 : "+tot+"\n");
	}
	
}
