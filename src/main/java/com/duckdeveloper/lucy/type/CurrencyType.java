package com.duckdeveloper.lucy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {

    BRL("Real Brasileiro", Country.BRAZIL),
    USD("Dólar americano", Country.USA),
    CAD("Dólar canadense", Country.CANADA),
    CLP("Peso chileno", Country.CHILE),
    COP("Peso colômbiano", Country.COLOMBIA),
    JPY("Iene", Country.JAPAN),
    EUR("Euro", Country.GERMANY);

    private String currency;
    private Country country;

    public String formatCountryNameToMessage() {
        return String.format("%s - %s (%s)", name(), currency, country.getFlag());
    }
}
