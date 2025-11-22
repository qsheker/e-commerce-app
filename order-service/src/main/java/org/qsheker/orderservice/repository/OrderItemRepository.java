package org.qsheker.orderservice.repository;

import org.qsheker.orderservice.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
