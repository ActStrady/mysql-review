package util;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FileUtilTest {

    @Test
    public void replace() throws FileNotFoundException {
        FileUtil fileUtil = new FileUtil("src/main/resources/ip.txt");
        fileUtil.replace("(\\d+\\.+\\d+\\.+\\d+\\.+\\d+)\\s+", "$1¦");
    }

    @Test
    public void fileToData() throws FileNotFoundException {
        FileUtil fileUtil = new FileUtil("src/main/resources/ip-new.txt");
        fileUtil.fileToData("useUnicode=true&characterEncoding=utf8" +
                "&rewriteBatchedStatements=true", "insert into db_ip.ip_range values (null, ?, ?, ?)", "¦");
    }
}