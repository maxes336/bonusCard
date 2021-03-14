package com.test.bonusCard.repository;

import com.test.bonusCard.model.BonusCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonusCardRepository extends JpaRepository<BonusCard, Long> {

    List<BonusCard> findByValidityPeriod(int validityPeriod);
}
