package com.xiyuan.leetcode;

import com.xiyuan.acm.factory.TreeNodeFactory;
import com.xiyuan.acm.model.TreeNode;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.xiyuan.util.XYLog.d;

/**
 * Created by xiyuan_fengyu on 2017/12/6 17:20.
 */
public class Solution {

    public static void main(String[] args) {
        new Solution().test();
    }

//    /**
//     * dp 加 回溯
//     * 212ms
//     * @param grid
//     * @return
//     */
//    public int cherryPickup(int[][] grid) {
//        int n = grid.length;
//        Map<String, Integer> caches = new HashMap<>();
//        return Math.max(cherryPickup(grid, 0, 0, 0, 0, caches), 0);
//    }
//
//    private int cherryPickup(int[][] grid, int row1, int col1, int row2, int col2, Map<String, Integer> caches) {
//        String key = row1 + "," + col1 + "," + row2 + "," + col2;
//        Integer cache = caches.get(key);
//        if (cache != null) return cache;
//
//        int n = grid.length;
//        if (row1 + 1 == n
//                && col1 + 1 == n
//                && row2 + 1 == n
//                && col2 + 1 == n) {
//            return grid[row1][col1];
//        }
//
//        int temp1 = grid[row1][col1];
//        grid[row1][col1] = 0;
//        int temp2 = grid[row2][col2];
//        grid[row2][col2] = 0;
//
//        int max = -1;
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                int nRow1 = row1 + 1 - i;
//                int nCol1 = col1 + i;
//                int nRow2 = row2 + 1 - j;
//                int nCol2 = col2 + j;
//                if (nRow1 < n && nCol1 < n && grid[nRow1][nCol1] != -1
//                        && nRow2 < n && nCol2 < n && grid[nRow2][nCol2] != -1) {
//                    max = Math.max(max, cherryPickup(grid, nRow1, nCol1, nRow2, nCol2, caches));
//                }
//            }
//        }
//
//        if (max != -1) {
//            max += temp1 + temp2;
//        }
//        grid[row2][col2] = temp2;
//        grid[row1][col1] = temp1;
//        caches.put(key, max);
//        return max;
//    }


    /**
     * 思路：(起点 -> 终点 -> 起点)  等效于  (起点 通过两条路径到达 终点)
     * 关键点：image/cherryPickup.jpg
     * @param grid
     * @return
     */
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        // 45度右上朝向的斜线的总数量
        int slashNum = n * 2 - 1;

        // cache[i][j] 在每一次循环中代表两个点处于斜线上第i, j个位置(左下开始)时的最大分数
        int[][] cache1 = new int[n][n];
        int[][] cache2 = new int[n][n];
        cache1[0][0] = grid[0][0];

