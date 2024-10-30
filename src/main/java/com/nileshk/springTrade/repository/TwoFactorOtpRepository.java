package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.TwoFactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOtp,String> {
    TwoFactorOtp findByUserId(Long userId);
}
