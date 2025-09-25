package com.spring.springGroupS.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.springGroupS.dao.Study2DAO;
import com.spring.springGroupS.vo.CrimeVO;
import com.spring.springGroupS.vo.TransactionVO;

@Service
public class Study2ServiceImpl implements Study2Service {

	@Autowired
	Study2DAO study2DAO;

	@Override
	public void getCalendar() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 오늘날짜 저장변수설정
		Calendar calToday = Calendar.getInstance();
		int toYear = calToday.get(Calendar.YEAR);
		int toMonth = calToday.get(Calendar.MONTH);
		int toDay = calToday.get(Calendar.DATE);
		
		Calendar calView = Calendar.getInstance();
		int yy = request.getParameter("yy")==null ? calView.get(Calendar.YEAR) : Integer.parseInt(request.getParameter("yy"));
		int mm = request.getParameter("mm")==null ? calView.get(Calendar.MONTH) : Integer.parseInt(request.getParameter("mm"));
		
		if(mm < 0) {
			mm = 11;
		  yy--;
		}
		if(mm > 11) {
			mm = 0;
			yy++;
		}
		calView.set(yy, mm, 1);
		
		int startWeek = calView.get(Calendar.DAY_OF_WEEK);							// 해당년월일의 요일값을 가져온다.
		int lastDay = calView.getActualMaximum(Calendar.DAY_OF_MONTH);	// 해당월의 마지막 일자를 가져온다.
		
		// 화면에 보여줄 '이전년/이전월/다음년/다음월' 변수 처리
		int prevYear = yy;
		int prevMonth = mm - 1;
		int nextYear = yy;
		int nextMonth = mm + 1;
		
		if(prevMonth == -1) {
			prevMonth = 11;
			prevYear--;
		}
		if(nextMonth == 12) {
			nextMonth = 0;
			nextYear++;
		}
		
		// 이전달력과 다음달력을 위한 변수
		Calendar calPre = Calendar.getInstance();
		calPre.set(prevYear, prevMonth, 1);
		int preLastDay = calPre.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		Calendar calNext = Calendar.getInstance();
		calNext.set(nextYear, nextMonth, 1);
		int nextStartWeek = calNext.get(Calendar.DAY_OF_WEEK);
		
		// ================================
		
		request.setAttribute("toYear", toYear);
		request.setAttribute("toMonth", toMonth);
		request.setAttribute("toDay", toDay);
		
		request.setAttribute("yy", yy);
		request.setAttribute("mm", mm);
		request.setAttribute("startWeek", startWeek);
		request.setAttribute("lastDay", lastDay);
		
		request.setAttribute("prevYear", prevYear);
		request.setAttribute("prevMonth", prevMonth);
		request.setAttribute("nextYear", nextYear);
		request.setAttribute("nextMonth", nextMonth);
		
		request.setAttribute("preLastDay", preLastDay);
		request.setAttribute("nextStartWeek", nextStartWeek);
	}

	@Override
	public List<TransactionVO> getUserList() {
		return study2DAO.getUserList();
	}

	@Override
	public int setValidatorFormOk(TransactionVO vo) {
		return study2DAO.setValidatorFormOk(vo);
	}

	@Override
	public int setValidatorDeleteOk(int idx) {
		return study2DAO.setValidatorDeleteOk(idx);
	}

	@Override
	public List<TransactionVO> getTransactionList() {
		return study2DAO.getTransactionList();
	}

	@Override
	public List<TransactionVO> getTransactionList2() {
		return study2DAO.getTransactionList2();
	}

	@Override
	public void setTransactionUser1Input(TransactionVO vo) {
		study2DAO.setTransactionUser1Input(vo);
	}

	@Override
	public void setTransactionUser2Input(TransactionVO vo) {
		study2DAO.setTransactionUser2Input(vo);
	}

	@Override
	public void setTransactionUserTotalInput(TransactionVO vo) {
		study2DAO.setTransactionUserTotalInput(vo);
	}

	@Override
	public void setSaveCrimeCheck(CrimeVO vo) {
		study2DAO.setSaveCrimeCheck(vo);
	}

	@Override
	public void setDeleteCrimeCheck(int year) {
		study2DAO.setDeleteCrimeCheck(year);
	}

	@Override
	public List<CrimeVO> setDbListCrimeCheck(int year) {
		return study2DAO.setDbListCrimeCheck(year);
	}

	@Override
	public List<CrimeVO> getDataApiPoliceForm(int year, String policeZone) {
		return study2DAO.getDataApiPoliceForm(year, policeZone);
	}

	@Override
	public CrimeVO getCrimeAnalyze(int year, String policeZone) {
		return study2DAO.getCrimeAnalyze(year, policeZone);
	}
	
}
