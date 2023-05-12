package com.duckdeveloper.lucy.model.currency;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Currency {

    public String fromCurrency;
    public String toCurrency;
    public String name;
    public String high;
    public String low;
    public String bidVariation;
    public String percentageChange;
    public String bidPrice;
    public String askPrice;
    public String updatedAtTimestamp;
    public String updatedAtDate;

}
