package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import com.xandr.pep_aston.mapper.UserMapper;
import com.xandr.pep_aston.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Optional<BankAccountDto> transferMoney(TransferDto transferDto) {

        // Проверка авторизации
        Optional<User> maybeUser = userService.findByNameAndPin(transferDto.getName(), transferDto.getPin());
        if (maybeUser.isEmpty()) {
            log.warn("Пароль или имя пользователя не совпадают.");
            return Optional.empty();
        }

        // Проверка существует ли BankAccountFrom
        Optional<BankAccount> maybeBankAccountFrom = bankAccountRepository.findById(transferDto.getNumberAccountFrom());
        if (maybeBankAccountFrom.isEmpty()) {
            log.warn("не существует счет BankAccountFrom.");
            return Optional.empty();
        }

        // Проверка существует ли BankAccountTo
        Optional<BankAccount> maybeBankAccountTo = bankAccountRepository.findById(transferDto.getNumberAccountTo());
        if (maybeBankAccountTo.isEmpty()) {
            log.warn("не существует счет BankAccountTo.");
            return Optional.empty();
        }

        // Проверка принадлежит ли bankAccount to user
        User user = maybeUser.get();
        BankAccount bankAccountFrom = maybeBankAccountFrom.get();
        if (!user.equals(bankAccountFrom.getUser())) {
            log.warn("Пользователю не принадлежит счет BankAccountFrom.");
            return Optional.empty();
        }

        // Проверка достаточности средств для перевода
        if (bankAccountFrom.getMoney() < transferDto.getMoney()) {
            log.warn("Не достаточно средств на счете BankAccountFrom.");
            return Optional.empty();
        }

        // Перевод средств
        BankAccount bankAccountTo = maybeBankAccountTo.get();
        this.transfer(transferDto, bankAccountTo, bankAccountFrom);

        return Optional.of(bankAccountFrom)
                .map(bankAccountMapper::BankAccountToBankAccountDto);

    }

    @Transactional
    protected void transfer(TransferDto transferDto, BankAccount bankAccountTo, BankAccount bankAccountFrom) {
        try {
            bankAccountTo.setMoney(bankAccountTo.getMoney() + transferDto.getMoney());
            bankAccountFrom.setMoney(bankAccountFrom.getMoney() - transferDto.getMoney());
            bankAccountRepository.save(bankAccountTo);
            bankAccountRepository.save(bankAccountFrom);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
