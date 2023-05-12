package com.duckdeveloper.lucy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@Getter
@AllArgsConstructor
public enum Country {

    BRAZIL("Brasil", new Locale("pt", "BR"), ":flag_br:"),
    USA("Estados Unidos", new Locale("en", "US"), ":flag_us:"),
    CANADA("Canadá", new Locale("en", "CA"), ":flag_ca:"),
    COLOMBIA("Colômbia", new Locale("es", "CO"), ":flag_co:"),
    CHILE("Chile", new Locale("es", "CL"), ":flag_cl:"),
    JAPAN("Japão", new Locale("jp", "JP"), ":flag_jp:"),
    GERMANY("Alemanha", new Locale("de", "DE"), ":flag_eu:");

    private String countryName;
    private Locale locale;
    private String flag;

}
