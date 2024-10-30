package com.nileshk.springTrade.service;

import com.nileshk.springTrade.domain.VerificationType;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);

}
