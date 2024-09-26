package com.xandr.pep_aston.mapper;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankAccountMapper {

    @Mapping(source = "id", target = "numberAccount")
    @Mapping(source = "user.name", target = "name")
    BankAccountDto BankAccountToBankAccountDto(BankAccount bankAccount);

    List<BankAccountDto> BankAccountToBankAccountDtoList(List<BankAccount> bankAccounts);

}