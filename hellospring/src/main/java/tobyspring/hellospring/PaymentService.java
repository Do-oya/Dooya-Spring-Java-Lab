package tobyspring.hellospring;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static tobyspring.hellospring.Payment.createPayment;

@Service
public class PaymentService {
    private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUtil = LocalDateTime.now().plusMinutes(30);

        return createPayment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUtil);
    }
}
