package com.xiyuan.acm;

import com.xiyuan.acm.model.ExpressionTreeNode;
import com.xiyuan.acm.model.Point;
import com.xiyuan.acm.util.DataUtil;
import com.xiyuan.util.XYLog;

import java.util.*;

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
        if (nums == null || nums.length == 0) {
            return "0";
        }

        int len = nums.length;
        String[] numStrs = new String[len];
        for (int i = 0; i < len; i++) {
            numStrs[i] = "" + nums[i];
        }
        Arrays.sort(numStrs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o2 + o1).compareTo(o1 + o2);
            }
        });
        StringBuilder strBld = new StringBuilder();
        for (String str: numStrs) {
            strBld.append(str);
        }

        String result = strBld.toString();
        int resultLen = result.length();
        int index = 0;
        while (index < resultLen && result.charAt(index) == '0') {
            index++;
        }
        if (index == resultLen) {
            return "0";
        }
        return result.substring(index);
    }




    /**
     * http://www.lintcode.com/zh-cn/problem/matrix-zigzag-traversal/
     * @param matrix: a matrix of integers
     * @return: an array of integers
     */
    public int[] printZMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[]{};
        }

        int row = matrix.length;
        int column = matrix[0].length;
        int[] nums = new int[row * column];
        int level = 0;
        int index = 0;
        int maxIndex = row + column - 2;
        while (level <= maxIndex) {
            if (level % 2 == 0) {
                int iStart = level < row? level: row - 1;
                int iEnd = level < column? 0: level - column + 1;
                for (int i = iStart; i >= iEnd; i--) {
                    nums[index++] = matrix[i][level - i];
                }
            }
            else {
                int iStart = level < column? 0: level - column + 1;
                int iEnd = level < row? level: row - 1;
                for (int i = iStart; i <= iEnd; i++) {
                    nums[index++] = matrix[i][level - i];
                }
            }
            level++;
        }
        return nums;
    }



    /**
     * http://www.lintcode.com/zh-cn/problem/max-points-on-a-line/
     * @param points an array of point
     * @return an integer
     */
    public int maxPoints(Point[] points) {
        //直线表达式：ax + by + c = 0;
        int max = 0;
        int len;
        if (points != null && (len= points.length) != 0) {
            if (len == 1) {
                return 1;
            }
            else {
                max = 2;
                HashMap<String, Set<Integer>> linePoints = new HashMap<>();
                for (int i = 0; i < len - 1; i++) {
                    for (int j = i + 1; j < len; j++) {
                        Point p1 = points[i];
                        Point p2 = points[j];

                        String key;
                        if (p1.x != p2.x || p1.y != p2.y) {
                            double a = 0;
                            double b = 0;
                            double c = 0;
                            if (p1.x == p2.x) {
                                a = 1;
                                b = 0;
                                c = -p1.x;
                            }
                            else {
                                b = 1;
                                c = (p1.y * p2.x - p1.x * p2.y) / (double) (p2.x - p1.x);
                                a = (p2.y - p1.y) / (double) (p2.x - p1.x);
                            }
                            key = a + "," + b + "," + c;
                        }
                        else {
                            key = p1.x + "," + p2.y;
                        }

                        if (linePoints.containsKey(key)) {
                            Set<Integer> tempPoints = linePoints.get(key);
                            tempPoints.add(i);
                            tempPoints.add(j);
                        }
                        else {
                            Set<Integer> tempPoints = new HashSet<>();
                            tempPoints.add(i);
                            tempPoints.add(j);
                            linePoints.put(key, tempPoints);
                        }
                        int temp = linePoints.get(key).size();
                        if (max < temp) {
                            max = temp;
                        }

                    }
                }


            }
        }
        return max;
    }

    public static void main(String[] args) {
        Solutions184_564 solutions = new Solutions184_564();

        /**
         最多有多少个点在一条直线上   [中等]
         http://www.lintcode.com/zh-cn/problem/max-points-on-a-line/
         给出二维平面上的n个点，求最多有多少点在同一条直线上。
         样例
         给出4个点：(1, 2), (3, 6), (0, 0), (1, 3)。
         一条直线上的点最多有3个。
         */
//        int[] nums = DataUtil.getIntArr("./data/max-points-on-a-line-69.in");
//        Point[] points = new Point[nums.length / 2];
//        for (int i = 0, len = points.length; i < len; i++) {
//            points[i] = new Point(nums[i * 2], nums[i * 2 + 1]);
//        }
////        Point[] points = new Point[]{
////                new Point(1, 2),
////                new Point(3, 6),
////                new Point(0, 0),
////                new Point(1, 3)
////        };
//        XYLog.d("在点集：\n", points, "\n最多有 ", solutions.maxPoints(points), " 个点共线");


        /**
         矩阵的之字型遍历   [容易]
         http://www.lintcode.com/zh-cn/problem/matrix-zigzag-traversal/
         给你一个包含 m x n 个元素的矩阵 (m 行, n 列), 求该矩阵的之字型遍历。
         样例
         对于如下矩阵：
         [
         [1, 2,  3,  4],
         [5, 6,  7,  8],
         [9,10, 11, 12]
         ]
         返回 [1, 2, 5, 9, 6, 3, 4, 7, 10, 11, 8, 12]
         */
//        int[][] matrix = {
//                {1, 2,  3,  4},
//                {5, 6,  7,  8},
//                {9,10, 11, 12}
//        };
//        XYLog.d(matrix, "的之字形遍历为：\n", solutions.printZMatrix(matrix));





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
//        int[] nums = {41,23,87,55,50,53,18,9,39,63,35,33,54,25,26,49,74,61,32,81,97,99,38,96,22,95,35,57,80,80,16,22,17,13,89,11,75,98,57,81,69,8,10,85,13,49,66,94,80,25,13,85,55,12,87,50,28,96,80,43,10,24,88,52,16,92,61,28,26,78,28,28,16,1,56,31,47,85,27,30,85,2,30,51,84,50,3,14,97,9,91,90,63,90,92,89,76,76,67,55};
//        XYLog.d(nums, "能拼接成的最大整数为：\n", solutions.largestNumber(nums));



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