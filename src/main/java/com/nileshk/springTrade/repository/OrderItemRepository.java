package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
