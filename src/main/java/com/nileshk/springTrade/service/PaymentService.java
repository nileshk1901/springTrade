package com.nileshk.springTrade.service;

import com.nileshk.springTrade.domain.PaymentMethod;
import com.nileshk.springTrade.model.PaymentOrder;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user,Long amount) throws RazorpayException;
    PaymentResponse createStripePaymentLink(User user, Long amount,Long orderId) throws StripeException;
}
