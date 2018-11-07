package de.tz.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tz.demo.persistence.dao.TransactionRepository;
import de.tz.demo.persistence.model.MyTransaction;
import de.tz.demo.persistence.model.User;

@Service
@Transactional
public class TransactionService implements ITransactionService{

	@Autowired
	TransactionRepository transactionRepository;
	
	@Override
	public List<MyTransaction> getTransactionsForUser(User user) {
		
		String userEmail = user.getEmail();
		List<MyTransaction> transactions =  transactionRepository.findByUserEmail(userEmail);
		return transactions;
	}
	
}
