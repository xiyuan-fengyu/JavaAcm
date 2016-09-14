package com.xiyuan.acm.util;

import java.io.*;

/**
 * Created by xiyuan_fengyu on 2016/9/9.
 */
public class DataUtil {

    public static String getFileContent(String path) {
        StringBuilder strBld = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBld.append(line).append('\n');
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return strBld.toString();
    }

    public static int[][] getTwoDimensArr(String path, int secondDimenLen) {
        if (path == null || secondDimenLen <= 0) {
            return null;
        }

        char[] fileContent = getFileContent(path).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c: fileContent) {
            if ((c >= '0' && c <= '9') || c == ',') {
                builder.append(c);
            }
        }
        String dataStr = builder.toString();
        String[] split = dataStr.split(",");
        int firstDimenLen = split.length / secondDimenLen;
        int[][] result = new int[firstDimenLen][secondDimenLen];
        for (int i = 0, len = split.length; i < len; i++) {
            int j = i / secondDimenLen;
            int k = i % secondDimenLen;
            int value = Integer.parseInt(split[i]);
            result[j][k] = value;
        }
        return  result;
    }

}