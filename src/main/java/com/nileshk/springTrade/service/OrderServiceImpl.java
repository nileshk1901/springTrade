package com.nileshk.springTrade.service;

import com.nileshk.springTrade.domain.OrderStatus;
import com.nileshk.springTrade.domain.OrderType;
import com.nileshk.springTrade.model.Coin;
import com.nileshk.springTrade.model.Order;
import com.nileshk.springTrade.model.OrderItem;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WalletService walletService;
    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {

        double price=orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order=new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {

        return orderRepository.findById(orderId).orElseThrow(()->new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrderOfUser(Long userid, OrderType orderType, String assetSymbol) {

        return orderRepository.findByUserId(userid);
    }
    private OrderItem createOrderItem(Coin coin,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return o
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
