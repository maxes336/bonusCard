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
        return transactionRepository.findByBonusCardId(bonusCard.getId());
    }

    public void createNewTransaction(BonusCard bonusCard) {
        Double amountOfTransaction = findAmountOfTransaction(bonusCard);
        if (amountOfTransaction != 0.0) {
            transactionRepository.save(new Transaction(amountOfTransaction, bonusCard));
        }
    }

    public Double findAmountOfTransaction(BonusCard bonusCard) {
        setBalanceOfCardAsZeroIfItIsNull(bonusCard);
        Double balanceOfThisCardInRepository = bonusCardRepository.getOne(bonusCard.getId()).getBalance();
        Double balanceOfThisCardInOperation = bonusCard.getBalance();
        return -(balanceOfThisCardInRepository-balanceOfThisCardInOperation);
    }

    public void setBalanceOfCardAsZeroIfItIsNull(BonusCard bonusCard) {
        if (bonusCard.getBalance() == null) {
            bonusCard.setBalance(0.0);
        }
    }

}
