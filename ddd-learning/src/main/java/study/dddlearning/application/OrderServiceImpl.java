package study.dddlearning.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.dddlearning.domain.Order;
import study.dddlearning.domain.OrderItem;
import study.dddlearning.domain.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(String productName, int quantity) {
        Order order = Order.create(new OrderItem(productName, quantity));
        return orderRepository.save(order);
    }

    @Override
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));
        order.complete();
    }
}
