package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
   List<Order>  findByUserId(Long userId);
}
