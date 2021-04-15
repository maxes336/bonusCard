package com.test.bonusCard.model;


import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@SequenceGenerator(name = "card_number_seq", initialValue = 100000)
@Table(name = "bonus_card")
public class BonusCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*@Column(name = "card_number")
    private Long cardNumber;*/

    //@CreationTimestamp
    @NonNull
    @Column(name = "issue_date", nullable = false, updatable = false)
    private String cardIssueDate;

    @Nullable
    @Column(name = "valid_thru")
    private String cardValidThru;

    @Nullable
    @Column(name = "last_use")
    private String cardLastUse;

    private Double balance;

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

    /*@Column(name = "card_creator_username")
    private String cardCreatorUsername;*/

    @ManyToOne
    @NonNull
    //@JoinColumn(name = "card_creator")
    private UserAccount cardCreator;


    public BonusCard(
            String cardIssueDate,
            String cardValidThru,
            Double balance,
            String statusOfCard,
            int validityPeriod,
            UserAccount cardCreator
    ) {
        this.cardIssueDate = cardIssueDate;
        this.cardValidThru = cardValidThru;
        this.balance = balance;
        this.statusOfCard = statusOfCard;
        this.validityPeriod = validityPeriod;
        this.cardCreator = cardCreator;
    }

    @Override
    public String toString(){
        try {
            return "BonusCard{" +
                    "id=" + id +
                    ", cardIssueDate='" + cardIssueDate + '\'' +
                    ", cardValidThru='" + cardValidThru + '\'' +
                    ", cardLastUse='" + cardLastUse + '\'' +
                    ", balance=" + balance +
                    ", transactions=" + transactions +
                    ", statusOfCard='" + statusOfCard + '\'' +
                    ", validityPeriod=" + validityPeriod +
                    ", cardCreator=" + cardCreator.getUsername() +
                    '}';
        } catch (NullPointerException npe){
            return "NULL HERE";
        }
    }

}
