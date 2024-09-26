package com.xandr.pep_aston.mapper;

import com.xandr.pep_aston.dto.TransferDto;
import com.xandr.pep_aston.entity.BankAccount;
import com.xandr.pep_aston.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransferMapper {

    @Mapping(target = "money", ignore = true)
    @Mapping(source = "numberAccountTo", target = "id")
    @Mapping(target = "user", ignore = true)
    BankAccount TransferDtoToBankAccountTo(TransferDto transferDto);

    @Mapping(target = "money", ignore = true)
    @Mapping(source = "numberAccountFrom", target = "id")
    @Mapping(target = "user", ignore = true)
    BankAccount TransferDtoToBankAccountFrom(TransferDto transferDto);

    @Mapping(target = "name")
    @Mapping(target = "pin")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bankAccounts", ignore = true)
    User TransferDtoToUser(TransferDto transferDto);

}