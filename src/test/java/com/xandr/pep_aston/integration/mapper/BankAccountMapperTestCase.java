package com.xandr.pep_aston.integration.mapper;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.integration.annotation.IT;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@IT
@AllArgsConstructor
class BankAccountMapperTestCase {

    private final BankAccountMapper bankAccountMapper;

    private static final User user = new User();
    private static final BankAccount bankAccount = new BankAccount();

    @BeforeEach
    void setUp() {

        user.setName("Test");
        bankAccount.setId(123L);
        bankAccount.setUser(user);

    }

    @Test
    void actualResultNullIfArgIsNull() {

        String message = "bankAccountDto should null after mapper if bankAccount is null";
        assertNull(bankAccountMapper.BankAccountToBankAccountDto(null), message);

    }

    @Test
    void actualResultPresentIfArgIsNotNull() {

        String message = "bankAccountDto should not null after mapper if bankAccount is not null";
        assertNotNull(bankAccountMapper.BankAccountToBankAccountDto(bankAccount), message);

    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        BankAccountDto actualResult = bankAccountMapper.BankAccountToBankAccountDto(bankAccount);

        assertEquals(actualResult.getName(), user.getName(), "bankAccountDto name should equal to user name");
        assertEquals(actualResult.getNumberAccount(), bankAccount.getId(), "numberAccount should equal to bankAccount id");

    }
}