package com.test.bonusCard.service;

import com.test.bonusCard.model.BonusCard;
import com.test.bonusCard.model.UserAccount;
import com.test.bonusCard.repository.BonusCardRepository;
import com.test.bonusCard.repository.UserAccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BonusCardService {

    private final BonusCardRepository bonusCardRepository;
    private final UserAccountService userAccountService;

    public BonusCardService(BonusCardRepository bonusCardRepository, UserAccountService userAccountService) {
        this.bonusCardRepository = bonusCardRepository;
        this.userAccountService = userAccountService;
    }

    public BonusCard findById(Long id) {
        return bonusCardRepository.getOne(id);
    }

    public void deleteById(Long id) {
        bonusCardRepository.deleteById(id);
    }

    public List<BonusCard> findAll() {
        List<BonusCard> allBonusCardsFromRepository = bonusCardRepository.findAll();
        checkAndUpdateStatusOfExpiredCardsInRepository(allBonusCardsFromRepository);
        sortListOfBonusCardsById(allBonusCardsFromRepository);
        return allBonusCardsFromRepository;
    }

    public List<BonusCard> findByValidityPeriod(int validityPeriod) {
        if (validityPeriod != -1) {
            List<BonusCard> bonusCardsFoundedByValidityPeriod = bonusCardRepository.findByValidityPeriod(validityPeriod);
            checkAndUpdateStatusOfExpiredCardsInRepository(bonusCardsFoundedByValidityPeriod);
            sortListOfBonusCardsById(bonusCardsFoundedByValidityPeriod);
            return bonusCardsFoundedByValidityPeriod;
        } else {
            return findAll();
        }
    }

    @Transactional
    public void saveAllNewBonusCards(BonusCard bonusCard, Integer numberOfCardsToCreate) {
        if (numberOfCardsToCreate > 0) {
            setDateAndTimeOfCreation(bonusCard);
            setExpirationDateOfBonusCard(bonusCard);
            setBalanceOfCardAsZeroIfItIsNull(bonusCard);
            bonusCard.setCardCreator(userAccountService.findLoggedInUserAccount());
            for (int i = 0; i < numberOfCardsToCreate; i++) {
                bonusCardRepository.save(copyNewBonusCard(bonusCard));
            }
        }
    }

    private BonusCard copyNewBonusCard(BonusCard bonusCard) {
        return new BonusCard(
                bonusCard.getCardIssueDate(),
                bonusCard.getCardValidThru(),
                bonusCard.getBalance(),
                bonusCard.getStatusOfCard(),
                bonusCard.getValidityPeriod(),
                bonusCard.getCardCreator());
    }

    public void updateBonusCard(BonusCard updatedBonusCard) {
        BonusCard originalCardToUpdate = findById(updatedBonusCard.getId());
        originalCardToUpdate.setValidityPeriod(updatedBonusCard.getValidityPeriod());
        setExpirationDateOfBonusCard(originalCardToUpdate);
        setBalanceOfCardAsZeroIfItIsNull(updatedBonusCard);
        if (!originalCardToUpdate.getBalance().equals(updatedBonusCard.getBalance())) {
            setDateAndTimeOfLastUse(originalCardToUpdate);
            originalCardToUpdate.setBalance(updatedBonusCard.getBalance());
        }
        bonusCardRepository.save(originalCardToUpdate);
    }

    private void setDateAndTimeOfCreation(BonusCard bonusCard) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        bonusCard.setCardIssueDate(dateFormat.format(cal.getTime()));
    }

    private void setDateAndTimeOfLastUse(BonusCard bonusCard) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        bonusCard.setCardLastUse(dateFormat.format(cal.getTime()));
    }

    private void setExpirationDateOfBonusCard(BonusCard bonusCard) {
        if (bonusCard.getValidityPeriod() == 0) {
            bonusCard.setCardValidThru(null);
        } else {
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

    private void setBalanceOfCardAsZeroIfItIsNull(BonusCard bonusCard) {
        if (bonusCard.getBalance() == null) {
            bonusCard.setBalance(0.0);
        }
    }

    private void sortListOfBonusCardsById(List<BonusCard> bonusCards) {
        bonusCards.sort(Comparator.comparingLong(BonusCard::getId));
    }

    private void checkAndUpdateStatusOfExpiredCardsInRepository(List<BonusCard> allBonusCardsFromRepository) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
        Date currentDateAndTime = Calendar.getInstance().getTime();

        Date dateAndTimeOfCardExpiration = null;
        for (BonusCard bonusCard : allBonusCardsFromRepository) {
            try {
                dateAndTimeOfCardExpiration = dateFormat.parse(bonusCard.getCardValidThru());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (currentDateAndTime.after(dateAndTimeOfCardExpiration)) {
                bonusCard.setStatusOfCard("expired");
                bonusCardRepository.save(bonusCard);
            }
        }
    }


}


