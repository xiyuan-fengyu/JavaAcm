package com.xiyuan.acm;

import com.xiyuan.acm.factory.SegmentTreeNodeFactory;
import com.xiyuan.acm.factory.TreeNodeFactory;
import com.xiyuan.acm.model.*;
import com.xiyuan.acm.util.DataUtil;
import com.xiyuan.acm.util.PrintUtil;
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









    /**
     * http://www.lintcode.com/zh-cn/problem/gas-station/
     * @param gas: an array of integers
     * @param cost: an array of integers
     * @return: an integer
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || gas.length == 0) {
            return -1;
        }

        int index = -1;
        int remainGas = 0;
        int subRemainGas = 0;
        for (int i = 0, len = gas.length; i< len; i++) {
            int delta = gas[i] - cost[i];
            remainGas += delta;
            if ((remainGas < 0 && delta > 0) || subRemainGas + delta >= 0) {
                subRemainGas += delta;
                if (index == -1) {
                    index = i;
                }
            }
            else {
                subRemainGas = 0;
                index = -1;
            }
        }
        return remainGas >= 0? index: -1;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/first-missing-positive/
     * @param arr: an array of integers
     * @return: an integer
     */
    public int firstMissingPositive(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 1;
        }

        int len = arr.length;
        for (int i = 0; i < len; i++) {
            while (arr[i] != i + 1) {
                if (arr[i] > len || arr[i] <= 0) {
                    arr[i] = 0;
                    break;
                }
                else {
                    int temp = arr[arr[i] - 1];
                    if (temp == arr[i]) {
                        break;
                    }
                    arr[arr[i] - 1] = arr[i];
                    arr[i] = temp;
                }
            }
        }

        for (int i = 0; i < len; i++) {
            if (arr[i] != i + 1) {
                return i + 1;
            }
        }
        return len + 1;
    }










    /**
     * http://www.lintcode.com/zh-cn/problem/next-permutation-ii/
     * @param nums: an array of integers
     * @return: return nothing (void), do not return anything, modify nums in-place instead
     */
    public void nextPermutation(int[] nums) {
        if (nums != null && nums.length > 1) {
            int len = nums.length;
            for (int i = len - 2; i > -1; i--) {
                for (int j = len - 1; j > i; j--) {
                    if (nums[i] < nums[j]) {
                        int temp = nums[j];
                        nums[j] = nums[i];
                        nums[i] = temp;
                        Arrays.sort(nums, i + 1, len);
                        return;
                    }
                }
            }

            Arrays.sort(nums, 0, len);
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/maximum-product-subarray/
     * @param nums: an array of integers
     * @return: an integer
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int[] mins = new int[len];
        int[] maxs = new int[len];
        mins[0] = nums[0];
        maxs[0] = nums[0];
        int result = maxs[0];
        for (int i = 1; i < len; i++) {
            if (nums[i] >= 0) {
                maxs[i] = Math.max(nums[i], maxs[i - 1] * nums[i]);
                mins[i] = Math.min(nums[i], mins[i - 1] * nums[i]);
            }
            else {
                maxs[i] = Math.max(nums[i], mins[i - 1] * nums[i]);
                mins[i] = Math.min(nums[i], maxs[i - 1] * nums[i]);
            }
            if (maxs[i] > result) {
                result = maxs[i];
            }
        }

        return result;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/wildcard-matching/
     * @param s: A string
     * @param p: A string includes "?" and "*"
     * @return: A boolean
     */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        isMatchCache.clear();
        return isMatch(s, 0, p, 0);
    }

    private HashMap<String, Boolean> isMatchCache = new HashMap<>();

    public boolean isMatch(String s, int sIndex, String p, int pIndex) {
        String key = sIndex + "," + pIndex;
        if (isMatchCache.containsKey(key)) {
            return false;
        }
        isMatchCache.put(key, false);

        int sLen = s.length();
        int pLen = p.length();
        if (pIndex == pLen) {
            return sIndex == sLen;
        }

        String nextP = nextPattern(p, pIndex);
        int nextPLen = nextP.length();
        char nextC = nextP.charAt(0);
        if (nextC == '?') {
            return isMatch(s, sIndex + 1, p, pIndex + 1);
        }
        else if (nextC == '*') {
            for (int i = sIndex - 1; i < sLen; i++) {
                boolean temp1 = isMatch(s, i + 1, p, pIndex + nextPLen);
                if (temp1) {
                    return true;
                }
            }
        }
        else {
            if (sIndex + nextPLen - 1 < sLen && s.substring(sIndex, sIndex + nextPLen).equals(nextP)) {
                return isMatch(s, sIndex + nextPLen, p, pIndex + nextPLen);
            }
            else return false;
        }
        return false;
    }

    private String nextPattern(String p, int start) {
        int len = p.length();
        if (p.charAt(start) == '?') {
            return "?";
        }
        else if (p.charAt(start) == '*') {
            int index = start + 1;
            while (index < len && p.charAt(index) == '*') {
                index++;
            }
            return p.substring(start, index);
        }
        else {
            int index = start + 1;
            while (index < len && p.charAt(index) != '*' && p.charAt(index) != '?') {
                index++;
            }
            return p.substring(start, index);
        }
    }


    /**
     * http://www.lintcode.com/zh-cn/problem/find-the-missing-number/
     * @param nums: an array of integers
     * @return: an integer
     */
    public int findMissing(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        for (int i = 0; i < len; i++) {
            while (nums[i] > -1 && nums[i] != i) {
                if (nums[i] >= len) {
                    nums[i] = -1;
                    break;
                }
                else {
                    int temp = nums[nums[i]];
                    nums[nums[i]] = nums[i];
                    nums[i] = temp;
                }
            }
        }

        for (int i = 0; i < len; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        return len;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/permutation-index/
     * @param nums an integer array
     * @return a long integer
     */
    public long permutationIndex(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }

        permutationIndexCache.clear();
        int len = nums.length;
        int[] copyNums = Arrays.copyOf(nums, len);
        Arrays.sort(copyNums);
        long total = 1;
        for (int i = 0; i < len; i++) {
            permutationIndexCache.put(copyNums[i], i);
            total *= (i + 1);
        }
        return permutationIndex(nums, 0, total);
    }

    private HashMap<Integer, Integer> permutationIndexCache = new HashMap<>();

    private long permutationIndex(int[] nums, int index, long total) {
        int len = nums.length;
        if (index == len - 1) {
            return 1;
        }
        else {
            int cur = nums[index];
            long rightTotal = total / (len - index);
            long temp = permutationIndexCache.get(cur) * rightTotal;
            for (int i = index; i < len; i++) {
                int item = nums[i];
                if (item > cur) {
                    permutationIndexCache.put(item, permutationIndexCache.get(item) - 1);
                }
            }
            return temp + permutationIndex(nums, index + 1, rightTotal);
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/permutation-index-ii/
     * @param nums an integer array
     * @return a long integer
     */
    public long permutationIndexII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }

        permutationNumCache.clear();
        int len = nums.length;
        int[] copyNums = Arrays.copyOf(nums, len);
        Arrays.sort(copyNums);
        for (int i = 0; i < len; i++) {
            int num = copyNums[i];
            if (permutationNumCache.containsKey(num)) {
                permutationNumCache.put(num, permutationNumCache.get(num) + 1);
            }
            else {
                permutationNumCache.put(num, 1);
            }
        }
        return permutationIndexII(nums, 0);
    }

    private HashMap<Integer, Integer> permutationNumCache = new HashMap<>();

    private HashMap<Integer, Long> oneTimesToNCache = new HashMap<>();

    private long oneTimesToN(int n) {
        if (n <= 1) {
            return 1;
        }
        else if (oneTimesToNCache.containsKey(n)) {
            return oneTimesToNCache.get(n);
        }
        else {
            long result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
            }
            oneTimesToNCache.put(n, result);
            return result;
        }
    }

    private long permutationIndexII(int[] nums, int index) {
        int len = nums.length;
        if (index == len - 1) {
            return 1;
        }
        else {
            int cur = nums[index];
            long temp0 = 1;
            for (Map.Entry<Integer, Integer> keyVal: permutationNumCache.entrySet()) {
                temp0 *= oneTimesToN(keyVal.getValue());
            }

            long temp1 = oneTimesToN(len - index - 1);

            long total = 0;
            HashMap<Integer, Boolean> tempCache = new HashMap<>();
            for (int i = index + 1; i < len; i++) {
                int item = nums[i];
                if (item < cur && !tempCache.containsKey(item)) {
                    tempCache.put(item, true);
                    total += temp1 * permutationNumCache.get(item) / temp0;
                }
            }

            int tempNum = permutationNumCache.get(cur);
            if (tempNum == 1) {
                permutationNumCache.remove(cur);
            }
            else {
                permutationNumCache.put(cur, tempNum - 1);
            }
            return total + permutationIndexII(nums, index + 1);
        }
    }










//    /**
//     * 穷举法：
//     * 时间复杂度：O(n^3)
//     *
//     * 动态规划：
//     * O(n ^ 2)  f[i][j] = if (char[i] == char[j]) f[i + 1][j - 1] else 0; f[i][i] = 1;
//     *
//     * 中间扩撒：
//     * O(n ^ 2)  以每一个字母为中心（或者中间两个靠左的）向两边扩散判断回文
//     *
//     * Manacher算法：
//     * O(n)
//     * http://www.lintcode.com/zh-cn/problem/longest-palindromic-substring/
//     * @param str input string
//     * @return the longest palindromic substring
//     */
//    public String longestPalindrome(String str) {
//        if (str == null) {
//            return null;
//        }
//
//        int len = str.length();
//        if (len < 2) {
//            return str;
//        }
//
//        int maxLeft = 0;
//        int maxRight = 0;
//        for (int i = 0; i < len; i++) {
//            for (int j = i; j < len; j++) {
//                if (isBackWord(str, i, j) && j - i > maxRight - maxLeft) {
//                    maxLeft = i;
//                    maxRight = j;
//                }
//            }
//        }
//        return str.substring(maxLeft, maxRight + 1);
//    }
//
//    private boolean isBackWord(String str, int left, int right) {
//        while (left < right) {
//            if (str.charAt(left) != str.charAt(right)) {
//                return false;
//            }
//            left++;
//            right--;
//        }
//        return true;
//    }

    /**
     * Manacher算法：
     * O(n)
     * http://www.lintcode.com/zh-cn/problem/longest-palindromic-substring/
     * @param str input string
     * @return the longest palindromic substring
     */
    public String longestPalindrome(String str) {
        if (str == null) {
            return null;
        }

        int len = str.length();
        if (len < 2) {
            return str;
        }

        char[] chars = new char[len * 2 + 2];
        int charsLen = chars.length;
        chars[0] = '^';
        chars[charsLen - 1] = '$';
        for (int i = 0; i < len; i++) {
            chars[i * 2 + 2] = str.charAt(i);
        }

        int[] f = new int[charsLen];
        int index = 0;
        int maxLen = 0;
        int maxLenIndex = 0;
        for (int i = 2; i < charsLen - 1; i++) {
            if (f[index] + index > i) {
                f[i] = Math.min(f[index * 2 - i], f[index] + index - i);
            }
            else {
                f[i] = 1;
            }
            while (chars[i + f[i]] == chars[i - f[i]]) {
                ++f[i];
            }
            if (f[i] + i > f[index] + index) {
                index = i;
            }
            if (maxLen < f[i] || (maxLen == f[i] && chars[i - f[i] + 1] != '\0')) {
                maxLen = f[i];
                maxLenIndex = i;
            }
        }
        StringBuilder strBld = new StringBuilder();
        for (int i = maxLenIndex - maxLen + 1; i <= maxLenIndex + maxLen - 1; i++) {
            if (chars[i] != '\0') {
                strBld.append(chars[i]);
            }
        }
        return strBld.toString();
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/segment-tree-build/
     * @param start, end: Denote an segment / interval
     * @return: The root of Segment Tree
     */
    public SegmentTreeNode build(int start, int end) {
        if (start > end) {
            return null;
        }
        else if (start == end) {
            return new SegmentTreeNode(start, end);
        }
        else {
            SegmentTreeNode root = new SegmentTreeNode(start, end);
            root.left = build(start, (start + end) / 2);
            root.right = build((start + end) / 2 + 1, end);
            return root;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/segment-tree-query/
     * @param root, start, end: The root of segment tree and an segment / interval
     * @return: The maximum number in the interval [start, end]
     */
    public int query(SegmentTreeNode root, int start, int end) {
        return SegmentTreeNodeFactory.query(root, start, end, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/segment-tree-modify/
     * @param root, index, value: The root of segment tree and change the node's value with [index, index] to the new given value
     * @return: void
     */
    public void modify(SegmentTreeNode root, int index, int value) {
        SegmentTreeNodeFactory.modify(root, index, value, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
    }









    private static class Solution {

        private static Solution instance = new Solution();

        private Solution() {}

        /**
         * http://www.lintcode.com/zh-cn/problem/singleton/
         * @return: The same instance of this class every time
         */
        public static Solution getInstance() {
            return instance;
        }
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/interval-minimum-number/
     * @param arr, queries: Given an integer array and an query list
     * @return: The result list
     */
    public ArrayList<Integer> intervalMinNumber(int[] arr, ArrayList<Interval> queries) {
        ArrayList<Integer> result = new ArrayList<>();
        if (arr != null && arr.length > 0) {
            Comparator<Integer> comparator = new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            };
            SegmentTreeNode root = SegmentTreeNodeFactory.build(arr, comparator);
            for (Interval item: queries) {
                result.add(SegmentTreeNodeFactory.query(root, item.start, item.end, comparator));
            }
        }
        return result;
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/interval-sum/
     * @param arr, queries: Given an integer array and an query list
     * @return: The result list
     */
    public ArrayList<Long> intervalSum(int[] arr, ArrayList<Interval> queries) {
        ArrayList<Long> result = new ArrayList<>();
        if (arr != null && arr.length >= 0) {
            int len = arr.length;
            Long[] copyArr = new Long[len];
            for (int i = 0; i < len; i++) {
                copyArr[i] = (long) arr[i];
            }

            IntervalTreeNode.Actions<Long> actions = new IntervalTreeNode.Actions<Long>() {
                @Override
                public void build(IntervalTreeNode<Long> root) {
                    root.data = root.left.data + root.right.data;
                }

                @Override
                public Long query(Long leftData, Long rightData) {
                    if (leftData != null && rightData != null) {
                        return leftData + rightData;
                    }
                    else if (leftData != null) {
                        return leftData;
                    }
                    else if (rightData != null) {
                        return rightData;
                    }
                    return null;
                }

                @Override
                public void modify(IntervalTreeNode<Long> root) {
                    root.data = root.left.data + root.right.data;
                }
            };

            IntervalTreeNode<Long> root = IntervalTreeNode.build(copyArr, actions);
            for (Interval interval: queries) {
                result.add(IntervalTreeNode.query(root, interval.start, interval.end, actions));
            }
        }
        return result;
    }


    /**
     * http://www.lintcode.com/zh-cn/problem/interval-sum-ii/
     */
    private static class SolutionIntervalSumII {

        private IntervalTreeNode.Actions<Long> actions;

        private IntervalTreeNode<Long> tree;

        /**
         * @param arr: An integer array
         */
        public SolutionIntervalSumII(int[] arr) {
            if (arr != null && arr.length > 0) {
                int len = arr.length;
                Long[] copyArr = new Long[len];
                for (int i = 0; i < len; i++) {
                    copyArr[i] = (long) arr[i];
                }

                actions = new IntervalTreeNode.Actions<Long>() {
                    @Override
                    public void build(IntervalTreeNode<Long> root) {
                        root.data = root.left.data + root.right.data;
                    }

                    @Override
                    public Long query(Long leftData, Long rightData) {
                        if (leftData != null && rightData != null) {
                            return leftData + rightData;
                        }
                        else if (leftData != null) {
                            return leftData;
                        }
                        else if (rightData != null) {
                            return rightData;
                        }
                        return null;
                    }

                    @Override
                    public void modify(IntervalTreeNode<Long> root) {
                        root.data = root.left.data + root.right.data;
                    }
                };

                tree = IntervalTreeNode.build(copyArr, actions);
            }
        }

        /**
         * @param start, end: Indices
         * @return: The sum from start to end
         */
        public long query(int start, int end) {
            return IntervalTreeNode.query(tree, start, end, actions);
        }

        /**
         * @param index, value: modify A[index] to value.
         */
        public void modify(int index, int value) {
            IntervalTreeNode.modify(tree, index, (long) value, actions);
        }
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/space-replacement/
     * @param chars: An array of Char
     * @param length: The true length of the string
     * @return: The true length of new string
     */
    public int replaceBlank(char[] chars, int length) {
        if (chars == null || chars.length == 0) {
            return 0;
        }

        int newLen = length;
        for (int i = 0; i < length; i++) {
            if (chars[i] == ' ') {
                newLen += 2;
            }
        }

        for (int i = length - 1, j = newLen - 1; i > -1; i--) {
            char c = chars[i];
            if (c == ' ') {
                chars[j--] = '0';
                chars[j--] = '2';
                chars[j--] = '%';
            }
            else {
                chars[j--] = c;
            }
        }
        return newLen;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/palindrome-linked-list/
     * @param head a ListNode
     * @return a boolean
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        //快慢指针找到中间位置
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode rightHalf = slow.next;
        //暂时以slow为截点，将右边斩断
        slow.next = null;
        rightHalf = reverse(rightHalf);

        boolean result = true;
        //将head和fast作为游标
        fast = rightHalf;
        while (head != null && fast != null) {
            if (head.val != fast.val) {
                result = false;
                break;
            }
            head = head.next;
            fast = fast.next;
        }
        //将rightHalf反转，并拼接到slow后面
        slow.next = reverse(rightHalf);
        return result;
    }

    private ListNode reverse(ListNode head) {
        ListNode cur = null;
        ListNode cursor = head;
        while (cursor != null) {
            ListNode temp = cursor;
            cursor = cursor.next;

            temp.next = cur;
            cur = temp;
        }
        return cur;
    }










    /**
     * http://www.lintcode.com/zh-cn/problem/subtree/
     * @param t1, t2: The roots of binary tree.
     * @return: True if T2 is a subtree of T1, or false.
     */
    public boolean isSubtree(TreeNode t1, TreeNode t2) {
        if (t2 == null) {
            return true;
        }
        return isSubtree(t1, t2, t2);
    }

    public boolean isSubtree(TreeNode t1, TreeNode t2, TreeNode t2Root) {
        if (t1 == null && t2 == null) {
            return true;
        }
        else if (t1 == null || t2 == null) {
            return false;
        }

        if (t1.val == t2.val) {
            if (isSubtree(t1.left, t2.left, t2Root) && isSubtree(t1.right, t2.right, t2Root)) {
                return true;
            }
        }
        return  isSubtree(t1.left, t2Root) || isSubtree(t1.right, t2Root);
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/segment-tree-query-ii/
     * @param root, start, end: The root of segment tree and an segment / interval
     * @return: The count number in the interval [start, end]
     */
    public int query247(SegmentTreeNode root, int start, int end) {
        if (root == null || start > end || start > root.end || end < root.start) {
            return 0;
        }

        if (root.start == start && root.end == end) {
            return root.max;
        }
        else {
            int leftCount = 0;
            if (root.left != null) {
                leftCount = query247(root.left, start, Math.min(root.left.end, end));
            }
            else {
                return root.max;
            }

            int rightCount = 0;
            if (root.right != null) {
                rightCount = query247(root.right, Math.max(root.right.start, start), end);
            }
            return leftCount + rightCount;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/count-of-smaller-number/
     * @param arr: An integer array
     * @return: The number of element in the array that
     *          are smaller that the given integer
     */
    public ArrayList<Integer> countOfSmallerNumber(int[] arr, int[] queries) {
        ArrayList<Integer> result = new ArrayList<>();
        int lenA = arr.length;
        int lenQ = queries.length;
        SegmentTreeNode247 root = SegmentTreeNode247.build(0, 10000);

        for (int i = 0; i < lenA; i++) {
            root.modify(arr[i], 1);
        }

        for (int i = 0; i < lenQ; i++) {
            int item = queries[i];
            if (item > 0) {
                result.add(root.query(0, item - 1));
            }
            else {
                result.add(0);
            }
        }

        return result;
    }

    private static class SegmentTreeNode247 {
        public int start;
        public int end;
        public int count;
        public SegmentTreeNode247 left;
        public SegmentTreeNode247 right;

        public SegmentTreeNode247(int start, int end, int count) {
            this.count = count;
            this.end = end;
            this.start = start;
        }

        public static SegmentTreeNode247 build(int start, int end) {
            SegmentTreeNode247 newNode = new SegmentTreeNode247(start, end, 0);
            if (start != end) {
                int mid = (start + end) / 2;
                newNode.left = build(start, mid);
                newNode.right = build(mid + 1, end);
            }
            return newNode;
        }

        public int query(int start, int end) {
            if (start == this.start && end == this.end) {
                return this.count;
            }
            else {
                int mid = (this.start + this.end) / 2;
                int leftCount = 0;
                int rightCount = 0;

                if (start <= mid && left != null) {
                    leftCount = left.query(start, Math.min(mid, end));
                    leftCount += 0;
                }
                if (mid < end && right != null) {
                    rightCount = right.query(Math.max(mid + 1, start), end);
                    rightCount += 0;
                }
                return leftCount + rightCount;
            }
        }

        public void modify(int index, int value) {
            if (this.start == index && this.end == index) {
                this.count += value;
            }
            else {
                int mid = (start + end) / 2;
                if (this.start <= index && index <= mid) {
                    left.modify(index, value);
                }
                else if (mid < index && index <= this.end) {
                    right.modify(index, value);
                }
                this.count = left.count + right.count;
            }
        }

    }






    /**
     * http://www.lintcode.com/zh-cn/problem/count-of-smaller-number-before-itself/
     * @param arr: An integer array
     * @return: Count the number of element before this element 'ai' is
     *          smaller than it and return count number array
     */
    public ArrayList<Integer> countOfSmallerNumberII(int[] arr) {
        ArrayList<Integer> result = new ArrayList<>();
        int lenA = arr.length;
        SegmentTreeNode247 root = SegmentTreeNode247.build(0, 10000);

        for (int i = 0; i < lenA; i++) {
            int item = arr[i];
            root.modify(arr[i], 1);
            if (item > 0) {
                result.add(root.query(0, item - 1));
            }
            else {
                result.add(0);
            }
        }
        return result;
    }










    /**
     * o(n^k)，在数据随机，数组长度很大的时候，这种算法平均运行时间更短
     * http://www.lintcode.com/zh-cn/problem/sliding-window-maximum/
     * @param nums: A list of integers.
     * @return: The maximum number inside the window at each moving.
     */
    public ArrayList<Integer> maxSlidingWindowNK(int[] nums, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int len = nums.length;
        if (len > 0 && k > 0) {
            int max = nums[0];
            for (int i = 1; i < k; i++) {
                if (nums[i] > max) {
                    max = nums[i];
                }
            }

            result.add(max);
            for (int i = k; i < len; i++) {
                if (nums[i] >= max) {
                    max = nums[i];
                }
                else {
                    if (nums[i - k] == max) {
                        max = nums[i - k + 1];
                        for (int j = i - k + 2; j <= i; j++) {
                            if (max < nums[j]) {
                                max = nums[j];
                            }
                        }
                    }
                }
                result.add(max);
            }
        }
        return result;
    }

    /**
     * o(n)
     * http://www.lintcode.com/zh-cn/problem/sliding-window-maximum/
     * @param nums: A list of integers.
     * @return: The maximum number inside the window at each moving.
     */
    public ArrayList<Integer> maxSlidingWindow(int[] nums, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int len = nums.length;
        if (len > 0 && k > 0) {
            Deque<Integer> deque = new ArrayDeque<>();
            for (int i = 0; i < len; i++) {
                int item = nums[i];
                while (!deque.isEmpty() && deque.peekLast() < item) {
                    deque.pollLast();
                }
                deque.offer(item);

                if (i >= k - 1) {
                    int first = deque.peekFirst();
                    result.add(first);
                    if (first == nums[i - k + 1]) {
                        deque.pollFirst();
                    }
                }
            }
        }
        return result;
    }







//    /**
//     * 时间复杂度：o(n)，空间复杂度：o(n)
//     * 先从右往左计算好每个位置右边的最高海拔，然后在从左往右遍历，记录每个位置左边的最高海拔
//     * 每个位置的出水量 = Math.min(左边最高海拔, 右边最高海拔) - 当前位置的海拔
//     * http://www.lintcode.com/zh-cn/problem/trapping-rain-water/
//     * @param heights: an array of integers
//     * @return: a integer
//     */
//    public int trapRainWater_N_N(int[] heights) {
//        int total = 0;
//        int len = heights.length;
//        if (len > 0) {
//            int[] rightMaxHs = new int[len];
//            rightMaxHs[len - 1] = heights[len - 1];
//            for (int i = len - 2; i > -1; i--) {
//                if (heights[i] > rightMaxHs[i + 1]) {
//                    rightMaxHs[i] = heights[i];
//                }
//                else {
//                    rightMaxHs[i] = rightMaxHs[i + 1];
//                }
//            }
//            int leftMax = heights[0];
//            for (int i = 1; i < len; i++) {
//                int h = heights[i];
//                if (h >= leftMax) {
//                    leftMax = h;
//                }
//                else if (h < rightMaxHs[i]) {
//                    total += Math.min(leftMax, rightMaxHs[i]) - h;
//                }
//            }
//        }
//        return total;
//    }

    /**
     * 时间复杂度：o(n)，空间复杂度：o(1)
     * 思路和 trapRainWater_N_N 类似，只是从左右两边往中间靠拢，在靠弄的过程中计算
     * http://www.lintcode.com/zh-cn/problem/trapping-rain-water/
     * @param heights: an array of integers
     * @return: a integer
     */
    public int trapRainWater(int[] heights) {
        int total = 0;
        int left = 0;
        int right = heights.length - 1;
        if (right > -1) {
            int leftH = heights[left];
            int rightH = heights[right];
            while (left < right) {
                if (leftH < rightH) {
                    left++;
                    if (leftH > heights[left]) {
                        total += leftH - heights[left];
                    }
                    else {
                        leftH = heights[left];
                    }
                }
                else {
                    right--;
                    if (rightH > heights[right]) {
                        total += rightH - heights[right];
                    }
                    else {
                        rightH = heights[right];
                    }
                }
            }
        }
        return total;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/count-1-in-binary/
     * @param num: an integer
     * @return: an integer, the number of ones in num
     */
    public int countOnes(int num) {
        int count = 0;
        while (num != 0) {
            count += num & 1;
            num >>>= 1;//最高位补0，这里不能用>>，否则对于负数，最高位补1,就不正确了
        }
        return count;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/fibonacci/
     * @param n: an integer
     * @return an integer f(n)
     */
    public int fibonacci(int n) {
        int a = 0;
        int b = 1;
        while (n-- > 1) {
            b = a + b;
            a = b - a;
        }
        return a;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/expression-evaluation/
     * @param expression: an array of strings;
     * @return: an integer
     */
    public int evaluateExpression(String[] expression) {
        int len = expression.length;
        if (len > 0) {
            Stack<String> options = new Stack<>();
            Stack<Integer> params = new Stack<>();
            for (int i = 0; i < len; i++) {
                String exp = expression[i];
                if (exp.equals("(")) {
                    options.push(exp);
                }
                else if (exp.equals("*") || exp.equals("/")) {
                    if (options.isEmpty()) {
                        options.push(exp);
                    }
                    else {
                        String lastExp = options.peek();
                        if (!lastExp.equals("(") && !lastExp.equals("+") && !lastExp.equals("-")) {
                            options.pop();
                            int param2 = params.pop();
                            int param1 = params.pop();
                            params.push(expResult(param1, lastExp, param2));
                        }
                        options.push(exp);
                    }
                }
                else if (exp.equals("+") || exp.equals("-")) {
                    if (options.isEmpty()) {
                        options.push(exp);
                    }
                    else {
                        String lastExp = options.peek();
                        if (!lastExp.equals("(")) {
                            options.pop();
                            int param2 = params.pop();
                            int param1 = params.pop();
                            params.push(expResult(param1, lastExp, param2));
                        }
                        options.push(exp);
                    }
                }
                else if (exp.equals(")")) {
                    String lastExp = options.peek();
                    while (!lastExp.equals("(")) {
                        options.pop();
                        int param2 = params.pop();
                        int param1 = params.pop();
                        params.push(expResult(param1, lastExp, param2));
                        lastExp = options.peek();
                    }
                    options.pop();
                }
                else {
                    params.push(Integer.parseInt(exp));
                }
            }

            while (!options.isEmpty()) {
                String lastExp = options.pop();
                int param2 = params.pop();
                int param1 = params.pop();
                params.push(expResult(param1, lastExp, param2));
            }

            return params.isEmpty()? 0: params.peek();
        }
        return 0;
    }

    private int expResult(int param1, String option, int param2) {
        if (option.equals("+")) {
            return param1 + param2;
        }
        else if (option.equals("-")) {
            return param1 - param2;
        }
        else if (option.equals("*")) {
            return param1 * param2;
        }
        else {
            return param1 / param2;
        }
    }



    /**
     * http://www.lintcode.com/zh-cn/problem/print-numbers-by-recursion/
     * @param n: An integer.
     * return : An array storing 1 to the largest number with n digits.
     */
    public List<Integer> numbersByRecursion(int n) {
        List<Integer> result = new ArrayList<>();
        numbersByRecursion(n, result);
        return result;
    }

    private void numbersByRecursion(int n, List<Integer> result) {
        if (n > 0) {
            int size = result.size();
            int start = size == 0? 1: result.get(size - 1) + 1;
            int end = start * 10 - 1;
            for (int i = start; i <= end; i++) {
                result.add(i);
            }
            numbersByRecursion(n - 1, result);
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/delete-node-in-the-middle-of-singly-linked-list/
     * @param node: the node in the list should be deleted
     * @return: nothing
     */
    public void deleteNode(ListNode node) {
        if (node != null && node.next != null) {
            ListNode next = node.next;
            node.val = next.val;
            node.next = next.next;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/partition-array-by-odd-and-even/
     * @param nums: an array of integers
     * @return: nothing
     */
    public void partitionArray(int[] nums) {
        int len = nums.length;
        if (len > 1) {
            int left = 0;
            int right = len - 1;
            while (left < right) {
                while (left < right && nums[left] % 2 == 1) {
                    left++;
                }
                while (left < right && nums[right] % 2 == 0) {
                    right--;
                }
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/spiral-matrix/
     * @param matrix a matrix of m x n elements
     * @return an integer list
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int row = matrix.length;
        if (row > 0) {
            int column = matrix[0].length;
            if (column > 0) {
                int rowS = 0;
                int rowE = row - 1;
                int columnS = 0;
                int columnE = column - 1;
                int i = 0;
                int j = 0;
                int ward = 0;
                while (rowS <= rowE && columnS <= columnE) {
                    if (ward % 4 == 0) {
                        for (j = columnS; j <= columnE; j++) {
                            result.add(matrix[i][j]);
                        }
                        j--;
                        rowS++;
                    }
                    else if (ward % 4 == 1) {
                        for (i = rowS; i <= rowE; i++) {
                            result.add(matrix[i][j]);
                        }
                        i--;
                        columnE--;
                    }
                    else if (ward % 4 == 2) {
                        for (j = columnE; j >= columnS; j--) {
                            result.add(matrix[i][j]);
                        }
                        j++;
                        rowE--;
                    }
                    else {
                        for (i = rowE; i >= rowS; i--) {
                            result.add(matrix[i][j]);
                        }
                        i++;
                        columnS++;
                    }
                    ward++;
                }

            }
        }
        return result;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/clone-binary-tree/
     * @param root: The root of binary tree
     * @return root of new tree
     */
    public TreeNode cloneTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode newNode = new TreeNode(root.val);
        newNode.left = cloneTree(root.left);
        newNode.right = cloneTree(root.right);
        return newNode;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/reorder-array-to-construct-the-minimum-number/
     * @param nums n non-negative integer array
     * @return a string
     */
    public String minNumber(int[] nums) {
        if (nums.length == 0) {
            return "0";
        }
        else {
            quickSortNums(nums, 0, nums.length - 1);
            StringBuilder strBld = new StringBuilder();
            for (int i: nums) {
                strBld.append(i);
            }
            String num = strBld.toString();
            int strLen = num.length();
            int index = 0;
            while (index < strLen && num.charAt(index) == '0') {
                index++;
            }
            if (index == strLen) {
                return "0";
            }
            return num.substring(index);
        }
    }

    private void quickSortNums(int[] nums, int left, int right) {
        if (left < right) {
            int key = nums[left];
            int i = left;
            int j = right;
            while (i < j) {
                while (i < j && compare(nums[j], key) >= 0) {
                    j--;
                }
                nums[i] = nums[j];
                while (i < j && compare(nums[i], key) <= 0) {
                    i++;
                }
                nums[j] = nums[i];
            }
            nums[i] = key;
            quickSortNums(nums, left, i - 1);
            quickSortNums(nums, i + 1, right);
        }
    }

    private int compare(int i1, int i2) {
        if (i1 == i2) {
            return 0;
        }
        else {
            return ("" + i1 + i2).compareTo("" + i2 + i1);
        }
    }








//    /**
//     * 时间复杂度o(n1 + n2),空间复杂度o(n1)
//     * http://www.lintcode.com/zh-cn/problem/intersection-of-two-linked-lists/
//     * @param headA: the first list
//     * @param headB: the second list
//     * @return: a ListNode
//     */
//    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
//        HashMap<ListNode, Boolean> cache = new HashMap<>();
//        ListNode cursorA = headA;
//        ListNode cursorB = headB;
//
//        while (cursorA != null) {
//            cache.put(cursorA, true);
//            cursorA = cursorA.next;
//        }
//
//        while (cursorB != null) {
//            if (cache.containsKey(cursorB)) {
//                return cursorB;
//            }
//            cursorB = cursorB.next;
//        }
//        return null;
//    }

    /**
     * 时间复杂度o(n1 + n2),空间复杂度o(1)
     * http://www.lintcode.com/zh-cn/problem/intersection-of-two-linked-lists/
     * @param headA: the first list
     * @param headB: the second list
     * @return: a ListNode
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode cursor = headA;
        while (cursor.next != null) {
            cursor = cursor.next;
        }
        cursor.next = headB;
        ListNode result = cycleNode(headA);
        cursor.next = null;
        return result;
    }

    private ListNode cycleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;

        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return null;
            }

            slow = slow.next;
            fast = fast.next.next;
        }

        slow = head;
        fast = fast.next;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/spiral-matrix-ii/
     * @param n an integer
     * @return a square matrix
     */
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        if (n > 0) {
            int rowS = 0;
            int rowE = n - 1;
            int columnS = 0;
            int columnE = n - 1;
            int ward = 0;
            int index = 1;
            int i = 0;
            int j = 0;
            while (rowS <= rowE && columnS <= columnE) {
                if (ward % 4 == 0) {
                    for (j = columnS; j <= columnE; j++) {
                        matrix[i][j] = index++;
                    }
                    j--;
                    rowS++;
                }
                else if (ward % 4 == 1) {
                    for (i = rowS; i <= rowE; i++) {
                        matrix[i][j] = index++;
                    }
                    i--;
                    columnE--;
                }
                else if (ward % 4 == 2) {
                    for (j = columnE; j >= columnS; j--) {
                        matrix[i][j] = index++;
                    }
                    j++;
                    rowE--;
                }
                else {
                    for (i = rowE; i >= rowS; i--) {
                        matrix[i][j] = index++;
                    }
                    i++;
                    columnS++;
                }
                ward++;
            }
        }
        return matrix;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/container-with-most-water/
     * @param heights: an array of integers
     * @return: an integer
     */
    public int maxArea(int[] heights) {
        int max = 0;
        int len = heights.length;
        if (len > 0) {
            int left = 0;
            int right = len - 1;
            max = (right - left) * Math.min(heights[left], heights[right]);
            while (left < right) {
                int temp;
                if (heights[left] == heights[right]) {
                    temp = heights[left];
                    while (left < right && heights[left] <= temp) {
                        left++;
                    }
                    while (left < right && heights[right] <= temp) {
                        right--;
                    }
                }
                else if (heights[left] > heights[right]) {
                    temp = heights[right];
                    while (left < right && heights[right] <= temp) {
                        right--;
                    }
                }
                else if (heights[left] < heights[right]) {
                    temp = heights[left];
                    while (left < right && heights[left] <= temp) {
                        left++;
                    }
                }

                if (left < right) {
                    temp = (right - left) * Math.min(heights[left], heights[right]);
                    if (temp > max) {
                        max = temp;
                    }
                }
            }
        }
        return max;
    }







//    /**
//     * http://www.lintcode.com/zh-cn/problem/longest-substring-without-repeating-characters/
//     * @param str: a string
//     * @return: an integer
//     */
//    public int lengthOfLongestSubstring(String str) {
//        if (str == null) {
//            return 0;
//        }
//
//        int len = str.length();
//        if (len <= 1) {
//            return len;
//        }
//
//        HashMap<Character, Integer> cache = new HashMap<>();
//        int[] nextDupIndexs = new int[len];
//        Arrays.fill(nextDupIndexs, len);
//
//        for (int i = 0; i < len; i++) {
//            char c = str.charAt(i);
//            if (cache.containsKey(c)) {
//                nextDupIndexs[cache.get(c)] = i;
//            }
//            cache.put(c, i);
//        }
//
//        int min = nextDupIndexs[len - 1];
//        for (int i = len - 2; i  > -1; i--) {
//            if (nextDupIndexs[i] > min) {
//                nextDupIndexs[i] = min;
//            }
//            else {
//                min = nextDupIndexs[i];
//            }
//        }
//
//        int max = 0;
//        for (int  i = 0; i < len; i++) {
//            int temp = nextDupIndexs[i] - i;
//            if (temp > max) {
//                max = temp;
//            }
//        }
//        return max;
//    }

    /**
     * http://www.lintcode.com/zh-cn/problem/longest-substring-without-repeating-characters/
     * @param str: a string
     * @return: an integer
     */
    public int lengthOfLongestSubstring(String str) {
        int[] map = new int[256];
        int max = 0;
        int i = 0;
        int j = 0;
        for (int len = str.length(); i < len && j < len; i++) {
            while (j < len && map[str.charAt(j)] == 0) {
                map[str.charAt(j)] = 1;
                max = Math.max(max, j - i + 1);
                j++;
            }
            map[str.charAt(i)] = 0;
        }
        return max;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/the-smallest-difference/
     * @param arrA, arrB: Two integer arrays.
     * @return: Their smallest difference.
     */
    public int smallestDifference(int[] arrA, int[] arrB) {
        Arrays.sort(arrA);
        Arrays.sort(arrB);
        int lenA = arrA.length;
        int lenB = arrB.length;
        int i = 0;
        int j = 0;
        int min = Integer.MAX_VALUE;
        while (i < lenA && j < lenB) {
            int a = arrA[i];
            int b = arrB[j];
            if (a == b) {
                return 0;
            }
            else if (a > b) {
                if (min > a - b) {
                    min = a - b;
                }
                j++;
            }
            else {
                if (min > b - a) {
                    min = b - a;
                }
                i++;
            }
        }
        return min;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/permutation-sequence/
     * @param n: n
     * @param k: the kth permutation
     * @return: return the k-th permutation
     */
    public String getPermutation(int n, int k) {
        ArrayList<Integer> arr = new ArrayList<>();
        int size = 1;
        for (int i = 0; i < n; i++) {
            arr.add(i + 1);
            size *= i + 1;
        }
        return getPermutation(arr, k, size);
    }

    public String getPermutation(ArrayList<Integer> arr, int k, int pSize) {
        int size = arr.size();
        if (size == 0) {
            return "";
        }

        int subSize = pSize / size;
        int index = (k - 1) / subSize;
        return "" + arr.remove(index) + getPermutation(arr, k - subSize * index, subSize);
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/number-of-airplanes-in-the-sky/
     * @param airplanes: An interval array
     * @return: Count of airplanes are in the sky.
     */
    public int countOfAirplanes(List<Interval> airplanes) {
        int size = airplanes.size();
        if (size <= 1) {
            return size;
        }

        int curSize = 0;
        int maxSize = 0;
        ArrayList<IntervalEdge> edges = new ArrayList<>();
        for (Interval item: airplanes) {
            edges.add(new IntervalEdge(item.start, true));
            edges.add(new IntervalEdge(item.end, false));
        }
        Collections.sort(edges, new Comparator<IntervalEdge>() {
            @Override
            public int compare(IntervalEdge o1, IntervalEdge o2) {
                if (o1.index != o2.index) {
                    return o1.index - o2.index;
                }
                else {
                    return !o1.isStart? -1: 1;
                }
            }
        });

        for (int i = 0, sizeE = edges.size(); i < sizeE; i++) {
            IntervalEdge item = edges.get(i);
            if (item.isStart) {
                curSize++;
                if (curSize > maxSize) {
                    maxSize = curSize;
                }
            }
            else {
                curSize--;
            }
        }
        return maxSize;
    }

    private class IntervalEdge {
        public final int index;
        public final boolean isStart;
        public IntervalEdge(int index, boolean isStart) {
            this.index = index;
            this.isStart = isStart;
        }
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/house-robber/
     * @param arr: An array of non-negative integers.
     * return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] arr) {
        int len = arr.length;
        if (len == 0) {
            return 0;
        }

        long[] f = new long[len];
        f[0] = arr[0];
        for (int i = 1; i < len; i++) {
            long temp = 0;
            if ( i - 2 >= 0) {
                temp = f[i - 2];
            }
            temp += arr[i];
            f[i] = Math.max(f[i - 1], temp);
        }
        return f[len - 1];
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/best-time-to-buy-and-sell-stock-iv/
     * @param k: An integer
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int k, int[] prices) {
        if (k == 0 || prices.length == 0) {
            return 0;
        }

        int len = prices.length;
        if (k >= len / 2) {
            int max = 0;
            for (int i = 1; i < len; i++) {
                int delta = prices[i] - prices[i - 1];
                if (delta > 0) {
                    max += delta;
                }
            }
            return max;
        }
        else {
            int[] local = new int[k + 1];
            int[] global = new int[k + 1];
            for (int i = 1; i < len; i++) {
                int delta = prices[i] - prices[i - 1];
                for (int j = k; j >= 1; --j) {
                    local[j] = Math.max(global[j - 1] + Math.max(0, delta), local[j] + delta);
                    global[j] = Math.max(global[j], local[j]);
                }
            }
            return global[k];
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/coins-in-a-line/
     * @param n: an integer
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int n) {
        //o(n)
//        boolean a = true;
//        boolean b = false;
//        while (n-- > 0) {
//            boolean temp = (!a) || (!b);
//            a = b;
//            b = temp;
//        }
//        return b;

        //o(1)
        return n % 3 != 0;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/coins-in-a-line-ii/
     * @param values: an array of integers
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int[] values) {
        int len = values.length;
        if (len == 0) {
            return false;
        }
        else if (len <= 2) {
            return true;
        }

        int[][] scores = new int[len][2];
        scores[len - 1] = new int[]{values[len - 1], 0};
        scores[len - 2] = new int[]{values[len - 1] + values[len - 2], 0};
        for (int i = len - 3; i >= 0; --i) {
            // 对于第i步，
            // 如果当前选手取一个，则对手下一步将从i+1开始取，对手在第i+1步的最优解为score[i+1][0],
            // 当前选手在第i+1步的最优解为score[i+1][1], 则当前选手在第i步取一个的时候的最优解为values[i] + score[i+1][1]
            // 如果当前选手取二个，则对手下一步将从i + 2开始取，对手在第i+2步的最优解为score[i+2][0],
            // 当前选手在第i+2步的最优解为score[i+2][1], 则当前选手在第i步取二个的时候的最优解为values[i] + values[i + 1] + score[i+2][1]
            // 当前选手会选取以上两种情况中较优的一种
            if (values[i] + scores[i + 1][1] > values[i] + values[i + 1] + scores[i + 2][1]) {
                scores[i] = new int[]{values[i] + scores[i + 1][1], scores[i + 1][0]};
            }
            else {
                scores[i] = new int[]{values[i] + values[i + 1] + scores[i + 2][1], scores[i + 2][0]};
            }
        }
        return scores[0][0] > scores[0][1];
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/longest-increasing-continuous-subsequence/
     * @param arr an array of Integer
     * @return  an integer
     */
    public int longestIncreasingContinuousSubsequence(int[] arr) {
        int len = arr.length;
        if (len <= 1) {
            return len;
        }

        int max = 1;
        int delta = arr[1] - arr[0];
        int curNum = delta == 0? 1: 2;
        for (int i = 2; i < len; i++) {
            int tempDelta = arr[i] - arr[i - 1];
            if (tempDelta * delta > 0) {
                curNum++;
            }
            else {
                curNum = tempDelta == 0? 1: 2;
                delta = tempDelta;
            }
            if (curNum > max) {
                max = curNum;
            }
        }
        return max;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/nuts-bolts-problem/
     * @param nuts: an array of integers
     * @param bolts: an array of integers
     * @param comparator: a instance of Comparator
     * @return: nothing
     */
    public void sortNutsAndBolts(String[] nuts, String[] bolts, NBComparator comparator) {
        if (nuts == null || bolts == null || nuts.length != bolts.length) {
            return;
        }
        quickSort(nuts, bolts, 0, nuts.length - 1, comparator);
    }

    private void quickSort(String[] nuts, String[] bolts, int left, int right, NBComparator comparator) {
        if (left < right) {
            int index = partition(nuts, bolts[left], left, right, comparator);
            partition(bolts, nuts[index], left, right, comparator);
            quickSort(nuts, bolts, left, index - 1, comparator);
            quickSort(nuts, bolts, index + 1, right, comparator);
        }
    }

    private int partition(String[] strs, String key, int left, int right, NBComparator comparator) {
        for (int i = left; i <= right; i++) {
            if (comparator.cmp(strs[i], key) == 0
                    || comparator.cmp(key, strs[i]) == 0) {
                swap(strs, i, left);
                break;
            }
        }

        String leftVal = strs[left];
        int l = left;
        int r = right;
        while (l < r) {
            while (l < r && (
                    comparator.cmp(strs[r], key) == 1
                            || comparator.cmp(key, strs[r]) == -1
                    )) {
                r--;
            }
            strs[l] = strs[r];

            while (l < r && (
                    comparator.cmp(strs[l], key) == -1
                            || comparator.cmp(key, strs[l]) == 1
            )) {
                l++;
            }
            strs[r] = strs[l];
        }
        strs[l] = leftVal;
        return l;
    }

    private void swap(String[] strs, int i1, int i2) {
        String temp = strs[i1];
        strs[i1] = strs[i2];
        strs[i2] = temp;
    }

    private static class NBComparator {
        public int cmp(String a, String b) {
            if (a == null) {
                return 1;
            }
            else if (b == null) {
                return -1;
            }
            else return a.toLowerCase().compareTo(b.toLowerCase());
        }
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/maximum-gap/
     * @param nums: an array of integers
     * @return: the maximum difference
     */
    public int maximumGap(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return 0;
        }

        int min = nums[0];
        int max = nums[0];
        for (int num: nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        if (min == max) {
            return 0;
        }

        float avg = (max - min) / (float) (len - 1);
        int[] maxs = new int[len];
        int[] mins = new int[len];
        for (int num: nums) {
            int temp = (int) ((num - min) / avg);
            maxs[temp] = Math.max(maxs[temp], num);
            if (mins[temp] == 0) {
                mins[temp] = maxs[temp];
            }
            else {
                mins[temp] = Math.min(mins[temp], num);
            }
        }

        int result = 0;
        for (int i = 1; i < len; i++) {
            if (mins[i] == 0) {
                mins[i] = mins[i - 1];
                maxs[i] = maxs[i - 1];
            }
            else {
                int temp = mins[i] - maxs[i - 1];
                if (temp > result) {
                    result = temp;
                }
            }
        }

//        PrintUtil.arr(mins, 2, 2);
//        PrintUtil.arr(maxs, 2, 2);

        return result;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/kth-smallest-number-in-sorted-matrix/
     * @param matrix: a matrix of integers
     * @param k: an integer
     * @return: the kth smallest number in the matrix
     */
    public int kthSmallest(int[][] matrix, int k) {
        int row = matrix.length;
        if (row == 0) {
            return 0;
        }

        int column = matrix[0].length;
        if (k >= row * column) {
            return matrix[row - 1][column - 1];
        }

        boolean[][] visited = new boolean[row][column];
        Deque<MatrixPoint> deque = new ArrayDeque<>();
        deque.offer(new MatrixPoint(0, 0));
        visited[0][0] = true;
        for (int i = 1; i <= k; ++i) {
            MatrixPoint cur = deque.pollFirst();
            if (i == k) {
                return matrix[cur.i][cur.j];
            }

            if (cur.j < column - 1) {
                if (!visited[cur.i][cur.j + 1]) {
                    MatrixPoint right = new MatrixPoint(cur.i, cur.j + 1);
                    visited[right.i][right.j] = addToDeque(deque, right, matrix);
                }
            }

            if (cur.i < row - 1) {
                if (!visited[cur.i + 1][cur.j]) {
                    MatrixPoint bottom = new MatrixPoint(cur.i + 1, cur.j);
                    visited[bottom.i][bottom.j] = addToDeque(deque, bottom, matrix);
                }
            }

        }
        return 0;
    }

    private boolean addToDeque(Deque<MatrixPoint> deque, MatrixPoint cur, int[][] matrix) {
        if (deque.isEmpty()) {
            deque.offer(cur);
            return true;
        }
        else {
            int curVal = matrix[cur.i][cur.j];
            MatrixPoint first = deque.peekFirst();
            int firstVal = matrix[first.i][first.j];
            if (curVal <= firstVal) {
                deque.offerFirst(cur);
                return true;
            }
            else {
                MatrixPoint last = deque.peekLast();
                int lastVal = matrix[last.i][last.j];
                if (curVal >= lastVal) {
                    deque.offerLast(cur);
                    return true;
                }
            }
        }
        return false;
    }

    private class MatrixPoint {
        public int i;
        public int j;
        public MatrixPoint(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/continuous-subarray-sum/
     * @param arr an integer array
     * @return  A list of integers includes the index of the first number and the index of the last number
     */
    public ArrayList<Integer> continuousSubarraySum(int[] arr) {
        ArrayList<Integer> result = new ArrayList<>();
        int len = arr.length;
        if (len == 0) {
            return result;
        }

        int[] maxs = new int[len];
        maxs[0] = arr[0];
        int max = arr[0];
        int maxIndex = 0;
        for (int i = 1; i < len; ++i) {
            int cur = arr[i];
            int temp = Math.max(maxs[i - 1] + cur, cur);
            maxs[i] = temp;
            if (temp > max) {
                max = temp;
                maxIndex = i;
            }
        }

        if (maxIndex == 0) {
            result.add(0);
            result.add(0);
        }
        else {
            int i = maxIndex - 1;
            for (; i > -1; --i) {
                if (maxs[i] <= 0) {
                    break;
                }
            }
            result.add(i + 1);
            result.add(maxIndex);
        }
        return result;
    }






//    /**
//     * o(n^4)
//     * http://www.lintcode.com/zh-cn/problem/submatrix-sum/
//     * @param matrix an integer matrix
//     * @return the coordinate of the left-up and right-down number
//     */
//    public int[][] submatrixSum(int[][] matrix) {
//        int row = matrix.length;
//        if (row == 0) {
//            return new int[2][2];
//        }
//
//        int column = matrix[0].length;
//        int[][] sumMatrix = new int[row][column];
//        for (int i = 0; i < row; ++i) {
//            int sum = 0;
//            for (int j = 0; j < column; ++j) {
//                sum += matrix[i][j];
//                if (i == 0) {
//                    sumMatrix[i][j] = sum;
//                }
//                else {
//                    sumMatrix[i][j] = sum + sumMatrix[i - 1][j];
//                }
//                if (sumMatrix[i][j] == 0) {
//                    return new int[][] {{0, 0}, {i, j}};
//                }
//            }
//        }
//
//        for (int i = 0; i < row; ++i) {
//            for (int j = 0; j < column; ++j) {
//                for (int k = i; k < row; ++k) {
//                    for (int l = j; l < column; ++l) {
//                        if (sumOfSubMatrix(sumMatrix, i, j, k, l) == 0) {
//                            return new int[][]{{i, j}, {k, l}};
//                        }
//                    }
//                }
//            }
//        }
//
//        return new int[2][2];
//    }
//
//    private int sumOfSubMatrix(int[][] sumMatrix, int i1, int j1, int i2, int j2) {
//        int sumLeftTop = 0;
//        int sumTop = 0;
//        int sumLeft = 0;
//        if (i1 != 0 && j1 != 0) {
//            sumLeftTop = sumMatrix[i1 - 1][j1 - 1];
//            sumTop = sumMatrix[i1 - 1][j2];
//            sumLeft = sumMatrix[i2][j1 - 1];
//        }
//        else if (i1 != 0) {
//            sumTop = sumMatrix[i1 - 1][j2];
//        }
//        else if (j1 != 0) {
//            sumLeft = sumMatrix[i2][j1 - 1];
//        }
//        int sumTotal = sumMatrix[i2][j2];
//        return sumTotal - sumLeft - sumTop + sumLeftTop;
//    }


    /**
     * o(n^3)
     * http://www.lintcode.com/zh-cn/problem/submatrix-sum/
     * @param matrix an integer matrix
     * @return the coordinate of the left-up and right-down number
     */
    public int[][] submatrixSum(int[][] matrix) {
        int row = matrix.length;
        if (row == 0) {
            return new int[2][2];
        }

        int column = matrix[0].length;
        int[][] sumMatrix = new int[row + 1][column + 1];
        for (int i = 0; i < row; ++i) {
            int sum = 0;
            for (int j = 0; j < column; ++j) {
                sum += matrix[i][j];
                sumMatrix[i + 1][j + 1] = sum + sumMatrix[i][j + 1];
                if (sumMatrix[i + 1][j + 1] == 0) {
                    return new int[][] {{0, 0}, {i, j}};
                }
            }
        }

        HashMap<Integer, Integer> cache = new HashMap<>();
        for (int i = 0; i < row; ++i) {
            for (int j = i + 1; j <= row; ++j) {
                cache.clear();
                for (int k = 0; k <= column; ++k) {
                    //和之前一道题的思想一样：以o(n)的时间复杂度求和为0的连续子数组
                    int delta = sumMatrix[j][k] - sumMatrix[i][k];
                    if (cache.containsKey(delta)) {
                        return new int[][]{{i, cache.get(delta)}, {j - 1, k - 1}};
                    }
                    else {
                        cache.put(delta, k);
                    }
                }
            }
        }

        return new int[2][2];
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/minimum-size-subarray-sum/
     * @param nums: an array of integers
     * @param s: an integer
     * @return: an integer representing the minimum size of subarray
     */
    public int minimumSize(int[] nums, int s) {
        int len = nums.length;
        if (len == 0) {
            return -1;
        }

        int result = len + 1;
        int sum = 0;
        for (int i = 0, j = 0; j < len;) {
            sum += nums[j];
            if (sum >= s) {
                if (j - i + 1 < result) {
                    result = j - i + 1;
                }
                for (; i <= j;) {
                    sum -= nums[i];
                    i++;
                    if (sum >= s) {
                        if (j - i + 1 < result) {
                            result = j - i + 1;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
            j++;
        }
        return result == len + 1? -1: result;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/plus-one/
     * @param digits a number represented as an array of digits
     * @return the result
     */
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        if (len == 0) {
            return new int[]{1};
        }

        int carry = 1;
        for (int i = len - 1; i > -1; --i) {
            int temp = carry + digits[i];
            digits[i] = temp % 10;
            carry = temp / 10;
            if (carry == 0) {
                break;
            }
        }

        if (carry == 0) {
            return digits;
        }
        else {
            int[] newDigits = new int[len + 1];
            newDigits[0] = carry;
            for (int i = 0; i< len; ++i) {
                newDigits[i + 1] = digits[i];
            }
            return newDigits;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/add-binary/
     * @param a a number
     * @param b a number
     * @return the result
     */
    public String addBinary(String a, String b) {
        if (a == null || a.equals("")) {
            if (b == null || b.equals("")) {
                return "0";
            }
            else return b;
        }
        else if (b == null || b.equals("")) {
            return a;
        }

        int lenA = a.length();
        int lenB = b.length();
        int carry = 0;
        StringBuilder strBld = new StringBuilder();
        for (int i = 0, max = Math.max(lenA, lenB); i < max; ++i) {
            char cA = i < lenA? a.charAt(lenA - i - 1): '0';
            char cB = i < lenB? b.charAt(lenB - i - 1): '0';
            int temp = carry + (cA - '0') + (cB - '0');
            strBld.insert(0, temp % 2);
            carry = temp / 2;
        }
        if (carry != 0) {
            strBld.insert(0, carry);
        }
        return strBld.toString();
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/gray-code/
     * @param n a number
     * @return Gray code
     */
    public ArrayList<Integer> grayCode(int n) {
        if (n == 0) {
            ArrayList<Integer> result = new ArrayList<>();
            result.add(0);
            return result;
        }
        int highRadix = 1 << (n - 1);
        return grayCode(n, highRadix);
    }

    private ArrayList<Integer> grayCode(int n, int highRadix) {
        ArrayList<Integer> result = new ArrayList<>();
        if (n == 1) {
            result.add(0);
            result.add(1);
        }
        else {
            ArrayList<Integer> subResult = grayCode(n - 1, highRadix / 2);
            for (Integer item: subResult) {
                if (result.isEmpty() || result.get(result.size() - 1) < highRadix) {
                    result.add(item);
                    result.add(item + highRadix);
                }
                else {
                    result.add(item + highRadix);
                    result.add(item);
                }
            }
        }
        return result;
    }



    /**
     * http://www.lintcode.com/zh-cn/problem/candy/
     * @param ratings Children's ratings
     * @return the minimum candies you must give
     */
    public int candy(int[] ratings) {
        int len = ratings.length;
        if (len == 0) {
            return 0;
        }

        int[] counts = new int[len];
        Arrays.fill(counts, 1);
        int sum = 0;
        for (int i = 1; i < len; ++i) {
            if (ratings[i] > ratings[i - 1]) {
                counts[i] = counts[i - 1] + 1;
            }
        }

        for (int i = len - 1; i >= 1; --i) {
            sum += counts[i];
            if (ratings[i - 1] > ratings[i] && counts[i - 1] <= counts[i]) {
                counts[i - 1] = counts[i] + 1;
            }
        }
        sum += counts[0];

        return sum;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/reverse-integer/
     * @param n the integer to be reversed
     * @return the reversed integer
     */
    public int reverseInteger(int n) {
        int reverse = 0;
        while (n != 0) {
            int temp = reverse * 10 + n % 10;
            if (temp / 10 != reverse) {
                return 0;
            }
            reverse = temp;
            n = n / 10;
        }
        return reverse;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/divide-two-integers/
     * @param dividend the dividend
     * @param divisor the divisor
     * @return the result
     */
    public int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return dividend >= 0? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        else if (dividend == 0) {
            return 0;
        }
        else if (divisor == 1) {
            return  dividend;
        }
        else if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        int result = 0;
        long absA = dividend > 0? dividend: - (long) dividend;
        long absB = divisor > 0? divisor: -(long) divisor;
        while (absA >= absB) {
            int shift = 0;
            while (absA >= (absB << shift)) {
                shift++;
            }
            absA -= absB << (shift - 1);
            result += 1 << (shift - 1);
        }

        if ((dividend >= 0 && divisor >= 0)
                || (dividend < 0 && divisor < 0)) {
            return result;
        }
        else return -result;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/valid-palindrome/
     * @param s A string
     * @return Whether the string is a valid palindrome
     */
    public boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        else if (s.equals("")) {
            return true;
        }

        int len = s.length();
        int i = 0;
        int j = len - 1;
        while (i < j) {
            while (i < j && !isLetterOrNum(s.charAt(i))) {
                i++;
            }
            while (i < j && !isLetterOrNum(s.charAt(j))) {
                j--;
            }

            if (i < j) {
                int delta = s.charAt(i) - s.charAt(j);
                if (delta < 0) {
                    delta = -delta;
                }
                if (delta == 0 || delta == 32) {
                    i++;
                    j--;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isLetterOrNum(char c) {
        return (c >= '0' && c <= '9')
                || (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z');
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/valid-number/
     * @param s the string that represents a number
     * @return whether the string is a valid number
     */
    public boolean isNumber(String s) {
        if (s == null || s.equals("")) {
            return false;
        }

        int len = s.length();
        int start = 0;
        int end = len - 1;
        while (start < len && s.charAt(start) == ' ') {
            start++;
        }
        while (end > -1 && s.charAt(end) == ' ') {
            end--;
        }

        if (start > end) {
            return false;
        }
        else if (start == end) {
            char c = s.charAt(start);
            return c >= '0' && c <= '9';
        }

        int dotNum = 0;
        int plusSubNum = 0;
        int eNum = 0;
        for (int i = start; i <= end; i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-') {
                plusSubNum++;
                if (plusSubNum > 2) {
                    return false;
                }
                else if (i != start && (s.charAt(i - 1) != 'e' || i == end)) {
                    return false;
                }
            }
            else if (c == '.') {
                dotNum++;
                if (dotNum > 1 || eNum == 1) {
                    return false;
                }
                else if (i == start) {
                    char next = s.charAt(i + 1);
                    if (next < '0' || next > '9') {
                        return false;
                    }
                }
                else if (i == end) {
                    char prev = s.charAt(i - 1);
                    if (prev < '0' || prev > '9') {
                        return false;
                    }
                }
            }
            else if (c == 'e') {
                eNum++;
                if (eNum > 1 || i == start || i == end) {
                    return false;
                }
            }
            else if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/integer-to-roman/
     * @param n The integer
     * @return Roman representation
     */
    public String intToRoman(int n) {
        //    I    V    X    L    C    D     M
        //    1    5   10   50  100  500  1000
        String[][] radix = {
                {"","I","II","III","IV","V","VI","VII","VIII","IX"},//1,2,3,4,5,6,7,8,9
                {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"},//10,20,30,40,50,60,70,80,90
                {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"},//100....900
                {"","M","MM","MMM"}//1000,2000,3000
        };
        StringBuilder strBld = new StringBuilder();
        strBld.append(radix[3][n / 1000 % 10]);
        strBld.append(radix[2][n / 100 % 10]);
        strBld.append(radix[1][n / 10 % 10]);
        strBld.append(radix[0][n % 10]);
        return strBld.toString();
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/roman-to-integer/
     * @param s Roman representation
     * @return an integer
     */
    public int romanToInt(String s) {
        if (s == null || s.length()==0) {
            return 0;
        }
        Map<Character, Integer> m = new HashMap<Character, Integer>();
        m.put('I', 1);
        m.put('V', 5);
        m.put('X', 10);
        m.put('L', 50);
        m.put('C', 100);
        m.put('D', 500);
        m.put('M', 1000);
        int length = s.length();
        int result = m.get(s.charAt(length - 1));
        for (int i = length - 2; i >= 0; i--) {
            if (m.get(s.charAt(i + 1)) <= m.get(s.charAt(i))) {
                result += m.get(s.charAt(i));
            } else {
                result -= m.get(s.charAt(i));
            }
        }
        return result;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/count-and-say/
     * @param n the nth
     * @return the nth sequence
     */
    public String countAndSay(int n) {
        if (n == 0) {
            return "";
        }
        else if (n == 1) {
            return "1";
        }

        ArrayList<Character> chars = new ArrayList<>();
        chars.add('1');
        chars.add('1');
        for (int i = 2; i < n; i++) {
            int sameCharCount = 1;
            for (int j = 1; j < chars.size();) {
                char cur = chars.get(j);
                char last = chars.get(j - 1);
                if (cur == last) {
                    sameCharCount++;
                    chars.remove(j);
                }
                else {
                    String numStr = "" + sameCharCount;
                    for (int k = 0, len = numStr.length(); k < len; k++) {
                        chars.add(j - 1, numStr.charAt(k));
                        j++;
                    }

                    sameCharCount = 1;
                    j++;
                }

                if (j == chars.size()) {
                    String numStr = "" + sameCharCount;
                    for (int k = 0, len = numStr.length(); k < len; k++) {
                        chars.add(j - 1, numStr.charAt(k));
                        j++;
                    }
                }
            }
        }
        StringBuilder strBld = new StringBuilder();
        for (Character c: chars) {
            strBld.append(c);
        }
        return strBld.toString();
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/simplify-path/
     * @param path the original path
     * @return the simplified path
     */
    public String simplifyPath(String path) {
        ArrayList<String> pathNodes = new ArrayList<>();
        int len = path.length();
        int i = 0;
        int j = 0;
        while (i < len && j < len) {
            while (i < len && path.charAt(i) == '/') {
                i++;
            }
            j = i;
            while (j < len && path.charAt(j) != '/') {
                j++;
            }
            if (i < len) {
                String pathNode = path.substring(i, j);
                if (pathNode.equals(".")) {
                }
                else if (pathNode.equals("..")) {
                    if (pathNodes.size() > 0) {
                        pathNodes.remove(pathNodes.size() - 1);
                    }
                }
                else {
                    pathNodes.add(pathNode);
                }
            }
            i = j;
        }


        if (pathNodes.isEmpty()) {
            return "/";
        }
        else {
            StringBuilder strBld = new StringBuilder();
            for (String pathNode: pathNodes) {
                strBld.append('/').append(pathNode);
            }
            return strBld.toString();
        }
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/length-of-last-word/
     * @param s A string
     * @return the length of last word
     */
    public int lengthOfLastWord(String s) {
        if (s == null) {
            return 0;
        }

        int result = 0;
        int len = s.length();
        int j = len - 1;
        while (j > -1 && s.charAt(j) == ' ') {
            j--;
        }
        result = j;
        while (j > -1 && s.charAt(j) != ' ') {
            j--;
        }
        result -= j;
        return result;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/valid-parentheses/
     * @param s A string
     * @return whether the string is a valid parentheses
     */
    public boolean isValidParentheses(String s) {
        Stack<Character> brackets = new Stack<>();
        for (int i = 0, len = s.length(); i< len; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                if (brackets.isEmpty()) {
                    brackets.push(c);
                }
                else {
                    Character top = brackets.peek();
                    if (top == ')' || top == ']' || top == '}') {
                        return false;
                    }
                    else {
                        brackets.push(c);
                    }
                }
            }
            else {
                if (brackets.isEmpty()) {
                    return false;
                }
                else {
                    Character top = brackets.peek();
                    if ((top == '(' && c != ')')
                            || (top == '[' && c != ']')
                            || (top == '{' && c != '}')) {
                        return false;
                    }
                    else {
                        brackets.pop();
                    }
                }
            }
        }
        return brackets.isEmpty();
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/evaluate-reverse-polish-notation/
     * @param tokens The Reverse Polish Notation
     * @return the value
     */
    public int evalRPN(String[] tokens) {
        if (tokens.length == 0) {
            return 0;
        }

        Stack<Integer> params = new Stack<>();
        for (String token: tokens) {
            if (token.equals("+")) {
                int param2 = params.pop();
                int param1 = params.pop();
                params.push(param1 + param2);
            }
            else if (token.equals("-")) {
                int param2 = params.pop();
                int param1 = params.pop();
                params.push(param1 - param2);
            }
            else if (token.equals("*")) {
                int param2 = params.pop();
                int param1 = params.pop();
                params.push(param1 * param2);
            }
            else if (token.equals("/")) {
                int param2 = params.pop();
                int param1 = params.pop();
                params.push(param1 / param2);
            }
            else {
                Integer i = Integer.parseInt(token);
                params.push(i);
            }
        }
        return params.pop();
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/letter-combinations-of-a-phone-number/
     * @param digits A digital string
     * @return all posible letter combinations
     */
    public ArrayList<String> letterCombinations(String digits) {
        ArrayList<String> result = new ArrayList<>();
        int len = digits.length();
        if (len > 0) {
            char[][] boards = new char[8][];
            boards[0] = new char[]{'a', 'b', 'c'};
            boards[1] = new char[]{'d', 'e', 'f'};
            boards[2] = new char[]{'g', 'h', 'i'};
            boards[3] = new char[]{'j', 'k', 'l'};

            boards[4] = new char[]{'m', 'n', 'o'};
            boards[5] = new char[]{'p', 'q', 'r', 's'};
            boards[6] = new char[]{'t', 'u', 'v'};
            boards[7] = new char[]{'w', 'x', 'y', 'z'};
            for (int i = len - 1; i > -1; i--) {
                int index = digits.charAt(i) - '0';
                char[] board = boards[index - 2];
                if (result.isEmpty()) {
                    for (char c: board) {
                        result.add("" + c);
                    }
                }
                else {
                    for (int j = result.size() - 1; j > -1; j--) {
                        String strJ = result.get(j);
                        result.remove(j);
                        for (int k = board.length - 1; k > -1; k--) {
                            result.add(j, board[k] + strJ);
                        }
                    }
                }
            }
        }
        return result;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/restore-ip-addresses/
     * @param s the IP string
     * @return All possible valid IP addresses
     */
    public ArrayList<String> restoreIpAddresses(String s) {
        ArrayList<String> result = new ArrayList<>();
        int len = s.length();
        if (len >= 4) {
            int[] mins = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 3};
            int[] maxs = {0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 3, 3};
            int min = mins[len];
            int max = maxs[len];
            for (int i = Math.max(min, len - 3 * max), endI = Math.min(max, len - 3 * min); i <= endI; i++) {
                int node1 = Integer.parseInt(s.substring(0, i));
                if (node1 <= 255 && (s.charAt(0) != '0' || i == 1)) {
                    for (int j = Math.max(i + min, len - 2 * max), endJ = Math.min(i + max, len - 2 * min); j <= endJ; j++) {
                        int node2 = Integer.parseInt(s.substring(i, j));
                        if (node2 <= 255 && (s.charAt(i) != '0' || j - i == 1)) {
                            for (int k = Math.max(j + min, len - max), endK = Math.min(j + max, len - min); k <= endK; k++) {
                                int node3 = Integer.parseInt(s.substring(j, k));
                                if (node3 <= 255 && (s.charAt(j) != '0' || k - j == 1)) {
                                    int node4 = Integer.parseInt(s.substring(k));
                                    if (node4 <= 255 && (s.charAt(k) != '0' || len - k == 1)) {
                                        result.add(node1 + "." + node2 + "." + node3 + "." + node4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/generate-parentheses/
     * @param n n pairs
     * @return All combinations of well-formed parentheses
     */
    public ArrayList<String> generateParenthesis(int n) {
        ArrayList<String> result = new ArrayList<>();
        if (n > 0) {
            StringBuilder strBld = new StringBuilder();
            generateParenthesis(n, 0, 0, strBld, result);
        }
        return result;
    }

    private void generateParenthesis(int n, int leftNum, int rightNum, StringBuilder strBld, ArrayList<String> result) {
        if (leftNum < n) {
            strBld.append('(');
            generateParenthesis(n, leftNum + 1, rightNum, strBld, result);
            strBld.deleteCharAt(strBld.length() - 1);
        }

        if (leftNum > rightNum) {
            strBld.append(')');
            if (leftNum == rightNum + 1 && n == leftNum) {
                result.add(strBld.toString());
            }
            else {
                generateParenthesis(n, leftNum, rightNum + 1, strBld, result);
            }
            strBld.deleteCharAt(strBld.length() - 1);
        }
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/powx-n/
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        else if (n == -1) {
            return 1 / x;
        }
        else if (x == 0) {
            return n > 0? 0 : 1 / x;
        }
        else {
            double temp = myPow(x, n / 2);
            if (n % 2 == 0) {
                return temp * temp;
            }
            else if (n > 0) {
                return temp * temp * x;
            }
            else {
                return temp * temp * 1 / x;
            }
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/scramble-string/
     * @param s1 A string
     * @param s2 Another string
     * @return whether s2 is a scrambled string of s1
     */
    public boolean isScramble(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 != len2) {
            return false;
        }
        else if (len1 == 0 || s1.equals(s2)) {
            return true;
        }

        int[][][] cache = new int[len1][len1][len1 + 1];
        return isScramble(s1, 0, s2, 0, len1, cache);
    }

    private boolean isScramble(String s1, int start1, String s2, int start2, int subLen, int[][][] cache) {
        int cacheResult = cache[start1][start2][subLen];
        if (cacheResult != 0) {
            return cacheResult == 1;
        }

        boolean flag = false;
        int equalType = isSortedSubStrEqual(s1, start1, s2, start2, subLen);
        if (equalType == 1) {
            flag = true;
        }
        else if (equalType == 0) {
            for (int i = 1; i < subLen; i++) {
                if (isScramble(s1, start1, s2, start2, i, cache) && isScramble(s1, start1 + i, s2, start2 + i, subLen - i, cache)) {
                    flag = true;
                    break;
                }

                if (isScramble(s1, start1, s2, start2 + subLen - i, i, cache) && isScramble(s1, start1 + i, s2, start2, subLen - i, cache)) {
                    flag = true;
                    break;
                }
            }
        }
        cache[start1][start2][subLen] = flag? 1: -1;
        return flag;
    }

    private int isSortedSubStrEqual(String s1, int start1, String s2, int start2, int subLen) {
        char[] chars1 = new char[subLen];
        char[] chars2 = new char[subLen];
        s1.getChars(start1, start1 + subLen, chars1, 0);
        s2.getChars(start2, start2 + subLen, chars2, 0);

        int flag = 1;
        for (int i = 0; i < subLen; i ++) {
            if (chars1[i] != chars2[i]) {
                flag = -1;
            }
        }

        if (flag == 1) {
            return 1;
        }

        Arrays.sort(chars1);
        Arrays.sort(chars2);
        for (int i = 0; i < subLen; i ++) {
            if (chars1[i] != chars2[i]) {
                return -1;
            }
        }
        return 0;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/number-of-islands/
     * @param grid a boolean 2D matrix
     * @return an integer
     */
    public int numIslands(boolean[][] grid) {
        int row = grid.length;
        if (row == 0) {
            return 0;
        }
        int column = grid[0].length;

        int num = 0;
        boolean[][] visited = new boolean[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (grid[i][j] && !visited[i][j]) {
                    num++;
                    visitGrid(grid, i, j, visited);
                }
            }
        }
        return num;
    }

    private void visitGrid(boolean[][] grid, int x, int y, boolean[][] visited) {
        int row = grid.length;
        int column = grid[0].length;
        if (x < 0 || x >= row || y < 0 || y >= column || visited[x][y]) {
            return;
        }

        visited[x][y] = true;

        if (grid[x][y]) {
            if (x > 0) {
                visitGrid(grid, x - 1, y, visited);
            }
            if (x < row) {
                visitGrid(grid, x + 1, y, visited);
            }
            if (y > 0) {
                visitGrid(grid, x, y - 1, visited);
            }
            if (y < column) {
                visitGrid(grid, x, y + 1, visited);
            }
        }
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/maximal-square/
     * @param matrix: a matrix of 0 and 1
     * @return: an integer
     */
    public int maxSquare(int[][] matrix) {
        int row = matrix.length;
        if (row == 0) {
            return 0;
        }

        int column = matrix[0].length;
        int[][] sumMatrix = new int[row + 1][column + 1];
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column; j++) {
                sumMatrix[i][j] = sumMatrix[i - 1][j] + sumMatrix[i][j - 1] - sumMatrix[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        int sum = sumMatrix[row][column];
        if (sum <= 1) {
            return sum;
        }
        else if (row == column && sum == row * row) {
            return sum;
        }
        else {
            int maxLen = Math.min(row, column);
            for (int i = maxLen; i >= 1; i--) {
                int ii = i * i;
                for (int j = i; j <= row; j++) {
                    for (int k = i; k <= column; k++) {
                        int temp = sumMatrix[j][k] - sumMatrix[j][k - i] - sumMatrix[j - i][k] + sumMatrix[j - i][k - i];
                        if (temp == ii) {
                            return ii;
                        }
                    }
                }
            }
        }
        return 0;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/copy-books/
     * @param pages: an array of integers
     * @param k: an integer
     * @return: an integer
     */
    public int copyBooks(int[] pages, int k) {
        int len = pages.length;
        if (len == 0) {
            return 0;
        }

        int[][] dp = new int[k + 1][len];
        for (int i = 0; i < len; i++) {
            dp[1][i] = i == 0? pages[0]: dp[1][i - 1] + pages[i];
        }

        for (int i = 2; i <= k; i++) {
            dp[i][1] = dp[1][1];
            int left = 0;
            int right = 1;
            while (right < len) {
                int delta = dp[1][right] - dp[1][left];
                int temp = Math.max(delta, dp[i - 1][left]);
                if (dp[i][right] == 0) {
                    dp[i][right] = temp;
                }
                else {
                    dp[i][right] = Math.min(temp, dp[i][right]);
                }

                if (left < right && dp[i - 1][left] < delta) {
                    left++;
                }
                else {
                    right++;
                    if (left > 0) {
                        left--;
                    }
                }
            }
        }
        return dp[k][len - 1];
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/segment-tree-build-ii/
     * @param arr: a list of integer
     * @return: The root of Segment Tree
     */
    public SegmentTreeNode build(int[] arr) {
        int len = arr.length;
        if (len == 0) {
            return null;
        }
        return build(arr, 0, len - 1);
    }

    private SegmentTreeNode build(int[] arr, int left, int right) {
        SegmentTreeNode newNode = new SegmentTreeNode(left, right, arr[left]);
        if (left < right)  {
            int mid = (left + right) / 2;
            newNode.left = build(arr, left, mid);
            newNode.right = build(arr, mid + 1, right);
            newNode.max = Math.max(newNode.left.max, newNode.right.max);
        }
        return newNode;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/cosine-similarity/
     * @param arrA: An integer array.
     * @param arrB: An integer array.
     * @return: Cosine similarity.
     */
    public double cosineSimilarity(int[] arrA, int[] arrB) {
        int len = arrA.length;
        if (len != arrB.length || len == 0) {
            return 2.0;
        }

        long totalAB = 0;
        long totalAA = 0;
        long totalBB = 0;
        for (int i = 0; i < len; i++) {
            int a = arrA[i];
            int b = arrB[i];
            totalAB += a * b;
            totalAA += a * a;
            totalBB += b * b;
        }

        if (totalAA == 0 || totalBB == 0) {
            return 2.0;
        }
        else return totalAB / (Math.pow(totalAA * totalBB, 0.5));
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/reverse-nodes-in-k-group/
     * @param head a ListNode
     * @param k an integer
     * @return a ListNode
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }

        ListNode newHead = new ListNode(0);
        ListNode cur = newHead;
        ListNode slow = head;
        ListNode fast = head;
        while (slow != null) {
            int tempK = k;
            while (tempK-- > 1 && fast != null) {
                fast = fast.next;
            }
            if (fast == null) {
                //剩余长度不够k
                cur.next = slow;
                slow = null;
            }
            else {
                fast = fast.next;
                ListNode tempHead = null;
                ListNode temp = slow;
                while (temp != fast) {
                    ListNode next = temp.next;
                    temp.next = null;
                    temp.next = tempHead;
                    tempHead = temp;
                    temp = next;
                }
                cur.next = tempHead;
                cur = slow;
                slow = fast;
            }
        }

        return newHead.next;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/swap-nodes-in-pairs/
     * @param head a ListNode
     * @return a ListNode
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode newHead = new ListNode(0);
        ListNode cur = newHead;
        ListNode slow = head;
        ListNode fast = head;
        while (slow != null) {
            if (fast.next == null) {
                cur.next = slow;
                slow = null;
            }
            else {
                fast = fast.next;
                slow.next = null;
                cur.next = fast;
                fast = fast.next;
                cur.next.next = slow;
                cur = slow;
                slow = fast;
            }
        }
        return newHead.next;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/remove-linked-list-elements/
     * @param head a ListNode
     * @param val an integer
     * @return a ListNode
     */
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        ListNode cur = head;
        ListNode next = cur.next;
        while (next != null) {
            if (next.val == val) {
                cur.next = next.next;
            }
            else {
                cur = next;
            }
            next = next.next;
        }
        return head.val == val? head.next: head;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/flatten-binary-tree-to-linked-list/
     * @param root: a TreeNode, the root of the binary tree
     * @return: nothing
     */
    public void flatten(TreeNode root) {
        if (root != null) {
            TreeNode left = root.left;
            TreeNode right = root.right;
            if (left != null) {
                flatten(left);
                root.left = null;
                root.right = left;
            }

            if (right != null) {
                flatten(right);
                if (left != null) {
                    while (left.right != null) {
                        left = left.right;
                    }
                    left.right = right;
                }
                else {
                    root.right = right;
                }
            }
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/sort-integers/
     * @param arr an integer array
     * @return void
     */
    public void sortIntegers(int[] arr) {
        int len = arr.length;
        if (len > 1) {
            for (int i = 0; i < len - 1; i++) {
                int minI = i;
                for (int j = i + 1; j < len; j++) {
                    if (arr[minI] > arr[j]) {
                        minI = j;
                    }
                }
                if (minI > i) {
                    int temp = arr[minI];
                    arr[minI] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/sort-integers-ii/
     * @param arr an integer array
     * @return void
     */
    public void sortIntegers2(int[] arr) {
        int len = arr.length;
        if (len > 1) {
//            quickSort(arr, 0, len - 1);
            mergeSort(arr, 0, len - 1);
        }
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int key = arr[left];
            int l = left;
            int r = right;
            while (l < r) {
                while (l < r && arr[r] >= key) {
                    r--;
                }
                arr[l] = arr[r];

                while (l < r && arr[l] <= key) {
                    l++;
                }
                arr[r] = arr[l];
            }
            arr[l] = key;
            quickSort(arr, left, l - 1);
            quickSort(arr, l + 1, right);
        }
    }

    private void mergeSort(int arr[], int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            int i = left;
            int j = mid + 1;
            int index = 0;
            int[] cache = new int[right - left + 1];
            while (i <= mid && j <= right) {
                if (arr[i] < arr[j]) {
                    cache[index++] = arr[i++];
                }
                else {
                    cache[index++] = arr[j++];
                }
            }
            if (i <= mid) {
                while (i <= mid) {
                    cache[index++] = arr[i++];
                }
            }
            else {
                while (j <= right) {
                    cache[index++] = arr[j++];
                }
            }

            for (int k = 0, len = cache.length; k < len; k++) {
                arr[left + k] = cache[k];
            }
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/identical-binary-tree/
     * @param a, b, the root of binary trees.
     * @return true if they are identical, or false.
     */
    public boolean isIdentical(TreeNode a, TreeNode b) {
        if (a == null || b == null) {
            return a == b;
        } else if (a == b) {
            return true;
        }

        return a.val.equals(b.val) && isIdentical(a.left, b.left) && isIdentical(a.right, b.right);
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/surrounded-regions/
     * @param board a 2D board containing 'X' and 'O'
     * @return void
     */
    public void surroundedRegions(char[][] board) {
        int row = board.length;
        if (row == 0) {
            return;
        }

        int column = board[0].length;
        if (row <= 2 || column <= 2) {
            return;
        }

        for (int i = 0; i < row; i++) {
            if (board[i][0] == charO && board[i][1] == charO) {
                visitBoard(board, i, 1);
            }
            if (board[i][column - 1] == charO && board[i][column - 2] == charO) {
                visitBoard(board, i, column - 2);
            }
        }
        for (int j = 0; j < column; j++) {
            if (board[0][j] == charO && board[1][j] == charO) {
                visitBoard(board, 1, j);
            }
            if (board[row - 1][j] == charO && board[row - 2][j] == charO) {
                visitBoard(board, row - 2, j);
            }
        }

        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (board[i][j] == charO) {
                    board[i][j] = 'X';
                }
                else if (board[i][j] == '1') {
                    board[i][j] = charO;
                }
            }
        }
    }

    private static final char charO = 'O';

    private void visitBoard(char[][] board, int i, int j) {
        int row = board.length;
        int column = board[0].length;
        if (i == 0 || i == row - 1 || j == 0 || j == column - 1) {
            return;
        }

        board[i][j] = '1';
        if (i - 1 > 0 && board[i - 1][j] == charO) {
            visitBoard(board, i - 1, j);
        }
        if (i + 1 < row - 1 && board[i + 1][j] == charO) {
            visitBoard(board, i + 1, j);
        }
        if (j - 1 > 0 && board[i][j - 1] == charO) {
            visitBoard(board, i, j - 1);
        }
        if (j + 1 < column - 1 && board[i][j + 1] == charO) {
            visitBoard(board, i, j + 1);
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/binary-tree-paths/
     * @param root the root of the binary tree
     * @return all root-to-leaf paths
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root != null) {
            visiteTree(root, "", result);
        }
        return result;
    }

    private void visiteTree(TreeNode node, String str, List<String> result) {
        str += str.equals("")? node.val: "->" + node.val;
        if (node.left == null && node.right == null) {
            result.add(str);
        }
        else {
            if (node.left != null) {
                visiteTree(node.left, str, result);
            }

            if (node.right != null) {
                visiteTree(node.right, str, result);
            }
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/happy-number/
     * @param n an integer
     * @return true if this is a happy number or false
     */
    public boolean isHappy(int n) {
        HashMap<Long, Boolean> cache = new HashMap<>();
        long temp = n;
        while (!cache.containsKey(temp = happChange(temp)) && temp != 1) {
            cache.put(temp, true);
        }
        return temp == 1;
    }

    private long happChange(long n) {
        long total = 0;
        while (n > 0) {
            long temp = n % 10;
            total += temp * temp;
            n /= 10;
        }
        return total;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/wiggle-sort-ii/
     * @param nums a list of integer
     * @return void
     */
    public void wiggleSort(int[] nums) {
        int len = nums.length;
        if (len > 1) {
            findMid(nums, 0, len - 1);

            int mid = (len - 1) / 2;
            int key = nums[mid];

//            XYLog.d("len=" + len);
//            XYLog.d("mid=" + mid);
//            XYLog.d("midVal=" + key);
//
//            PrintUtil.arr(Arrays.copyOfRange(nums, 0, (len - 1) / 2 + 1), 2, 1);
//            PrintUtil.arr(Arrays.copyOfRange(nums, (len - 1) / 2 + 1, len), 2, 1);


            //使用了o(n)的额外空间
//            int[] cache = new int[len];
//            int leftMinIndex = 0;
//            int rightMinIndex = (len - 1) / 2 * 2;
//            int leftMaxIndex = 1;
//            int rightMaxIndex = len / 2 * 2 - 1;
//            for (int i = 0; i <= mid; i++) {
//                if (nums[i] == key) {
//                    cache[leftMinIndex] = key;
//                    leftMinIndex += 2;
//                }
//                else {
//                    cache[rightMinIndex] = nums[i];
//                    rightMinIndex -= 2;
//                }
//
//                int rI = len - 1 - i;
//                if (rI > mid) {
//                    if (nums[rI] == key) {
//                        cache[rightMaxIndex] = key;
//                        rightMaxIndex -= 2;
//                    }
//                    else {
//                        cache[leftMaxIndex] = nums[rI];
//                        leftMaxIndex += 2;
//                    }
//                }
//            }
//            System.arraycopy(cache, 0, nums, 0, len);



            //不使用额外空间
            int i = 0;
            int j = 0;
            int k = len - 1;
            while (j <= k) {
                int sJ = (1 + j * 2) % (len | 1);
                if (nums[sJ] > key) {
                    specialSwap(nums, i++, j++);
                }
                else if (nums[sJ] < key) {
                    specialSwap(nums, j, k--);
                }
                else {
                    j++;
                }
            }




        }
    }

    private void specialSwap(int[] nums, int i, int j) {
//        PrintUtil.arr(nums, 1, 2);

        int len = nums.length;
        int sI = (1 + i * 2) % (len | 1);
        int sJ = (1 + j * 2) % (len | 1);
        int temp = nums[sI];
        nums[sI] = nums[sJ];
        nums[sJ] = temp;

//        String[] tempArr = new String[len];
//        Arrays.fill(tempArr, " ");
//        tempArr[sI] = "" + nums[sI];
//        tempArr[sJ] = "" + nums[sJ];
//        PrintUtil.arr(tempArr, 1, 2);
//        System.out.println();
    }

    private void findMid(int[] nums, int left, int right) {
        if (left < right) {
            int len = nums.length;
            int mid = (len - 1) / 2;

            int key = nums[left];
            int l = left;
            int r = right;
            while (l < r) {
                while (l < r && nums[r] >= key) {
                    r--;
                }
                nums[l] = nums[r];

                while (l < r && nums[l] <= key) {
                    l++;
                }
                nums[r] = nums[l];
            }
            nums[l] = key;

            if (l < mid) {
                findMid(nums, l + 1, right);
            }
            else if (l > mid) {
                findMid(nums, left, l - 1);
            }
        }
    }

    public static void main(String[] args) {
        Solutions184_564 solutions = new Solutions184_564();

        /**
         摆动排序 II   [中等]
         http://www.lintcode.com/zh-cn/problem/wiggle-sort-ii/
         答案参考：http://bookshadow.com/weblog/2015/12/31/leetcode-wiggle-sort-ii/
         给你一个数组nums，将它重排列如下形式
         nums[0] < nums[1] > nums[2] < nums[3]....
         注意事项
         你可以认为每个输入都有合法解
         样例
         给出 nums = [1, 5, 1, 1, 6, 4],一种方案为 [1, 4, 1, 5, 1, 6].
         给出 nums = [1, 3, 2, 2, 3, 1],一种方案为 [2, 3, 1, 3, 1, 2].
         挑战
         O(N)时间复杂度 O(1)额外空间
         */
//        int[] nums = {1,2,1,2,1,1,2,2,1};
////        int[] nums = {1, 3, 2, 2, 3, 1};
////        int[] nums = {1, 5, 1, 1, 6, 4};
////        int[] nums = {2, 8, 4, 2, 1, 2, 6, 3, 1, 5, 7, 3, 4};
////        int[] nums = {1,2,1,2,1,2,1,2,1,2};
////        int[] nums = DataUtil.getIntArr("data/wiggle-sort-ii-66.in");
//        int[] arr0 = new int[nums.length];
//        System.arraycopy(nums, 0, arr0, 0, nums.length);
//        solutions.wiggleSort(nums);
//        XYLog.d(nums);
//
//        int[] arr1 = nums;
//        Arrays.sort(arr0);
//        Arrays.sort(arr1);
//        XYLog.d(arr0);
//        XYLog.d(arr1);






        /**
         迷你推特   [中等]
         http://www.lintcode.com/zh-cn/problem/mini-twitter/
         实现一个迷你的推特，支持下列几种方法
         1.postTweet(user_id, tweet_text). 发布一条推特.
         2.getTimeline(user_id). 获得给定用户最新发布的十条推特，按照发布时间从最近的到之前排序
         3.getNewsFeed(user_id). 获得给定用户的朋友或者他自己发布的最新十条推特，从发布时间最近到之前排序
         4.follow(from_user_id, to_user_id). from_user_id 关注 to_user_id.
         5.unfollow(from_user_id, to_user_id). from_user_id 取消关注 to_user_id.
         */
//        MiniTwitter.test();



        /**
         单词计数 (Map Reduce版本)   [容易]
         http://www.lintcode.com/zh-cn/problem/word-count-map-reduce/
         */
//        new WordCount();



        /**
         停车场   [困难]   [未通过]   [很可能时数据问题]
         http://www.lintcode.com/zh-cn/problem/parking-lot/
         设计一个停车场
         1. 一共有n层，每层m列，每列k个位置
         2.停车场可以停摩托车，公交车，汽车
         3.停车位分别有摩托车位，汽车位，大型停车位
         4.每一列，摩托车位编号范围为[0,k/4),汽车停车位编号范围为[k/4,k/4*3),大型停车位编号范围为[k/4*3,k)
         5.一辆摩托车可以停在任何停车位
         6.一辆汽车可以停在一个汽车位或者大型停车位
         7.一辆公交车可以停在一列里的连续5个大型停车位。
         */
//        ParkingLot.test();



        /**
         快乐数   [容易]
         http://www.lintcode.com/zh-cn/problem/happy-number/
         写一个算法来判断一个数是不是"快乐数"。
         一个数是不是快乐是这么定义的：对于一个正整数，每一次将该数替换为他每个位置上的数字的平方和，
         然后重复这个过程直到这个数变为1，或是无限循环但始终变不到1。如果可以变为1，那么这个数就是快乐数。
         样例
         19 就是一个快乐数。
         1^2 + 9^2 = 82
         8^2 + 2^2 = 68
         6^2 + 8^2 = 100
         1^2 + 0^2 + 0^2 = 1
         */
//        XYLog.d(solutions.isHappy(18));





        /**
         二叉树的所有路径   [容易]
         http://www.lintcode.com/zh-cn/problem/binary-tree-paths/
         给一棵二叉树，找出从根节点到叶子节点的所有路径。
         */
//        TreeNode tree = TreeNodeFactory.build("1,2,3,#,5");
//        XYLog.d(tree);
//        XYLog.d(solutions.binaryTreePaths(tree));



        /**
         被围绕的区域   [中等]
         http://www.lintcode.com/zh-cn/problem/surrounded-regions/
         给一个二维的矩阵，包含 'X' 和 'O', 找到所有被 'X' 围绕的区域，并用 'X' 填充满。
         样例
         给出二维矩阵：
         X X X X
         X O O X
         X X O X
         X O X X
         把被 'X' 围绕的区域填充之后变为：
         X X X X
         X X X X
         X X X X
         X O X X
         */
//        String[] boardStrs = {"XOOXXXOXOO","XOXXXXXXXX","XXXXOXXXXX","XOXXXOXXXO","OXXXOXOXOX","XXOXXOOXXX","OXXOOXOXXO","OXXXXXOXXX","XOOXXOXXOO","XXXOOXOXXO"};
//        char[][] board = new char[boardStrs.length][];
//        for (int i = 0, len = board.length; i < len; i++) {
//            board[i] = boardStrs[i].toCharArray();
//        }
//        PrintUtil.matrix(board, 2);
//        solutions.surroundedRegions(board);
//        PrintUtil.matrix(board, 2);




        /**
         单词的添加与查找   [中等]
         http://www.lintcode.com/zh-cn/problem/add-and-search-word/
         设计一个包含下面两个操作的数据结构：addWord(word), search(word)
         addWord(word)会在数据结构中添加一个单词。而search(word)则支持普通的单词查询或是只包含.和a-z的简易正则表达式的查询。
         */
//        WordDictionary dictionary = new WordDictionary();
//        dictionary.addWord("word");
//        XYLog.d(dictionary.search("word"));
//        XYLog.d(dictionary.search("wordd"));
//        XYLog.d(dictionary.search("wor"));
//        XYLog.d(dictionary.search("wo.d"));
//        XYLog.d(dictionary.search("..rd"));



        /**
         等价二叉树   [容易]
         http://www.lintcode.com/zh-cn/problem/identical-binary-tree/
         检查两棵二叉树是否等价。等价的意思是说，首先两棵二叉树必须拥有相同的结构，并且每个对应位置上的节点上的数都相等。
         */
//        TreeNode tree1 = TreeNodeFactory.build("1,2,3,4");
//        TreeNode tree2 = TreeNodeFactory.build("1,2,3,4");
//        TreeNode tree3 = TreeNodeFactory.build("1,2,3,#,4");
//        XYLog.d(solutions.isIdentical(tree1, tree2));
//        XYLog.d(solutions.isIdentical(tree1, tree3));



        /**
         整数排序 II   [入门]
         http://www.lintcode.com/zh-cn/problem/sort-integers-ii/
         给一组整数，按照升序排序。使用归并排序，快速排序，堆排序或者任何其他 O(n log n) 的排序算法。
         */
//        int[] arr = {3,2,1,4,5};
//        solutions.sortIntegers2(arr);
//        XYLog.d(arr);



        /**
         整数排序   [入门]
         http://www.lintcode.com/zh-cn/problem/sort-integers/
         给一组整数，按照升序排序，使用选择排序，冒泡排序，插入排序或者任何 O(n2) 的排序算法。
         样例
         对于数组 [3, 2, 1, 4, 5], 排序后为：[1, 2, 3, 4, 5]。
         */
//        int[] arr = {3,2,1,4,5};
//        solutions.sortIntegers(arr);
//        XYLog.d(arr);




        /**
         将二叉树拆成链表   [容易]
         http://www.lintcode.com/zh-cn/problem/flatten-binary-tree-to-linked-list/
         将一棵二叉树按照前序遍历拆解成为一个假链表。所谓的假链表是说，用二叉树的 right 指针，来表示链表中的 next 指针。
         注意事项
         不要忘记将左儿子标记为 null，否则你可能会得到空间溢出或是时间溢出。
         */
//        TreeNode tree = TreeNodeFactory.build("1,2,5,3,4,#,6");
//        XYLog.d(tree);
//        solutions.flatten(tree);
//        XYLog.d(tree);


        /**
         删除链表中的元素   [入门]
         http://www.lintcode.com/zh-cn/problem/remove-linked-list-elements/
         删除链表中等于给定值val的所有节点。
         样例
         给出链表 1->2->3->3->4->5->3, 和 val = 3, 你需要返回删除3之后的链表：1->2->4->5。
         */
//        ListNode head = ListNodeFactory.build("1->2->3->3->4->5->3");
//        int val = 3;
//        XYLog.d(solutions.removeElements(head, val));



        /**
         两两交换链表中的节点   [容易]
         http://www.lintcode.com/zh-cn/problem/swap-nodes-in-pairs/
         给一个链表，两两交换其中的节点，然后返回交换后的链表。
         样例
         给出 1->2->3->4, 你应该返回的链表是 2->1->4->3。
         挑战
         你的算法只能使用常数的额外空间，并且不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
         */
//        ListNode head = ListNodeFactory.build("1->2->3->4");
//        XYLog.d(solutions.swapPairs(head));






        /**
         K组翻转链表   [困难]
         http://www.lintcode.com/zh-cn/problem/reverse-nodes-in-k-group/
         给你一个链表以及一个k,将这个链表从头指针开始每k个翻转一下。
         链表元素个数不是k的倍数，最后剩余的不用翻转。
         样例
         给出链表 1->2->3->4->5
         k = 2, 返回 2->1->4->3->5
         k = 3, 返回 3->2->1->4->5
         */
//        ListNode head = ListNodeFactory.build("1->2->3->4->5");
//        int k = 3;
//        XYLog.d(solutions.reverseKGroup(head, k));






        /**
         余弦相似度   [容易]
         http://www.lintcode.com/zh-cn/problem/cosine-similarity/
         公式：http://www.lintcode.com/media/problem/cosine-similarity.png
         给你两个相同大小的向量 A B，求出他们的余弦相似度
         如果余弦相似不合法 (比如 A = [0] B = [0])，返回 2.0000
         样例
         给出 A = [1, 2, 3], B = [2, 3 ,4]. 返回 0.9926.
         给出 A = [0], B = [0]. 返回 2.0000
         */
//        int[] arrA = {1,2,3};
//        int[] arrB = {2,3,4};
//        XYLog.d(solutions.cosineSimilarity(arrA, arrB));



        /**
         实现 Trie   [中等]
         http://www.lintcode.com/zh-cn/problem/implement-trie/
         实现一个 Trie，包含 insert, search, 和 startsWith 这三个方法。
         注意事项
         你可以假设所有的输入都是小写字母a-z。
         */
//        Trie trie = new Trie();
//        trie.insert("lintcode");
//        XYLog.d(trie.search("lint"));
//        XYLog.d(trie.startsWith("lint"));




        /**
         线段树的构造 II   [中等]
         http://www.lintcode.com/zh-cn/problem/segment-tree-build-ii/
         线段树是一棵二叉树，他的每个节点包含了两个额外的属性start和end用于表示该节点所代表的区间。start和end都是整数，并按照如下的方式赋值:
         根节点的 start 和 end 由 build 方法所给出。
         对于节点 A 的左儿子，有 start=A.left, end=(A.left + A.right) / 2。
         对于节点 A 的右儿子，有 start=(A.left + A.right) / 2 + 1, end=A.right。
         如果 start 等于 end, 那么该节点是叶子节点，不再有左右儿子。
         对于给定数组设计一个build方法，构造出线段树
         */
//        int[] arr = {3,2,1,4};
//        XYLog.d(solutions.build(arr));





        /**
         书籍复印   [困难]
         http://www.lintcode.com/zh-cn/problem/copy-books/
         给出一个数组A包含n个元素，表示n本书以及各自的页数。现在有个k个人复印书籍，每个人只能复印连续一段编号的书，比如A[1],A[2]由第一个人复印，但是不能A[1],A[3]由第一个人复印，求最少需要的时间复印所有书。
         样例
         A = [3,2,4],k = 2
         返回5，第一个人复印前两本书
         挑战
         时间复杂度 O(nk)
         */
//        int[] pages = {3,6};
//        int k = 2;
//        XYLog.d(solutions.copyBooks(pages, k));






        /**
         最大正方形   [中等]
         http://www.lintcode.com/zh-cn/problem/maximal-square/
         在一个二维01矩阵中找到全为1的最大正方形
         样例
         1 0 1 0 0
         1 0 1 1 1
         1 1 1 1 1
         1 0 0 1 0
         返回 4
         */
//        int[][] matrix = {
//                {1, 0, 1, 0, 0},
//                {1, 0, 1, 1, 1},
//                {1, 1, 1, 1, 1},
//                {1, 0, 0, 1, 0}
//        };
//        XYLog.d(solutions.maxSquare(matrix));



        /**
         岛屿的个数   [容易]
         http://www.lintcode.com/zh-cn/problem/number-of-islands/
         给一个01矩阵，求不同的岛屿的个数。
         0代表海，1代表岛，如果两个1相邻，那么这两个1属于同一个岛。我们只考虑上下左右为相邻。
         样例
         在矩阵：
         [
         [1, 1, 0, 0, 0],
         [0, 1, 0, 0, 1],
         [0, 0, 0, 1, 1],
         [0, 0, 0, 0, 0],
         [0, 0, 0, 0, 1]
         ]
         中有 3 个岛.
         */
//        int[][] matrix = {
//                {1, 1, 0, 0, 0},
//                {0, 1, 0, 0, 1},
//                {0, 0, 0, 1, 1},
//                {0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1}
//        };
//        boolean[][] grid = new boolean[matrix.length][matrix.length == 0? 0: matrix[0].length];
//        for (int i = 0, lenI = matrix.length; i < lenI; i++) {
//            for (int j = 0, lenJ = matrix[0].length; j < lenJ; j++) {
//                grid[i][j] = matrix[i][j] == 1;
//            }
//        }
//        XYLog.d(solutions.numIslands(grid));





        /**
         攀爬字符串   [困难]
         http://www.lintcode.com/zh-cn/problem/scramble-string/
         */
//        XYLog.d(solutions.isScramble("greet", "regte"));
//        XYLog.d(solutions.isScramble("greet", "regte"));




        /**
         x的n次幂   [中等]
         http://www.lintcode.com/zh-cn/problem/powx-n/
         实现 pow(x,n)
         注意事项
         不用担心精度，当答案和标准输出差绝对值小于1e-3时都算正确
         样例
         Pow(2.1, 3) = 9.261
         Pow(0, 1) = 0
         Pow(1, 0) = 1
         */
//        double x = 0;
//        int n = -4;
//        XYLog.d(solutions.myPow(x, n));




        /**
         生成括号   [中等]
         http://www.lintcode.com/zh-cn/problem/generate-parentheses/
         给定 n 对括号，请写一个函数以将其生成新的括号组合，并返回所有组合结果。
         样例
         给定 n = 3, 可生成的组合如下:
         "((()))", "(()())", "(())()", "()(())", "()()()"
         */
//        int n = 4;
//        XYLog.d(solutions.generateParenthesis(n));



        /**
         恢复IP地址   [中等]
         http://www.lintcode.com/zh-cn/problem/restore-ip-addresses/
         给一个由数字组成的字符串。求出其可能恢复为的所有IP地址。
         样例
         给出字符串 "25525511135"，所有可能的IP地址为：
         [
         "255.255.11.135",
         "255.255.111.35"
         ]
         （顺序无关紧要）
         */
//        String ip = "010010";
//        XYLog.d(solutions.restoreIpAddresses(ip));



        /**
         电话号码的字母组合   [中等]
         http://www.lintcode.com/zh-cn/problem/letter-combinations-of-a-phone-number/
         给一个不包含01的数字字符串，每个数字代表一个字母，请返回其所有可能的字母组合。
         下图的手机按键图，就表示了每个数字可以代表的字母。
         http://www.lintcode.com/media/problem/200px-Telephone-keypad2.svg.png
         注意事项
         以上的答案是按照词典编撰顺序进行输出的，不过，在做本题时，你也可以任意选择你喜欢的输出顺序。
         样例
         给定 "23"
         返回 ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
         */
//        String digits = "23";
//        XYLog.d(solutions.letterCombinations(digits));



        /**
         逆波兰表达式求值   [中等]
         http://www.lintcode.com/zh-cn/problem/evaluate-reverse-polish-notation/
         求逆波兰表达式的值。
         在逆波兰表达法中，其有效的运算符号包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰计数表达。
         说明
         什么是逆波兰表达式？
         http://en.wikipedia.org/wiki/Reverse_Polish_notation
         样例
         ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
         ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
         */
//        String[] tokens = {"4", "13", "5", "/", "+"};
//        XYLog.d(solutions.evalRPN(tokens));





        /**
         有效的括号序列   [容易]
         http://www.lintcode.com/zh-cn/problem/valid-parentheses/
         给定一个字符串所表示的括号序列，包含以下字符： '(', ')', '{', '}', '[' and ']'， 判定是否是有效的括号序列。
         样例
         括号必须依照 "()" 顺序表示， "()[]{}" 是有效的括号，但 "([)]"则是无效的括号。
         挑战
         O(n)的时间，n为括号的个数
         */
//        String s = "([{}])";
//        XYLog.d(solutions.isValidParentheses(s));



        /**
         最后一个单词的长度   [中等]
         http://www.lintcode.com/zh-cn/problem/length-of-last-word/
         给定一个字符串， 包含大小写字母、空格' '，请返回其最后一个单词的长度。
         如果不存在最后一个单词，请返回 0 。
         注意事项
         一个单词的界定是，由字母组成，但不包含任何的空格。
         样例
         给定 s = "Hello World"，返回 5。
         */
//        String s = "s sssss s";
//        XYLog.d(solutions.lengthOfLastWord(s));





        /**
         简化路径   [中等]
         http://www.lintcode.com/zh-cn/problem/simplify-path/
         给定一个文档(Unix-style)的完全路径，请进行路径简化。
         样例
         "/home/", => "/home"
         "/a/./b/../../c/", => "/c"
         挑战
         你是否考虑了 路径 = "/../" 的情况？
         在这种情况下，你需返回"/"。
         此外，路径中也可能包含双斜杠'/'，如 "/home//foo/"。
         在这种情况下，可忽略多余的斜杠，返回 "/home/foo"。
         */
//        String[] paths = {
//                "/home/",
//                "/a/./b/../../c/",
//                "/../",
//                "/home//foo/"
//        };
//        for (String str: paths) {
//            XYLog.d(str, " => ", solutions.simplifyPath(str));
//        }





        /**
         报数   [中等]
         http://www.lintcode.com/zh-cn/problem/count-and-say/
         报数指的是，按照其中的整数的顺序进行报数，然后得到下一个数。如下所示：
         1, 11, 21, 1211, 111221, ...
         1 读作 "one 1" -> 11.
         11 读作 "two 1s" -> 21.
         21 读作 "one 2, then one 1" -> 1211.
         给定一个整数 n, 返回 第 n 个顺序。
         注意事项
         整数的顺序将表示为一个字符串。
         样例
         给定 n = 5, 返回 "111221".
         */
////        int n = 6;
////        XYLog.d(solutions.countAndSay(n));
//        for (int i = 0; i < 10; i++) {
//            XYLog.d(solutions.countAndSay(i));
//        }





        /**
         罗马数字转整数   [中等]
         http://www.lintcode.com/zh-cn/problem/roman-to-integer/
         给定一个罗马数字，将其转换成整数。
         返回的结果要求在1到3999的范围内。
         样例
         IV -> 4
         XII -> 12
         XXI -> 21
         XCIX -> 99
         */
//        String s = "XCIX";
//        XYLog.d(solutions.romanToInt(s));



        /**
         整数转罗马数字   [中等]
         http://www.lintcode.com/zh-cn/problem/integer-to-roman/
         给定一个整数，将其转换成罗马数字。
         返回的结果要求在1-3999的范围内。
         说明
         什么是 罗马数字?
         https://en.wikipedia.org/wiki/Roman_numerals
         https://zh.wikipedia.org/wiki/%E7%BD%97%E9%A9%AC%E6%95%B0%E5%AD%97
         http://baike.baidu.com/view/42061.htm
         样例
         4 -> IV
         12 -> XII
         21 -> XXI
         99 -> XCIX
         更多案例，请戳 http://literacy.kent.edu/Minigrants/Cinci/romanchart.htm
         */
//        int n = 99;
//        XYLog.d(solutions.intToRoman(n));






        /**
         有效数字   [困难]
         http://www.lintcode.com/zh-cn/problem/valid-number/
         给定一个字符串，验证其是否为数字。
         样例
         "0" => true
         " 0.1 " => true
         "abc" => false
         "1 a" => false
         "2e10" => true
         */
//        String str = "2e10";
//        XYLog.d(solutions.isNumber(str));


        /**
         有效回文串   [容易]
         http://www.lintcode.com/zh-cn/problem/valid-palindrome/
         给定一个字符串，判断其是否为一个回文串。只包含字母和数字，忽略大小写。
         注意事项
         你是否考虑过，字符串有可能是空字符串？这是面试过程中，面试官常常会问的问题。
         在这个题目中，我们将空字符串判定为有效回文。
         样例
         "A man, a plan, a canal: Panama" 是一个回文。
         "race a car" 不是一个回文。
         挑战
         O(n) 时间复杂度，且不占用额外空间。
         */
//        String str = ",.";
//        XYLog.d(solutions.isPalindrome(str));



        /**
         两个整数相除   [中等]
         http://www.lintcode.com/zh-cn/problem/divide-two-integers/
         将两个整数相除，要求不使用乘法、除法和 mod 运算符。
         如果溢出，返回 2147483647 。
         样例
         给定被除数 = 100 ，除数 = 9，返回 11。
         */
//        int a = -2147483648;
//        int b = -2;
//        XYLog.d(solutions.divide(a, b));




        /**
         反转整数   [容易]
         http://www.lintcode.com/zh-cn/problem/reverse-integer/
         将一个整数中的数字进行颠倒，当颠倒后的整数溢出时，返回 0 (标记为 32 位整数)。
         样例
         给定 x = 123，返回 321
         给定 x = -123，返回 -321
         */
//        int n = -123;
//        XYLog.d(solutions.reverseInteger(n));





        /**
         分糖果   [困难]
         http://www.lintcode.com/zh-cn/problem/candy/
         有 N 个小孩站成一列。每个小孩有一个评级。
         按照以下要求，给小孩分糖果：
         每个小孩至少得到一颗糖果。
         评级越高的小孩可以得到比左右相邻的孩子（如果有的话）更多的糖果。
         需最少准备多少糖果？
         样例
         给定评级 = [1, 2], 返回 3.
         给定评级 = [1, 1, 1], 返回 3.
         给定评级 = [1, 2, 2], 返回 4. ([1,2,1]).
         */
//        int[] arr = {1,2,2,2,2};
//        XYLog.d(solutions.candy(arr));




        /**
         格雷编码   [中等]
         http://www.lintcode.com/zh-cn/problem/gray-code/
         格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个二进制的差异。
         给定一个非负整数 n ，表示该代码中所有二进制的总数，请找出其格雷编码顺序。一个格雷编码顺序必须以 0 开始，并覆盖所有的 2n 个整数。
         注意事项
         对于给定的 n，其格雷编码顺序并不唯一。
         根据以上定义， [0,2,3,1] 也是一个有效的格雷编码顺序。
         样例
         给定 n = 2， 返回 [0,1,3,2]。其格雷编码顺序为：
         00 - 0
         01 - 1
         11 - 3
         10 - 2
         挑战
         O(2n) 时间复杂度。
         */
//        int n = 3;
//        XYLog.d(solutions.grayCode(n));



        /**
         二进制求和   [容易]
         http://www.lintcode.com/zh-cn/problem/add-binary/
         给定两个二进制字符串，返回他们的和（用二进制表示）。
         样例
         a = 11
         b = 1
         返回 100
         */
//        String a = "11";
//        String b = "1";
//        XYLog.d(solutions.addBinary(a, b));





        /**
         加一   [容易]
         http://www.lintcode.com/zh-cn/problem/plus-one/
         给定一个非负数，表示一个数字数组，在该数的基础上+1，返回一个新的数组。
         该数字按照大小进行排列，最大的数在列表的最前面。
         样例
         给定 [1,2,3] 表示 123, 返回 [1,2,4].
         给定 [9,9,9] 表示 999, 返回 [1,0,0,0].
         */
////        int[] arr = {1,2,3};
//        int[] arr = {0};
//        XYLog.d(solutions.plusOne(arr), "");





        /**
         和大于S的最小子数组   [中等]
         http://www.lintcode.com/zh-cn/problem/minimum-size-subarray-sum/
         给定一个由 n 个整数组成的数组和一个正整数 s ，请找出该数组中满足其和 ≥ s 的最小长度子数组。如果无解，则返回 -1。
         样例
         给定数组 [2,3,1,2,4,3] 和 s = 7, 子数组 [4,3] 是该条件下的最小长度子数组。
         挑战
         如果你已经完成了O(n)时间复杂度的编程，请再试试 O(n log n)时间复杂度。
         */
//        int[] arr = {100,50,99,50,100,50,100,50,100,50};
//        int s = 250;
//        XYLog.d(solutions.minimumSize(arr, s));




        /**
         和为零的子矩阵   [中等]
         http://www.lintcode.com/zh-cn/problem/submatrix-sum/
         给定一个整数矩阵，请找出一个子矩阵，使得其数字之和等于0.输出答案时，请返回左上数字和右下数字的坐标。
         样例
         给定矩阵
         [
         [1 ,5 ,7],
         [3 ,7 ,-8],
         [4 ,-8 ,9],
         ]
         返回 [(1,1), (2,2)]
         挑战
         O(n3) 时间复杂度。
         */
//        int[][] matrix = {
//                {1 , 5 , 7},
//                {3 , 7 , -8},
//                {4 , -8 , 9},
//        };
////        int[][] matrix = {{1,1,1,1,1,1,1,1,1,1,1,-10,1,1,1,1,1,1,1,1,1,1,1}};
//        int[][] matrix = {{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {-10}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};
//        XYLog.d(solutions.submatrixSum(matrix), "");




        /**
         连续子数组求和   [中等]
         http://www.lintcode.com/zh-cn/problem/continuous-subarray-sum/
         给定一个整数数组，请找出一个连续子数组，使得该子数组的和最大。输出答案时，请分别返回第一个数字和最后一个数字的下标。（如果两个相同的答案，请返回其中任意一个）
         样例
         给定 [-3, 1, 3, -3, 4], 返回[1,4].
         */
//        int[] arr = {1, 1, 3, -3, 4};
//        XYLog.d(solutions.continuousSubarraySum(arr));









        /**
         排序矩阵中的从小到大第k个数   [中等]
         http://www.lintcode.com/zh-cn/problem/kth-smallest-number-in-sorted-matrix/
         在一个排序矩阵中找从小到大的第 k 个整数。
         排序矩阵的定义为：每一行递增，每一列也递增。
         样例
         给出 k = 4 和一个排序矩阵：
         [
         [1 ,5 ,7],
         [3 ,7 ,8],
         [4 ,8 ,9],
         ]
         返回 5。
         挑战
         使用O(k log n)的方法，n为矩阵的宽度和高度中的最大值。
         */
//        int[][] matrix = {
//                {1, 2, 3, 4, 5},
//                {2, 3, 4, 5, 6},
//                {3, 4, 5, 6, 7},
//                {4, 5, 6, 7, 8}
//        };
//        int k = 19;
//        XYLog.d(solutions.kthSmallest(matrix, k));





        /**
         最大间距   [困难]
         http://www.lintcode.com/zh-cn/problem/maximum-gap/
         给定一个未经排序的数组，请找出其排序表中连续两个要素的最大间距。
         如果数组中的要素少于 2 个，请返回 0.
         注意事项
         可以假定数组中的所有要素都是非负整数，且最大不超过 32 位整数。
         样例
         给定数组 [1, 9, 2, 5]，其排序表为 [1, 2, 5, 9]，其最大的间距是在 5 和 9 之间，= 4.
         挑战
         用排序的方法解决这个问题是比较简单的方法，但是排序的时间复杂度是O(nlogn), 能否使用线性的时间和空间复杂度的方法解决这个问题。
         */
//        int[] arr = {1,9,2,5,6,7,6};
////        int[] arr = DataUtil.getIntArr("./data/maximum-gap-73.in");
//        XYLog.d(solutions.maximumGap(arr));




        /**
         Nuts 和 Bolts 的问题   [中等]
         http://www.lintcode.com/zh-cn/problem/nuts-bolts-problem/
         给定一组 n 个不同大小的 nuts 和 n 个不同大小的 bolts。nuts 和 bolts 一一匹配。 不允许将 nut 之间互相比较，也不允许将 bolt 之间互相比较。也就是说，只许将 nut 与 bolt 进行比较， 或将 bolt 与 nut 进行比较。请比较 nut 与 bolt 的大小。
         样例
         给出 nuts = ['ab','bc','dd','gg'], bolts = ['AB','GG', 'DD', 'BC']
         你的程序应该找出bolts和nuts的匹配。
         一组可能的返回结果是：
         nuts = ['ab','bc','dd','gg'], bolts = ['AB','BC','DD','GG']
         我们将给你一个匹配的比较函数，如果我们给你另外的比较函数，可能返回的结果是：
         nuts = ['ab','bc','dd','gg'], bolts = ['BC','AB','DD','GG']
         因此的结果完全取决于比较函数，而不是字符串本身。
         因为你必须使用比较函数来进行排序。
         各自的排序当中nuts和bolts的顺序是无关紧要的，只要他们一一匹配就可以。
         */
//        String[] nuts = {"b", "a"};
//        String[] bolts = {"A", "B"};
//        solutions.sortNutsAndBolts(nuts, bolts, new NBComparator());
//        XYLog.d(nuts, "");
//        XYLog.d(bolts, "");



        /**
         最长上升连续子序列   [容易]
         http://www.lintcode.com/zh-cn/problem/longest-increasing-continuous-subsequence/
         给定一个整数数组（下标从 0 到 n-1， n 表示整个数组的规模），请找出该数组中的最长上升连续子序列。（最长上升连续子序列可以定义为从右到左或从左到右的序列。）
         样例
         给定 [5, 4, 2, 1, 3], 其最长上升连续子序列（LICS）为 [5, 4, 2, 1], 返回 4.
         给定 [5, 1, 2, 3, 4], 其最长上升连续子序列（LICS）为 [1, 2, 3, 4], 返回 4.
         */
//        int[] arr = {5, 1, 2, 3, 4};
//        XYLog.d(solutions.longestIncreasingContinuousSubsequence(arr));






        /**
         硬币排成线 II   [中等]
         http://www.lintcode.com/zh-cn/problem/coins-in-a-line-ii/
         有 n 个不同价值的硬币排成一条线。两个参赛者轮流从左边依次拿走 1 或 2 个硬币，直到没有硬币为止。计算两个人分别拿到的硬币总价值，价值高的人获胜。
         请判定 第一个玩家 是输还是赢？
         样例
         给定数组 A = [1,2,2], 返回 true.
         给定数组 A = [1,2,4], 返回 false.
         */
//        int[] arr = {1,2,4,3,4,8,5,6,12};
//        XYLog.d(solutions.firstWillWin(arr));



        /**
         硬币排成线   [中等]
         http://www.lintcode.com/zh-cn/problem/coins-in-a-line/
         有 n 个硬币排成一条线。两个参赛者轮流从右边依次拿走 1 或 2 个硬币，直到没有硬币为止。拿到最后一枚硬币的人获胜。
         请判定 第一个玩家 是输还是赢？
         样例
         n = 1, 返回 true.
         n = 2, 返回 true.
         n = 3, 返回 false.
         n = 4, 返回 true.
         n = 5, 返回 true.
         挑战
         O(1) 时间复杂度且O(1) 存储。
         */
//        for (int i = 0; i <= 10; i++) {
//            XYLog.d(solutions.firstWillWin(i));
//        }




        /**
         买卖股票的最佳时机 IV   [困难]
         http://www.lintcode.com/zh-cn/problem/best-time-to-buy-and-sell-stock-iv/
         假设你有一个数组，它的第i个元素是一支给定的股票在第i天的价格。
         设计一个算法来找到最大的利润。你最多可以完成 k 笔交易。
         注意事项
         你不可以同时参与多笔交易(你必须在再次购买前出售掉之前的股票)
         样例
         给定价格 = [4,4,6,1,1,4,2,5], 且 k = 2, 返回 6.
         挑战
         O(nk) 时间序列。
         */
////        int[] arr = {1,2};
////        int k = 1;
////        int[] arr = DataUtil.getIntArr("./data/best-time-to-buy-and-sell-stock-iv-45.in");//result 1648961
////        int k = 1000000000;
//        int[] arr = DataUtil.getIntArr("./data/best-time-to-buy-and-sell-stock-iv-42.in");//result 2818
//        int k = 29;
////        int[] arr = DataUtil.getIntArr("./data/best-time-to-buy-and-sell-stock-iv-66.in");//result 38011
////        int k = 56;
//        XYLog.d(solutions.maxProfit(k, arr));




        /**
         打劫房屋   [中等]
         http://www.lintcode.com/zh-cn/problem/house-robber/
         假设你是一个专业的窃贼，准备沿着一条街打劫房屋。每个房子都存放着特定金额的钱。你面临的唯一约束条件是：相邻的房子装着相互联系的防盗系统，且 当相邻的两个房子同一天被打劫时，该系统会自动报警。
         给定一个非负整数列表，表示每个房子中存放的钱， 算一算，如果今晚去打劫，你最多可以得到多少钱 在不触动报警装置的情况下。
         样例
         给定 [3, 8, 4], 返回 8.
         挑战
         O(n) 时间复杂度 且 O(1) 存储。
         */
//        int[] arr = {3, 8, 4, 5, 7};
//        XYLog.d(solutions.houseRobber(arr));





        /**
         数飞机   [中等]
         http://www.lintcode.com/zh-cn/problem/number-of-airplanes-in-the-sky/
         给出飞机的起飞和降落时间的列表，用 interval 序列表示. 请计算出天上同时最多有多少架飞机？
         注意事项
         如果多架飞机降落和起飞在同一时刻，我们认为降落有优先权。
         样例
         对于每架飞机的起降时间列表：[[1,10],[2,3],[5,8],[4,7]], 返回3。
         */
//        ArrayList<Interval> airplanes = ArrayListUtil.build(
//                new Interval(1, 10),
//                new Interval(2, 3),
//                new Interval(5, 8),
//                new Interval(4, 7)
//        );
//        XYLog.d(solutions.countOfAirplanes(airplanes));




        /**
         第k个排列   [中等]
         http://www.lintcode.com/zh-cn/problem/permutation-sequence/
         给定 n 和 k，求123..n组成的排列中的第 k 个排列。
         注意事项
         1 ≤ n ≤ 9
         样例
         对于 n = 3, 所有的排列如下：
         123
         132
         213
         231
         312
         321
         如果 k = 4, 第4个排列为，231.
         */
//        int n = 9;
//        int k = 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2;
//        XYLog.d(solutions.getPermutation(n, k));



        /**
         最小差   [中等]
         http://www.lintcode.com/zh-cn/problem/the-smallest-difference/
         给定两个整数数组（第一个是数组 A，第二个是数组 B），在数组 A 中取 A[i]，数组 B 中取 B[j]，A[i] 和 B[j]两者的差越小越好(|A[i] - B[j]|)。返回最小差。
         样例
         给定数组 A = [3,4,6,7]， B = [2,3,8,9]，返回 0。
         挑战
         时间复杂度 O(n log n)
         */
//        int[] arrA = {3,4,6,7};
//        int[] arrB = {2,5,8,9};
//        XYLog.d(solutions.smallestDifference(arrA, arrB));





        /**
         最长无重复字符的子串   [中等]

         给定一个字符串，请找出其中无重复字符的最长子字符串。
         样例
         例如，在"abcabcbb"中，其无重复字符的最长子字符串是"abc"，其长度为 3。
         对于，"bbbbb"，其无重复字符的最长子字符串为"b"，长度为1。
         挑战
         O(n) 时间
         */
//        String str = "gehmbfqmozbpripibusbezagafqtypz";
//        XYLog.d(solutions.lengthOfLongestSubstring(str));




        /**
         装最多水的容器   [中等]
         http://www.lintcode.com/zh-cn/problem/container-with-most-water/
         给定 n 个非负整数 a1, a2, ..., an, 每个数代表了坐标中的一个点 (i, ai)。画 n 条垂直线，使得 i 垂直线的两个端点分别为(i, ai)和(i, 0)。找到两条线，使得其与 x 轴共同构成一个容器，以容纳最多水。
         样例
         给出[1,3,2], 最大的储水面积是2.
         */
//        int[] arr = {1,3,2};
//        XYLog.d(solutions.maxArea(arr));



        /**
         螺旋矩阵 II   [中等]
         http://www.lintcode.com/zh-cn/problem/spiral-matrix-ii/
         给你一个数n生成一个包含1-n^2的螺旋形矩阵
         样例
         n = 3
         矩阵为
         [
         [ 1, 2, 3 ],
         [ 8, 9, 4 ],
         [ 7, 6, 5 ]
         ]
         */
//        int n = 3;
//        XYLog.d(solutions.generateMatrix(n), "");






        /**
         两个链表的交叉   [中等]
         http://www.lintcode.com/zh-cn/problem/intersection-of-two-linked-lists/
         请写一个程序，找到两个单链表最开始的交叉节点。
         注意事项
         如果两个链表没有交叉，返回null。
         在返回结果后，两个链表仍须保持原有的结构。
         可假定整个链表结构中没有循环。
         样例
         下列两个链表：
         A:        a1 → a2
                           ↘
                              c1 → c2 → c3
                           ↗
         B: b1 → b2 → b3
         在节点 c1 开始交叉。
         挑战
         需满足 O(n) 时间复杂度，且仅用 O(1) 内存。
         */
//        ListNode listA = ListNodeFactory.build("1->2");
//        ListNode listB = ListNodeFactory.build("1->2->3");
//        ListNode listC = ListNodeFactory.build("4->5->6");
//        ListNode cursor = listA;
//        while (cursor.next != null) {
//            cursor = cursor.next;
//        }
//        cursor.next = listC;
//        cursor = listB;
//        while (cursor.next != null) {
//            cursor = cursor.next;
//        }
//        cursor.next = listC;
//        XYLog.d(solutions.getIntersectionNode(listA, listB));




        /**
         将数组重新排序以构造最小值   [中等]
         http://www.lintcode.com/zh-cn/problem/reorder-array-to-construct-the-minimum-number/
         给定一个整数数组，请将其重新排序，以构造最小值。
         样例
         给定 [3, 32, 321]，通过将数组重新排序，可构造 6 个可能性数字：
         3+32+321=332321
         3+321+32=332132
         32+3+321=323321
         32+321+3=323213
         321+3+32=321332
         321+32+3=321323
         其中，最小值为 321323，所以，将数组重新排序后，该数组变为 [321, 32, 3]。
         挑战
         在原数组上完成，不使用额外空间。
         */
//        int[] nums = {3, 32, 321};
//        XYLog.d(solutions.minNumber(nums));




        /**
         克隆二叉树   [容易]
         http://www.lintcode.com/zh-cn/problem/clone-binary-tree/
         深度复制一个二叉树。
         给定一个二叉树，返回一个他的 克隆品 。
         */
//        TreeNode tree = TreeNodeFactory.build("1,2,3,4,5");
//        XYLog.d(tree);
//        XYLog.d(solutions.cloneTree(tree));


        /**
         螺旋矩阵   [中等]
         http://www.lintcode.com/zh-cn/problem/spiral-matrix/
         给定一个包含 m x n 个要素的矩阵，（m 行, n 列），按照螺旋顺序，返回该矩阵中的所有要素。
         样例
         给定如下矩阵：
         [
         [ 1, 2, 3 ],
         [ 4, 5, 6 ],
         [ 7, 8, 9 ]
         ]
         应返回 [1,2,3,6,9,8,7,4,5]。
         */
//        int[][] matrix = {
//                { 1, 2, 3, 4},
//                { 5, 6, 7, 8}
//        };
//        XYLog.d(solutions.spiralOrder(matrix));




        /**
         奇偶分割数组   [容易]
         http://www.lintcode.com/zh-cn/problem/partition-array-by-odd-and-even/
         分割一个整数数组，使得奇数在前偶数在后。
         样例
         给定 [1, 2, 3, 4]，返回 [1, 3, 2, 4]。
         挑战
         在原数组中完成，不使用额外空间。
         */
//        int[] arr = {1, 2, 3, 4};
//        solutions.partitionArray(arr);
//        XYLog.d(arr, "");




        /**
         在O(1)时间复杂度删除链表节点   [容易]
         http://www.lintcode.com/zh-cn/problem/delete-node-in-the-middle-of-singly-linked-list/
         给定一个单链表中的一个等待被删除的节点(非表头或表尾)。请在在O(1)时间复杂度删除该链表节点。
         样例
         给定 1->2->3->4，和节点 3，删除 3 之后，链表应该变为 1->2->4。
         */
//        ListNode head = ListNodeFactory.build("1->2->3->4");
//        solutions.deleteNode(head.next.next);
//        XYLog.d(head);




        /**
         用递归打印数字   [中等]
         http://www.lintcode.com/zh-cn/problem/print-numbers-by-recursion/
         用递归的方法找到从1到最大的N位整数。
         样例
         给出 N = 1, 返回[1,2,3,4,5,6,7,8,9].
         给出 N = 2, 返回[1,2,3,4,5,6,7,8,9,10,11,...,99].
         */
//        XYLog.d(solutions.numbersByRecursion(2));



        /**
         表达式求值   [困难]
         http://www.lintcode.com/zh-cn/problem/expression-evaluation/
         给一个用字符串表示的表达式数组，求出这个表达式的值。
         注意事项
         表达式只包含整数, +, -, *, /, (, ).
         样例
         对于表达式 (2*6-(23+7)/(1+2)), 对应的数组为：
         [
         "2", "*", "6", "-", "(",
         "23", "+", "7", ")", "/",
         (", "1", "+", "2", ")"
         ],
         其值为 2
         */
//        String[] exps = {"(","999","/","3","/","3","/","3",")","+","(","1","+","9","/","3",")"};
//        XYLog.d(solutions.evaluateExpression(exps));



        /**
         斐波纳契数列   [入门]
         http://www.lintcode.com/zh-cn/problem/fibonacci/
         */
//        XYLog.d(solutions.fibonacci(10));



        /**
         二进制中有多少个1  [中等]
         http://www.lintcode.com/zh-cn/problem/count-1-in-binary/
         计算在一个 32 位的整数的二进制表式中有多少个 1.
         样例
         给定 32 (100000)，返回 1
         给定 5 (101)，返回 2
         给定 1023 (1111111111)，返回 10
         */
//        XYLog.d(solutions.countOnes(1023));





        /**
         接雨水   [中等]
         http://www.lintcode.com/zh-cn/problem/trapping-rain-water/
         给出 n 个非负整数，代表一张X轴上每个区域宽度为 1 的海拔图, 计算这个海拔图最多能接住多少（面积）雨水。
         样例
         如上图所示，海拔分别为 [0,1,0,2,1,0,1,3,2,1,2,1], 返回 6.
         挑战
         O(n) 时间, O(1) 空间
         O(n) 时间, O(n) 空间也可以接受
         */
//        int[] heights = {0,1,0,2,1,0,1,3,2,1,2,1};
//        XYLog.d(solutions.trapRainWater(heights));






        /**
         滑动窗口的最大值   [超难]
         http://www.lintcode.com/zh-cn/problem/sliding-window-maximum/
         给出一个可能包含重复的整数数组，和一个大小为 k 的滑动窗口, 从左到右在数组中滑动这个窗口，找到数组中每个窗口内的最大值。
         样例
         给出数组 [1,2,7,7,8], 滑动窗口大小为 k = 3. 返回 [7,7,8].
         解释：
         最开始，窗口的状态如下：
         [|1, 2 ,7| ,7 , 8], 最大值为 7;
         然后窗口向右移动一位：
         [1, |2, 7, 7|, 8], 最大值为 7;
         最后窗口再向右移动一位：
         [1, 2, |7, 7, 8|], 最大值为 8.
         挑战
         O(n)时间，O(k)的额外空间
         */
//        int[] nums = {1,3,2,6,5,8,7,4,4,6};
//        int k = 3;
//        XYLog.d(solutions.maxSlidingWindow(nums, k));
//        //两种算法的实际运行时间比较，maxSlidingWindowNK的平均速度更快，后面一种算法没有考虑队列的出入队列的复杂度
//        int[][] randomArr = RandomUtil.randomArr(1000, 200000, 1000);
//        int len = randomArr.length;
//        int[] randomKs = new int[len];
//        for (int i = 0; i < len; i++) {
//            randomKs[i] = (int) (Math.random() * randomArr[i].length);
//        }
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < len; i++) {
//            solutions.maxSlidingWindowNK(randomArr[i], randomKs[i]);
//        }
//        XYLog.d("maxSlidingWindowNK:", (System.currentTimeMillis() - startTime) / (double) len);
//        startTime = System.currentTimeMillis();
//        for (int i = 0; i < len; i++) {
//            solutions.maxSlidingWindow(randomArr[i], randomKs[i]);
//        }
//        XYLog.d("maxSlidingWindow:", (System.currentTimeMillis() - startTime) / (double) len);



        /**
         统计前面比自己小的数的个数   [困难]
         http://www.lintcode.com/zh-cn/problem/count-of-smaller-number-before-itself/
         给定一个整数数组（下标由 0 到 n-1， n 表示数组的规模，取值范围由 0 到10000）。对于数组中的每个 ai 元素，请计算 ai 前的数中比它小的元素的数量。
         样例
         对于数组[1,2,7,8,5] ，返回 [0,1,2,3,2]
         */
//        int[] arr = {1,2,7,1,2};
//        XYLog.d(solutions.countOfSmallerNumberII(arr));




        /**
         统计比给定整数小的数的个数   [中等]
         http://www.lintcode.com/zh-cn/problem/count-of-smaller-number/
   '    给定一个整数数组 （下标由 0 到 n-1，其中 n 表示数组的规模，数值范围由 0 到 10000），以及一个 查询列表。对于每一个查询，将会给你一个整数，请你返回该数组中小于给定整数的元素的数量。
         */
//        int[] arr = {1,2,7,8,5};
//        int[] queries = {1,8,5};
//        XYLog.d(solutions.countOfSmallerNumber(arr, queries));




        /**
         线段树查询 II   [中等]
         http://www.lintcode.com/zh-cn/problem/segment-tree-query-ii/
         对于一个数组，我们可以对其建立一棵 线段树, 每个结点存储一个额外的值 count 来代表这个结点所指代的数组区间内的元素个数. (数组中并不一定每个位置上都有元素)
         实现一个 query 的方法，该方法接受三个参数 root, start 和 end, 分别代表线段树的根节点和需要查询的区间，找到数组中在区间[start, end]内的元素个数。
         样例
         对于数组 [0, 空，2, 3], 对应的线段树为：

                                [0, 3, count=3]
                               /             \
                  [0,1,count=1]             [2,3,count=2]
                 /         \               /            \
         [0,0,count=1] [1,1,count=0] [2,2,count=1], [3,3,count=1]
         query(1, 1), return 0
         query(1, 2), return 1
         */
//        String str = "[0,14,count=3][0,7,count=2][8,14,count=1][0,3,count=2][4,7,count=0][8,11,count=0][12,14,count=1][0,1,count=1][2,3,count=1][4,5,count=0][6,7,count=0][8,9,count=0][10,11,count=0][12,13,count=0][14,14,count=1][0,0,count=1][1,1,count=0][2,2,count=1][3,3,count=0][4,4,count=0][5,5,count=0][6,6,count=0][7,7,count=0][8,8,count=0][9,9,count=0][10,10,count=0][11,11,count=0][12,12,count=0][13,13,count=0]";
//        String[] split = str.replaceAll("\\[", "").replaceAll("\\]", ";").split(";");
//        ArrayList<SegmentTreeNode> nodes = new ArrayList<>();
//        for (int i = 0, len = split.length; i < len; i++) {
//            String item = split[i];
//            String[] params = item.split(",");
//            int start = Integer.parseInt(params[0]);
//            int end = Integer.parseInt(params[1]);
//            int count = Integer.parseInt(params[2].replace("count=", ""));
//            SegmentTreeNode newNode = new SegmentTreeNode(start, end, count);
//            nodes.add(newNode);
//            if (i != 0)  {
//                SegmentTreeNode parent = nodes.get((i - 1) / 2);
//                if (i % 2 == 1) {
//                    parent.left = newNode;
//                }
//                else {
//                    parent.right = newNode;
//                }
//            }
//        }
//        XYLog.d(solutions.query247(nodes.get(0), 1, 16));




        /**
         子树   [容易]
         http://www.lintcode.com/zh-cn/problem/subtree/
         有两个不同大小的二进制树： T1 有上百万的节点； T2 有好几百的节点。请设计一种算法，判定 T2 是否为 T1的子树。
         注意事项
         若 T1 中存在从节点 n 开始的子树与 T2 相同，我们称 T2 是 T1 的子树。也就是说，如果在 T1 节点 n 处将树砍断，砍断的部分将与 T2 完全相同。
         样例
         下面的例子中 T2 是 T1 的子树：
                 1                3
               / \              /
         T1 = 2   3      T2 =  4
                /
              4
         下面的例子中 T2 不是 T1 的子树：
                 1               3
               / \               \
         T1 = 2   3       T2 =    4
                /
              4
         */
//        TreeNode tree1 = TreeNodeFactory.build("1,1,1,2,3,#,2,#,#,4,5,3");
//        TreeNode tree2 = TreeNodeFactory.build("1,2,3,#,#,4,5");
//        XYLog.d(tree1, tree2);
//        XYLog.d(solutions.isSubtree(tree1, tree2));




        /**
         用栈模拟汉诺塔问题  [容易]
         http://www.lintcode.com/zh-cn/problem/mock-hanoi-tower-by-stacks/
         在经典的汉诺塔问题中，有 3 个塔和 N 个可用来堆砌成塔的不同大小的盘子。要求盘子必须按照从小到大的顺序从上往下堆 （如，任意一个盘子，其必须堆在比它大的盘子上面）。同时，你必须满足以下限制条件：
         (1) 每次只能移动一个盘子。
         (2) 每个盘子从堆的顶部被移动后，只能置放于下一个堆中。
         (3) 每个盘子只能放在比它大的盘子上面。
         请写一段程序，实现将第一个堆的盘子移动到最后一个堆中。
         */
//        int n = 3;
//        Tower[] towers = new Tower[3];
//        for (int i = 0; i < n; i++) towers[i] = new Tower(i);
//        for (int i = n - 1; i >= 0; i--) towers[0].add(i);
//        towers[0].moveDisks(n, towers[2], towers[1]);
//        XYLog.d(towers);


        /**
         回文链表   [中等]
         http://www.lintcode.com/zh-cn/problem/palindrome-linked-list/
         设计一种方式检查一个链表是否为回文链表。
         样例
         1->2->1 就是一个回文链表。
         挑战
         O(n)的时间和O(1)的额外空间。
         */
//        ListNode head = ListNodeFactory.build("1->2->2->1");
//        XYLog.d(head, "\n", solutions.isPalindrome(head));




        /**
         空格替换   [容易]
         http://www.lintcode.com/zh-cn/problem/space-replacement/
         设计一种方法，将一个字符串中的所有空格替换成 %20 。你可以假设该字符串有足够的空间来加入新的字符，且你得到的是“真实的”字符长度。
         你的程序还需要返回被替换后的字符串的长度。
         样例
         对于字符串"Mr John Smith", 长度为 13
         替换空格之后，参数中的字符串需要变为"Mr%20John%20Smith"，并且把新长度 17 作为结果返回。
         挑战
         在原字符串(字符数组)中完成替换，不适用额外空间
         */
//        String str = "Mr John Smith";
//        int len = str.length();
//        char[] chars = new char[str.length() * 3];
//        for (int i = 0; i < len; i++) {
//            chars[i] = str.charAt(i);
//        }
//        int newLen = solutions.replaceBlank(chars, len);
//        XYLog.d("新的字符串：", String.copyValueOf(chars, 0, newLen), ", 长度：", newLen);




        /**
         区间求和 II   [困难]
         http://www.lintcode.com/zh-cn/problem/interval-sum-ii/
         在类的构造函数中给一个整数数组, 实现两个方法 query(start, end) 和 modify(index, value):
         对于 query(start, end), 返回数组中下标 start 到 end 的 和。
         对于 modify(index, value), 修改数组中下标为 index 上的数为 value.
         样例
         给定数组 A = [1,2,7,8,5].
         query(0, 2), 返回 10.
         modify(0, 4), 将 A[0] 修改为 4.
         query(0, 1), 返回 6.
         modify(2, 1), 将 A[2] 修改为 1.
         query(2, 4), 返回 14.
         */
//        int[] arr = {1,2,7,8,5};
//        SolutionIntervalSumII solution = new SolutionIntervalSumII(arr);
//        XYLog.d(solution.query(0, 2));
//        solution.modify(0, 4);
//        XYLog.d(solution.query(0, 1));
//        solution.modify(2, 1);
//        XYLog.d(solution.query(2, 4));






        /**
         区间求和 I   [中等]
         http://www.lintcode.com/zh-cn/problem/interval-sum/
         给定一个整数数组（下标由 0 到 n-1，其中 n 表示数组的规模），以及一个查询列表。每一个查询列表有两个整数 [start, end] 。 对于每个查询，计算出数组中从下标 start 到 end 之间的数的总和，并返回在结果列表中。
         样例
         对于数组 [1,2,7,8,5]，查询[(1,2),(0,4),(2,4)], 返回 [9,23,20]
         */
//        int[] arr = {1,2,7,8,5};
//        ArrayList<Interval> intervals = ArrayListUtil.build(
//                new Interval(1, 2),
//                new Interval(0, 4),
//                new Interval(2, 4)
//        );
//        XYLog.d(solutions.intervalSum(arr, intervals));



        /**
         区间最小数   [中等]
         http://www.lintcode.com/zh-cn/problem/interval-minimum-number/
         给定一个整数数组（下标由 0 到 n-1，其中 n 表示数组的规模），以及一个查询列表。每一个查询列表有两个整数 [start, end]。 对于每个查询，计算出数组中从下标 start 到 end 之间的数的最小值，并返回在结果列表中。
         注意事项
         在做此题前，建议先完成以下三道题 线段树的构造， 线段树的查询 及 线段树的修改。
         样例
         对于数组 [1,2,7,8,5]， 查询 [(1,2),(0,4),(2,4)]，返回 [2,1,5]
         挑战
         每次查询在O(logN)的时间内完成
         */
//        int[] arr = {1,2,7,8,5};
//        ArrayList<Interval> intervals = ArrayListUtil.build(
//                new Interval(1, 2),
//                new Interval(0, 4),
//                new Interval(2, 4)
//        );
//        XYLog.d(solutions.intervalMinNumber(arr, intervals));



        /**
         单例   [容易]
         http://www.lintcode.com/zh-cn/problem/singleton/
         单例 是最为最常见的设计模式之一。对于任何时刻，如果某个类只存在且最多存在一个具体的实例，那么我们称这种设计模式为单例。例如，对于 class Mouse (不是动物的mouse哦)，我们应将其设计为 singleton 模式。
         你的任务是设计一个 getInstance 方法，对于给定的类，每次调用 getInstance 时，都可得到同一个实例。
         样例
         在 Java 中:
         A a = A.getInstance();
         A b = A.getInstance();
         a 应等于 b.
         挑战
         如果并发的调用 getInstance，你的程序也可以正确的执行么？
         */
//        for (int i = 0; i < 1000; i++) {
//            new Thread() {
//                @Override
//                public void run() {
//                    XYLog.d(Solution.getInstance().hashCode());
//                }
//            }.start();
//        }





        /**
         线段树的修改   [中等]
         http://www.lintcode.com/zh-cn/problem/segment-tree-modify/
         对于一棵 最大线段树, 每个节点包含一个额外的 max 属性，用于存储该节点所代表区间的最大值。
         设计一个 modify 的方法，接受三个参数 root、 index 和 value。该方法将 root 为跟的线段树中 [start, end] = [index, index] 的节点修改为了新的 value ，并确保在修改后，线段树的每个节点的 max 属性仍然具有正确的值。
         样例
         对于线段树:
                                [0, 3, max=3]
                             /                \
                  [0, 1, max=2]                [2, 3, max=3]
                /              \             /             \
         [0, 0, max=2]  [1, 1, max=1]  [2, 2, max=0]  [3, 3, max=3]
         如果调用 modify(root, 2, 4), 返回:
                                [0, 3, max=4]
                             /                \
                 [0, 1, max=4]                [2, 3, max=3]
                /              \             /             \
         [0, 0, max=2]  [1, 1, max=4]  [2, 2, max=0]  [3, 3, max=3]
         挑战
         时间复杂度 O(h) , h 是线段树的高度
         */
//        int index = 1;
//        int value = 4;
//        int[] arr = {2, 1, 0, 3};
//        SegmentTreeNode root = SegmentTreeNodeFactory.build(arr, "max");
//        XYLog.d(root);
//        solutions.modify(root, index, value);
//        XYLog.d("将[", index, ", ", index, "]的值改为", value, "之后，线段树变为：", root);






        /**
         线段树的查询   [中等]
         http://www.lintcode.com/zh-cn/problem/segment-tree-query/
         对于一个有n个数的整数数组，在对应的线段树中, 根节点所代表的区间为0-n-1, 每个节点有一个额外的属性max，值为该节点所代表的数组区间start到end内的最大值。
         为SegmentTree设计一个 query 的方法，接受3个参数root, start和end，线段树root所代表的数组中子区间[start, end]内的最大值。
         样例
         对于数组 [1, 4, 2, 3], 对应的线段树为：
                            [0, 3, max=4]
                          /             \
                 [0,1,max=4]           [2,3,max=3]
               /          \          /            \
         [0,0,max=1] [1,1,max=4] [2,2,max=2], [3,3,max=3]
         query(root, 1, 1), return 4
         query(root, 1, 2), return 4
         query(root, 2, 3), return 3
         query(root, 0, 2), return 4
         */
//        int start = 0;
//        int end = 2;
//        int[] arr = {1, 4, 2, 3};
//        SegmentTreeNode root = SegmentTreeNodeFactory.build(arr, "max");
//        XYLog.d(root, "这个线段树中，区间[", start, ", ", end, "]的最大值为：", solutions.query(root, start, end));





        /**
         线段树的构造   [中等]
         http://www.lintcode.com/zh-cn/problem/segment-tree-build/
         线段树是一棵二叉树，他的每个节点包含了两个额外的属性start和end用于表示该节点所代表的区间。start和end都是整数，并按照如下的方式赋值:
         根节点的 start 和 end 由 build 方法所给出。
         对于节点 A 的左儿子，有 start=A.left, end=(A.left + A.right) / 2。
         对于节点 A 的右儿子，有 start=(A.left + A.right) / 2 + 1, end=A.right。
         如果 start 等于 end, 那么该节点是叶子节点，不再有左右儿子。
         实现一个 build 方法，接受 start 和 end 作为参数, 然后构造一个代表区间 [start, end] 的线段树，返回这棵线段树的根。
         说明
         线段树(又称区间树), 是一种高级数据结构，他可以支持这样的一些操作:
         查找给定的点包含在了哪些区间内
         查找给定的区间包含了哪些点
         见百科：
         线段树
         区间树
         样例
         比如给定start=1, end=6，对应的线段树为：
                             [1,  6]
                           /        \
                   [1,  3]           [4,  6]
                  /     \           /     \
              [1, 2]  [3,3]     [4, 5]   [6,6]
             /    \           /     \
         [1,1]   [2,2]     [4,4]   [5,5]
         */
//        int start = 1;
//        int end = 6;
//        XYLog.d(start, " 到 ", end, "的线段树：", solutions.build(start, end));


        /**
         最长回文子串   [中等]
         http://www.lintcode.com/zh-cn/problem/longest-palindromic-substring/
         给出一个字符串（假设长度最长为1000），求出它的最长回文子串，你可以假定只有一个满足条件的最长回文串。
         样例
         给出字符串 "abcdzdcab"，它的最长回文子串为 "cdzdc"。
         挑战
         O(n2) 时间复杂度的算法是可以接受的，如果你能用 O(n) 的算法那自然更好。
         */
//        String str = "acaccadacc";
//        XYLog.d(str, "中最长的回文字串为：\n", solutions.longestPalindrome(str));




        /**
         排列序号II   [中等]
         http://www.lintcode.com/zh-cn/problem/permutation-index-ii/
         给出一个可能包含重复数字的排列，求这些数字的所有排列按字典序排序后该排列在其中的编号。编号从1开始。
         样例
         给出排列[1, 4, 2, 2]，其编号为3。
         */
//        int[] nums = {3, 2, 1, 2, 1};
//        XYLog.d(nums, "是第", solutions.permutationIndexII(nums), "个排列");
////        int[] numsTemp = {1,1,2,2,3};
////        for (int i = 1; i <= 30; i++) {
////            XYLog.d(numsTemp, "是第", solutions.permutationIndexII(numsTemp), "个排列");
////            solutions.nextPermutation(numsTemp);
////        }




        /**
         排列序号   [容易]
         http://www.lintcode.com/zh-cn/problem/permutation-index/
         给出一个不含重复数字的排列，求这些数字的所有排列按字典序排序后该排列的编号。其中，编号从1开始。
         样例
         例如，排列 [1,2,4] 是第 1 个排列。
         */
//        int[] nums = {2,3,1};
//        XYLog.d(nums, "是第", solutions.permutationIndex(nums), "个排列");




        /**
         寻找缺失的数   [中等]
         http://www.lintcode.com/zh-cn/problem/find-the-missing-number/
         给出一个包含 0 .. N 中 N 个数的序列，找出0 .. N 中没有出现在序列中的那个数。
         注意事项
         可以改变序列中数的位置。
         样例
         N = 4 且序列为 [0, 1, 3] 时，缺失的数为2。
         挑战
         在数组上原地完成，使用O(1)的额外空间和O(N)的时间。
         */
//        int[] nums = {9,8,7,6,2,0,1,5,4};
//        XYLog.d(nums, "\n中缺少的数为：");
//        XYLog.d(solutions.findMissing(nums));


        /**
         通配符匹配   [困难]
         http://www.lintcode.com/zh-cn/problem/wildcard-matching/
         判断两个可能包含通配符“？”和“*”的字符串是否匹配。匹配规则如下：
         '?' 可以匹配任何单个字符。
         '*' 可以匹配任意字符串（包括空字符串）。
         两个串完全匹配才算匹配成功。
         样例
         一些例子：
         isMatch("aa","a") → false
         isMatch("aa","aa") → true
         isMatch("aaa","aa") → false
         isMatch("aa", "*") → true
         isMatch("aa", "a*") → true
         isMatch("ab", "?*") → true
         isMatch("aab", "c*a*b") → false
         */
//        String s = "abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb";
//        String p = "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb";
//        XYLog.d("isMatch(\"" + s + ", \"" + p + "\") = " + solutions.isMatch(s, p));


        /**
         乘积最大子序列   [中等]
         http://www.lintcode.com/zh-cn/problem/maximum-product-subarray/
         找出一个序列中乘积最大的连续子序列（至少包含一个数）。
         样例
         比如, 序列 [2,3,-2,4] 中乘积最大的子序列为 [2,3] ，其乘积为6。
         */
//        int[] nums = {2,3,-2,4};
//        XYLog.d(nums, "中的最大乘积子序列为：", solutions.maxProduct(nums));




        /**
         下一个排列   [中等]
         http://www.lintcode.com/zh-cn/problem/next-permutation-ii/
         给定一个若干整数的排列，给出按正数大小进行字典序从小到大排序后的下一个排列。
         如果没有下一个排列，则输出字典序最小的序列。
         样例
         左边是原始排列，右边是对应的下一个排列。
         1,2,3 → 1,3,2
         3,2,1 → 1,2,3
         1,1,5 → 1,5,1
         挑战
         不允许使用额外的空间。
         */
//        int[] arr = {1,2,3,4,5};
//        XYLog.d(arr, "\n下一个排列：");
//        for (int i = 0; i < 40; i++) {
//            solutions.nextPermutation(arr);
//            XYLog.d(arr);
//        }




        /**
         丢失的第一个正整数   [中等]
         http://www.lintcode.com/zh-cn/problem/first-missing-positive/
         给出一个无序的正数数组，找出其中没有出现的最小正整数。
         样例
         如果给出 [1,2,0], return 3
         如果给出 [3,4,-1,1], return 2
         挑战
         只允许时间复杂度O(n)的算法，并且只能使用常数级别的空间。
         */
//        int[] arr = {1,1};
//        XYLog.d(arr, "\n中丢失的最小正整数为：\n", solutions.firstMissingPositive(arr));




        /**
         加油站   [中等]
         http://www.lintcode.com/zh-cn/problem/gas-station/
         在一条环路上有 N 个加油站，其中第 i 个加油站有汽油gas[i]，并且从第_i_个加油站前往第_i_+1个加油站需要消耗汽油cost[i]。
         你有一辆油箱容量无限大的汽车，现在要从某一个加油站出发绕环路一周，一开始油箱为空。
         求可环绕环路一周时出发的加油站的编号，若不存在环绕一周的方案，则返回-1。
         注意事项
         数据保证答案唯一。
         样例
         现在有4个加油站，汽油量gas[i]=[1, 1, 3, 1]，环路旅行时消耗的汽油量cost[i]=[2, 2, 1, 1]。则出发的加油站的编号为2。
         */
//        int[] gas = {1,2,3,4,5};
//        int[] cost = {3,4,5,1,2};
//        XYLog.d("gas: ", gas);
//        XYLog.d("cost: ", cost);
//        XYLog.d(solutions.canCompleteCircuit(gas, cost));




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