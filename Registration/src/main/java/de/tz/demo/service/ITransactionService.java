package de.tz.demo.service;

import java.util.List;

import de.tz.demo.persistence.model.MyTransaction;
import de.tz.demo.persistence.model.User;

public interface ITransactionService {
	
	List<MyTransaction> getTransactionsForUser(User user);
}
