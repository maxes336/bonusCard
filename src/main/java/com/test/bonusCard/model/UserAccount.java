package com.test.bonusCard.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@SequenceGenerator(name = "user_account_seq", allocationSize = 1)
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(generator = "user_account_seq", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    //@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    //@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "cardCreator")
    private List<BonusCard> bonusCards;
}
