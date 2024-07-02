package com.duckdeveloper.lucy.service;

import com.duckdeveloper.lucy.exception.CryptoNotFoundException;
import com.duckdeveloper.lucy.exception.ExchangeNotFoundException;
import com.duckdeveloper.lucy.model.crypto.CryptoResult;
import com.duckdeveloper.lucy.model.currency.CurrencyResult;
import com.duckdeveloper.lucy.model.stockexchange.Stock;
import com.duckdeveloper.lucy.model.stockexchange.StockResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient("stock-exchange")
public interface StockExchangeService {

    @GetMapping(value = "query?function=GLOBAL_QUOTE&symbol={tickers}", produces = "application/json")
    Stock findStockExchange(@PathVariable String tickers) throws ExchangeNotFoundException;

    @GetMapping(value = "v2/currency?currency={currency}-{targetCurrency}")
    CurrencyResult findCurrency(@PathVariable String currency, @PathVariable String targetCurrency);

    @GetMapping(value = "v1/cryptocurrency/map?symbol={crypto}&convert=BRL")
    CryptoResult findCrypto(@PathVariable String crypto) throws CryptoNotFoundException;

}