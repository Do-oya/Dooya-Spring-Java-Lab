package study.dddlearning.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import study.dddlearning.domain.Order;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {
}
