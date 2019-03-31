package util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: mysql-review
 * @description: 项目中对JDBC操作的工具类
 * @author: ActStrady
 * @create: 2019-03-8 10:15
 **/
@Slf4j
public class JdbcUtil {
    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    // 静态代码块，只加载一次避免了资源浪费，读取配置文件
    static {
        Properties properties = new Properties();
        try (InputStream jdbcResource = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
            properties.load(jdbcResource);
        } catch (IOException e) {
            log.error("Properties read error", e);
        }
        DRIVER = properties.getProperty("driverClass");
        URL = properties.getProperty("jdbcUrl");
        USER = properties.getProperty("user");
        PASSWORD = properties.getProperty("password");
        log.info("Read jdbc.properties successful!");
    }

    /**
     * 以默认的配置文件获取jdbc连接,
     *
     * @return Connection 连接
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            log.error("Connection achieve error!", e);
        }
        log.info("Connection achieve success!");
        return connection;
    }

    /**
     * 以自己提供的参数来获取jdbc连接,
     *
     * @return Connection 连接
     * @throws SQLException sql异常
     */
    public static Connection getConnection(String parameter) throws SQLException {
        Connection connection;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL + parameter, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            log.error("Connection achieve error!", e);
            throw new SQLException();
        }
        log.info("Connection achieve success!");
        return connection;
    }
}
