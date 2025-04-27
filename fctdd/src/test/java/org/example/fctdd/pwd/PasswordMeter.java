package org.example.fctdd.pwd;

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
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            return PasswordStrength.NORMAL;
        }
        return PasswordStrength.STRONG;
    }
}
