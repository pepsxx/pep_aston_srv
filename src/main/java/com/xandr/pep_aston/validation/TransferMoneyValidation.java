package com.xandr.pep_aston.validation;

import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.repository.BankAccountRepository;
import com.xandr.pep_aston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferMoneyValidation {

    private final UserService userService;
    private final BankAccountRepository bankAccountRepository;

    public Map<String, Object> isValid(TransferDto transferDto) {

        Map<String, Object> validMapObject = new HashMap<>();

        Optional<User> maybeUser = userService.findByNameAndPin(transferDto.getName(), transferDto.getPin());
        if (maybeUser.isEmpty()) {
            log.warn("Пароль или имя пользователя не совпадают.");
            return new HashMap<>();
        }
        validMapObject.put("user", maybeUser.get());

        Optional<BankAccount> maybeBankAccountFrom = bankAccountRepository.findById(transferDto.getNumberAccountFrom());
        if (maybeBankAccountFrom.isEmpty()) {
            log.warn("не существует счет BankAccountFrom.");
            return new HashMap<>();
        }
        validMapObject.put("bankAccountFrom", maybeBankAccountFrom.get());

        Optional<BankAccount> maybeBankAccountTo = bankAccountRepository.findById(transferDto.getNumberAccountTo());
        if (maybeBankAccountTo.isEmpty()) {
            log.warn("не существует счет BankAccountTo.");
            return new HashMap<>();
        }
        validMapObject.put("bankAccountTo", maybeBankAccountTo.get());

        User user = maybeUser.get();
        BankAccount bankAccountFrom = maybeBankAccountFrom.get();

        if (!user.equals(bankAccountFrom.getUser())) {
            log.warn("Пользователю не принадлежит счет BankAccountFrom.");
            return new HashMap<>();
        }
        if (transferDto.getMoney() > bankAccountFrom.getMoney()) {
            log.warn("Не достаточно средств на счете BankAccountFrom.");
            return new HashMap<>();
        }
        if (transferDto.getMoney() < 0) {
            log.warn("Сумма для перевода должна быть положительной");
            return new HashMap<>();
        }

        return validMapObject;

    }
}
