package com.xandr.pep_aston.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction_money")
public class TransactionMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "local_date_time", nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_from_id")
    private BankAccount bankAccountFrom;

    @Column(nullable = false)
    private Integer money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_to_id")
    private BankAccount bankAccountTo;
}
