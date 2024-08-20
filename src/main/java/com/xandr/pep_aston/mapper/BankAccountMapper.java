package com.xandr.pep_aston.mapper;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.entity.BankAccount;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class BankAccountMapper {
    public static Optional<BankAccountDto> mapToDto(BankAccount bankAccount) {
        return Optional.ofNullable(BankAccountDto.builder()
                .money(bankAccount.getMoney())
                .name(bankAccount.getUser().getName())
                .numberAccount(bankAccount.getId())
                .build());
    }
}
