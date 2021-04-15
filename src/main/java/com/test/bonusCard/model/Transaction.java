package com.test.bonusCard.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@RequiredArgsConstructor
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
    @NonNull
    private Double amount;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp timestampOfTransaction;

    @ManyToOne
    @JoinColumn(name = "bonusCard_id")
    @NonNull
    private BonusCard bonusCard;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", timestampOfTransaction=" + timestampOfTransaction +
                ", bonusCard=" + bonusCard.getId() +
                '}';
    }
}
