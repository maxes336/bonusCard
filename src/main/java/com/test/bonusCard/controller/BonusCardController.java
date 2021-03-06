package com.test.bonusCard.controller;

import com.test.bonusCard.model.BonusCard;
import com.test.bonusCard.model.Transaction;
import com.test.bonusCard.model.UserAccount;
import com.test.bonusCard.service.BonusCardService;
import com.test.bonusCard.service.TransactionService;
import com.test.bonusCard.service.UserAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bonusCards")
public class BonusCardController {

    private final BonusCardService bonusCardService;
    private final TransactionService transactionService;
    private final UserAccountService userAccountService;

    public BonusCardController(BonusCardService bonusCardService, TransactionService transactionService, UserAccountService userAccountService) {
        this.bonusCardService = bonusCardService;
        this.transactionService = transactionService;
        this.userAccountService = userAccountService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    //@PreAuthorize("hasAuthority('own_cards:read')")
    public String findAll(Model model) {
        List<BonusCard> bonusCards = bonusCardService.findAll();
        model.addAttribute("bonusCards", bonusCards);
        String usernameOfLoggedInUser = userAccountService.findLoggedInUserAccount().getUsername();
        model.addAttribute("username", usernameOfLoggedInUser);
        return "bonusCard/bonusCard-list";
    }

    @GetMapping("/filter")
    public String findAllAtFilterPage(Model model) {
        List<BonusCard> bonusCards = bonusCardService.findAll();
        model.addAttribute("bonusCards", bonusCards);
        return "bonusCard/bonusCard-list";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam int filter, Map<String, Object> model) {
        List<BonusCard> bonusCards = bonusCardService.findByValidityPeriod(filter);
        model.put("bonusCards", bonusCards);
        return "bonusCard/bonusCard-list";
    }

    @GetMapping("/create")
    public String createBonusCardForm(BonusCard bonusCard) {
        return "bonusCard/bonusCard-create";
    }

    @PostMapping("/create")
    public String createBonusCard(
            @ModelAttribute BonusCard bonusCard,
            @RequestParam(value = "numberOfCardsToCreate", required = false, defaultValue = "1") Integer numberOfCardsToCreate
    ) {
        bonusCardService.saveAllNewBonusCards(bonusCard, numberOfCardsToCreate);
        return "redirect:/bonusCards";
    }

    @GetMapping("/card-profile/{id}")
    public String bonusCardProfile(@PathVariable Long id, Model model) {
        BonusCard bonusCardToUpdate = bonusCardService.findById(id);
        model.addAttribute("bonusCardToUpdate", bonusCardToUpdate);
        return "bonusCard/bonusCard-profile";
    }

    @PostMapping("/card-profile/{id}")
    public String updateBonusCard(@ModelAttribute BonusCard bonusCard) {
        transactionService.createNewTransaction(bonusCard);
        bonusCardService.updateBonusCard(bonusCard);
        return "redirect:/bonusCards";
    }

    @GetMapping("/delete/{id}")
    public String deleteBonusCard(@PathVariable("id") Long id) {
        bonusCardService.deleteById(id);
        return "redirect:/bonusCards";
    }
}
