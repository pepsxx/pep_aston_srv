package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    Optional<BankAccountDto> createBankAccount(UserDto userDto);

    Optional<List<BankAccountDto>> report();

    @Transactional
    Optional<BankAccountDto> transferMoney(TransferDto transferDto);
}
