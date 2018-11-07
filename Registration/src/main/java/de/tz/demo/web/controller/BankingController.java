package de.tz.demo.web.controller;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.tz.demo.persistence.dao.TransactionRepository;
import de.tz.demo.persistence.model.MyTransaction;
import de.tz.demo.service.IUserService;

@Controller
public class BankingController {
	@Autowired
	TransactionRepository transactionrepository;
	
	@Autowired
    IUserService userService;
	
	@RequestMapping(value = "/userTransactions", method = RequestMethod.GET)
    public String getUserTransactions(final Locale locale, final Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		String[] splitted = name.split(",");
		String emailString = splitted[3];
		String email = emailString.replaceAll("email=", "").trim();
		List<MyTransaction> transactions = transactionrepository.findByUserEmail(email);
		List<String> transactionStrings = new Vector<>();
		transactions.stream().forEach(e -> transactionStrings.add(e.getTransactionString()));
		
        model.addAttribute("transactions", transactionStrings);
        return "transactions";
    }
}
