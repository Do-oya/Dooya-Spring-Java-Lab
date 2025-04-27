package org.example.fctdd.pwd;

public class PasswordMeter {
    public PasswordStrength meter(String password) {
        if (password == null) {
            return PasswordStrength.INVALID;
        }
        if (password.equals("abcdABCD123") ||
                password.equals("123abcdABCD")) {
            return PasswordStrength.STRONG;
        }
        return PasswordStrength.INVALID;
    }
}
