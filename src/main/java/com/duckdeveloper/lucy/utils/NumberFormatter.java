package com.duckdeveloper.lucy.utils;

import com.duckdeveloper.lucy.type.CurrencyType;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberFormatter {

    private NumberFormatter() {}

    public static String format(CurrencyType currencyType, BigDecimal value) {
        var country = currencyType.getCountry();
        var numberFormat = NumberFormat.getCurrencyInstance(country.getLocale());
        return numberFormat.format(value);
    }

    public static String format(CurrencyType currencyType, double value) {
        var country = currencyType.getCountry();
        var numberFormat = NumberFormat.getCurrencyInstance(country.getLocale());
        return numberFormat.format(value);
    }
}
