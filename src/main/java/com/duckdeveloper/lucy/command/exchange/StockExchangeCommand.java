package com.duckdeveloper.lucy.command.exchange;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.exception.ExchangeNotFoundException;
import com.duckdeveloper.lucy.model.stockexchange.Stock;
import com.duckdeveloper.lucy.service.StockExchangeService;
import com.duckdeveloper.lucy.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Component
@CommandHandler(name = "bolsa")
@RequiredArgsConstructor
public class StockExchangeCommand extends AbstractCommand {

    private final StockExchangeService stockExchangeService;

    private final int maxSize = 20;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var exchangeName = event.getOption("name").getAsString();

        List<Stock> stockExchangeResults;
        try {
            stockExchangeResults = this.stockExchangeService.findStockExchange(exchangeName).getResults();
        } catch (ExchangeNotFoundException exception) {
            event.reply("Código de bolsa de valor não encontrada!").queue();
            return;
        }

        var stockOptional = this.findFirst(stockExchangeResults);

        if (stockOptional.isEmpty()) {
            event.reply("Código de bolsa de valor não encontrada!").queue();
            return;
        }

        var stock = stockOptional.get();
        var companyName = getCompanyName(stock.getLongName());
        var embedColor = stock.getRegularMarketChangePercent() > 0 ? Color.decode("#62c947") : Color.decode("#d42f22");

        event.replyEmbeds(new EmbedBuilder()
                .setThumbnail(stock.getLogourl())
                .setTitle(":bar_chart: Resultados para bolsa de valor **%s**".formatted(stock.getSymbol()))
                .addField(":department_store: Empresa", companyName, true)
                .addField(":dollar: Moeda", stock.getCurrency(), true)
                .addField(":moneybag: Valor atual", this.formatValue(stock.getRegularMarketPrice()), true)
                .addField(":arrow_up_small: Valor mais alto", this.formatValue(stock.getRegularMarketDayHigh()), true)
                .addField(":arrow_down_small: Valor mais baixo", this.formatValue(stock.getRegularMarketDayLow()), true)
                .addField(":date: Data", TimeUtils.format(stock.getRegularMarketTime()), false)
                .setFooter(user.getName(), user.getAvatarUrl())
                .setColor(embedColor)
                .build()
        ).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("bolsa", "Obter informações de bolsa de valores")
                .addOption(OptionType.STRING, "name", "Nome da bolsa de valores", true);
    }

    protected Optional<Stock> findFirst(List<Stock> results) {
        return results.stream()
                .filter(stock -> stock.getCurrency() != null && !stock.getLongName().isBlank())
                .findFirst();
    }

    protected String formatValue(Double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.FLOOR).toString();
    }

    protected String getCompanyName(String companyLongName) {
        var companyName = companyLongName.length() > maxSize ? companyLongName.substring(0, maxSize) : companyLongName;
        return companyName.trim();
    }
}