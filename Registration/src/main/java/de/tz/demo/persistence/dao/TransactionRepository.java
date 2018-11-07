package de.tz.demo.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tz.demo.persistence.model.MyTransaction;


public interface TransactionRepository extends JpaRepository<MyTransaction, Long>{
	
	List<MyTransaction> findByUserEmail(String useremail);

}
