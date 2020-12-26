package redmine.db;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static redmine.Property.*;

public class DataBaseConnection {
    private String dbHost;
    private Integer dbPort;
    private String dbUser;
    private String dbPass;
    private String dbName;
    private Connection connection;

    public DataBaseConnection() {
        initVariables();
        connect();
    }


    private void initVariables() {
        dbHost = getStringProperty("dbHost");
        dbPort = getIntegerProperty("dbPort");
        dbUser = getStringProperty("dbUser");
        dbPass = getStringProperty("dbPass");
        dbName = getStringProperty("dbName");
    }

    @SneakyThrows
    private void connect() {
        Class.forName("org.postgresql.Driver");
        String url = String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s", dbHost, dbPort, dbName, dbUser, dbPass);
        connection = DriverManager.getConnection(url);
    }

    @SneakyThrows
    public void executeQuery(String query) {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int count = resultSet.getMetaData().getColumnCount();
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            columnNames.add(columnName);
        }
        //TODO продолжить написание класса
        /*while (resultSet.next()) {

        }*/
    }
}
