package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.TransactionMoney;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import com.xandr.pep_aston.repository.BankAccountRepository;
import com.xandr.pep_aston.repository.TransactionMoneyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionMoneyService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionMoneyRepository transactionMoneyRepository;
    private final BankAccountMapper bankAccountMapper;

    @Transactional
    protected Optional<BankAccountDto> transactionMoney(BigDecimal moneyForTransfer, User user, BankAccount bankAccountTo, BankAccount bankAccountFrom) {

        try {
            bankAccountTo.setMoney(bankAccountTo.getMoney().add(moneyForTransfer));
            bankAccountFrom.setMoney(bankAccountFrom.getMoney().subtract(moneyForTransfer));
            bankAccountRepository.save(bankAccountTo);
            bankAccountRepository.save(bankAccountFrom);
            transactionMoneyRepository.save(
                    TransactionMoney.builder()
                            .localDateTime(LocalDateTime.now())
                            .user(user)
                            .bankAccountFrom(bankAccountFrom)
                            .money(moneyForTransfer)
                            .bankAccountTo(bankAccountTo)
                            .build()
            );
            return Optional.of(bankAccountFrom)
                    .map(bankAccountMapper::BankAccountToBankAccountDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
