package sql_tests;

import org.junit.Test;
import redmine.managers.Manager;

public class DataBaseConnectionTest {

    @Test
    public void basicSqlTest() {
        Manager.getConnection().executeQuery("SELECT * FROM projects");
        System.out.println();
        Manager.getConnection().executeQuery("SELECT * FROM roles");
    }
}
