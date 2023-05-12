package com.duckdeveloper.lucy.command.exchange;

import com.duckdeveloper.lucy.command.model.AbstractCommand;
import com.duckdeveloper.lucy.command.model.CommandHandler;
import com.duckdeveloper.lucy.exception.CryptoNotFoundException;
import com.duckdeveloper.lucy.model.crypto.Crypto;
import com.duckdeveloper.lucy.service.StockExchangeService;
import com.duckdeveloper.lucy.type.CurrencyType;
import com.duckdeveloper.lucy.utils.NumberFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Component
@CommandHandler(name = "crypto")
public class CryptoCommand extends AbstractCommand {

    private final StockExchangeService stockExchangeService;

    @Lazy
    public CryptoCommand(StockExchangeService stockExchangeService) {
        this.stockExchangeService = stockExchangeService;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var symbol = event.getOption("symbol").getAsString();
        var user = event.getUser();

        List<Crypto> cryptoResults;
        try {
            cryptoResults = this.stockExchangeService.findCrypto(symbol).getCoins();
        } catch (CryptoNotFoundException exception) {
            event.reply("Criptomoeda não encontrada.").queue();
            return;
        }

        var cryptoOptional = this.findFirst(cryptoResults);
        if (cryptoOptional.isEmpty()) {
            event.reply("Criptomoeda não encontrada.").queue();
            return;
        }

        var crypto = cryptoOptional.get();

        event.replyEmbeds(new EmbedBuilder()
                .setTitle(":bar_chart: Resultados para a criptomoeda **%s**".formatted(symbol))
                .setThumbnail(crypto.getCoinImageUrl())
                .addField(":identification_card: Nome", crypto.getCoinName(), true)
                .addField(":moneybag: Valor atual", NumberFormatter.format(CurrencyType.BRL, crypto.getRegularMarketPrice()), true)
                .setFooter(user.getName(), user.getAvatarUrl())
                .setColor(Color.decode("#e5e4b8"))
                .build()
        ).queue();
    }

    @Override
    public CommandData getCommandSlash() {
        return Commands.slash("crypto", "Verifique o valor de uma criptomoeda")
                .addOption(OptionType.STRING, "symbol", "símbolo da moeda.", true);
    }

    protected Optional<Crypto> findFirst(List<Crypto> results) {
        return results.stream()
                .filter(stock -> stock.getCurrency() != null && !stock.getCoinName().isBlank())
                .findFirst();
    }
}
