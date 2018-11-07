package de.tz.demo.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.tz.demo.persistence.dao.PrivilegeRepository;
import de.tz.demo.persistence.dao.RoleRepository;
import de.tz.demo.persistence.dao.TransactionRepository;
import de.tz.demo.persistence.dao.UserRepository;
import de.tz.demo.persistence.model.MyTransaction;
import de.tz.demo.persistence.model.Privilege;
import de.tz.demo.persistence.model.Role;
import de.tz.demo.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TransactionRepository transactionRepository;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        createUserIfNotFound("test@test.com", "Test", "Test", "test", new ArrayList<Role>(Arrays.asList(adminRole)));
        createUserIfNotFound("2fa@2fa.com","2fa", "2fa", "2fa",  new ArrayList<Role>(Arrays.asList(adminRole)));
        createUserIfNotFound("user@user.com","user", "user", "user",  new ArrayList<Role>(Arrays.asList(userRole)));
        createUserIfNotFound("rem@rem.com","rem", "rem", "rem",  new ArrayList<Role>(Arrays.asList(userRole)));
        
        /**
        createTransactionIfNotFound("test@test.com", "100  Euro abgebucht");
        createTransactionIfNotFound("test@test.com", "75   Euro eingezahlt");
        createTransactionIfNotFound("test@test.com", "23   Euro abgebucht");
        createTransactionIfNotFound("test@test.com", "1.20 Euro abgebucht");
        createTransactionIfNotFound("user@user.com", "300  Euro eingezahlt");
        */
        alreadySetup = true;
    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    private final User createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, final Collection<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }
    
    private final MyTransaction createTransactionIfNotFound(String userEmail, String transactionString) {
    	List<MyTransaction> transactions = transactionRepository.findByUserEmail(userEmail); 
    	MyTransaction transaction = new MyTransaction();
    	if(transactions.size() == 0) {
    		transaction.setTransactionString(transactionString);
    		transaction.setUserEmail(userEmail);
    	}
    	transaction = transactionRepository.save(transaction);
    	return transaction;
    }

}