package com.duckdeveloper.lucy.model.stockexchange;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CashDividends {

    private String assetIssued;
    private String paymentDate;
    private Double rate;
    private String relatedTo;
    private String approvedOn;
    private String isinCode;
    private String label;
    private String lastDatePrior;
    private String remarks;

}