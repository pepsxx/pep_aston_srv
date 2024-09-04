package com.xandr.pep_aston.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountDto {

    private BigDecimal money;

    @NotBlank
    private String name;

    @Positive
    private Long numberAccount;

}