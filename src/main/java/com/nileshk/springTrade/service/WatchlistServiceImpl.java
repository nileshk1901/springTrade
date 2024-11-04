package com.nileshk.springTrade.service;

import com.nileshk.springTrade.model.Coin;
import com.nileshk.springTrade.model.User;
import com.nileshk.springTrade.model.Watchlist;
import com.nileshk.springTrade.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class WatchlistServiceImpl implements WatchlistService{



    @Autowired
    private WatchlistRepository watchlistRepository;


    @Override
    public Watchlist findUserWatchList(Long userId) throws Exception {
        Watchlist watchlist=watchlistRepository.findByUserId(userId);
        if(watchlist==null){
            throw new Exception("Watchlist not found");
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchlist(User user) {
       Watchlist watchlist=new Watchlist();
       watchlist.setUser(user);
       return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlistOptional=watchlistRepository.findById(id);
        if(watchlistOptional.isEmpty()){
            throw new Exception("watchlist not found");
        }
        return watchlistOptional.get();
    }

    @Override
    public Coin addItemToWatchList(Coin coin, User user) throws Exception {
        Watchlist watchlist=findUserWatchList(user.getId());
        if(watchlist.getCoins().contains(coin)){
            watchlist.getCoins().remove(coin);
        }else {
            watchlist.getCoins().add(coin);
        }
        watchlistRepository.save(watchlist);
        return coin;
    }
}
