package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Сервис по работе с банковскими счетами
 */
public interface BankAccountService {

    /**
     * Создаёт новый банковский счет для пользователя
     * @param userDto (name, pin)
     * @return Optional.of(BankAccountDto) если удалось создать банковский счет, в противном случае Optional.empty().
     */
    Optional<BankAccountDto> createBankAccount(UserDto userDto);

    /**
     * Формирует отчет по банковским счетам
     * @return Optional.of(List.of(BankAccountDto)) если они существуют, в противном случае Optional.empty().
     */
    Optional<List<BankAccountDto>> report();

    /**
     * Производит трансфер денег с одного счета на другой
     * @param transferDto (name, pin, money, numberAccountFrom, numberAccountTo)
     * @return Optional.of(BankAccountDto) с которого перевелись денежные средства при удачной транзакции, в противном случае Optional.empty().
     */
    @Transactional
    Optional<BankAccountDto> transferMoney(TransferDto transferDto);
}
