package com.spring.springGroupS.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 	- cron 사용방법(숫자) : 정해진시간수행 : '초 분 시 일 월 년' , 매번수행 : '초/숫자 ------'
*/

//@Component
public class SpringGroupScheduler {

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	ProjectProvide projectProvide;
	
	
	// 5초에 한번씩 수행하기
	@Scheduled(cron = "0/5 * * * * *")
	public void scheduleRun1() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("5초에 한번씩 수행합니다." + strToday);
	}
	
	// 매분 5초에 한번씩 수행하기
	@Scheduled(cron = "5 * * * * *")
	public void scheduleRun2() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("매분 5초에 한번씩 수행합니다." + strToday);
	}
	
	// 매일 1번씩 정해진 시간에 자동으로 메일 보내기
	@Scheduled(cron = "0 32 10 * * *")
	public void scheduleRun3() throws MessagingException {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strToday = sdf.format(today);
		System.out.println("매일밤 10시 40분에 메일을 전송합니다." + strToday);
		
		String email = "ds1202kr@gmail.com";
		String title = "신제품 안내 메일";
		String content = "겨울 신상품 안내 메일 입니다.";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		messageHelper.setTo(email);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>신상품</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>cjgreen</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		FileSystemResource file = new FileSystemResource("D:\\springGroup\\springframework\\works\\springGroupS\\src\\main\\webapp\\resources\\images\\main.jpg");
		messageHelper.addInline("main.jpg", file);
		
		mailSender.send(message);
	}
	
	
	
	
	
	
	
	
}
