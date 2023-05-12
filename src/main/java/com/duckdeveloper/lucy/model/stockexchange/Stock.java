package com.duckdeveloper.lucy.model.stockexchange;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class Stock {

    private String symbol;
    private String shortName;
    private String longName;
    private String currency;
    private Double regularMarketPrice;
    private Double regularMarketDayHigh;
    private Double regularMarketDayLow;
    private String regularMarketDayRange;
    private Double regularMarketChange;
    private Double regularMarketChangePercent;
    private LocalDateTime regularMarketTime;
    private Long marketCap;
    private Long regularMarketVolume;
    private Double regularMarketPreviousClose;
    private Double regularMarketOpen;
    private Long averageDailyVolume10Day;
    private Long averageDailyVolume3Month;
    private Double fiftyTwoWeekLowChange;
    private String fiftyTwoWeekRange;
    private Double fiftyTwoWeekHighChange;
    private Double fiftyTwoWeekHighChangePercent;
    private Double fiftyTwoWeekLow;
    private Double fiftyTwoWeekHigh;
    private Double twoHundredDayAverage;
    private Double twoHundredDayAverageChange;
    private Double twoHundredDayAverageChangePercent;
    private List<String> validRanges;
    private List<HistoricalDataPrice> historicalDataPrice;
    private Double priceEarnings;
    private Double earningsPerShare;
    private String logourl;
    private DividendsData dividendsData;

}
