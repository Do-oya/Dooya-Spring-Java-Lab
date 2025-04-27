package org.example.fctdd.pwd;

public class PasswordMeter {
    public PasswordStrength meter(String password) {
        if (password == null) {
            return PasswordStrength.INVALID;
        }
        return PasswordStrength.STRONG;
    }
}
