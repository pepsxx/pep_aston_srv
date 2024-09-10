package com.xandr.pep_aston.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 44)
    private String pin;

}