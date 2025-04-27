package org.example.fctdd.pwd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordMeterTest {
    @Test
    void nullInput() {
        PasswordMeter meter = new PasswordMeter();
        PasswordStrength result = meter.meter(null);
        assertThat(result).isEqualTo(PasswordStrength.INVALID);
    }
}
