package com.nileshk.springTrade.service;

import com.nileshk.springTrade.model.Order;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet,Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet,Long amount) throws Exception;
    Wallet payOrderPayment(Order order,User user) throws Exception;

}