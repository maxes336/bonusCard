package com.test.bonusCard.service;

import com.test.bonusCard.model.BonusCard;
import com.test.bonusCard.model.Role;
import com.test.bonusCard.model.UserAccount;
import com.test.bonusCard.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount findById(Long id) {
        return userAccountRepository.getOne(id);
    }

    public void deleteById(Long id) {
        userAccountRepository.deleteById(id);
    }

    public List<UserAccount> findAll() {
        List<UserAccount> allUserAccountsFromRepository = userAccountRepository.findAll();
        sortListOfUserAccountsById(allUserAccountsFromRepository);
        return allUserAccountsFromRepository;
    }

    private void sortListOfUserAccountsById(List<UserAccount> userAccounts) {
        userAccounts.sort(Comparator.comparingLong(UserAccount::getId));
    }

    public void saveNewAccount(UserAccount userAccount) {
        checkUsername(userAccount.getUsername());
        userAccount.setIsActive(Boolean.FALSE);
        String encPassword = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encPassword);
        userAccount.setRole(Role.USER);
        userAccountRepository.save(userAccount);
    }

    private void checkUsername(String username) {
        final Optional<UserAccount> userAccountFromDb = userAccountRepository.findByUsername(username);
        if(userAccountFromDb.isPresent()) {
            throw new RuntimeException("User with username " + username + " already exist");
        }
    }

    public void updateUserAccount(UserAccount userAccount) {
        String origPassword = userAccountRepository.getOne(userAccount.getId()).getPassword();
        if (userAccount.getPassword().equals("") || userAccount.getPassword().equals(origPassword)) {
            userAccount.setPassword(origPassword);
        } else {
            String encPassword = passwordEncoder.encode(userAccount.getPassword());
            userAccount.setPassword(encPassword);
        }
        userAccountRepository.save(userAccount);
    }

    public UserAccount findLoggedInUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount loggedInUserAccount = userAccountRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("No logged in user found!"));
        return loggedInUserAccount;
    }
}
