package com.xiyuan.acm.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiyuan_fengyu on 2016/9/9.
 */
public class DataUtil {

    public static String getFileContent(String path) {
        StringBuilder strBld = new StringBuilder();
        for (String line: getFileLines(path)) {
            strBld.append(line).append('\n');
        }
        return strBld.toString();
    }

    public static List<String> getFileLines(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static ArrayList<String> getStringArr(String path) {
        String fileContent = getFileContent(path);
        String[] split = fileContent.split(",");
        ArrayList<String> result = new ArrayList<>();
        for (String item: split) {
            item = item.trim();
            if (item.startsWith("\"") && item.endsWith("\"")) {
                result.add(item.substring(1, item.length() - 1));
            }
            else {
                result.add(item);
            }
        }
        return  result;
    }

    public static int[] getIntArr(String path) {
        char[] fileContent = getFileContent(path).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c: fileContent) {
            if ((c + "").matches("[0-9,-]")) {
                builder.append(c);
            }
        }
        String dataStr = builder.toString();
        String[] split = dataStr.split(",");
        int len = split.length;
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.parseInt(split[i]);
        }
        return  result;
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
