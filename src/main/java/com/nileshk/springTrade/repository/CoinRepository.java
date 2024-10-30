package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin,String> {

}
