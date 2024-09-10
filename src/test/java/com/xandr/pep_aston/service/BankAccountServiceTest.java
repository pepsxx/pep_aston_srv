package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.mapper.BankAccountMapper;
import com.xandr.pep_aston.mapper.UserMapper;
import com.xandr.pep_aston.repository.BankAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private static UserService userService;
    @Mock
    private static UserMapper userMapper;
    @Mock
    private static BankAccountMapper bankAccountMapper;
    @Captor
    private static ArgumentCaptor<BankAccount> argumentCaptor;

    @InjectMocks
    private static BankAccountService bankAccountService;

    private static final Integer START_BALANCE = 0;
    private static final String PIN = "TestPin";
    private static final String NAME = "TestName";
    private static final User user = new User();
    private static final UserDto userDto = new UserDto();
    private static final BankAccount bankAccount = new BankAccount();
    private static final BankAccountDto bankAccountDto = new BankAccountDto();

    @Test
    @DisplayName("Создание банковского счета")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void createBankAccount() {

        user.setPin(PIN);
        user.setName(NAME);

        userDto.setPin(PIN);
        userDto.setName(NAME);

        bankAccount.setUser(user);
        bankAccount.setMoney(START_BALANCE);

        bankAccountDto.setName(NAME);
        bankAccountDto.setMoney(START_BALANCE);

        doReturn(user).when(userMapper).userDtoToUser(userDto);
        doReturn(bankAccount).when(bankAccountRepository).save(bankAccount);
        doReturn(Optional.of(user)).when(userService).findByNameAndPin(NAME, PIN);
        doReturn(bankAccountDto).when(bankAccountMapper).BankAccountToBankAccountDto(bankAccount);

        Optional<BankAccountDto> maybeActualResult = bankAccountService.createBankAccount(userDto);
        verify(bankAccountRepository).save(argumentCaptor.capture());

        assertEquals(START_BALANCE, argumentCaptor.getValue().getMoney(), "Star balance should match 0");

        assertTrue(maybeActualResult.isPresent());

        BankAccountDto actualResult = maybeActualResult.get();

        BankAccountDto exceptedResult = new BankAccountDto();
        exceptedResult.setMoney(START_BALANCE);
        exceptedResult.setName(NAME);

        assertEquals(exceptedResult.getName(), actualResult.getName());
        assertEquals(exceptedResult.getMoney(), actualResult.getMoney());

    }
}