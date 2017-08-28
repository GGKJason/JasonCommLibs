package com.jason.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by bob on 2017/8/24.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/bozhai">GGKJason</a>
 * @Description
 */

public class IOCloseUtils {
    private IOCloseUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    /**
     * @param closeables
     *
     * @author: bob
     * create at 2017/8/24 下午4:31
     */
    public static void ioClose(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }

        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param closeables
     *
     * @author: bob
     * create at 2017/8/24 下午4:31
     */
    public static void isCloseWithoutException(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }

        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {

                }
            }
        }
    }
}
