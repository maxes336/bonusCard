package com.test.bonusCard.service;

import com.test.bonusCard.model.BonusCard;
import com.test.bonusCard.model.Transaction;
import com.test.bonusCard.repository.BonusCardRepository;
import com.test.bonusCard.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BonusCardRepository bonusCardRepository;

    public TransactionService(TransactionRepository transactionRepository, BonusCardRepository bonusCardRepository) {
        this.transactionRepository = transactionRepository;
        this.bonusCardRepository = bonusCardRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByBonusCard(BonusCard bonusCard) {
        return transactionRepository.findByBonusCard(bonusCard);
    }

    public void createNewTransaction(BonusCard bonusCard) {
        Long amountOfTransaction = findAmountOfTransaction(bonusCard);
        if (amountOfTransaction != 0) {
            transactionRepository.save(new Transaction(amountOfTransaction));
        }
    }

    public Long findAmountOfTransaction(BonusCard bonusCard) {
        setBalanceOfCardAsZeroIfItIsNull(bonusCard);
        Long balanceOfThisCardInRepository = bonusCardRepository.getOne(bonusCard.getId()).getBalance();
        Long balanceOfThisCardInOperation = bonusCard.getBalance();
        return Math.abs(balanceOfThisCardInRepository-balanceOfThisCardInOperation);
    }

    public void setBalanceOfCardAsZeroIfItIsNull(BonusCard bonusCard) {
        if (bonusCard.getBalance() == null) {
            bonusCard.setBalance(0L);
        }
    }

}
