package com.xandr.pep_aston.repository;

import com.xandr.pep_aston.entity.BankAccount;
import org.springframework.data.repository.Repository;

public interface BankAccountRepository extends Repository<BankAccount, Long> {

    BankAccount save(BankAccount bankAccount);

}