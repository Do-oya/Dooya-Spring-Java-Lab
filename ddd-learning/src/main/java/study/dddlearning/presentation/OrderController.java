package study.dddlearning.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.dddlearning.application.OrderService;
import study.dddlearning.domain.Order;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        Order order = orderService.createOrder(request.productName(), request.quantity());
        return OrderResponse.from(order);
    }

    @PostMapping("/{id}/complete")
    public void completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
    }

    public record OrderRequest(String productName, int quantity) {}

    public record OrderResponse(Long id, String productName, int quantity, String status) {
        public static OrderResponse from(Order order) {
            return new OrderResponse(
                    order.getId(),
                    order.getItem().getProductName(),
                    order.getItem().getQuantity(),
                    order.getStatus().name()
            );
        }
    }
}
