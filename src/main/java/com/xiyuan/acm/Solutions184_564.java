package com.xiyuan.acm;

import com.xiyuan.acm.factory.ListNodeFactory;
import com.xiyuan.acm.factory.SegmentTreeNodeFactory;
import com.xiyuan.acm.model.*;
import com.xiyuan.acm.util.ArrayListUtil;
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



    public static void main(String[] args) {
        Solutions184_564 solutions = new Solutions184_564();

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