package com.duckdeveloper.lucy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = "coinmarket-cap")
public interface StockExchangeV2Service {

}
