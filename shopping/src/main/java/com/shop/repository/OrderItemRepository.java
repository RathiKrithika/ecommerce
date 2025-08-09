package com.shop.repository;

import com.shop.model.OrderItem;
import com.shop.model.OrderStatus;
import jdk.jshell.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderIdAndStatus(Long orderId, OrderStatus orderStatus);
    List<OrderItem> findByOrderIdAndProductIdAndStatus(Long orderId,Long productId, OrderStatus status);
}
