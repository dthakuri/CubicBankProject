package com.rab3tech.customer.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerRepository;
import com.rab3tech.customer.dao.repository.LoginRepository;
import com.rab3tech.customer.dao.repository.PayeeInfoRepository;
import com.rab3tech.customer.dao.repository.TransactionRepository;
import com.rab3tech.customer.service.TransactionService;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.dao.entity.TransactionEntity;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.TransactionVO;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private PayeeInfoRepository payeeInfoRepository;

	@Autowired
	private CustomerAccountInfoRepository customerAccountInfoRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public String trasferCheck(TransactionVO transcationVo) {

		TransactionEntity entity = new TransactionEntity();

		Customer customer = customerRepository.findByEmail(transcationVo.getCustomerId()).get();
		Optional<CustomerAccountInfo> debitor = customerAccountInfoRepository.findByCustomerId(customer.getLogin());
		if (debitor.isPresent()) {
			transcationVo.setDebitAccountNumber(debitor.get().getAccountNumber());
			if (debitor.get().getAccountType().getId() != 1) {
				return "You don't have saving account!";
			}
			// no column to determine if the account is active or not
			float balance = debitor.get().getAvBalance();
			if (balance < transcationVo.getTransferAmount()) {
				return "Amount should not exceeds more than available balance of : " + debitor.get().getAvBalance();
			}
			debitor.get().setAvBalance((balance - transcationVo.getTransferAmount()));

		}

		PayeeInfo payeeInfo = payeeInfoRepository.findById(transcationVo.getPayeeID()).get();
		Optional<CustomerAccountInfo> creditor = customerAccountInfoRepository
				.findByAccountNumber(payeeInfo.getPayeeAccountNo());
		if (creditor.isPresent()) {
			if (creditor.get().getAccountType().getId() != 1) {
				return "Benefecery  don't have saving account!";
			}
			// no column to determine if the account is active or not
			float balance2 = creditor.get().getAvBalance();
			creditor.get().setAvBalance((balance2 + transcationVo.getTransferAmount()));
		}
		
		customerAccountInfoRepository.save(debitor.get());
		customerAccountInfoRepository.save(creditor.get());
		entity.setDebitAccountNumber(transcationVo.getDebitAccountNumber());
		entity.setDescription(transcationVo.getDescription());
		entity.setAmount(transcationVo.getTransferAmount());
		entity.setDOE(new Timestamp(new Date().getTime()));
		entity.setPayeeId(payeeInfo);
		transactionRepository.save(entity);

		return "Funds successfully transfered";
	}
}
