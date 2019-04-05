package net.rodrigocarvalho.lucy;

import lombok.var;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.rodrigocarvalho.lucy.command.Command;
import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.event.EventAdapter;
import net.rodrigocarvalho.lucy.mysql.MySQL;
import net.rodrigocarvalho.lucy.task.PresenceTask;
import net.rodrigocarvalho.lucy.utils.BotUtils;
import net.rodrigocarvalho.lucy.utils.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.util.Timer;

public class Lucy {

    private static JDA jda;
    private final String CONFIG_NAME;
    private static final Logger LOGGER = LoggerFactory.getLogger("Main");
    private static Command command;

    private MySQL mySQL;

    public Lucy(String configName) {
        CONFIG_NAME = configName;
        init();
    }

    private void init() {
        print("Starting bot...");
        FileUtils.create(CONFIG_NAME);
        var parser = new JSONParser();
        try (var reader = new FileReader(CONFIG_NAME)){
            var json = (JSONObject) parser.parse(reader);
            var token = (String) json.get("token");
            startBot(token);

            var mysqlJson = (JSONObject) json.get("mysql");
            startMySQL(mysqlJson);
            startTasks();
            BotUtils.setStartTime(System.currentTimeMillis());
            BotUtils.registerLocalCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBot(String token) {
        command = new Command();
        try {
            jda = new JDABuilder(token).setAutoReconnect(true).build();
            jda.awaitReady();
            AbstractCommand.setJda(jda);
            jda.addEventListener(command);
            jda.addEventListener(new EventAdapter());
        } catch (Exception e) {
            print("Não foi possível iniciar o bot: " + e.getMessage());
        }
    }

    private void startMySQL(JSONObject json) {
        var user = (String) json.get("user");
        var password = (String) json.get("password");
        var url = (String) json.get("url");
        print("Trying MySQL connection with url=" + url + ",user=" + user + ",password=" + password);
        this.mySQL = new MySQL(url, user, password);
        if (this.mySQL.init()) {
            this.mySQL.createDatabases();
            print("MySQL connection estabilished successfully.");
        } else {
            print("MySQL connection not estabilished, disabling...");
            if (jda != null) {
                jda.shutdownNow();
            }
        }
    }

    private void startTasks() {
        var timer = new Timer();
        timer.schedule(new PresenceTask(), 20000L, 20000L);
    }

    public static JDA getJda() {
        return jda;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static void print(String message) {
        System.out.println("[Lucy] - " + message);
    }

    public static Command getCommand() {
        return command;
    }
}
