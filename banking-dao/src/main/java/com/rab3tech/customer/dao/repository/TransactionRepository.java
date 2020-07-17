package com.rab3tech.customer.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.dao.entity.TransactionEntity;



public interface TransactionRepository  extends JpaRepository<TransactionEntity, Integer> {


	List<TransactionEntity> findAllByDebitAccountNumber(String accountNumber);


	List<TransactionEntity> findAllByPayeeId(PayeeInfo payeeInfo);

}
