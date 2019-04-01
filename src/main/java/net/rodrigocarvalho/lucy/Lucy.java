package net.rodrigocarvalho.lucy;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.rodrigocarvalho.lucy.mysql.MySQL;
import net.rodrigocarvalho.lucy.task.PresenceTask;
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

    private MySQL mySQL;

    public Lucy(String configName) {
        CONFIG_NAME = configName;
        init();
    }

    private void init() {
        FileUtils.create(CONFIG_NAME);
        var parser = new JSONParser();
        try (var reader = new FileReader(CONFIG_NAME)){
            var json = (JSONObject) parser.parse(reader);
            var token = (String) json.get("token");
            startBot(token);

            var mysqlJson = (JSONObject) json.get("mysql");
            startMySQL(mysqlJson);
            startTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBot(String token) {
        try {
            jda = new JDABuilder(token).build();
            jda.awaitReady();
        } catch (Exception e) {
            LOGGER.error("Não foi possível iniciar o bot: " + e.getMessage());
        }
    }

    private void startMySQL(JSONObject json) {
        var host = (String) json.get("host");
        var database = (String) json.get("database");
        var user = (String) json.get("user");
        var password = (String) json.get("password");
        this.mySQL = new MySQL(host, database, user, password);
        if (this.mySQL.init()) {
            this.mySQL.createDatabases();
            LOGGER.info("Conexão com MySQL estabelecida com sucesso.");
        } else {
            jda.shutdown();
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
}
