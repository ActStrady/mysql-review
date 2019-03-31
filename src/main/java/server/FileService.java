package server;

import util.FileUtil;

/**
 * @program: mysql-review
 * @description: 文件的具体操作
 * @author: ActStrady
 * @create: 2019-03-31 19:01
 **/
public class FileService {
    public static void main(String[] args) {
        // 替换分隔符
        FileUtil.replace("src/main/resources/csdn.sql", "\\s#\\s", "\\s¦\\s");
        FileUtil.replace("src/main/resources/ip.txt", "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s+", "$1¦");
    }
}
