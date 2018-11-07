package de.tz.demo.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tz.demo.persistence.model.Bank;

public interface BankRepository  extends JpaRepository<Bank, Long> {
	
	Bank findByName(String name);

	@Override
	void delete(Bank bank);

}
