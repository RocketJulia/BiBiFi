package de.tz.demo.web.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.tz.demo.persistence.dao.TransactionRepository;
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
		// FIXME
        model.addAttribute("transactions", transactionrepository.findByUserEmail(name));
        return "transactions";
    }
}
