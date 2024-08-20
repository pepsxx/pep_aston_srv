package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
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

    public Optional<BankAccountDto> createBankAccount(UserDto userDto) {

        Optional<User> maybeUser = UserMapper.mapToUser(userDto);
        if (maybeUser.isEmpty()) {
            return Optional.empty();
        }
        User user = maybeUser.get();

        maybeUser = userService.findByNameAndPin(user.getName(), user.getPin());
        if (maybeUser.isEmpty()) {
            return Optional.empty();
        }
        user = maybeUser.get();

        BankAccount newBankAccount = BankAccount.builder()
                .user(user)
                .money(0)
                .build();
        BankAccount bankAccount = bankAccountRepository.save(newBankAccount);

        return BankAccountMapper.mapToDto(bankAccount);
    }
}
