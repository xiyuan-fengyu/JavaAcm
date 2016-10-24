package com.xiyuan.acm.util;

/**
 * Created by xiyuan_fengyu on 2016/10/24.
 */
public class RandomUtil {

    public static int[][] randomArr(int row, int maxColumn, int maxValue) {
        int[][] result = new int[row][];
        for (int i = 0; i < row; i++) {
            int column = (int) (Math.random() * maxColumn);
            int[] resultItem = new int[column];
            for (int j = 0; j < column; j++) {
                resultItem[j] = (int) (Math.random() * maxValue);
            }
            result[i] = resultItem;
        }
        return result;
    }

}
