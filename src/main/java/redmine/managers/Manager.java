package redmine.managers;

import redmine.db.DataBaseConnection;

import java.sql.Connection;

public class Manager {
    public final static DataBaseConnection dbConnection = new DataBaseConnection();
}
