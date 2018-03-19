package db;

import org.junit.Test;
import university.DBUtils;

public class DBTest {
    @Test
    public void testConnection(){

        DBUtils.checkConection();
    }
}
