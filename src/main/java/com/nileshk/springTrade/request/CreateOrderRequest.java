package com.nileshk.springTrade.request;

import com.nileshk.springTrade.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
