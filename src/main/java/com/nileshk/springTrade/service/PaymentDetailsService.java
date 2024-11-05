package com.nileshk.springTrade.service;

import com.nileshk.springTrade.model.PaymentDetails;
import com.nileshk.springTrade.model.User;

import java.awt.*;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user);
    public PaymentDetails getUserPaymentDetails(User user);
}
