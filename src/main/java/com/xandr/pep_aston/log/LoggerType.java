package com.xandr.pep_aston.log;

import lombok.Getter;

@Getter
public enum LoggerType {
    BEGIN("Start"),
    END("Finish"),
    OK("Ok"),
    BAD("Bad"),
    NONE(""),
    ERR("Error"),
    RETURN("Return");

    private final String description;

    LoggerType(String description) {
        this.description = description;
    }
}
