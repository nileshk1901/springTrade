package com.nileshk.springTrade.service;

import com.nileshk.springTrade.model.Coin;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.model.Watchlist;

public interface WatchlistService {
    Watchlist findUserWatchList(Long userId) throws Exception;
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id) throws Exception;
    Coin addItemToWatchList(Coin coin,User user) throws Exception;
}
