package com.nileshk.springTrade.model;

import com.nileshk.springTrade.domain.PaymentMethod;
import com.nileshk.springTrade.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private Long amount;
    private PaymentOrderStatus status;
    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;


}
