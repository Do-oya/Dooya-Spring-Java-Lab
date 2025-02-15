package tobyspring.hellospring.payment.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ExRateProviderStub implements ExRateProvider {
    private BigDecimal exRate;

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        return exRate;
    }
}
