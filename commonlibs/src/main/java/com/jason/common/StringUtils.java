package com.jason.common;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by bob on 2017/8/23.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/bozhai">GGKJason</a>
 * @Description
 */

public class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    /**
     * @param str
     *
     * @return boolean
     *
     * @author: bob
     * create at 2017/8/23 下午12:24
     */
    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * @param str
     *
     * @return boolean
     *
     * @author: bob
     * create at 2017/8/23 下午12:24
     */
    public static boolean isEmptyOptionTrim(String str) {
        return null == str || isEmpty(str.trim());
    }

    /**
     * @param str
     *
     * @return boolean
     *
     * @author: bob
     * create at 2017/8/23 下午12:23
     */
    public static boolean isEmptyWithNULL(CharSequence str) {

        return isEmpty(str) || "null".equalsIgnoreCase(str.toString());
    }

    /**
     * @param str
     *
     * @return boolean - if empty,null or "null"
     *
     * @author: bob
     * create at 2017/8/23 下午12:11
     */
    public static boolean isEmptyWithNULLOptionTrim(String str) {
        return isEmptyOptionTrim(str) || "null".equalsIgnoreCase(str);
    }

    /**
     * @param str
     *
     * @return 首字母大写
     *
     * @author: bob
     * create at 2017/8/23 下午12:27
     */
    public static String upperFirstLetter(String str) {
        if (isEmptyOptionTrim(str) || Character.isUpperCase(str.charAt(0))) {
            return str;
        }

        char[] charArr = str.toCharArray();
        charArr[0] = Character.toUpperCase(charArr[0]);

        return String.valueOf(charArr);
    }

    /**
     * @param str
     *
     * @return 首字母小写
     *
     * @author: bob
     * create at 2017/8/23 下午12:27
     */
    public static String lowerFirstLetter(String str) {

        if (isEmptyOptionTrim(str) || Character.isLowerCase(str.charAt(0))) {
            return str;
        }

        char[] charArr = str.toCharArray();
        charArr[0] = Character.toLowerCase(charArr[0]);

        return String.valueOf(charArr);
    }

    /**
     * 将字符串按照字母反转
     *
     * @param str
     *
     * @return
     *
     * @author: bob
     * create at 2017/8/23 下午3:41
     */
    public static String reverseLetter(String str) {
        if (isEmptyOptionTrim(str)) {
            return str;
        }

        return new StringBuilder(str).reverse().toString();

    }

    /**
     * 将字符串按照regex分割反转
     *
     * @param str
     * @param regex
     * @param append
     *
     * @author: bob
     * create at 2017/8/23 下午3:40
     */
    public static String reverseWordWithRegex(String str, String regex, String append) {
        if (isEmptyOptionTrim(str)) {
            return str;
        }

        if (isEmpty(regex)) {
            // 默认使用空格匹配
            regex = " ";
        }

        if (isEmpty(append)) {
            // 默认使用空格拼接
            append = " ";
        }

        String[] sArr = str.split(regex);
        List<String> list = Arrays.asList(sArr);
        Collections.reverse(list);

        StringBuilder sb = new StringBuilder();
        for (String word : list) {
            sb.append(word).append(append);
        }

        return sb.replace(sb.lastIndexOf(append), sb.length(), "").toString();

    }
}
