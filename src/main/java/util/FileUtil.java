package util;

import lombok.Cleanup;
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

    private BufferedReader bufferedReader;
    private String filePath;

    /**
     * 有参构造用来read文件
     *
     * @param filePath 文件路径
     */
    public FileUtil(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            log.info("read file successful!");
        } catch (FileNotFoundException e) {
            log.error("read file error!", e);
            throw e;
        }
    }

    /**
     * 文件的替换
     * @param regular 替换内容的正则表达式
     * @param revised 替换后的内容
     */
    public void replace(String regular, String revised) {
        try {
            String path = filePath.replace(".", "-new.");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line.replaceAll(regular, revised) + "\n");
            }
            log.info("replace file successful!");
        } catch (IOException e) {
            log.error("replace file error", e);
        }
    }

    public void fileToData(String jdbcParameter, String sql, String divide) {
        try {
            @Cleanup Connection connection = JdbcUtil.getConnection(jdbcParameter);
            // 关闭自动提交事务
            connection.setAutoCommit(false);
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(divide);
                for (int i = 0; i < data.length; i++) {
                    preparedStatement.setString(i + 1, data[i]);
                }
                // 添加到批量提交
                preparedStatement.addBatch();
            }
            // 提交批量
            preparedStatement.executeBatch();
            // 提交事务
            connection.commit();
        } catch (SQLException | IOException e) {
            log.error("fileToData", e);
        }
    }
}
