package com.xandr.pep_aston.service;

import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;

    public boolean save(User user) {
        Integer maybeId = userService.findByNameAndPin(user.getName(), user.getPin());
        if (maybeId == 0) {
            return false;
        }

        user.setId(maybeId);
        BankAccount newBankAccount = BankAccount.builder()
                .user(user)
                .money(0)
                .build();
        BankAccount bankAccount = bankAccountRepository.save(newBankAccount);
        log.info("Bank account saved {}", bankAccount.getId());
        return true;

    }
}
