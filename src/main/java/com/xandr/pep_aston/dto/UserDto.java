package com.xandr.pep_aston.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    String name;
    Integer pin;
}