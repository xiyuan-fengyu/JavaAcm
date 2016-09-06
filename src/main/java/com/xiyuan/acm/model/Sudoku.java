package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/9/6.
 */
public class Sudoku {

    public final char[][] board = new char[9][9];

    public Sudoku(String[] strs) {
        if (strs != null && strs.length > 0) {
            for (int i = 0, len = strs.length; i < len; i++) {
                String str = strs[i];
                for (int j = 0, strLen = str.length(); j < strLen; j++) {
                    board[i][j] = str.charAt(j);
                }
            }
        }
    }

}
