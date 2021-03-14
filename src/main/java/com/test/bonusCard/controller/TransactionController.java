package com.test.bonusCard.controller;

import com.test.bonusCard.service.TransactionService;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

}
