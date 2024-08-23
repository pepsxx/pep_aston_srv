package com.xandr.pep_aston.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDto {

    private Integer money;

    @NotBlank
    private String name;

    @Positive
    private Long numberAccount;

}