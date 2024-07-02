package com.duckdeveloper.lucy.command.exchange;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.model.currency.Currency;
import com.duckdeveloper.lucy.service.StockExchangeService;
import com.duckdeveloper.lucy.type.CurrencyType;
import com.duckdeveloper.lucy.utils.NumberFormatter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@Component
//@CommandHandler(name = "moeda")
@RequiredArgsConstructor
public class CurrencyCommmand extends AbstractCommand {

    private final StockExchangeService stockExchangeService;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var currency = CurrencyType.valueOf(event.getOption("currency").getAsString().toUpperCase());
        var targetCurrency = CurrencyType.valueOf(event.getOption("target-currency").getAsString().toUpperCase());
        var value = event.getOption("value").getAsString();
        var valueFormatted = NumberFormatter.format(currency, new BigDecimal(value));

        var currencyResult = this.stockExchangeService.findCurrency(currency.name(), targetCurrency.name()).getCurrency();
        var currencyResponseOptional = findFirst(currencyResult);

        if (currencyResponseOptional.isEmpty()) {
            event.reply("Moeda não encontrada!").queue();
            return;
        }

        var currencyResponse = currencyResponseOptional.get();

        var result = NumberFormatter.format(targetCurrency, new BigDecimal(currencyResponse.getBidPrice()).multiply(new BigDecimal(value)));

        event.reply("%s convertidos de %s em %s: %s".formatted(valueFormatted, currency.formatCountryNameToMessage(), targetCurrency.formatCountryNameToMessage(), result)).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        var currencyOptionData = new OptionData(OptionType.STRING, "currency", "Moeda 1", true);
        var targetCurrencyOptionData = new OptionData(OptionType.STRING, "target-currency", "Moeda 2", true);

        Arrays.stream(CurrencyType.values())
                .forEachOrdered(currencyType -> {
                    var name = currencyType.name().toLowerCase();
                    currencyOptionData.addChoice(currencyType.getCurrency(), name);
                    targetCurrencyOptionData.addChoice(currencyType.getCurrency(), name);
                });

        return Commands.slash("moeda", "Converta moedas de diferentes países")
                .addOptions(currencyOptionData, targetCurrencyOptionData)
                .addOption(OptionType.NUMBER, "value", "Valor a ser convertido", true);
    }

    protected Optional<Currency> findFirst(List<Currency> results) {
        return results.stream()
                .filter(currency -> currency.getToCurrency() != null)
                .findFirst();
    }
}
