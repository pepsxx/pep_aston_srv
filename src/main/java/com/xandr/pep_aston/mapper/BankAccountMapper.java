package com.xandr.pep_aston.mapper;

import com.xandr.pep_aston.dto.BankAccountDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankAccountMapper {

    @Mapping(source = "id", target = "numberAccount")
    @Mapping(source = "user", target = "name")
    BankAccountDto BankAccountToBankAccountDto(BankAccount bankAccount);

    default String map(User value) {
        return value.getName();
    }
}
