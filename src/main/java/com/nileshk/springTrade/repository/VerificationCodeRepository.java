package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {

    public VerificationCode findByUserid(Long userId);
}
