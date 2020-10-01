package ltd.icecold.paidcommand.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * @author ice-cold
 */
public class IOUtil {
    /**
     * @param f 文件
     * @return 返回文件内的字符串 使用UTF-8编码
     */
    public static String readFile(File f) {
        if (!f.exists()) {
            throw new NullPointerException("文件不存在");
        }
        try {
            BufferedInputStream biss = new BufferedInputStream(new FileInputStream(f));
            byte[] b = new byte[biss.available()];
            biss.read(b);
            biss.close();
            return new String(b, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void rewriteFile(File file, String data) {
        if (!file.exists()) {
            throw new NullPointerException("文件不存在");
        }
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}