        int[][] lastCache;
        int[][] curCache = cache1;
        int firstRow = 0;
        int jRow;
        int jCol;
        int kRow;
        int kCol;
        boolean noPath;
        for (int i = 1; i < slashNum; i++) {
            if (i < n) firstRow ++;

            lastCache = i % 2 == 1 ? cache1 : cache2;
            curCache = i % 2 == 1 ? cache2 : cache1;

            noPath = true;
            int posNum = n - Math.abs(i - n + 1);
            for (int j = 0; j < posNum; j++) {
                jRow = firstRow - j;
                jCol = i - jRow;
                if (grid[jRow][jCol] == -1) {
                    for (int k = 0; k < posNum; k++) {
                        curCache[j][k] = curCache[k][j] = -1;
                    }
                }
                else {
                    for (int k = j; k < posNum; k++) {
                        kRow = firstRow - k;
                        kCol = i - kRow;
                        if (grid[kRow][kCol] != -1) {
                            int max = -1;
                            if (i < n) {
                                if (k + 1 < posNum) {
                                    max = Math.max(max, lastCache[j][k]);
                                }
                                if (j > 0 && k + 1 < posNum) {
                                    max = Math.max(max, lastCache[j - 1][k]);
                                }
                                if (j + 1 < posNum && k > 0) {
                                    max = Math.max(max, lastCache[j][k - 1]);
                                }
                                if (j > 0) {
                                    max = Math.max(max, lastCache[j - 1][k - 1]);
                                }
                            }
                            else {
                                max = Math.max(lastCache[j][k], lastCache[j + 1][k]);
                                max = Math.max(max, lastCache[j][k + 1]);
                                max = Math.max(max, lastCache[j + 1][k + 1]);
                            }
                            curCache[j][k] = max == -1 ? -1 : max + grid[jRow][jCol] + (j == k ? 0 : grid[kRow][kCol]);
                            if (curCache[j][k] != -1) {
                                noPath = false;
                            }
                        }
                    }
                }
            }

            if (noPath) return 0;
        }
        return curCache[0][0];
    }




    public int findClosestLeaf(TreeNode root, int k) {
        if (root == null) return 0;

        Integer[] minDepths = new Integer[2];
        computeMinDepth(root, k, minDepths);
        return minDepths[0];
    }

    private Integer[] computeMinDepth(TreeNode root, int k, Integer[] minDepths) {
        Integer[] leftMD = null;
        if (root.left != null) {
            leftMD = computeMinDepth(root.left, k, minDepths);
        }

        Integer[] rightMD = null;
        if (root.right != null) {
            rightMD = computeMinDepth(root.right, k, minDepths);
        }

        int ksParent = -1;
        Integer[] res;
        if (leftMD == null && rightMD == null) {
            res = new Integer[]{root.val, 1, null};
        }
        else if (leftMD == null) {
            ksParent = rightMD[2];
            res = new Integer[] {rightMD[0], 1 + rightMD[1], null};
        }
        else if (rightMD == null) {
            ksParent = leftMD[2];
            res = new Integer[] {leftMD[0], 1 + leftMD[1], null};
        }
        else {
            ksParent = leftMD[2] > -1 ? leftMD[2] : rightMD[2];
            if (leftMD[1] <= rightMD[1]) {
                res = new Integer[] {leftMD[0], 1 + leftMD[1], null};
            }
            else {
                res = new Integer[] {rightMD[0], 1 + rightMD[1], null};
            }
        }

        if (k == root.val) {
            minDepths[0] = res[0];
            minDepths[1] = res[1];
            res[2] = 0;
        }
        else if (ksParent > -1) {
            res[2] = ksParent + 1;
            if (minDepths[1] > res[2] + res[1]) {
                minDepths[0] = res[0];
                minDepths[1] = res[2] + res[1];
            }
        }
        else {
            res[2] = -1;
        }
        return res;
    }



    private void test() {

//        TreeNode root = TreeNodeFactory.build("1,2,3,4,null,null,null,5,null,6");
//        int k = 2;
////        TreeNode root = TreeNodeFactory.build("1,2,3,4,null,null,null,5,null,6,null,7,null,8");
////        int k = 4;
//        System.out.println(root);
//        System.out.println(findClosestLeaf(root, k));


////        int[][] grid = Util.toIntArr2("[[1,1,-1],[1,-1,1],[-1,1,1]]");
////        int[][] grid = Util.toIntArr2("[[0,1,-1],[1,0,-1],[1,1,1]]");
//        int[][] grid = Util.toIntArr2("[[0,0,0,-1,0,0,0,0,1,0,0,0,1,0,1,1,-1,0,1,0,1,0,1,-1,0,-1,0,-1,0,-1,1,0,-1,1,1,1,1,0,-1,1,1,1,1,1,1,0,1,1,0,1],[1,0,0,1,1,1,0,1,-1,0,1,1,0,1,1,1,0,0,1,1,1,0,-1,0,1,0,1,0,1,1,0,1,1,-1,0,1,1,1,1,0,-1,0,1,0,0,0,0,0,1,0],[0,0,1,0,1,1,0,0,0,-1,0,0,0,0,1,0,1,1,1,0,0,1,-1,1,1,1,0,1,1,1,0,1,0,0,-1,1,1,1,0,1,0,0,1,1,0,1,0,1,1,1],[1,0,1,1,0,1,1,0,-1,1,-1,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,-1,0],[0,0,0,1,-1,1,0,-1,0,1,1,1,0,1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,0,0,-1,1,0,0,0,0,0,1,0,0,0,1,0,1,1],[0,1,0,0,1,0,1,0,0,0,0,1,0,1,0,0,0,1,0,1,-1,1,1,1,0,0,0,0,0,0,1,1,1,0,0,1,1,0,1,1,0,1,0,1,1,0,0,0,1,0],[-1,0,0,1,1,0,1,1,1,1,0,0,1,0,0,1,1,1,0,0,1,0,0,0,1,1,1,1,0,0,0,0,1,-1,1,0,1,1,0,0,0,0,0,0,0,1,1,0,1,1],[0,1,1,0,1,0,1,0,0,-1,0,1,1,1,0,0,0,1,1,-1,1,-1,-1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,1,0,-1,0,1,0,0,1,1,1,0,-1],[1,0,1,-1,1,-1,1,0,0,0,0,1,0,0,0,-1,0,0,0,1,0,1,0,1,1,-1,1,0,1,1,1,1,1,1,-1,0,0,0,0,1,0,1,1,1,-1,1,1,1,-1,1],[1,1,1,0,-1,0,-1,1,1,0,0,1,1,1,1,1,-1,1,0,1,1,-1,1,1,0,1,1,0,1,0,1,0,1,1,0,1,0,1,1,0,0,-1,1,1,1,0,0,0,1,-1],[1,1,1,-1,1,-1,1,0,0,-1,0,1,-1,0,-1,0,1,1,0,-1,1,0,-1,0,-1,0,1,1,-1,1,1,1,0,0,0,-1,0,1,1,1,0,1,1,1,1,1,1,0,1,1],[1,0,1,1,1,0,0,0,1,0,0,1,0,1,1,0,0,1,1,1,1,1,1,0,0,-1,0,0,1,0,1,0,1,1,0,0,1,0,0,1,0,1,1,1,0,0,0,0,0,1],[1,1,1,1,1,-1,0,1,1,1,1,0,1,-1,1,0,0,1,1,1,0,0,0,1,1,-1,1,1,-1,1,0,1,-1,1,0,1,1,0,0,0,1,0,-1,1,0,1,1,0,1,1],[1,1,1,1,-1,0,1,0,1,1,0,0,1,0,1,0,0,-1,0,1,1,0,0,1,0,0,0,0,0,1,0,0,0,-1,1,1,0,0,1,0,0,1,0,1,0,1,0,0,0,0],[1,1,0,0,0,0,1,0,1,0,0,1,0,1,1,1,0,0,0,0,1,0,0,-1,0,0,1,0,1,-1,-1,0,0,0,1,1,0,0,1,0,0,0,1,1,1,1,0,0,1,1],[0,0,1,1,1,1,0,0,0,1,1,-1,1,1,-1,-1,0,1,-1,0,0,1,0,1,0,1,0,1,1,0,0,0,-1,0,1,0,1,0,1,0,0,1,-1,1,0,1,1,0,0,0],[0,1,1,0,0,0,0,0,1,0,1,0,0,0,1,1,1,0,0,1,1,0,0,1,1,0,0,1,0,0,0,1,1,1,1,0,0,1,0,0,0,-1,-1,1,1,1,0,0,1,1],[1,0,-1,0,1,1,1,0,0,1,0,0,0,1,1,0,-1,1,0,-1,0,0,-1,-1,0,0,0,1,-1,1,0,-1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,0,0,0,-1],[-1,-1,1,-1,0,1,1,1,0,-1,1,-1,0,0,0,0,0,-1,0,1,1,-1,1,0,1,1,0,1,0,1,0,1,1,0,1,1,-1,-1,1,1,1,0,0,0,0,1,0,1,0,1],[0,-1,1,1,1,-1,-1,0,-1,-1,1,0,0,0,0,-1,-1,1,0,0,0,1,-1,0,1,0,-1,0,-1,1,1,1,1,-1,0,0,1,0,1,0,-1,0,0,1,0,0,0,0,-1,0],[1,1,0,1,0,1,0,-1,1,0,0,1,0,1,1,0,1,1,0,1,1,1,0,1,0,-1,1,0,1,1,0,-1,1,0,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,0],[0,1,0,-1,0,0,0,1,1,-1,1,0,0,0,1,-1,0,0,1,1,1,1,1,1,1,-1,0,-1,0,1,-1,1,0,0,-1,1,1,1,1,0,-1,1,1,0,0,0,0,1,1,0],[0,1,-1,1,1,1,1,-1,0,0,1,1,0,1,0,0,-1,1,0,0,1,1,0,0,1,0,0,-1,1,0,1,0,1,0,0,1,1,0,-1,0,1,0,1,0,1,0,0,1,1,0],[0,0,0,1,0,0,-1,1,0,-1,0,0,0,1,0,0,0,0,1,1,0,0,0,1,0,0,1,1,0,1,1,1,1,0,1,0,1,1,1,0,-1,1,0,0,0,1,0,1,1,1],[1,1,1,-1,0,1,1,1,1,0,0,0,0,1,0,1,0,-1,0,0,0,0,1,0,0,1,1,0,-1,-1,1,0,1,1,0,1,0,1,-1,-1,1,0,0,0,0,1,1,-1,0,0],[0,1,0,1,0,0,1,0,0,0,1,1,1,1,0,0,0,0,1,-1,0,0,1,1,0,0,-1,-1,1,0,0,1,1,1,0,1,0,0,1,1,0,1,1,0,1,1,0,1,1,0],[1,-1,-1,1,-1,1,0,0,0,1,-1,1,1,1,1,0,1,0,1,0,0,1,1,1,0,0,0,0,0,-1,0,0,1,0,0,1,0,1,1,0,0,0,0,0,0,1,0,1,1,0],[1,1,1,0,0,0,0,-1,0,1,1,0,1,-1,1,0,0,1,0,0,-1,1,-1,1,0,1,0,1,0,1,-1,1,0,1,1,0,-1,0,1,0,0,0,1,0,0,1,0,0,1,1],[0,0,0,0,1,0,1,0,0,1,1,1,1,1,1,1,0,0,0,1,1,1,-1,0,0,1,1,1,1,0,1,0,1,0,0,0,1,0,1,0,0,-1,1,0,1,0,1,1,1,0],[0,1,1,0,1,0,0,0,1,1,-1,1,0,1,0,0,1,0,-1,0,1,1,0,0,-1,0,1,-1,1,1,0,0,1,-1,0,0,0,1,-1,0,1,1,1,0,1,0,1,0,1,-1],[0,1,-1,1,0,1,1,0,0,0,0,1,0,1,0,-1,1,0,1,0,0,1,0,1,0,1,0,0,0,1,1,0,1,0,-1,1,0,-1,0,0,1,0,1,1,0,0,0,1,1,0],[1,0,0,0,0,1,0,0,0,1,1,-1,1,0,1,0,1,1,1,0,1,1,0,1,1,-1,1,1,1,0,-1,0,1,1,1,0,0,0,1,0,0,1,0,1,1,1,0,1,1,0],[1,1,1,1,0,-1,0,0,0,-1,1,0,1,0,1,0,0,0,1,1,1,1,0,-1,1,1,0,1,0,0,1,-1,-1,1,0,1,-1,0,-1,1,0,0,-1,0,0,1,1,0,1,1],[0,1,1,0,0,1,1,1,1,-1,1,0,0,0,0,0,-1,1,0,0,1,0,0,0,0,1,1,0,0,1,1,1,1,1,0,1,1,1,-1,0,0,0,0,1,0,1,1,0,1,-1],[0,1,-1,0,1,-1,1,0,0,1,1,1,-1,0,1,1,1,1,1,0,1,1,0,1,0,1,1,0,0,1,0,1,0,-1,0,1,0,0,-1,0,0,0,0,1,1,1,0,-1,-1,1],[1,0,1,-1,1,1,0,0,0,1,1,0,1,0,0,1,0,-1,0,-1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,0,-1,0,1,-1,1,0],[0,0,0,0,-1,1,1,1,0,0,0,1,0,0,1,-1,1,-1,1,1,-1,0,1,0,-1,-1,0,0,1,1,1,0,-1,0,0,-1,-1,1,0,0,1,0,1,1,0,1,1,-1,-1,1],[1,0,1,0,1,1,0,0,0,1,1,1,0,1,1,1,1,0,1,-1,0,0,1,0,1,-1,-1,-1,1,1,1,1,0,-1,0,-1,0,1,1,0,0,0,0,0,1,1,0,1,0,0],[1,1,0,-1,-1,0,0,0,1,0,0,1,1,0,1,0,1,1,0,-1,0,1,1,1,0,0,1,0,0,1,0,-1,1,1,0,0,1,0,-1,1,0,0,1,0,0,1,-1,0,0,0],[1,0,1,1,-1,0,1,1,0,-1,1,1,-1,-1,1,-1,0,0,0,0,0,1,0,0,1,1,1,0,0,1,1,1,1,1,-1,0,0,1,-1,0,1,0,0,1,0,1,1,0,1,-1],[0,0,1,1,0,0,0,0,1,0,1,1,0,0,1,-1,0,1,-1,1,1,0,1,0,1,1,1,1,1,0,0,0,1,0,0,1,1,0,1,0,0,1,0,0,-1,1,1,1,1,1],[0,0,0,1,1,1,0,1,-1,1,0,0,1,0,1,0,1,0,1,0,-1,0,0,0,0,-1,0,0,0,0,1,1,0,1,1,1,-1,1,1,0,-1,0,0,1,1,1,-1,0,0,0],[1,0,0,1,1,0,1,0,0,1,1,-1,1,0,0,1,1,0,-1,1,0,0,1,1,1,1,1,1,0,0,0,-1,1,1,0,0,1,0,0,0,1,0,1,0,1,1,-1,1,1,1],[1,1,-1,1,0,0,1,0,1,1,1,1,0,0,0,0,1,1,1,0,1,1,0,0,1,1,1,0,1,0,0,1,1,1,0,0,0,1,1,0,0,1,1,1,1,1,1,0,0,1],[1,0,1,0,1,1,0,-1,0,1,1,1,1,1,1,-1,1,1,-1,1,0,0,1,1,0,1,0,0,0,0,0,0,0,0,-1,1,0,0,0,0,0,0,1,0,0,-1,1,0,1,0],[0,1,0,1,1,1,0,0,0,-1,0,0,1,1,0,0,0,0,0,1,1,0,1,1,0,1,1,1,0,-1,1,1,1,0,-1,1,1,1,1,0,1,0,0,1,0,1,1,0,0,-1],[0,0,1,0,0,0,0,1,0,1,1,0,1,1,1,0,-1,0,1,1,1,1,0,0,0,0,1,0,-1,-1,1,-1,0,0,0,-1,0,-1,0,1,0,1,0,1,0,1,1,1,-1,0],[0,0,1,1,1,0,1,-1,1,0,0,1,1,0,1,0,0,1,1,-1,1,1,1,0,0,-1,0,1,0,0,1,0,0,1,0,1,0,1,0,-1,1,1,1,1,1,0,0,-1,1,1],[0,1,-1,0,0,1,1,1,-1,1,-1,1,0,0,1,1,1,1,0,1,0,0,-1,0,0,0,1,1,0,1,0,1,1,0,-1,1,1,1,0,1,-1,1,-1,0,0,1,0,-1,0,1],[0,1,1,0,1,1,1,0,0,0,0,0,0,0,-1,0,0,0,1,1,1,1,0,0,0,0,1,0,0,0,1,0,-1,1,0,0,0,1,0,0,0,0,1,1,-1,-1,0,-1,1,1]]");
//        for (int[] ints : grid) {
//            for (int anInt : ints) {
//                System.out.print(String.format("%2d ", anInt));
//            }
//            System.out.println();
//        }
//        d(cherryPickup(grid));

    }

}