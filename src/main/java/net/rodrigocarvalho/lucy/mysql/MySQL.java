package net.rodrigocarvalho.lucy.mysql;

import net.rodrigocarvalho.lucy.Lucy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    private String host, database, user, password;
    private Connection connection;

    public MySQL(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public boolean init() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", user, password);
            return true;
        } catch (Exception e) {
            Lucy.getLogger().error("Não foi possível estabelecer uma conexão com o mysql.");
            return false;
        }
    }

    public void createDatabases() {
        try (Statement statement = connection.createStatement()) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
