package ltd.icecold.paidcommand.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;


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

    /**
     * @param f    要写入的文件
     * @param data 数据
     */
    public static void writeFile(File f, String data) {
        writeFile(f, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param f    要写入的文件
     * @param data 数据
     */
    public static void writeFile(File f, byte[] data) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedOutputStream boss = new BufferedOutputStream(new FileOutputStream(f));
            boss.write(data);
            boss.flush();
            boss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sourcePath 原路径
     * @param newPath    新路径
     */
    public static void copyDir(String sourcePath, String newPath) {
        File start = new File(sourcePath);
        File end = new File(newPath);
        copyDir(start, end);
    }

    public static void copyDir(File start, File end) {
        String[] filePath = start.list();
        if (!end.exists()) {
            end.mkdir();
        }

        Stream.of(filePath).forEach((v) -> {
            File f = new File(start, v);
            if (f.isDirectory()) {
                copyDir(f, new File(end, v));
            } else {
                copyFile(new File(start, v), new File(end, v));
            }
        });
    }

    /**
     * @param sourcePath 原路径
     * @param newPath    新路径
     */
    public static void copyFile(String sourcePath, String newPath) {
        File start = new File(sourcePath);
        File end = new File(newPath);
        copyFile(start, end);
    }

    public static void copyFile(File start, File end) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(start));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(end))) {
            byte[] b = new byte[bis.available()];
            bis.read(b);
            bos.write(b);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除路径
     *
     * @param path 欲删除路径
     * @return 是否成功删除
     */
    public static boolean deletePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                if (!f.delete()) {
                    return false;
                }
            } else {
                if (!deletePath(f.getAbsolutePath())) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}