package study.dddlearning.application;

import org.springframework.transaction.annotation.Transactional;
import study.dddlearning.domain.Order;

@Transactional
public interface OrderService {
    Order createOrder(String productName, int quantity);
    void completeOrder(Long orderId);
}
