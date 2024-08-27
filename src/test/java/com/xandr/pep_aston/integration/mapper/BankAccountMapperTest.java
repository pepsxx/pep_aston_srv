package com.xandr.pep_aston.integration.mapper;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.integration.IntegrationTestBase;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class BankAccountMapperTest extends IntegrationTestBase {

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
    @DisplayName("Фактический результат Null, если аргумент Null")
    void actualResultNullIfArgIsNull() {

        String message = "bankAccountDto should null after mapper if bankAccount is null";
        assertNull(bankAccountMapper.BankAccountToBankAccountDto(null), message);

    }

    @Test
    @DisplayName("Фактический результат присутствует, если аргумент не NotNull")
    void actualResultPresentIfArgIsNotNull() {

        String message = "bankAccountDto should not null after mapper if bankAccount is not null";
        assertNotNull(bankAccountMapper.BankAccountToBankAccountDto(bankAccount), message);

    }

    @Test
    @DisplayName("Фактический результат равен ожидаемому результату")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        BankAccountDto actualResult = bankAccountMapper.BankAccountToBankAccountDto(bankAccount);

        assertEquals(actualResult.getName(), user.getName(), "bankAccountDto name should equal to user name");
        assertEquals(actualResult.getNumberAccount(), bankAccount.getId(), "numberAccount should equal to bankAccount id");

    }
}