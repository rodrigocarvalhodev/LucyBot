package com.duckdeveloper.lucy.model.crypto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Crypto {

    private String currency;
    private double currencyRateFromUSD;
    private String coinName;
    private String coin;
    private double regularMarketChange;
    private double regularMarketPrice;
    private double regularMarketChangePercent;
    private double regularMarketDayLow;
    private double regularMarketDayHigh;
    private String regularMarketDayRange;
    private double regularMarketVolume;
    private double marketCap;
    private long regularMarketTime;
    private String coinImageUrl;

}
