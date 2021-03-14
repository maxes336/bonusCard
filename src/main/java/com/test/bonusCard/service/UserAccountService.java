package com.test.bonusCard.service;

import com.test.bonusCard.model.Role;
import com.test.bonusCard.model.UserAccount;
import com.test.bonusCard.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserAccountService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount save(UserAccount userAccount) {
        checkUsername(userAccount.getUsername());
        userAccount.setActive(Boolean.TRUE);

        String encPassword = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encPassword);
        userAccount.setRole(Collections.singleton(Role.USER));

        return userAccountRepository.save(userAccount);
    }

    private void checkUsername(String username) {
        UserAccount userAccountFromDb = userAccountRepository.findByUsername(username);
        if (userAccountFromDb != null) {
            throw new RuntimeException("User with username " + username + " already exist");
        }
    }
}
