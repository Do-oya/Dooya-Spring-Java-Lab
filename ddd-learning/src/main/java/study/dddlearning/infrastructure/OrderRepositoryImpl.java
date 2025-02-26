package study.dddlearning.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.dddlearning.domain.Order;
import study.dddlearning.domain.OrderRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public Order save(Order order) {
        return jpaOrderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaOrderRepository.findById(id);
    }
}
