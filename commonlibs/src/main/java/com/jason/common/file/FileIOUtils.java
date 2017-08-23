package com.jason.common.file;

import java.io.File;
import java.io.InputStream;

/**
 * Created by bob on 2017/7/26.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/bozhai">GGKJason</a>
 * @Description
 */

public class FileIOUtils {
    private static final String TAG = FileIOUtils.class.getSimpleName();

    // 文件读取默认使用的缓存大小为最大运行时内存的1/8
    private static int sBufferSize = (int) Runtime.getRuntime().maxMemory() / 8;

    private FileIOUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static void setBufferSize(int bufferSize) {
        FileIOUtils.sBufferSize = bufferSize;
    }


    public static boolean writeFileWithInputStream(File file, InputStream in, boolean append) {
        return false;
    }
}
