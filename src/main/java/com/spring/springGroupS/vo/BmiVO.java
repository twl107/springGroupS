package com.spring.springGroupS.vo;

import lombok.Data;

@Data
public class BmiVO {
	// 사용자 데이터
	private String name;
	private int height;
	private int weight;
	
	private double bmi;
	private String result;
	private String bigo;
	
	// 비만 지수값
	private double low;			// 저체중
	private double normal;	// 정상
	private double high;		// 과체중, 이후는 비만
}
