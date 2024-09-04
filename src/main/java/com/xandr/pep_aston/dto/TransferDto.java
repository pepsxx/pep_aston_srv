package com.xandr.pep_aston.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class TransferDto {

    @NotBlank
    private String name;

    @NotBlank
    private String pin;

    @NotNull
    @Positive
    private BigDecimal money;

    @NotNull
    @Positive
    private Long numberAccountFrom;

    @NotNull
    @Positive
    private Long numberAccountTo;
}
