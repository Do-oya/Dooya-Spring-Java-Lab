package tobyspring.hellospring.exrate;

import lombok.RequiredArgsConstructor;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class CachedExRateProvider implements ExRateProvider {
    private final ExRateProvider target;

    private BigDecimal cachedExRate;

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        if (cachedExRate == null) {
            cachedExRate = target.getExRate(currency);

            System.out.println("Cache Updated");
        }
        return cachedExRate;
    }
}
