package tobyspring.hellospring;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class PaymentServiceTest {

    @Test
    void test() throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
