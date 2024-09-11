package com.xandr.pep_aston.service.impl;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.Transfer;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import com.xandr.pep_aston.mapper.UserMapper;
import com.xandr.pep_aston.repository.BankAccountRepository;
import com.xandr.pep_aston.repository.TransferRepository;
import com.xandr.pep_aston.repository.UserRepository;
import com.xandr.pep_aston.service.BankAccountService;
import com.xandr.pep_aston.service.UserService;
import com.xandr.pep_aston.validation.TransferMoneyValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BankAccountMapper bankAccountMapper;
    private final BankAccountRepository bankAccountRepository;
    private final TransferMoneyValidation transferMoneyValidation;
    private final TransferRepository transferRepository;

    @Override
    public Optional<BankAccountDto> createBankAccount(UserDto userDto) {

        return Optional.ofNullable(userDto)
                .map(userMapper::userDtoToUser)
                .flatMap(user -> userService.findByNameAndPin(user.getName(), user.getPin()))
                .map(user -> BankAccount.builder()
                        .user(user)
                        .money(BigDecimal.ZERO)
                        .build())
                .map(bankAccountRepository::save)
                .map(bankAccountMapper::BankAccountToBankAccountDto);

    }

    @Override
    public Optional<List<BankAccountDto>> report() {

        List<BankAccount> listBankAccount = bankAccountRepository.findAll();
        if (listBankAccount.isEmpty()) {
            return Optional.empty();
        }

        userRepository.findAllById(listBankAccount.stream()
                .map(ba -> ba.getUser().getId())
                .toList());

        return Optional.of(listBankAccount.stream()
                .map(bankAccountMapper::BankAccountToBankAccountDto)
                .toList());

    }

    @Override
    @Transactional
    public Optional<BankAccountDto> transferMoney(TransferDto transferDto) {

        User user = transferMoneyValidation.isValidUser(transferDto);
        BankAccount bankAccountTo = transferMoneyValidation.isValidBankAccountTo(transferDto);
        BankAccount bankAccountFrom = transferMoneyValidation.isValidBankAccountFrom(transferDto);

        transferMoneyValidation.isValid(transferDto, user, bankAccountFrom);

        BigDecimal moneyForTransfer = transferDto.getMoney();
        bankAccountTo.setMoney(bankAccountTo.getMoney().add(moneyForTransfer));
        bankAccountFrom.setMoney(bankAccountFrom.getMoney().subtract(moneyForTransfer));

        return Optional.of(transferRepository.save(
                        Transfer.builder()
                                .localDateTime(LocalDateTime.now())
                                .user(user)
                                .bankAccountFrom(bankAccountFrom)
                                .money(moneyForTransfer)
                                .bankAccountTo(bankAccountTo)
                                .build()))
                .map(Transfer::getBankAccountFrom)
                .map(bankAccountMapper::BankAccountToBankAccountDto);

    }

}
