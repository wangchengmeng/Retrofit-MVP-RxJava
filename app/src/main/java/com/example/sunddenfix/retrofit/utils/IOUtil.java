package com.maogu.htclibrary.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO工具
 */
public class IOUtil {

    /**
     * 关闭流
     *
     * @param stream 可关闭的流
     */
    public static void closeStream(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
