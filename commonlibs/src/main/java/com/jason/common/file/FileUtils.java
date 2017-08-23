package com.jason.common.file;

import java.io.File;

/**
 * Created by bob on 2017/7/26.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/bozhai">GGKJason</a>
 * @Description
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    private FileUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static File getFileByPath(String path) {
        return isSpace(path) ? null : new File(path);
    }

    public static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }

        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

}
