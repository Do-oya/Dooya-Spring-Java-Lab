package tobyspring.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static tobyspring.hellospring.Payment.createPayment;

abstract public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUtil = LocalDateTime.now().plusMinutes(30);

        return createPayment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUtil);
    }

    abstract BigDecimal getExRate(String currency) throws IOException;
}
