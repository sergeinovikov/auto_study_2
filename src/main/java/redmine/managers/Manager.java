package redmine.managers;

import redmine.db.DataBaseConnection;

import java.sql.Connection;

/**
 * Класс создающий подключение к БД
 */

public class Manager {
    public final static DataBaseConnection dbConnection = new DataBaseConnection();
}
