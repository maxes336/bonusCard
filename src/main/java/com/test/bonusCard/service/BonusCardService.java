package com.test.bonusCard.service;

import com.test.bonusCard.model.BonusCard;
import com.test.bonusCard.repository.BonusCardRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class BonusCardService {

    private final BonusCardRepository bonusCardRepository;

    public BonusCardService(BonusCardRepository bonusCardRepository) {
        this.bonusCardRepository = bonusCardRepository;
    }

    public List<BonusCard> findAll() {
        return bonusCardRepository.findAll();
    }

    public List<BonusCard> findByValidityPeriod(int validityPeriod) {
        if (validityPeriod != 0) {
            return bonusCardRepository.findByValidityPeriod(validityPeriod);
        } else {
            return bonusCardRepository.findAll();
        }
    }

    public void saveAllNewBonusCards(BonusCard bonusCard, Integer numberOfCardsToCreate) {
        if (numberOfCardsToCreate > 0) {
            setDateAndTimeOfCreation(bonusCard);
            setExpirationDateOfBonusCard(bonusCard);
            bonusCardRepository.save(bonusCard);
            for (int i = 1; i < numberOfCardsToCreate; i++) {
                bonusCardRepository.save(copyNewBonusCard(bonusCard));
            }
        }
    }

    public void saveOneBonusCard(BonusCard bonusCard) {
        bonusCardRepository.save(bonusCard);
    }

    public void updateBonusCard(BonusCard bonusCard) {
        BonusCard originalCard = findById(bonusCard.getId());
        bonusCard.setCardIssueDate(originalCard.getCardIssueDate());
        setExpirationDateOfBonusCard(bonusCard);
        bonusCardRepository.save(bonusCard);
    }

    public BonusCard findById(Long id) {
        return bonusCardRepository.getOne(id);
    }

    public void deleteById(Long id) {
        bonusCardRepository.deleteById(id);
    }

    public BonusCard copyNewBonusCard(BonusCard bonusCard) {
        return new BonusCard(
                bonusCard.getCardIssueDate(),
                bonusCard.getCardValidThru(),
                bonusCard.getBalance(),
                bonusCard.getStatusOfCard(),
                bonusCard.getValidityPeriod());
    }

    public void setDateAndTimeOfCreation(BonusCard bonusCard) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        bonusCard.setCardIssueDate(dateFormat.format(cal.getTime()));
    }

    public void setExpirationDateOfBonusCard(BonusCard bonusCard) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
        Date dateAndTimeOfCardCreation = null;
        try {
            dateAndTimeOfCardCreation = dateFormat.parse(bonusCard.getCardIssueDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateAndTimeOfCardCreation != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateAndTimeOfCardCreation);
            cal.add(Calendar.MONTH, bonusCard.getValidityPeriod());
            bonusCard.setCardValidThru(dateFormat.format(cal.getTime()));
        }
    }

}
