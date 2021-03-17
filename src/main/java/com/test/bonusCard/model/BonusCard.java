package com.test.bonusCard.model;


import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@RequiredArgsConstructor
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
    @NonNull
    @Column(name = "issue_date", nullable = false, updatable = false)
    private String cardIssueDate;

    @Nullable
    @Column(name = "valid_thru")
    private String cardValidThru;

    @Column(name = "last_use")
    private Timestamp cardLastUse;

    private Long balance;

    @OneToMany(
            mappedBy = "bonusCard",
            cascade = CascadeType.REMOVE
            //orphanRemoval = true ????????
    )
    private List<Transaction> transactions;

    @NonNull
    @Column(name = "status")
    private String statusOfCard;

    @NonNull
    @Column(name = "validity_period")
    private int validityPeriod;


    public BonusCard(
            String cardIssueDate,
            String cardValidThru,
            Long balance,
            String statusOfCard,
            int validityPeriod
    ) {
        this.cardIssueDate = cardIssueDate;
        this.cardValidThru = cardValidThru;
        this.balance = balance;
        this.statusOfCard = statusOfCard;
        this.validityPeriod = validityPeriod;
    }
}
