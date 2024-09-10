package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import com.xandr.pep_aston.mapper.UserMapper;
import com.xandr.pep_aston.repository.BankAccountRepository;
import com.xandr.pep_aston.repository.UserRepository;
import com.xandr.pep_aston.validation.TransferMoneyValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final UserMapper userMapper;
    private final UserService userService;
    private final BankAccountMapper bankAccountMapper;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransferMoneyValidation transferMoneyValidation;
    private final TransactionMoneyService transactionMoneyService;

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

    @Transactional
    public Optional<BankAccountDto> transferMoney(TransferDto transferDto) {

        Map<String, Object> validMapObject = transferMoneyValidation.isValid(transferDto);
        if (validMapObject.isEmpty()) {
            return Optional.empty();
        }

        return transactionMoneyService.transactionMoney(
                transferDto.getMoney(),
                (User) validMapObject.get("user"),
                (BankAccount) validMapObject.get("bankAccountTo"),
                (BankAccount) validMapObject.get("bankAccountFrom"));

    }

}
