package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.mapper.BankAccountMapperImpl;
import com.xandr.pep_aston.mapper.UserMapperImpl;
import com.xandr.pep_aston.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;
    private final UserMapperImpl userMapperImpl;
    private final BankAccountMapperImpl bankAccountMapperImpl;

    public Optional<BankAccountDto> createBankAccount(UserDto userDto) {

        return Optional.of(userDto)
                .map(userMapperImpl::userDtoToUser)
                .flatMap(u -> userService.findByNameAndPin(u.getName(), u.getPin()))
                .map(user -> BankAccount.builder()
                        .user(user)
                        .money(0)
                        .build())
                .map(bankAccountRepository::save)
                .map(bankAccountMapperImpl::BankAccountToBankAccountDto);
    }
}
