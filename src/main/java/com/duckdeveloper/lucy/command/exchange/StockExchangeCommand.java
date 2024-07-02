package com.duckdeveloper.lucy.command.exchange;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.exception.ExchangeNotFoundException;
import com.duckdeveloper.lucy.factory.EmbedBuilderFactory;
import com.duckdeveloper.lucy.model.stockexchange.Stock;
import com.duckdeveloper.lucy.service.StockExchangeService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//@Component
//@CommandHandler(name = "bolsa")
@RequiredArgsConstructor
public class StockExchangeCommand extends AbstractCommand {

    private final StockExchangeService stockExchangeService;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var user = event.getUser();
        var exchangeName = event.getOption("name").getAsString();

        List<Stock> stockExchangeResults = null;
        try {
//            stockExchangeResults = this.stockExchangeService.findStockExchange(exchangeName).getResults();
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

        var messageEmbed = EmbedBuilderFactory.stockExchangeCommandWithStockAndUser(stock, user);
        event.replyEmbeds(messageEmbed)
                .queue();
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
}