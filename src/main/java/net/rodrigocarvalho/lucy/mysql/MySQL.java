package net.rodrigocarvalho.lucy.mysql;

import lombok.var;
import net.rodrigocarvalho.lucy.Lucy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private String url, user, password;
    private Connection connection;

    public MySQL(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public boolean init() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createDatabases() {
        try (var statement = connection.createStatement()) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
