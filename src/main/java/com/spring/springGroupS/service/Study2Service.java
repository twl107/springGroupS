package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.CrimeVO;
import com.spring.springGroupS.vo.TransactionVO;

public interface Study2Service {

	void getCalendar();

	List<TransactionVO> getUserList();

	int setValidatorFormOk(TransactionVO vo);

	int setValidatorDeleteOk(int idx);

	List<TransactionVO> getTransactionList();

	List<TransactionVO> getTransactionList2();

	void setTransactionUser1Input(TransactionVO vo);

	void setTransactionUser2Input(TransactionVO vo);

	void setTransactionUserTotalInput(TransactionVO vo);

	void setSaveCrimeCheck(CrimeVO vo);

	void setDeleteCrimeCheck(int year);

	List<CrimeVO> setDbListCrimeCheck(int year);

	List<CrimeVO> getDataApiPoliceForm(int year, String policeZone);

	CrimeVO getCrimeAnalyze(int year, String policeZone);

}
