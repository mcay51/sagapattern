package tr.com.mcay.orderservice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.mcay.orderservice.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
