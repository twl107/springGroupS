package com.spring.springGroupS.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private String content;
	private String hostIp;
	private String openSw;
	private int readNum;
	private String wDate;
	private int good;
	private String complaint;
	
	private int hour_diff;	// new.gif를 출력하기위한 변수(24시간 내에)
	private int date_diff;	// 글쓴날짜를 오늘이후는 '날짜표시', 오늘 이전은 '시간/날짜-시간' 표시 하기위한 변후
	private int replyCnt;		// 댓글의 갯수를 저장하기위한 변수
}
