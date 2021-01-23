package redmine.managers;

import redmine.db.DataBaseConnection;

/**
 * Класс создающий подключение к БД
 */

public class Manager {
    public final static DataBaseConnection dbConnection = new DataBaseConnection();
}
