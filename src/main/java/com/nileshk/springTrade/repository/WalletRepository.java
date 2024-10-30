package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet ,Long> {
    Wallet findByUserid(Long userId);
}
