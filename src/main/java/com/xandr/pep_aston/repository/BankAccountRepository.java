package com.xandr.pep_aston.repository;

import com.xandr.pep_aston.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {}