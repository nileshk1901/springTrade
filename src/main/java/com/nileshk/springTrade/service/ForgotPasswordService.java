package com.nileshk.springTrade.service;

import com.nileshk.springTrade.domain.VerificationType;
import com.nileshk.springTrade.model.ForgotPasswordToken;
import com.nileshk.springTrade.model.User;


public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);

}
