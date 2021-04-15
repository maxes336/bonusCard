package com.test.bonusCard.controller;

import com.test.bonusCard.model.UserAccount;
import com.test.bonusCard.service.UserAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/user-list")
    //@PreAuthorize("hasAuthority('user:read')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String findAllUsers(Model model){
        List<UserAccount> userAccounts = userAccountService.findAll();
        model.addAttribute("users", userAccounts);
        return "user/userList";
    }

    @GetMapping("/registration")
    public String registration(){
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute UserAccount userAccount) {
        userAccountService.saveNewAccount(userAccount);
        return "redirect:/bonusCards";
    }

    /*@PostMapping("/registration")
    public ResponseEntity<UserAccount> registerUser(@ModelAttribute UserAccount userAccount) {
        UserAccount newUserAccount = userAccountService.save(userAccount);
        return new ResponseEntity<>(newUserAccount, HttpStatus.OK);
        //return "redirect:/login";
    }*/

    @GetMapping("/user-profile/{id}")
    public String UserProfile(@PathVariable("id") Long id, Model model) {
        UserAccount userAccountToUpdate = userAccountService.findById(id);
        model.addAttribute("userAccountToUpdate", userAccountToUpdate);
        return "user/user-profile";
    }

    @PostMapping("/user-profile")
    public String updateUserAccount(UserAccount userAccount) {
        userAccountService.updateUserAccount(userAccount);
        return "redirect:/user/user-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserAccount(@PathVariable("id") Long id) {
        userAccountService.deleteById(id);
        return "redirect:/user/user-list";
    }
}
