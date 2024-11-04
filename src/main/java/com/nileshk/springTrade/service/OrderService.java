package com.nileshk.springTrade.service;

import com.nileshk.springTrade.domain.OrderType;
import com.nileshk.springTrade.model.Coin;
import com.nileshk.springTrade.model.Order;
import com.nileshk.springTrade.model.OrderItem;
import com.nileshk.springTrade.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrderOfUser(Long userid,OrderType orderType,String assetSymbol);
    Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;

}
