package com.jason.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by bob on 2017/8/23.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/bozhai">GGKJason</a>
 * @Description
 */

public class CheckUtils {
    /**
     * @param context
     *
     * @author: bob
     * create at 2017/8/23 下午5:52
     */
    public static void checkContextIsApplication(Context context) {
        if (!(context instanceof Application)) {
            throw new RuntimeException("You should use the Application Context!");
        }

    }
}
