package com.spring.springGroupS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

//@Slf4j
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/","/h","/index"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		// trace --- < debug < error < warning < information
		logger.info("info : Welcome home! The client locale is {}.", locale);
//		logger.warn("warn : Welcome home! The client locale is {}.", locale);
//		logger.error("erro : Welcome home! The client locale is {}.", locale);
//		logger.debug("debu : Welcome home! The client locale is {}.", locale);
//		System.out.println("==============================================");
		
		// lombok에서 제공하는 slf4j라이브러리 사용
//		log.info("lombok의 infor");
//		log.warn("lombok의 warn");
//		log.error("lombok의 error");
//		log.debug("lombok의 debug");
//		log.trace("lombok의 trace");
//		log.info("lombok의 infor");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@PostMapping("/imageUpload")
	public void imageUploadGet(MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		String oFileName = upload.getOriginalFilename();
		
		// 확장자 제한처리(이미지파일(jpg,gif,png) + 동영상파일(mp4))
		String regExt = "(jpg|jpeg|gif|png|mp4)";
		String ext = oFileName.substring(oFileName.lastIndexOf(".")+1);
		if(!ext.matches(regExt)) {
			System.out.println("잘못된 파일 업로드중...");
//			PrintWriter out = response.getWriter();		// 사용자 메세지 처리 안됨...ㅜㅜ...
//			out.println("<script>");
//			out.println("alert('업로드할 화일형식을 확인하세요');");
//			out.println("</script>");
			return;
		}
		
		// 파일명 중복방지를 위해 날짜구분기호로 처리
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date) + "_" + oFileName;
		
		FileOutputStream fos = new FileOutputStream(new File(realPath + oFileName));
		byte[] bytes = upload.getBytes();
		fos.write(bytes);
		
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath()+"/data/ckeditor/"+oFileName;
		out.println("{\"originalFilename\":\""+oFileName+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");
		
		fos.close();
	}
	
	// data폴더아래 다운받고자 할때 수행하는 메소드(다운받을 경로와 파일명을 넘져줘서 처리한다.)
	@RequestMapping(value = "/fileDownAction", method = RequestMethod.GET)
	public void fileDownActionGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getParameter("path");
		String file = request.getParameter("file");
		
		if(path.equals("pds")) path += "/temp/";
		
		String realPathFile = request.getSession().getServletContext().getRealPath("/resources/data/" + path) + file;
		
		File downFile = new File(realPathFile);
		String downFileName = new String(file.getBytes("UTF-8"), "8859_1");
		response.setHeader("Content-Disposition", "attachment;filename=" + downFileName);
		
		FileInputStream fis = new FileInputStream(downFile);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] bytes = new byte[2048];
		int data;
		while((data = fis.read(bytes, 0, bytes.length)) != -1) {
			sos.write(bytes, 0, data);
		}
		sos.flush();
		sos.close();
		fis.close();
		
		downFile.delete();
	}
	
}
