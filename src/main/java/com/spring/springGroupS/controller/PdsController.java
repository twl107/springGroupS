package com.spring.springGroupS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS.common.Pagination;
import com.spring.springGroupS.service.PdsService;
import com.spring.springGroupS.vo.PageVO;
import com.spring.springGroupS.vo.PdsVO;
import com.spring.springGroupS.vo.ReviewVO;

@Controller
@RequestMapping("/pds")
public class PdsController {

	@Autowired
	PdsService pdsService;
	
	@Autowired
	Pagination pagination;
	
	
	@GetMapping("/pdsList")
	public String pdsListGet(Model model, PageVO pageVO) {
		pageVO.setSection("pds");
		pageVO = pagination.pagination(pageVO);
		
		List<PdsVO> vos = pdsService.getPdsList(pageVO.getStartIndexNo(), pageVO.getPageSize(), pageVO.getPart());
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
		return "pds/pdsList";
	}
	
	// 자료실 입력폼 보기
	@GetMapping("/pdsInput")
	public String pdsInputGet() {
		return "pds/pdsInput";
	}
	
	// 자료실 입력 처리
	@PostMapping("/pdsInput")
	public String pdsInputPost(MultipartHttpServletRequest mFile, PdsVO vo) {
		int res = pdsService.setPdsInput(mFile, vo);
		
		if(res != 0) return "redirect:/message/pdsInputOk";
		else return "redirect:/message/pdsInputNo";
	}
	
	// 자료실 내용 보기
	@GetMapping("/pdsContent")
	public String pdsContentGet(Model model, int idx, PageVO pageVO) {
		PdsVO vo = pdsService.getPdsContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pageVO", pageVO);
		
		// 등록된 리뷰도 불러와서 함께 content로 보내기
		List<ReviewVO> reviewVos = pdsService.getReviewList(idx, "pds");
		model.addAttribute("reviewVos", reviewVos);
		
		// 리뷰 별점 평균 구하기
		int reviewTot = 0;
		for(ReviewVO r : reviewVos) {
			reviewTot += r.getStar();
		}
		double reviewAvg = 0.0;
		if(reviewVos.size() != 0) reviewAvg = (double) reviewTot / reviewVos.size();
		model.addAttribute("reviewAvg", reviewAvg);
		
		return "pds/pdsContent";
	}
	
	// 다운로드수 증가처리
	@ResponseBody
	@PostMapping("/pdsDownNumCheck")
	public void pdsDownNumCheckPost(int idx) {
		pdsService.setPdsDownNumCheck(idx);
	}
	
	// 자료실 내역 삭제처리(파일삭제 + DB자료 삭제)
	@ResponseBody
	@PostMapping("/pdsDeleteCheck")
	public int pdsDeleteCheckPost(int idx, String fSName, HttpServletRequest request) {
		return pdsService.setPdsDeleteCheck(idx, fSName, request);
	}
	
	// 전체파일 다운로드
	@SuppressWarnings("deprecation")
	@GetMapping("/pdsTotalDown")
	public String pdsTotalDownGet(int idx, HttpServletRequest request) throws IOException {
		// 다운로드수 증가
		pdsService.setPdsDownNumCheck(idx);
		
		// 여러개의 파일을 하나의 파일(zip)로 압축(통합)하여 다운로드처리.(압축파일명 : 제목.zip)
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		// 저장된 파일의 정보를 찾아온다.
		PdsVO vo = pdsService.getPdsContent(idx);
		
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		String zipPath = realPath + "temp/";
		String zipName = vo.getTitle() + ".zip";
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath + zipName));
		
		byte[] bytes = new byte[2048];
		
		for(int i=0; i<fNames.length; i++) {
			fis = new FileInputStream(realPath + fSNames[i]);
			fos = new FileOutputStream(zipPath + fNames[i]);
			
			int data = 0;
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				fos.write(bytes, 0, data);
			}
			fos.flush();
			fos.close();
			fis.close();
			
			// 위쪽작업이 완료되면 pds에 있던 원본파일이 temp방에 복사되어 있다.
			// 복사해온 temp방에 존재하는 파일들을 zip파일에 담아준다.
			
			File copyFile = new File(zipPath + fNames[i]);
			fis = new FileInputStream(copyFile);
			
			zout.putNextEntry(new ZipEntry(fNames[i]));
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				zout.write(bytes, 0, data);
			}
			zout.flush();
			zout.closeEntry();
			fis.close();
		}
		zout.close();
		
		// 압축된 파일을 다운로드 시켜준다.
		return "redirect:/fileDownAction?path=pds&file="+java.net.URLEncoder.encode(zipName);
	}
	
	
}
