package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import com.xandr.pep_aston.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final BankAccountMapper bankAccountMapper;
    private final int START_BALANCE = 0;

    public Optional<BankAccountDto> createBankAccount(UserDto userDto) {

        return Optional.ofNullable(userDto)
                .map(userMapper::userDtoToUser)
                .flatMap(user -> userService.findByNameAndPin(user.getName(), user.getPin()))
                .map(user -> BankAccount.builder()
                        .user(user)
                        .money(START_BALANCE)
                        .build())
                .map(bankAccountRepository::save)
                .map(bankAccountMapper::BankAccountToBankAccountDto);
    }
}
