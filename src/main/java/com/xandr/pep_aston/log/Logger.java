package com.xandr.pep_aston.log;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Logger {

    private static final String PREFIX = "-=-=- : ";

    public static void setLogg(LoggerType type, String clazz, String methodOrMessage) {
        if (clazz.length() > 20) {
            clazz = clazz.substring(0, 19) + ">";
        }

        String message = "%s%-6s: %-20s: %s".formatted(PREFIX, type.getDescription(), clazz, methodOrMessage);

        switch (type){
            case BAD -> log.warn(message);
            case ERR -> log.error(message);
            default -> log.info(message);
        }

    }
}
