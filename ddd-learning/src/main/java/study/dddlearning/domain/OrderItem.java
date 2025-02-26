package study.dddlearning.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class OrderItem {
    private String productName;
    private int quantity;

    protected OrderItem() {}

    public OrderItem(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }
}
