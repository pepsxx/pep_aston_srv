package com.xandr.pep_aston.util;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@UtilityClass
public class HashCodeUtil {

    public String getSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            // Обработка исключения реализовать
            throw new RuntimeException(e);
        }
    }
}
