package com.duckdeveloper.lucy.model.stockexchange;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HistoricalDataPrice {

    private Long date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
    private Double adjustedClose;
}
