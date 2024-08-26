package com.xandr.pep_aston.integration.repository;

import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.integration.annotation.IT;
import com.xandr.pep_aston.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@IT
@AllArgsConstructor
class BankAccountRepositoryTestCase {

    private final BankAccountRepository bankAccountRepository;

    private static final User user = new User();
    private static final BankAccount bankAccount = new BankAccount();

    @BeforeEach
    void setUpEach() {

        user.setId(1L);
        bankAccount.setUser(user);
        bankAccount.setMoney(0);

    }

    @Test
    void ThrowsIfBankAccountIsNull() {

        String message = "Should be exception if bankAccount is null";
        assertThrows(Throwable.class, () -> bankAccountRepository.save(null), message);

    }

    @Test
    void ThrowsIfUserIsNull() {

        bankAccount.setUser(null);

        String message = "Should be exception if user is null";
        assertThrows(Throwable.class, () -> bankAccountRepository.save(bankAccount), message);

    }

    @Test
    void ThrowsIfMoneyIsNull() {

        bankAccount.setMoney(null);

        String message = "Should be exception if Money is null";
        assertThrows(Throwable.class, () -> bankAccountRepository.save(bankAccount), message);

    }

    @Test
    void actualResultPresentIfArgIsNotNull() {

        String message = "BankAccount should not null after if critical arguments is not null";
        assertNotNull(bankAccountRepository.save(bankAccount), message);

    }

    @Test
    @Transactional
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        BankAccount actualResult = bankAccountRepository.save(bankAccount);

        assertEquals(actualResult.getMoney(), 0, "Money should equal to 0");
        assertEquals(actualResult.getUser(), user, "BankAccount user should equal to same user");

    }
}