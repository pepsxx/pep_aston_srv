package com.xandr.pep_aston.validation;

import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.exception.BankAccountDoesNotBelongUserException;
import com.xandr.pep_aston.exception.BankAccountNotCorrectMoney;
import com.xandr.pep_aston.exception.BankAccountNotFoundException;
import com.xandr.pep_aston.exception.UserNotFoundException;
import com.xandr.pep_aston.mapper.TransferMapper;
import com.xandr.pep_aston.repository.BankAccountRepository;
import com.xandr.pep_aston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferMoneyValidation {

    private final UserService userService;
    private final TransferMapper transferMapper;
    private final BankAccountRepository bankAccountRepository;

    public User isValidUser(TransferDto transferDto) {

        return Optional.of(transferMapper.TransferDtoToUser(transferDto))
                .flatMap(u -> userService.findByNameAndPin(u.getName(), u.getPin()))
                .orElseThrow(() -> new UserNotFoundException("Пароль или имя пользователя не совпадают."));

    }

    public BankAccount isValidBankAccountFrom(TransferDto transferDto) {

        return Optional.of(transferMapper.TransferDtoToBankAccountFrom(transferDto))
                .flatMap(ba -> bankAccountRepository.findById(ba.getId()))
                .orElseThrow(() -> new BankAccountNotFoundException("не существует счет BankAccountFrom."));

    }

    public BankAccount isValidBankAccountTo(TransferDto transferDto) {

        return Optional.of(transferMapper.TransferDtoToBankAccountTo(transferDto))
                .flatMap(ba -> bankAccountRepository.findById(ba.getId()))
                .orElseThrow(() -> new BankAccountNotFoundException("не существует счет BankAccountFrom."));

    }

    public void isValid(TransferDto transferDto, User user, BankAccount bankAccountFrom) {

        if (!user.equals(bankAccountFrom.getUser())) {
            throw new BankAccountDoesNotBelongUserException("Пользователю не принадлежит счет BankAccountFrom.");
        }

        if (transferDto.getMoney().compareTo(bankAccountFrom.getMoney()) > 0) {
            throw new BankAccountNotCorrectMoney("Не достаточно средств на счете BankAccountFrom.");
        }

        if (transferDto.getMoney().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BankAccountNotCorrectMoney("Сумма для перевода должна быть положительной");
        }

    }

}
