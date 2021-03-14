package com.test.bonusCard.controller;

import com.test.bonusCard.model.UserAccount;
import com.test.bonusCard.service.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
//@RequestMapping("/user")
public class UserRegistrationController {

    private final UserAccountService userAccountService;

    public UserRegistrationController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/registration")
    public String getRegistrationView() {
        return "user/registration";
    }

    @PostMapping("/registration")
    public ResponseEntity<UserAccount> registerUser(@ModelAttribute UserAccount userAccount) {
        UserAccount newUserAccount = userAccountService.save(userAccount);
        return new ResponseEntity<>(newUserAccount, HttpStatus.OK);
        //return "redirect:/login";
    }

}
