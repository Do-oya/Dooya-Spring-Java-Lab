package tobyspring.hellospring;

import org.junit.jupiter.api.Test;

public class PaymentServiceTest {

    @Test
    void test() {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare();
        System.out.println(payment);
    }
}
