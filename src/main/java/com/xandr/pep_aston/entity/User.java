package com.xandr.pep_aston.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@ToString(exclude = "bankAccounts")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String pin;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts = new ArrayList<>();

}