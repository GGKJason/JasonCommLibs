package com.jason.common;

/**
 * Created by bob on 2017/8/23.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/bozhai">GGKJason</a>
 * @Description
 */

public class ObjectUtils {

    /**
     * 前后交换数组元素T
     *
     * @param arr
     * @param begin
     * @param end
     *
     * @throws IndexOutOfBoundsException 调用方需对数组进行必要判断
     * @author: bob
     * create at 2017/8/23 下午12:37
     */
    public static <T> void swap(T[] arr, int begin, int end) throws IndexOutOfBoundsException {
        while (begin < end) {
            T temp = arr[begin];
            arr[begin] = arr[end];
            arr[end] = temp;
            begin++;
            end--;
        }
    }
}
