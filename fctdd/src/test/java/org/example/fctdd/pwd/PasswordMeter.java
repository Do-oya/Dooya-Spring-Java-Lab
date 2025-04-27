package org.example.fctdd.pwd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMeter {
    public PasswordStrength meter(String password) {
        if (password == null) {
            return PasswordStrength.INVALID;
        }
        if (password.isEmpty()) {
            return PasswordStrength.INVALID;
        }
        if (password.length() < 8) {
            return PasswordStrength.NORMAL;
        }
        boolean containsDigit = containsDigit(password);
        if (!containsDigit) {
            return PasswordStrength.NORMAL;
        }
        return PasswordStrength.STRONG;
    }

    private static boolean containsDigit(String password) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
