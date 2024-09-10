package com.xandr.pep_aston.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoggerType {
    BEGIN("Start"),
    END("Finish"),
    OK("Ok"),
    BAD("Bad"),
    NONE(""),
    ERR("Error"),
    RETURN("Return");

    private final String description;

}
