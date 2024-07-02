package com.duckdeveloper.lucy.factory;

import com.duckdeveloper.lucy.model.crypto.Crypto;
import com.duckdeveloper.lucy.model.stockexchange.Stock;
import com.duckdeveloper.lucy.type.CurrencyType;
import com.duckdeveloper.lucy.utils.BotUtils;
import com.duckdeveloper.lucy.utils.NumberFormatter;
import com.duckdeveloper.lucy.utils.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmbedBuilderFactory {

    private static final int maxSize = 20;

    private EmbedBuilderFactory() {
    }

    public static MessageEmbed botInfoWithGuildSizeAndUserSizeAndUser(int guildSize, int userSize, User user) {
        return new EmbedBuilder()
                .setTitle("Olá! Eu sou a Lucy <:mytime:759000043582652456>")
                .addField(":computer: Guilds", String.valueOf(guildSize), true)
                .addField(":bust_in_silhouette: Usuários", String.valueOf(userSize), true)
                .addField(":crown: Criador", "Rodrigo Carvalho", true)
                .addField(":hourglass: Uptime", BotUtils.getTime(), true)
                .setFooter(user.getName(), user.getAvatarUrl())
                .build();
    }

    public static MessageEmbed cryptoCommandWithSymbolAndCryptoAndUser(String symbol, Crypto crypto, User user) {
        return new EmbedBuilder()
                .setTitle(":bar_chart: Resultados para a criptomoeda **%s**".formatted(symbol))
                .setThumbnail(crypto.getCoinImageUrl())
                .addField(":identification_card: Nome", crypto.getCoinName(), true)
                .addField(":moneybag: Valor atual", NumberFormatter.format(CurrencyType.BRL, crypto.getRegularMarketPrice()), true)
                .setFooter(user.getName(), user.getAvatarUrl())
                .setColor(Color.decode("#e5e4b8"))
                .build();
    }

    public static MessageEmbed stockExchangeCommandWithStockAndUser(Stock stock, User user) {
        var companyName = getStockExchangeCompanyName(stock.getLongName());
        var embedColor = getStockExchangeEmbedColor(stock);

        return new EmbedBuilder()
                .setThumbnail(stock.getLogourl())
                .setTitle(":bar_chart: Resultados para bolsa de valor **%s**".formatted(stock.getSymbol()))
                .addField(":department_store: Empresa", companyName, true)
                .addField(":dollar: Moeda", stock.getCurrency(), true)
                .addField(":moneybag: Valor atual", formatValue(stock.getRegularMarketPrice()), true)
                .addField(":arrow_up_small: Valor mais alto", formatValue(stock.getRegularMarketDayHigh()), true)
                .addField(":arrow_down_small: Valor mais baixo", formatValue(stock.getRegularMarketDayLow()), true)
                .addField(":date: Data", TimeUtils.format(stock.getRegularMarketTime()), false)
                .setFooter(user.getName(), user.getAvatarUrl())
                .setColor(embedColor)
                .build();
    }

    private static String getStockExchangeCompanyName(String companyLongName) {
        var companyName = companyLongName.length() > maxSize ? companyLongName.substring(0, maxSize) : companyLongName;
        return companyName.trim();
    }

    private static Color getStockExchangeEmbedColor(Stock stock) {
        return stock.getRegularMarketChangePercent() > 0 ? Color.decode("#62c947") : Color.decode("#d42f22");
    }

    private static String formatValue(Double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.FLOOR).toString();
    }
}
