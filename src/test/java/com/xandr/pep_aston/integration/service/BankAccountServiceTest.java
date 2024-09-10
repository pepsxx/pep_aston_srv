package com.xandr.pep_aston.integration.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.integration.IntegrationTestBase;
import com.xandr.pep_aston.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class BankAccountServiceTest extends IntegrationTestBase {

    private final BankAccountService bankAccountService;

    private static final String NAME_OK = "Nik";
    private static final String NAME_BAD = "PepBadStringForTestName";
    private static final String PIN_OK = "wfMw0K/zHByHQD8eQ0e8whr/fBeZCHI1NfKzFyNwJSU=";
    private static final String PIN_BAD = "PepBadStringForTestPin";
    private static final UserDto userDto = new UserDto();

    @Test
    @DisplayName("Фактический результат NotNull, если аргументы Null")
    void actualResultNotNullIfArgsIsNull() {

        String message = "Result should be Optional";
        assertNotNull(bankAccountService.createBankAccount(null), message);

    }

    @Test
    @DisplayName("Фактический результат пуст, если имя или пин-код неверны")
    void actualResultIsEmptyIfNameOrPinIsBad() {

        String message = "The bankAccountDto must not exist if, ";

        userDto.setPin(PIN_OK);
        userDto.setName(NAME_BAD);
        assertTrue(bankAccountService.createBankAccount(userDto).isEmpty(), message + "the pin is incorrect.");

        userDto.setPin(PIN_BAD);
        userDto.setName(NAME_OK);
        assertTrue(bankAccountService.createBankAccount(userDto).isEmpty(), message + "the name is incorrect.");

        userDto.setPin(PIN_BAD);
        userDto.setName(NAME_BAD);
        assertTrue(bankAccountService.createBankAccount(userDto).isEmpty(), message + "the name and pin is incorrect.");

    }

    @Test
    @DisplayName("bankAccountDto существует, если имя или пин-код верны")
    void bankAccountDtoPresentIfNameOrPinIsOk() {

        String message = "The bankAccountDto must exist if, the name and pin is correct.";

        userDto.setPin(PIN_OK);
        userDto.setName(NAME_OK);
        assertTrue(bankAccountService.createBankAccount(userDto).isPresent(), message);

    }

    @Test
    @DisplayName("Стартовый баланс равен нулю, если банковский счет создан")
    void moneyEqualsZeroIfBankAccountCreat() {

        userDto.setPin(PIN_OK);
        userDto.setName(NAME_OK);
        Optional<BankAccountDto> maybeActualResult = bankAccountService.createBankAccount(userDto);

        assertTrue(maybeActualResult.isPresent());
        BankAccountDto actualResult = maybeActualResult.get();

        assertEquals(0, actualResult.getMoney(), "In new bankAccount money should be equals zero.");

    }

    @Test
    @DisplayName("Фактический результат равен ожидаемому результату")
    void actualResultEqualsExpectedResult() {

        userDto.setPin(PIN_OK);
        userDto.setName(NAME_OK);
        Optional<BankAccountDto> maybeActualResult = bankAccountService.createBankAccount(userDto);

        assertTrue(maybeActualResult.isPresent());
        BankAccountDto actualResult = maybeActualResult.get();

        assertEquals(0, actualResult.getMoney(), "In new BankAccount money  should be equals zero");
        assertEquals(NAME_OK, actualResult.getName(), "In new BankAccount UserName should be equals ExpectedUserName");
        assertTrue(actualResult.getNumberAccount() > 0, "In the new BankAccount NumberAccount must exist and be positive");

    }
}