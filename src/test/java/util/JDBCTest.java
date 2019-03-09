package util;

import org.junit.Test;

import java.sql.SQLException;

public class JDBCTest {
    @Test
    public void conTest() throws SQLException, ClassNotFoundException {
        JDBCUtil.getConnection();
    }
}
