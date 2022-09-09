package com.dev.stockservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.stockservice.model.StockWrapper;
import com.dev.stockservice.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockServiceController {
 
   private final StockService stockService;
   
   public StockServiceController(StockService stockService) {
      this.stockService = stockService;
   }


   @GetMapping()
     public List<StockWrapper> getStocks(@RequestParam String ticker){
         List<String> tickerList=new ArrayList<>();
         tickerList.add(ticker);

        return stockService.findStocks(tickerList);
     }
}
