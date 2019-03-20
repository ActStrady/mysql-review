package util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FileUtilTest {

    @Test
    public void replace() {
        FileUtil fileUtil = new FileUtil("src/main/resources/ip.txt");
        // FileUtil fileUtil = new FileUtil(FileUtil.class.getClassLoader().getResource("IP.txt").getPath());
        fileUtil.replace("(\\d+\\.+\\d+\\.+\\d+\\.+\\d+)\\s+", "$1¦");
        // File file = new File(FileUtil.class.getClassLoader().getResource("IP.txt").getFile());
        // System.out.println(file.getPath());
    }
}