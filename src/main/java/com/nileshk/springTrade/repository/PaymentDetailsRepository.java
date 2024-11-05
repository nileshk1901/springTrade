package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long > {
    PaymentDetails findByUserId(Long userId);


}
