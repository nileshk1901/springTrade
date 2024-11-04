package com.nileshk.springTrade.repository;

import com.nileshk.springTrade.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist,Long> {
    Watchlist findByUserId(Long userId);
}
