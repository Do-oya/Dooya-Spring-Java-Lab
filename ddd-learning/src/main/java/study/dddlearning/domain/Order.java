package study.dddlearning.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OrderItem item;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected Order() {}

    private Order(OrderItem item) {
        this.item = item;
        this.status = OrderStatus.CREATED;
    }

    public static Order create(OrderItem item) {
        return new Order(item);
    }

    public void complete() {
        this.status = OrderStatus.COMPLETED;
    }
}
