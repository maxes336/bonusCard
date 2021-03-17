package com.test.bonusCard.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@SequenceGenerator(name = "card_number_seq", initialValue = 1001, allocationSize = 100)
@Table(name = "test")
public class BonusCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_number_seq")
    @Column(name = "card_number")
    private Long cardNumber;*/

    //@CreationTimestamp
    @Column(name = "issue_date", nullable = false, updatable = false)
    private String cardIssueDate;

    @Column(name = "valid_thru")
    private String cardValidThru;

    @Column(name = "last_use")
    private Timestamp cardLastUse;

    private Long balance;

    @OneToMany(
            targetEntity = Transaction.class,
            mappedBy = "bonusCard"
            //cascade = CascadeType.ALL
            //orphanRemoval = true
    )
    private List<Transaction> transactions;

    @Column(name = "status")
    private String statusOfCard;

    @Column(name = "validity_period")
    private int validityPeriod;

    public BonusCard(String cardIssueDate, String cardValidThru, Long balance, String statusOfCard, int validityPeriod) {
        this.cardIssueDate = cardIssueDate;
        this.cardValidThru = cardValidThru;
        this.balance = balance;
        this.statusOfCard = statusOfCard;
        this.validityPeriod = validityPeriod;
    }


}
