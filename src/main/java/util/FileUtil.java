package util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @program: mysql-review
 * @description: 项目中对本地文件的操作类
 * @author: ActStrady
 * @create: 2019-03-20 10:15
 **/
@Slf4j
public class FileUtil {
    /**
     * 文件内容的替换
     *
     * @param filePath 文件路径
     * @param regular  替换内容的正则表达式
     * @param revised  替换后的内容
     * @return 替换后文件路径
     */
    public static String replace(String filePath, String regular, String revised) {
        String path = filePath.replace(".", "-new.");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String newLine = line.replaceAll(regular, revised);
                bufferedWriter.write(newLine + "\n");
            }
        } catch (IOException e) {
            log.error("replace file error", e);
        }
        log.info("replace file successful!");
        return path;
    }

    /**
     * 利用java切割字符串的方式批量插入，效率低下，不建议使用 建议使用原生的sql load来做
     * jdk1.7 后新特性，在try后括号的内容自动释放
     *
     * @param filePath      文件路径
     * @param jdbcParameter jdbc的参数
     * @param sql           sql语句
     * @param divide        切割符
     */
    public static void fileToData(String filePath, String jdbcParameter, String sql, String divide) {
        try (Connection connection = JdbcUtil.getConnection(jdbcParameter);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            // 关闭自动提交
            connection.setAutoCommit(false);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(divide);
                for (int i = 0; i < data.length; i++) {
                    preparedStatement.setString(i + 1, data[i]);
                    log.debug(data[i]);
                }
                // 添加到批量提交
                preparedStatement.addBatch();
            }
            // 提交批量
            preparedStatement.executeBatch();
            // 提交事务
            connection.commit();
        } catch (SQLException | IOException e) {
            log.error("file to data error!", e);
        }
        log.info("file to data successful!");
    }
}
