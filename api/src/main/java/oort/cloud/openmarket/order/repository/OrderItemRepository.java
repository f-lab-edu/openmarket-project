package oort.cloud.openmarket.order.repository;

import oort.cloud.openmarket.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
