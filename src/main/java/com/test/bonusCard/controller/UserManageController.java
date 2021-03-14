package com.test.bonusCard.controller;

import com.test.bonusCard.repository.UserAccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserManageController {

    private UserAccountRepository userAccountRepository;

    @GetMapping
    public String userList(){
        return "user/userList";
    }
}
