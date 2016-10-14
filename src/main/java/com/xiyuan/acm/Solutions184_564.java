package com.xiyuan.acm;

import com.xiyuan.acm.model.ExpressionTreeNode;
import com.xiyuan.util.XYLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiyuan_fengyu on 2016/10/14.
 */
public class Solutions184_564 {

    /**
     * http://www.lintcode.com/zh-cn/problem/expression-tree-build/
     * @param expression: A string array
     * @return: The root of expression tree
     */
    public ExpressionTreeNode build(String[] expression) {
        if (expression == null || expression.length == 0) {
            return null;
        }
        List<Object> exps = new ArrayList<Object>();
        Collections.addAll(exps, expression);
        return buildExpTree(exps);
    }

    private ExpressionTreeNode buildExpTree(List<Object> exps) {
        int leftBracketIndex = -1;
        int rightBracketIndex = -1;
        int leftBracketNum = 0;
        int rightBracketNum = 0;

        //处理所有的括号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("(")) {
                    leftBracketNum++;
                    if (leftBracketNum == 1) {
                        leftBracketIndex = i;
                    }
                }
                else if (itemStr.equals(")")) {
                    rightBracketNum++;
                    if (rightBracketNum == leftBracketNum) {
                        rightBracketIndex = i;
                        //将leftBracketIndex + 1到rightBracketIndex - 1之间的表达式转换为ExpressionTreeNode
                        List<Object> subExps = exps.subList(leftBracketIndex + 1, rightBracketIndex);
                        ExpressionTreeNode newNode = buildExpTree(subExps);
                        if (exps.size() > leftBracketIndex) {
                            exps.set(leftBracketIndex, newNode);
                            exps.remove(leftBracketIndex + 1);
                            if (newNode != null) {
                                exps.remove(leftBracketIndex + 1);
                            }
                        }

                        i = leftBracketIndex;
                        leftBracketIndex = -1;
                        rightBracketIndex = -1;
                        leftBracketNum = 0;
                        rightBracketNum = 0;
                    }
                }
            }
            i++;
        }

        //处理所有的乘号或除号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("*") || itemStr.equals("/")) {
                    exps.set(i - 1, buildSimpleTree(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }
            i++;
        }

        //处理所有的加号或减号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("+") || itemStr.equals("-")) {
                    exps.set(i - 1, buildSimpleTree(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }

            i++;
        }

        if (exps.size() > 0) {
            Object item0 = exps.get(0);
            if (item0 instanceof String) {
                return new ExpressionTreeNode((String) item0);
            }
            else {
                return (ExpressionTreeNode) exps.get(0);
            }
        }
        return null;
    }

    private ExpressionTreeNode buildSimpleTree(Object leftExp, String option, Object rightExp) {
        ExpressionTreeNode left = null;
        if (leftExp instanceof ExpressionTreeNode) {
            left = (ExpressionTreeNode) leftExp;
        }
        else {
            left = new ExpressionTreeNode((String) leftExp);
        }

        ExpressionTreeNode right = null;
        if (rightExp instanceof ExpressionTreeNode) {
            right = (ExpressionTreeNode) rightExp;
        }
        else {
            right = new ExpressionTreeNode((String) rightExp);
        }
        ExpressionTreeNode newNode = new ExpressionTreeNode(option);
        newNode.left = left;
        newNode.right = right;
        return newNode;
    }










    /**
     * http://www.lintcode.com/zh-cn/problem/convert-expression-to-reverse-polish-notation/
     * @param expression: A string array
     * @return: The Reverse Polish notation of this expression
     */
    public ArrayList<String> convertToRPN(String[] expression) {
        if (expression == null || expression.length == 0) {
            return null;
        }
        List<Object> exps = new ArrayList<Object>();
        Collections.addAll(exps, expression);
        Expression exp = buildExpression(exps);
        return expressionToRpnList(exp);
    }

    private ArrayList<String> expressionToRpnList(Expression exp) {
        ArrayList<String> result = new ArrayList<String>();
        if (exp != null) {
            if (exp.left != null && exp.right != null) {
                ArrayList<String> left = expressionToRpnList(exp.left);
                ArrayList<String> right = expressionToRpnList(exp.right);
                result.addAll(left);
                result.addAll(right);
            }
            result.add(exp.val);
        }
        return result;
    }

    private Expression buildExpression(List<Object> exps) {
        int leftBracketIndex = -1;
        int rightBracketIndex = -1;
        int leftBracketNum = 0;
        int rightBracketNum = 0;

        //处理所有的括号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("(")) {
                    leftBracketNum++;
                    if (leftBracketNum == 1) {
                        leftBracketIndex = i;
                    }
                }
                else if (itemStr.equals(")")) {
                    rightBracketNum++;
                    if (rightBracketNum == leftBracketNum) {
                        rightBracketIndex = i;
                        //将leftBracketIndex + 1到rightBracketIndex - 1之间的表达式转换为ExpressionTreeNode
                        List<Object> subExps = exps.subList(leftBracketIndex + 1, rightBracketIndex);
                        Expression newNode = buildExpression(subExps);
                        if (exps.size() > leftBracketIndex) {
                            exps.set(leftBracketIndex, newNode);
                            exps.remove(leftBracketIndex + 1);
                            if (newNode != null) {
                                exps.remove(leftBracketIndex + 1);
                            }
                        }

                        i = leftBracketIndex;
                        leftBracketIndex = -1;
                        rightBracketIndex = -1;
                        leftBracketNum = 0;
                        rightBracketNum = 0;
                    }
                }
            }
            i++;
        }

        //处理所有的乘号或除号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("*") || itemStr.equals("/")) {
                    exps.set(i - 1, buildSimpleExpression(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }
            i++;
        }

        //处理所有的加号或减号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("+") || itemStr.equals("-")) {
                    exps.set(i - 1, buildSimpleExpression(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }

            i++;
        }

        if (exps.size() > 0) {
            Object item0 = exps.get(0);
            if (item0 instanceof String) {
                return new Expression((String) item0);
            }
            else {
                return (Expression) exps.get(0);
            }
        }
        return null;
    }

    private Expression buildSimpleExpression(Object leftExp, String option, Object rightExp) {
        Expression left = null;
        if (leftExp instanceof Expression) {
            left = (Expression) leftExp;
        }
        else {
            left = new Expression((String) leftExp);
        }

        Expression right = null;
        if (rightExp instanceof Expression) {
            right = (Expression) rightExp;
        }
        else {
            right = new Expression((String) rightExp);
        }
        Expression newNode = new Expression(option);
        newNode.left = left;
        newNode.right = right;
        return newNode;
    }

    private class Expression {

        public Expression left;

        public Expression right;

        public String val;

        public Expression(String val) {
            this.val = val;
        }
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/valid-sudoku/
     * @param board: the board
     * @return: wether the Sudoku is valid
     */
    public boolean isValidSudoku(char[][] board) {
        HashMap<Character, Boolean> rowExiste = new HashMap<Character, Boolean>();
        HashMap<Character, Boolean> colExiste = new HashMap<Character, Boolean>();
        //检测每一行/列
        for (int i = 0; i < 9; i++) {
            rowExiste.clear();
            colExiste.clear();
            for (int j = 0; j < 9; j++) {
                char rowC = board[i][j];
                if (isNumChar(rowC)) {
                    if (rowExiste.containsKey(rowC)) {
                        return false;
                    }
                    else {
                        rowExiste.put(rowC, true);
                    }
                }

                char colC = board[j][i];
                if (isNumChar(colC)) {
                    if (colExiste.containsKey(colC)) {
                        return false;
                    }
                    else {
                        colExiste.put(colC, true);
                    }
                }
            }
        }

        //检查每一个九宫格
        for (int i = 0; i < 9; i++) {
            rowExiste.clear();

            int startIndex = i / 3 * 9 + i % 3 * 3;
            int startX = startIndex / 9;
            int startY = startIndex % 9;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    char c = board[startX + j][startY + k];
                    if (isNumChar(c)) {
                        if (rowExiste.containsKey(c)) {
                            return false;
                        }
                        else {
                            rowExiste.put(c, true);
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isNumChar(char c) {
        return c >= '1' && c <= '9';
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/largest-number/
     * @param nums: A list of non negative integers
     * @return: A string
     */
    public String largestNumber(int[] nums) {

        return null;
    }

    public static void main(String[] args) {
        Solutions184_564 solutions = new Solutions184_564();

        /**
         最大数   [中等]
         http://www.lintcode.com/zh-cn/problem/largest-number/
         给出一组非负整数，重新排列他们的顺序把他们组成一个最大的整数。
         注意事项
         最后的结果可能很大，所以我们返回一个字符串来代替这个整数。
         样例
         给出 [1, 20, 23, 4, 8]，返回组合最大的整数应为8423201。
         挑战
         在 O(nlogn) 的时间复杂度内完成。
         */
        int[] nums = {1, 20, 23, 4, 8};
        XYLog.d(nums, "能拼接成的最大整数为：", solutions.largestNumber(nums));



        /**
         判断数独是否合法   [容易]
         http://www.lintcode.com/zh-cn/problem/valid-sudoku/
         请判定一个数独是否有效。
         该数独可能只填充了部分数字，其中缺少的数字用 . 表示。
         注意事项
         一个合法的数独（仅部分填充）并不一定是可解的。我们仅需使填充的空格有效即可。

         什么是 数独？
         http://sudoku.com.au/TheRules.aspx
         http://baike.baidu.com/subview/961/10842669.htm
         */
//        String[] strs = {"..5.....6","....14...",".........",".....92..","5....2...",".......3.","...54....","3.....42.","...27.6.."};
//        Sudoku sudoku = new Sudoku(strs);
//        XYLog.d(sudoku.board, "\n", solutions.isValidSudoku(sudoku.board)? "": "不", "是合法的数独");






        /**
         将表达式转换为逆波兰表达式   [困难]
         http://www.lintcode.com/zh-cn/problem/convert-expression-to-reverse-polish-notation/
         给定一个表达式字符串数组，返回该表达式的逆波兰表达式（即去掉括号）。
         样例
         对于 [3 - 4 + 5]的表达式（该表达式可表示为["3", "-", "4", "+", "5"]），返回 [3 4 - 5 +]（该表达式可表示为 ["3", "4", "-", "5", "+"]）。
         */
//        String[] exps = {"3", "-", "4", "*", "5"};
//        XYLog.d(solutions.convertToRPN(exps));





        /**
         表达树构造   [困难]
         http://www.lintcode.com/zh-cn/problem/expression-tree-build/
         表达树是一个二叉树的结构，用于衡量特定的表达。所有表达树的叶子都有一个数字字符串值。而所有表达树的非叶子都有另一个操作字符串值。
         给定一个表达数组，请构造该表达的表达树，并返回该表达树的根。
         样例
         对于 (2*6-(23+7)/(1+2)) 的表达（可表示为 ["2" "*" "6" "-" "(" "23" "+" "7" ")" "/" "(" "1" "+" "2" ")"]).
         其表达树如下：
                     -
                  /    \
                *      /
              / \   /   \
             2   6 +     +
                  / \   / \
                23   7 1   2
         */
////        String[] expression = {"(","(","(","(","(",")",")",")",")",")"};
//        String[] expression = {"2","*","6","-","(","(","23","+","7",")","/","(","1","+","2",")", "-", "12", ")"};
//        XYLog.d("表达式", expression, "的表达树为：", solutions.build(expression));
    }

}