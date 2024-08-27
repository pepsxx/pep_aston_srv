package com.xandr.pep_aston.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class HashCodeUtilTest {

    private static final String PIN = "0000";
    private static final String HASH_PIN = "mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA=";

    @Test
    @DisplayName("Исключение, если аргумент Null")
    void ExceptionIfArgIsNull() {

        String message = "Should be exception if pin is null";
        assertThrows(NullPointerException.class, () -> HashCodeUtil.getSHA256Hash(null), message);

    }

    @Test
    @DisplayName("Хэш нип-кода Не равен нип-коду")
    void hashPinNotEqualsPin() {

        String message = "PIN should not be equal to HashPin";
        assertNotEquals(PIN, HashCodeUtil.getSHA256Hash(PIN), message);

    }

    @Test
    @DisplayName("Фактический результат равен ожидаемому результату")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void actualResultEqualsExpectedResult() {

        String message = "HashPin should be equal to ExpectedHashPin";
        assertEquals(HASH_PIN, HashCodeUtil.getSHA256Hash(PIN), message);

    }
}