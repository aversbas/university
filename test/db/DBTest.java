package db;

import org.junit.Test;
import university.db.DBUtils;

public class DBTest {
    @Test
    public void testConnection(){

        DBUtils.checkConection();
    }
}
