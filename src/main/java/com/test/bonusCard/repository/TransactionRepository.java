package com.test.bonusCard.repository;

import com.test.bonusCard.model.BonusCard;
import com.test.bonusCard.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByBonusCard(BonusCard bonusCard);
}
