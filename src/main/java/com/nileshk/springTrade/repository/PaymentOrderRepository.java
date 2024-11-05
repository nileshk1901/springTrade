package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {


}
