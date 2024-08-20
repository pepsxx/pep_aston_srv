package com.xandr.pep_aston.repository;

import com.xandr.pep_aston.entity.BankAccount;
import org.springframework.data.repository.Repository;

public interface BankAccountRepository extends Repository<BankAccount, Integer> {

    BankAccount save(BankAccount bankAccount);

//    List<BankAccount> findAll();
}