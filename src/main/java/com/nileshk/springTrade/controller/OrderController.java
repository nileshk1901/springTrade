package com.nileshk.springTrade.controller;

import com.nileshk.springTrade.domain.OrderType;
import com.nileshk.springTrade.model.Coin;
import com.nileshk.springTrade.model.Order;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.request.CreateOrderRequest;
import com.nileshk.springTrade.service.CoinService;
import com.nileshk.springTrade.service.OrderService;
import com.nileshk.springTrade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;


    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest req)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(req.getCoinId());

        Order order=orderService.processOrder(coin, req.getQuantity(), req.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Order order=orderService.getOrderById(orderId);
        if (order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        }else{
           throw new Exception("You don't have access");
        }
    }
    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt,
                                                           @RequestParam(required = false) OrderType order_type,
                                                           @RequestParam(required = false)String asset_symbol) throws Exception {
        Long userId=userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders=orderService.getAllOrderOfUser(userId,order_type,asset_symbol);
        return ResponseEntity.ok(userOrders);
    }

}
