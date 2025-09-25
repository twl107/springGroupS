package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.CrimeVO;
import com.spring.springGroupS.vo.TransactionVO;

public interface Study2DAO {

	List<TransactionVO> getUserList();

	int setValidatorFormOk(@Param("vo") TransactionVO vo);

	int setValidatorDeleteOk(@Param("idx") int idx);

	List<TransactionVO> getTransactionList();

	List<TransactionVO> getTransactionList2();

	void setTransactionUser1Input(@Param("vo") TransactionVO vo);

	void setTransactionUser2Input(@Param("vo") TransactionVO vo);

	void setTransactionUserTotalInput(@Param("vo") TransactionVO vo);

	void setSaveCrimeCheck(@Param("vo") CrimeVO vo);

	void setDeleteCrimeCheck(@Param("year") int year);

	List<CrimeVO> setDbListCrimeCheck(@Param("year") int year);

	List<CrimeVO> getDataApiPoliceForm(@Param("year") int year, @Param("policeZone") String policeZone);

	CrimeVO getCrimeAnalyze(@Param("year") int year, @Param("policeZone") String policeZone);

}
