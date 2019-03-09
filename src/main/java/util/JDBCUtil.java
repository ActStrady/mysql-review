package util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class JDBCUtil {
    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private static Connection connection;

    // 静态代码块，只加载一次避免了资源浪费，读取配置文件
    static {
        Properties properties = new Properties();

        try {
            // 创建一个读取配置文件的输入流， @Cleanup 代表用完自动释放
            @Cleanup InputStream JDBCResource = JDBCUtil.class.getClassLoader().getResourceAsStream(
                    "jdbc.properties");
            properties.load(JDBCResource);
            log.info("Read jdbc.properties successful!");
        } catch (IOException e) {
            log.error("Properties read error", e);
        }
        DRIVER = properties.getProperty("driverClass");
        URL = properties.getProperty("jdbcUrl");
        USER = properties.getProperty("user");
        PASSWORD = properties.getProperty("password");
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            log.info("GetConnection successful!");
        } catch (ClassNotFoundException | SQLException e) {
            log.error("JDBCConnectionError", e);
            throw e;
        }
        log.info("Connection achieve success!");
        return connection;
    }
}
