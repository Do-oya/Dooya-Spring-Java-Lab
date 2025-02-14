package tobyspring.hellospring;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        // 환율 가져오기
        // 금액 계산
        // 유효 시간 계산
        return Payment.builder()
                .orderId(orderId)
                .currency(currency)
                .foreignCurrencyAmount(foreignCurrencyAmount)
                .exRate(BigDecimal.ZERO)
                .convertedAmount(BigDecimal.ZERO)
                .validUntil(LocalDateTime.now())
                .build();
    }
}
