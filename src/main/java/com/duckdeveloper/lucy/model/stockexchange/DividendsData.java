package com.duckdeveloper.lucy.model.stockexchange;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DividendsData {

    private List<CashDividends> cashDividends;
}
