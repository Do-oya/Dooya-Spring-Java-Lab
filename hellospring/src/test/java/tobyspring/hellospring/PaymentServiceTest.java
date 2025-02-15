package tobyspring.hellospring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;

public class PaymentServiceTest {

    @Test
    void test() throws IOException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment1: " + payment1);
        System.out.println("---------------------------------------------------------");

        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment2: " + payment2);
    }
}
