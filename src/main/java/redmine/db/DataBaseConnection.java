package redmine.db;

import cucumber.api.java.mn.Харин;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static redmine.Property.*;

/**
 * Класс описывающий подключение и типы запрососв к БД
 */

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

    private void connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException exception) {
            throw new IllegalArgumentException("Не найден класс с драйвером" );
        }
        String url = String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s", dbHost, dbPort, dbName, dbUser, dbPass);
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException exception) {
            throw new IllegalArgumentException("Ошибка соединения с БД" + exception.getMessage());
        }

    }

    /**
     * Выполняет SQL - запрос и возвращает результат
     *
     * @param query - sql запрос
     * @return данные - результат запроса
     */

    @SneakyThrows
    public synchronized List<Map<String, Object>> executeQuery(String query) {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        int count = resultSet.getMetaData().getColumnCount();
        List<String> columnNames = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            columnNames.add(columnName);
        }
        while (resultSet.next()) {
            Map<String, Object> columnData = new TreeMap<>();
            for (String columnName : columnNames) {
                Object value = resultSet.getObject(columnName);
                columnData.put(columnName, value);
            }
            result.add(columnData);
        }
        return result;
    }

    /**
     * Выполняет SQL - запрос с заранее подготовленными данными и возвращает результат
     *
     * @param query      - sql запрос
     * @param parameters - параметры, подставляемые в запрос
     * @return данные - результат запроса
     */

    @SneakyThrows
    public synchronized List<Map<String, Object>> executePreparedQuery(String query, Object... parameters) {
        PreparedStatement statement = connection.prepareStatement(query);
        int index = 1;
        for (Object object : parameters) {
            statement.setObject(index++, object);
        }
        ResultSet resultSet = statement.executeQuery();
        int count = resultSet.getMetaData().getColumnCount();
        List<String> columnNames = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            columnNames.add(columnName);
        }
        while (resultSet.next()) {
            Map<String, Object> columnData = new TreeMap<>();
            for (String columnName : columnNames) {
                Object value = resultSet.getObject(columnName);
                columnData.put(columnName, value);
            }
            result.add(columnData);
        }
        return result;
    }

    /**
     * Выполняет SQL - запрос с заранее подготовленными данными без возвратного типа
     *
     * @param query      - sql запрос
     * @param parameters - параметры, подставляемые в запрос
     */

    @SneakyThrows
    public synchronized void executeDeleteQuery(String query, Object... parameters) {
        PreparedStatement statement = connection.prepareStatement(query);
        int index = 1;
        for (Object object : parameters) {
            statement.setObject(index++, object);
        }
        statement.executeUpdate();
    }
}
