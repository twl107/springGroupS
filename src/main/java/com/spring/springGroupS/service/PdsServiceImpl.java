package com.spring.springGroupS.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.springGroupS.common.ProjectProvide;
import com.spring.springGroupS.dao.PdsDAO;
import com.spring.springGroupS.vo.PdsVO;
import com.spring.springGroupS.vo.ReviewVO;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	PdsDAO pdsDAO;
	
	@Autowired
	ProjectProvide projectProvide;
	

	@Override
	public int getTotRecCnt(String part) {
		return pdsDAO.getTotRecCnt(part);
	}

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}

	@Override
	public int setPdsInput(MultipartHttpServletRequest mFile, PdsVO vo) {
		try {
			List<MultipartFile> fileList = mFile.getFiles("file");
			String oFileNames = "";
			String sFileNames = "";
			//int fileSize = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				//String sFileName = mid + "_" + UUID.randomUUID().toString().substring(0, 4) + "_" + oFileName;
				String sFileName = projectProvide.saveFileName(oFileName);
			
				projectProvide.writeFile(file, sFileName, "pds");
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				//fileSize += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			
			//System.out.println("원본파일목록 : " + oFileNames);
			//System.out.println("저장파일목록 : " + sFileNames);
			
			// 업로드시킨 자료의 정보를 DB에 저장시켜준다.
			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdsDAO.setPdsInput(vo);
	}

	@Override
	public PdsVO getPdsContent(int idx) {
		return pdsDAO.getPdsContent(idx);
	}

	@Override
	public void setPdsDownNumCheck(int idx) {
		pdsDAO.setPdsDownNumCheck(idx);
	}

	@Override
	public int setPdsDeleteCheck(int idx, String fSName, HttpServletRequest request) {
		// 1. 서버파일시스템에 저장된 파일 삭제처리
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		//System.out.println("fSName : " + fSName);
		String[] fSNames = fSName.split("/");
		
		for(String fName : fSNames) {
			new File(realPath + fName).delete();
		}
		
		// 2. DB에 저장된 자료 삭제
		return pdsDAO.setPdsDeleteCheck(idx);
	}

	@Override
	public List<ReviewVO> getReviewList(int idx, String part) {
		return pdsDAO.getReviewList(idx, part);
	}
	
}
