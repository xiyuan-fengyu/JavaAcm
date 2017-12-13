package com.xiyuan.leetcode;

import java.util.HashMap;
import java.util.Map;

import static com.xiyuan.util.XYLog.d;

/**
 * Created by xiyuan_fengyu on 2017/12/6 17:20.
 */
public class Solution {

    public static void main(String[] args) {
        new Solution().test();
    }


    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        Map<String, Integer> caches = new HashMap<>();
        return Math.max(cherryPickup(grid, 0, 0, 0, 0, caches), 0);
    }

    private int cherryPickup(int[][] grid, int row1, int col1, int row2, int col2, Map<String, Integer> caches) {
        String key = row1 + "," + col1 + "," + row2 + "," + col2;
        Integer cache = caches.get(key);
        if (cache != null) return cache;

        int n = grid.length;
        if (row1 + 1 == n
                && col1 + 1 == n
                && row2 + 1 == n
                && col2 + 1 == n) {
            return grid[row1][col1];
        }

        int temp1 = grid[row1][col1];
        grid[row1][col1] = 0;
        int temp2 = grid[row2][col2];
        grid[row2][col2] = 0;

        int max = -1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int nRow1 = row1 + 1 - i;
                int nCol1 = col1 + i;
                int nRow2 = row2 + 1 - j;
                int nCol2 = col2 + j;
                if (nRow1 < n && nCol1 < n && grid[nRow1][nCol1] != -1
                        && nRow2 < n && nCol2 < n && grid[nRow2][nCol2] != -1) {
                    max = Math.max(max, cherryPickup(grid, nRow1, nCol1, nRow2, nCol2, caches));
                }
            }
        }

        if (max != -1) {
            max += temp1 + temp2;
        }
        grid[row2][col2] = temp2;
        grid[row1][col1] = temp1;
        caches.put(key, max);
        return max;
    }

    private void test() {


        int[][] grid = Util.toIntArr2("[[1,1,-1],[1,-1,1],[-1,1,1]]");
//        int[][] grid = Util.toIntArr2("[[0,1,-1],[1,0,-1],[1,1,1]]");
//        int[][] grid = Util.toIntArr2("[[1,1,1,1,0,0,0],[0,0,0,1,0,0,0],[0,0,0,1,0,0,1],[1,0,0,1,0,0,0],[0,0,0,1,0,0,0],[0,0,0,1,0,0,0],[0,0,0,1,1,1,1]]");
        d(cherryPickup(grid));

    }

}