package com.xiyuan.acm.util;

import java.util.Arrays;

/**
 * Created by xiyuan_fengyu on 2016/11/23.
 * http://www.lintcode.com/zh-cn/problem/left-pad/
 */
public class StringUtil {

    /**
     * @param originalStr the string we want to append to with spaces
     * @param size the target length of the string
     * @return a string
     */
    public static String leftPad(String originalStr, int size) {
        return leftPad(originalStr, size, ' ');
    }

    /**
     * @param originalStr the string we want to append to
     * @param size the target length of the string
     * @param padChar the character to pad to the left side of the string
     * @return a string
     */
    public static String leftPad(String originalStr, int size, char padChar) {
        int oldLen = originalStr.length();
        int newLen = Math.max(oldLen, size);
        if (oldLen == newLen) {
            return originalStr;
        }

        char[] pads = new char[newLen - oldLen];
        Arrays.fill(pads, padChar);
        return String.valueOf(pads) + originalStr;
    }

}
