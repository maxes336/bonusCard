package com.test.bonusCard.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue//(generator = "UUID")
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(updatable = false, nullable = false)
    private Long amount;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp timestampOfTransaction;

    /*@Column(name = "card_id", updatable = false, nullable = false)
    private Long cardId;*/

    @ManyToOne(targetEntity = BonusCard.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bonusCard_id")
    private BonusCard bonusCard;

    public Transaction(Long amountOfTransaction) {
        this.amount = amountOfTransaction;
    }

    /*public Transaction(Long amount, BonusCard bonusCard) {
        this.amount = amount;
        this.bonusCard = bonusCard;
    }*/
}
