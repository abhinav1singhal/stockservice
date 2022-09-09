package com.dev.stockservice.service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.dev.stockservice.model.StockWrapper;



@Service
public class RefreshService {
    private static final ScheduledExecutorService scheduler= Executors.newScheduledThreadPool(1);
    private final Map<StockWrapper, Boolean> stocksToRefresh;
    private static final Duration refreshPeriod = Duration.ofSeconds(15);

    public RefreshService(){
        stocksToRefresh = new HashMap<>();
    }

    public boolean shouldRefresh(final StockWrapper stock){
        if( !stocksToRefresh.containsKey(stock)){
            stocksToRefresh.put(stock,false);
            return true;
        }
        return stocksToRefresh.get(stock);
    }

    public void ShouldRefreshEvery15Minute(){
        scheduler.scheduleAtFixedRate(()->
        stocksToRefresh.forEach((stock, value)->{
            if(stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshPeriod))){
                System.out.println("Setting should refresh" + stock.getStock().getSymbol());
                stocksToRefresh.remove(stock);
                stocksToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
            }
        }),0,15,TimeUnit.SECONDS);
    }
    
}
