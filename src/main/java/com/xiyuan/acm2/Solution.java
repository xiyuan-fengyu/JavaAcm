package com.xiyuan.acm2;

import com.xiyuan.acm.factory.ListNodeFactory;
import com.xiyuan.acm.factory.TreeNodeFactory;
import com.xiyuan.acm.model.DirectedGraphNode;
import com.xiyuan.acm.model.ListNode;
import com.xiyuan.acm.model.RandomListNode;
import com.xiyuan.acm.model.TreeNode;
import com.xiyuan.acm2.model.*;
import com.xiyuan.acm2.model.Dictionary;
import com.xiyuan.util.XYLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiyuan.util.XYLog.d;
import static com.xiyuan.util.XYLog.e;

/**
 * Created by xiyuan_fengyu on 2017/4/7.
 */
@SuppressWarnings("unchecked")
public class Solution {

    public int aplusb(int a, int b) {
        if (b == 0) {
            return a;
        }
        else {
            return aplusb(a ^ b, (a & b) << 1);
        }
    }



    public long trailingZeros(long n) {
        if (n < 5) return 0;
        else {
            long m = n / 5;
            return m + trailingZeros(m);
        }
    }



    public int digitCounts(int k, int n) {
        if (n == 0) {
            return k == 0 ? 1 : 0;
        }

        int heighNum = n;
        int ten = 1;
        int tenIndex = 0;
        while (heighNum >= 10) {
            ten *= 10;
            heighNum /= 10;
            tenIndex++;
        }

        int lower = n - heighNum * ten;
        int total = digitCounts(k, lower) + (heighNum - 1) * digitCountCache09[tenIndex];
        if (k != 0 && heighNum >= k) {
            total += heighNum == k ? (lower + 1) : ten;
        }
        //优化前
//        if (ten >= 10) {
//            total += digitCounts(k, ten - 1);
//        }

        //优化后
        total += k == 0 ? digitCountCache0[tenIndex] : digitCountCache09[tenIndex];

        return total;
    }

    //digitCountCache0 和 digitCountCache09 是在算法完后计算出来的不变值，可以优化算法计算速度
    //digitCountCache0[index] 表示 一个数字 0 在 0 ~ 9{index,}中出现的次数
    private static final int[] digitCountCache0 = {0, 1, 10, 180, 2760, 37520, 475040, 5750080, 67500160, 775000320};
    //digitCountCache09[index] 表示 一个数字（0~9） 在 [0~9]{index,}中出现的次数
    private static final int[] digitCountCache09 = {0, 1, 20, 300, 4000, 50000, 600000, 7000000, 80000000, 900000000};






    public int nthUglyNumber(int n) {
        int[] arr = new int[n];
        arr[0] = 1;
        int[] index235 = {0, 0, 0};

        for (int i = 1; i < n;) {
            int min = Integer.MAX_VALUE;
            int minIndex = 0;
            int temp;
            if ((temp = arr[index235[0]] * 2) < min) {
                min = temp;
                minIndex = 0;
            }
            if ((temp = arr[index235[1]] * 3) < min) {
                min = temp;
                minIndex = 1;
            }
            if ((temp = arr[index235[2]] * 5) < min) {
                min = temp;
                minIndex = 2;
            }

            if (min > arr[i - 1]) {
                arr[i] = min;
                i++;
            }

            index235[minIndex]++;
        }

        return arr[n - 1];
    }




    public int kthLargestElement(int k, int[] nums) {
        int len = nums.length;
        return kthLargestElement(len - k, nums, 0, len - 1);
    }

    private int kthLargestElement(int k, int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        else {
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

            if (l == k){
                return nums[l];
            }
            else if (l > k) {
                return kthLargestElement(k, nums, left, l - 1);
            }
            else {
                return kthLargestElement(k, nums, l + 1, right);
            }
        }
    }




    public int[] mergeSortedArray(int[] arrA, int[] arrB) {
        int lenA = arrA.length;
        int lenB = arrB.length;
        int[] result = new int[lenA + lenB];

        int i = 0;
        int j = 0;
        int k = 0;
        for (; i < lenA && j < lenB;) {
            if (arrA[i] <= arrB[j]) {
                result[k++] = arrA[i++];
            }
            else {
                result[k++] = arrB[j++];
            }
        }

        if (i < lenA) {
            for (; i < lenA;) {
                result[k++] = arrA[i++];
            }
        }
        else {
            for (; j < lenB;) {
                result[k++] = arrB[j++];
            }
        }

        return result;
    }




    public String serialize(TreeNode root) {
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode cur = nodes.get(i);
            if (cur != null) {
                nodes.add(cur.left);
                nodes.add(cur.right);
            }
        }

        int size;
        while ((size = nodes.size()) > 0 && nodes.get(size - 1) == null) {
            nodes.remove(size - 1);
        }

        StringBuilder builder = new StringBuilder();
        for (TreeNode node : nodes) {
            builder.append(node == null ? "#" : node.val).append(',');
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == null) {
            return null;
        }

        String[] split = data.split(",");
        ArrayList<TreeNode> nodes = new ArrayList<>();
        int len = split.length;
        if (len > 0) {
            nodes.add(parseNode(split[0]));
            for (int i = 0, j = 1; j < len && i < j; i++) {
                TreeNode curNode = nodes.get(i);
                if (curNode != null) {
                    if (j < len) {
                        TreeNode node = parseNode(split[j++]);
                        curNode.left = node;
                        nodes.add(node);
                    }
                    if (j < len) {
                        TreeNode node = parseNode(split[j++]);
                        curNode.right = node;
                        nodes.add(node);
                    }
                }
            }
        }

        return nodes.size() == 0 ? null : nodes.get(0);
    }

    private TreeNode parseNode(String node) {
        if (node.matches("[0-9]+")) {
            return new TreeNode(Integer.parseInt(node));
        }
        else return null;
    }




    public void rotateString(char[] str, int offset) {
        if (str != null && offset != 0) {
            int len = str.length;
            if (len > 0) {
                offset %= len;

                char last;
                char temp;
                for (int i = 0, j = 0; i < offset && j < len; i++) {
                    last = str[i];
                    for (int k = i + offset; ;) {
                        temp = str[k];
                        str[k] = last;
                        last = temp;
                        j++;

                        k += offset;
                        k %= len;

                        if (k == i) {
                            str[i] = last;
                            j++;
                            break;
                        }
                    }
                }
            }
        }
    }





    public ArrayList<Integer> searchRange(TreeNode root, int k1, int k2) {
        ArrayList<Integer> result = searchRangeNoSort(root, k1, k2);
        Collections.sort(result);
        return result;
    }

    private ArrayList<Integer> searchRangeNoSort(TreeNode root, int k1, int k2) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root != null) {
            if (root.val >= k1 && root.val <= k2) {
                result.add(root.val);
            }

            if (root.left != null && root.val > k1) {
                result.addAll(searchRangeNoSort(root.left, k1, k2));
            }

            if (root.right != null && root.val < k2) {
                result.addAll(searchRangeNoSort(root.right, k1, k2));
            }
        }
        return result;
    }






    public int strStr(String source, String target) {
        if (source == null || target == null) {
            return -1;
        }

        int lenS = source.length();
        int lenT = target.length();
        if (lenT == 0) {
            return 0;
        }
        else if (lenS < lenT) {
            return -1;
        }

        for (int i = 0; i <= lenS - lenT; i++) {
            for (int j = 0; j < lenT; j++) {
                if (source.charAt(i + j) == target.charAt(j)) {
                    if (j + 1 == lenT) {
                        return i;
                    }
                }
                else {
                    break;
                }
            }
        }

        return -1;
    }




    public int strStrKMP(String source, String target) {
        if (source == null || target == null) {
            return -1;
        }

        int lenS = source.length();
        int lenT = target.length();
        if (lenT == 0) {
            return 0;
        }
        else if (lenS < lenT) {
            return -1;
        }

        int[] next = getNext(target);
        for (int i = 0, j = 0; i < lenS; i++) {
            while (j > 0 && source.charAt(i) != target.charAt(j)) j = next[j];
            if (source.charAt(i) == target.charAt(j)) j++;
            if (j == lenT) {
                return i - lenT + 1;
            }
        }
        return -1;
    }

    private int[] getNext(String str) {
        int len = str.length();
        int[] next = new int[len + 1];
        next[0] = next[1] = 0;
        for (int i = 1, j = 0; i < len; i++) {
            while (j > 0 && str.charAt(i) != str.charAt(j)) j = next[j];
            if (str.charAt(i) == str.charAt(j)) j++;
            next[i + 1] = j;
        }
        return next;
    }




    public int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        else return binarySearch(nums, target, 0, nums.length - 1);
    }

    private int binarySearch(int[] nums, int target, int left, int right) {
        if (left >= right) {
            return nums[left] == target ? left : -1;
        }

        int midI = (left + right) / 2;
        int midV = nums[midI];
        if (midV >= target) {
            int temp = binarySearch(nums, target, left, midI - 1);
            return temp == -1 ? (midV == target ? midI : -1) : temp;
        }
        else return binarySearch(nums, target, midI + 1, right);
    }




    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums != null) {
            Arrays.sort(nums);
            do {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int num : nums) {
                    temp.add(num);
                }
                result.add(temp);
            } while (nextPermute(nums));
        }
        return result;
    }

    private boolean nextPermute(int[] nums) {
        int len = nums.length;
        for (int i = len - 2; i > -1; i--) {
            for (int j = len - 1; j > i; j--) {
                if (nums[j] > nums[i]) {
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                    Arrays.sort(nums, i + 1, len);
                    return true;
                }
            }
        }
        return false;
    }




    public ArrayList<ArrayList<Integer>> subsets(int[] nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (nums != null) {
            Arrays.sort(nums);

            result.add(new ArrayList<Integer>());

            for (int num : nums) {
                for (int i = 0, size = result.size(); i < size; i++) {
                    ArrayList<Integer> clone = (ArrayList<Integer>) result.get(i).clone();
                    clone.add(num);
                    result.add(clone);
                }
            }
        }
        return result;
    }



    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (nums != null) {
            Arrays.sort(nums);

            result.add(new ArrayList<Integer>());

            int len = nums.length;
            for (int i = 0; i < len; ) {
                int size = result.size();
                ArrayList<Integer> dups = new ArrayList<>();
                do {
                    dups.add(nums[i]);
                    ArrayList<Integer> dupsClone = (ArrayList<Integer>) dups.clone();
                    for (int k = 0; k < size; k++) {
                        ArrayList<Integer> clone = (ArrayList<Integer>) result.get(k).clone();
                        clone.addAll(dupsClone);
                        result.add(clone);
                    }
                    i++;
                } while (i < len && nums[i - 1] == nums[i]);
            }
        }
        return result;
    }




    public List<Map.Entry<Integer, Double>> dicesSum(int n) {
        double[][] cache = new double[n + 1][];
        cache[1] = new double[7];
        for (int i = 1; i <= 6; i++) {
            cache[1][i] = 1 / 6.0;
        }

        for (int i = 2; i <= n; i++) {
            cache[i] = new double[6 * i + 1];
            for (int j = i, last = 6 * i; j <= last; j++) {
                for (int k = Math.max(1, j - 6 * (i - 1)), maxK = Math.min(6, j - i + 1); k <= maxK; k++) {
                    cache[i][j] += cache[i - 1][j - k] / 6.0;
                }
            }
        }


        List<Map.Entry<Integer, Double>> result = new ArrayList<>();
        for (int i = n, last = 6 * n; i <= last; i++) {
            result.add(new AbstractMap.SimpleEntry<>(i,  cache[n][i]));
        }
        return result;
    }

    public List<Map.Entry<Integer, Double>> dicesSumLessComputeAndMem(int n) {
        double[][] cache = new double[n + 1][];
        cache[1] = new double[3];
        for (int i = 0; i < 3; i++) {
            cache[1][i] = 1 / 6.0;
        }

        for (int i = 2; i <= n; i++) {
            int lastMinMaxSum = 7 * (i - 1);
            int lastMinMaxHalf = lastMinMaxSum / 2;
            int halfLen = (5 * i + 2) / 2;
            cache[i] = new double[halfLen];
            for (int j = 0; j < halfLen; j++) {
                for (int k = Math.max(j - 5 * i, 1); k <= Math.min(j + 1, 6); k++) {
                    int lastSum = j + i - k;
                    if (lastSum > lastMinMaxHalf) {
                        lastSum = lastMinMaxSum - lastSum;
                    }
                    cache[i][j] += cache[i - 1][lastSum - (i - 1)] / 6.0;
                }
            }
        }

        List<Map.Entry<Integer, Double>> result = new ArrayList<>();
        for (int i = 0, len = 5 * n + 1, half = (len - 1) / 2; i < len; i++) {
            result.add(new AbstractMap.SimpleEntry<>(i + n,  cache[n][i <= half ? i : len - 1 - i]));
        }
        return result;
    }




    public List<Integer> flatten(List<NestedInteger> nestedList) {
        if (nestedList == null) return null;

        List<Integer> result = new ArrayList<>();
        Stack<NestedInteger> stack = new Stack<>();
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }

        while (!stack.isEmpty()) {
            NestedInteger top = stack.pop();
            if (top.isInteger()) {
                result.add(top.getInteger());
            }
            else {
                List<NestedInteger> list = top.getList();
                for (int i = list.size() - 1; i >= 0; i--) {
                    stack.push(list.get(i));
                }
            }
        }
        return result;
    }





    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null) return false;

        int row = matrix.length;
        if (row > 0) {
            int column = matrix[0].length;
            int r = row - 1;
            int c = 0;
            while (r > -1 && c < column) {
                int cur = matrix[r][c];
                if (cur == target) return true;
                else if (cur > target) {
                    r--;
                }
                else {
                    c++;
                }
            }
        }
        return false;
    }




    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) return false;
        return s1.length() + s2.length() == s3.length() && isInterleave(s1, 0, s2, 0, s3, 0);
    }

    private boolean isInterleave(String s1, int index1, String s2, int index2, String s3, int index3) {
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();

        if (len1 == index1 && len2 == index2 && index3 == len3) return true;
        else {
            char c3 = s3.charAt(index3);
            if (index1 < len1 && c3 == s1.charAt(index1)) {
                if (isInterleave(s1, index1 + 1, s2, index2, s3, index3 + 1)) {
                    return true;
                }
            }

            if (index2 < len2 && c3 == s2.charAt(index2)) {
                if (isInterleave(s1, index1, s2, index2 + 1, s3, index3 + 1)) {
                    return true;
                }
            }
        }
        return false;
    }





    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        ArrayList<Interval> result = new ArrayList<Interval>();
        ArrayList<int[]> points = new ArrayList<>();
        for (Interval interval : intervals) {
            points.add(new int[]{interval.start, 0});
            points.add(new int[]{interval.end, 1});
        }
        points.add(new int[]{newInterval.start, 0});
        points.add(new int[]{newInterval.end, 1});
        Collections.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });
        Stack<int[]> stack = new Stack<>();
        for (int[] point : points) {
            if (stack.isEmpty()) {
                stack.push(point);
            }
            else {
                if (point[1] == 0) {
                    stack.push(point);
                }
                else {
                    int[] top = stack.pop();
                    if (stack.isEmpty()) {
                        result.add(new Interval(top[0], point[0]));
                    }
                }
            }
        }
        return result;
    }




    public int partitionArray(int[] nums, int k) {
        if (nums == null) return -1;

        int i = 0;
        int j = nums.length - 1;

        while (i < j) {
            while (i < j && nums[j] >= k) {
                j--;
            }

            while (i < j && nums[i] < k) {
                i++;
            }

            int temp = nums[j];
            nums[j] = nums[i];
            nums[i] = temp;
        }
        return j < 0 || nums[j] < k ? j + 1 : j;
    }



    public String minWindow(String source, String target) {
        if (source == null || target == null || source.equals("") || target.equals("")) return "";

        int[] charCounts = new int[256];
        int charNum = 0;
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            if (charCounts[c] == 0) {
                charNum++;
            }
            charCounts[c]++;
        }

        int minLeft = -1;
        int minRight = -1;
        int[] charFound = new int[256];
        int found = 0;
        for (int right = 0, left = 0; right < source.length(); right++) {
            char c = source.charAt(right);
            if (charCounts[c] > 0 && (charFound[c]++) == charCounts[c] - 1) {
                found++;
                while (found == charNum) {
                    if (minRight == -1 || minRight - minLeft > right - left) {
                        minLeft = left;
                        minRight = right;
                    }
                    char leftC = source.charAt(left++);
                    if (charCounts[leftC] > 0 && (charFound[leftC]--) == charCounts[leftC]) {
                        found--;
                    }
                }
            }
        }

        return minRight == -1 ? "" : source.substring(minLeft, minRight + 1);
    }





    public ArrayList<ArrayList<String>> solveNQueens(int n) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        char[] rowChars = new char[n];
        Arrays.fill(rowChars, '.');
        int[] columns = new int[n];
        Arrays.fill(columns, -1);
        for (int row = 0, col; ;) {
            for (col = columns[row] + 1; col < n; col++) {
                if (isValid(columns, row, col)) {
                    columns[row] = col;
                    if (row == n - 1) {
                        ArrayList<String> strs = new ArrayList<>();
                        for (int i = 0; i < n; i++) {
                            int tempCol = columns[i];
                            rowChars[tempCol] = 'Q';
                            strs.add(String.valueOf(rowChars));
                            rowChars[tempCol] = '.';
                        }
                        result.add(strs);
                        col = n;
                    }
                    else {
                        row++;
                    }
                    break;
                }
            }

            if (col == n) {
                if (row == 0) break;

                columns[row] = -1;
                row--;
            }
        }
        return result;
    }

    private boolean isValid(int[] columns, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (columns[i] == col || Math.abs(row - i) == Math.abs(col - columns[i])) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<ArrayList<String>> solveNQueensIt(int n) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        char[][] chars = new char[n][];
        for (int i = 0; i < n; i++) {
            char[] temp = new char[n];
            Arrays.fill(temp, '.');
            chars[i] = temp;
        }

        int[] columns = new int[n];
        Arrays.fill(columns, -1);

        solveNQueensIt(result, chars, 0, columns);
        return result;
    }

    private void solveNQueensIt(ArrayList<ArrayList<String>> result, char[][] chars, int row, int[] columns) {
        int n = chars.length;
        if (row == n) {
            ArrayList<String> resultItem = new ArrayList<>();
            for (char[] cs : chars) {
                resultItem.add(String.valueOf(cs));
            }
            result.add(resultItem);
        }
        else {
            for (int col = 0; col < n; col++) {
                if (isValid(columns, row, col)) {
                    columns[row] = col;
                    chars[row][col] = 'Q';
                    solveNQueensIt(result, chars, row + 1, columns);
                    columns[row] = -1;
                    chars[row][col] = '.';
                }
            }
        }
    }


    public int totalNQueens(int n) {
        int total = 0;

        int[] columns = new int[n];
        Arrays.fill(columns, -1);
        for (int row = 0, col; ;) {
            for (col = columns[row] + 1; col < n; col++) {
                if (isValid(columns, row, col)) {
                    columns[row] = col;
                    if (row == n - 1) {
                        total++;
                        col = n;
                    }
                    else {
                        row++;
                    }
                    break;
                }
            }

            if (col == n) {
                if (row == 0) break;

                columns[row] = -1;
                row--;
            }
        }
        return total;
    }

    /**
     * 最高效的算法
     * @param n
     * @return
     */
    public ArrayList<ArrayList<String>> solveNQueensByBit(int n) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        char[][] chars = new char[n][];
        for (int i = 0; i < n; i++) {
            char[] temp = new char[n];
            Arrays.fill(temp, '.');
            chars[i] = temp;
        }

        int upperLimit = (1 << n) - 1;
        solveNQueensByBitIt(result, chars, upperLimit, 0, 0, 0, 0);
        return result;
    }

    private void solveNQueensByBitIt(ArrayList<ArrayList<String>> result, char[][] chars, int upperLimit, int row, int rowBit, int ldBit, int rdBit) {
        int valid;
        int col;
        if (rowBit != upperLimit) {
            valid = upperLimit & (~(rowBit | ldBit | rdBit));
            while (valid != 0) {
                col = valid & (~valid + 1);
                valid -= col;
                setQueen(chars, row, col, 'Q');
                solveNQueensByBitIt(result, chars, upperLimit, row + 1, rowBit | col, (ldBit | col) << 1, (rdBit | col) >>> 1);
                setQueen(chars, row, col, '.');
            }
        }
        else {
            ArrayList<String> resultItem = new ArrayList<>();
            for (char[] cs : chars) {
                resultItem.add(String.valueOf(cs));
            }
            result.add(resultItem);
        }
    }

    private void setQueen(char[][] chars, int row, int col, char c) {
        int colIndex = 0;
        while ((col & 1) == 0) {
            col >>>= 1;
            colIndex++;
        }
        chars[row][chars.length - 1 - colIndex] = c;
    }






    public ListNode reverse(ListNode head) {
        ListNode cur = null;
        while (head != null) {
            ListNode tempNext = head.next;
            head.next = cur;
            cur = head;
            head = tempNext;
        }
        return cur;
    }


    public ListNode reverseBetween(ListNode head, int m , int n) {
        if (head == null || m == n) return head;

        ListNode newHead = new ListNode(0);
        newHead.next = head;

        ListNode last = newHead;
        ListNode cur = head;
        int curIndex = 1;
        while (cur != null && curIndex != m) {
            last = cur;
            cur = cur.next;
            curIndex++;
        }

        if (curIndex == m) {
            last.next = null;

            ListNode reverseTail = cur;
            ListNode reverseNode = null;
            while (cur != null && curIndex <= n) {
                ListNode tempNext = cur.next;
                cur.next = reverseNode;
                reverseNode = cur;
                cur = tempNext;
                curIndex++;
            }
            last.next = reverseNode;
            if (reverseTail != null) {
                reverseTail.next = cur;
            }
        }
        return newHead.next;
    }


    public int searchMatrixII(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return 0;

        int row = matrix.length;
        int col = matrix[0].length;
        if (target < matrix[0][0] || target > matrix[row - 1][col - 1]) return 0;

        int total = 0;
        for (int i = row - 1, j = 0; i > -1 && j < col;) {
            int cur = matrix[i][j];
            if (cur == target) {
                total++;
                i--;
                j++;
            }
            else if (cur < target) {
                j++;
            }
            else {
                i--;
            }
        }
        return total;
    }




    public void recoverRotatedSortedArray(ArrayList<Integer> nums) {
        if (nums == null || nums.size() <= 1) return;

        int size = nums.size();
        int offset = 0;
        for (int i = 1; i < size; i++) {
            if (nums.get(i - 1) > nums.get(i)) {
                offset = i;
                break;
            }
        }

        if (offset > 0) {
            offset = size - offset;
            int changeNum = 0;
            Integer temp;
            for (int i = 0; changeNum < size && i < offset; i++) {
                Integer last = nums.get(i);
                int index = (i + offset) % size;
                while (true) {
                    temp = nums.get(index);
                    nums.set(index, last);
                    last = temp;
                    changeNum++;
                    if (i == index) {
                        break;
                    }
                    else {
                        index = (index + offset) % size;
                    }
                }
            }
        }
    }





    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int tempMaxSum = nums[0];
        int maxSum = tempMaxSum;
        for (int i = 1; i < nums.length; i++) {
            tempMaxSum = Math.max(tempMaxSum, 0) + nums[i];
            maxSum = Math.max(maxSum, tempMaxSum);
        }
        return maxSum;
    }



    public int maxTwoSubArrays(ArrayList<Integer> nums) {
        if (nums == null || nums.size() <= 1) return 0;

        int size = nums.size();
        int[] l2rMax = new int[size];
        int[] r2lMax = new int[size];

        int tempMaxSum = nums.get(0);
        int maxSum = tempMaxSum;
        l2rMax[0] = tempMaxSum;
        for (int i = 1; i < size - 1; i++) {
            tempMaxSum = Math.max(tempMaxSum, 0) + nums.get(i);
            maxSum = Math.max(maxSum, tempMaxSum);
            l2rMax[i] = maxSum;
        }

        tempMaxSum = nums.get(size - 1);
        maxSum = tempMaxSum;
        r2lMax[size - 1] = tempMaxSum;
        for (int i = size - 2; i > 0; i--) {
            tempMaxSum = Math.max(tempMaxSum, 0) + nums.get(i);
            maxSum = Math.max(maxSum, tempMaxSum);
            r2lMax[i] = maxSum;
        }

        maxSum = l2rMax[0] + r2lMax[1];
        for (int i = 1; i < size - 1; i++) {
            maxSum = Math.max(maxSum, l2rMax[i] + r2lMax[i + 1]);
        }

        return maxSum;
    }


    public int maxTwoSubArrays(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;

        int len = nums.length;
        int[] l2rMax = new int[len];
        int[] r2lMax = new int[len];

        int tempMaxSum = nums[0];
        int maxSum = tempMaxSum;
        l2rMax[0] = tempMaxSum;
        for (int i = 1; i < len - 1; i++) {
            tempMaxSum = Math.max(tempMaxSum, 0) + nums[i];
            maxSum = Math.max(maxSum, tempMaxSum);
            l2rMax[i] = maxSum;
        }

        tempMaxSum = nums[len - 1];
        maxSum = tempMaxSum;
        r2lMax[len - 1] = tempMaxSum;
        for (int i = len - 2; i > 0; i--) {
            tempMaxSum = Math.max(tempMaxSum, 0) + nums[i];
            maxSum = Math.max(maxSum, tempMaxSum);
            r2lMax[i] = maxSum;
        }

        maxSum = l2rMax[0] + r2lMax[1];
        for (int i = 1; i < len - 1; i++) {
            maxSum = Math.max(maxSum, l2rMax[i] + r2lMax[i + 1]);
        }

        return maxSum;
    }


    public int maxSubArray(int[] nums, int k) {
        if (nums.length < k) {
            return 0;
        }

        int len = nums.length;
        int[][] localMax = new int[k + 1][len + 1];
        int[][] globalMax = new int[k + 1][len + 1];
        for (int i = 1; i <= k; i++) {
            localMax[i][i - 1] = Integer.MIN_VALUE;
            globalMax[i][i - 1] = Integer.MIN_VALUE;
            for (int j = i; j <= len; j++) {
                // 由 localMax[i][j] 的计算公式，可以保证 nums[j - 1]一定在locaMax[i][j]这种分组情况的最后一个组中
                // 同理，nums[j - 2] 一定在locaMax[i][j - 1]这种分组情况的最后一个组中, 所以 nums[j - 1] 可以直接附加到 localMax[i][j - 1]的最后一组
                // localMax[i][j - 1] + nums[j - 1] 可以理解为：将前j - 1个数分为 i 组， nums[j - 1]附加到最后一组，所以还是i组；
                // globalMax[i - 1][j - 1] + nums[j - 1] 理解为：将前j - 1个数分为 i - 1 组， nums[j - 1]单独做为一个新组；

                // 由 globalMax[i][j] 的计算公式，可以知道：
                // 当 globalMax[i][j - 1] 更大的时候，nums[j - 1] 没有包含在分组中；
                // 当 localMax[i][j] 更大的时候，nums[j - 1] 包含在最后一个分组中；
                // 所以在 globalMax[i][j] 这种分组情况下，不能保证 nums[j - 1] 包含在分组中；
                // 同理在 globalMax[i][j - 1] 这种分组情况下，不能保证 nums[j - 2] 包含在分组中；
                // 所以在计算 localMax[i][j] 的第二种情况时，只能将 nums[j - 1] 单独做为一组， 前 j - 1 个数分为 i - 1 组

                // 综上：
                // localMax[i][j] 的意义为：将前j个数分为i组，其中 nums[i - 1]一定包含在最后一个分组的情况下的最大值；
                // globalMax[i][j] 的意义为：将前j个数分为i组，其中 nums[i - 1]不一定包含在最后一个分组的情况下的最大值；
                localMax[i][j] = Math.max(localMax[i][j - 1], globalMax[i - 1][j - 1]) + nums[j - 1];
                globalMax[i][j] = Math.max(globalMax[i][j - 1], localMax[i][j]);
            }
        }
        return globalMax[k][len];
    }



    public int minSubArray(ArrayList<Integer> nums) {
        if (nums == null || nums.size() == 0) return 0;

        int localMin = nums.get(0);
        int globalMin = localMin;
        for (int i = 1, size = nums.size(); i < size ; i++) {
            localMin = Math.min(localMin, 0) + nums.get(i);
            globalMin = Math.min(localMin, globalMin);
        }
        return globalMin;
    }


    public int maxDiffSubArrays(int[] nums) {
        if (nums == null || nums.length < 2) return 0;

        int len = nums.length;

        int localMaxL2R = Integer.MIN_VALUE;
        int globalMaxL2R = localMaxL2R;
        int[] l2rMax = new int[len];

        int localMaxR2L = Integer.MIN_VALUE;
        int globalMaxR2L = localMaxR2L;
        int[] r2lMax = new int[len];

        int localMinL2R = Integer.MAX_VALUE;
        int globalMinL2R = localMinL2R;
        int[] l2rMin = new int[len];

        int localMinR2L = Integer.MAX_VALUE;
        int globalMinR2L = localMinR2L;
        int[] r2lMin = new int[len];

        for (int i = 0, j = len - 1; i < len; i++, j--) {
            int numI = nums[i];
            int numJ = nums[j];

            localMaxL2R = Math.max(localMaxL2R, 0) + numI;
            globalMaxL2R = Math.max(localMaxL2R, globalMaxL2R);
            l2rMax[i] = globalMaxL2R;

            localMaxR2L = Math.max(localMaxR2L, 0) + numJ;
            globalMaxR2L = Math.max(localMaxR2L, globalMaxR2L);
            r2lMax[j] = globalMaxR2L;

            localMinL2R = Math.min(localMinL2R, 0) + numI;
            globalMinL2R = Math.min(localMinL2R, globalMinL2R);
            l2rMin[i] = globalMinL2R;

            localMinR2L = Math.min(localMinR2L, 0) + numJ;
            globalMinR2L = Math.min(localMinR2L, globalMinR2L);
            r2lMin[j] = globalMinR2L;
        }

        int maxDiff = Integer.MIN_VALUE;
        for (int i = 0; i < len - 1; i++) {
            maxDiff = Math.max(maxDiff, Math.abs(l2rMax[i] - r2lMin[i + 1]));
            maxDiff = Math.max(maxDiff, Math.abs(l2rMin[i] - r2lMax[i + 1]));
        }
        return maxDiff;
    }



    public int majorityNumber(ArrayList<Integer> nums) {
        int count = 0;
        int major = 0;
        for (Integer num : nums) {
            if (count == 0) {
                major = num;
                count++;
            }
            else {
                if (num != major) {
                    count--;
                }
                else {
                    count++;
                }
            }
        }
        return major;
    }


    public int majorityNumber1_3(ArrayList<Integer> nums) {
        int count0 = 0;
        int count1 = 0;
        int major0 = 0;
        int major1 = 0;

        for (Integer num : nums) {
            if (num == major0) {
                count0++;
            }
            else if (num == major1) {
                count1 = 1;
            }
            else {
                if (count0 == 0) {
                    major0 = num;
                    count0 = 1;
                }
                else if (count1 == 0) {
                    major1 = num;
                    count1 = 2;
                }
                else {
                    count0--;
                    count1--;
                }
            }
        }

        count0 = count1 = 0;
        for (Integer num : nums) {
            if (num == major0) count0++;
            else if (num == major1) count1++;
        }
        return count0 > count1 ? major0 : major1;
    }



    public int majorityNumber(ArrayList<Integer> nums, int k) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (Integer num : nums) {
            Integer count = counts.get(num);
            if (count != null) {
                counts.put(num, count + 1);
            }
            else {
                if (counts.size() >= k) {
                    Iterator<Map.Entry<Integer, Integer>> it = counts.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Integer, Integer> keyVal = it.next();
                        if (keyVal.getValue() - 1 == 0) {
                            it.remove();
                        }
                        else {
                            keyVal.setValue(keyVal.getValue() - 1);
                        }
                    }
                }
                else {
                    counts.put(num, 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            entry.setValue(0);
        }

        for (Integer num : nums) {
            Integer count = counts.get(num);
            if (count != null) {
                counts.put(num, count + 1);
            }
        }

        int maxCount = 0;
        int maxNum = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxNum = entry.getKey();
            }
        }

        return maxNum;
    }




    public void sortLetters(char[] chars) {
        int i = 0;
        int j = chars.length - 1;
        while (i < j) {
            while (i < j && chars[j] <= 'Z') {
                j--;
            }

            while (i < j && chars[i] >= 'a') {
                i++;
            }

            if (i < j) {
                char temp = chars[j];
                chars[j] = chars[i];
                chars[i] = temp;
            }
        }
    }


    public ArrayList<Long> productExcludeItself(ArrayList<Integer> nums) {
        ArrayList<Long> result = new ArrayList<>();
        if (nums == null || nums.size() <= 1) {
            result.add(1L);
            return result;
        }

        int size = nums.size();
        long[] r2l = new long[size];
        r2l[size - 1] = nums.get(size - 1);
        for (int i = size - 2; i > 0; i--) {
            r2l[i] = nums.get(i) * r2l[i + 1];
        }

        long l2r = 1;
        for (int i = 0; i < size - 1; i++) {
            result.add(l2r * r2l[i + 1]);
            l2r *= nums.get(i);
        }
        result.add(l2r);
        return result;
    }



    public ArrayList<Integer> previousPermuation(ArrayList<Integer> nums) {
        if (nums == null || nums.size() <= 1) return nums;

        Comparator<Integer> reverse = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };

        boolean isLast = true;
        int size = nums.size();
        loop:
        for (int i = size - 2; i > -1; i--) {
            int numI = nums.get(i);
            for (int j = size - 1; j > i; j--) {
                if (numI > nums.get(j)) {
                    isLast = false;
                    nums.set(i, nums.get(j));
                    nums.set(j, numI);
                    quickSort(nums, reverse, i + 1, size - 1);
                    break loop;
                }
            }
        }

        if (isLast) {
            quickSort(nums, reverse, 0, size - 1);
        }
        return nums;
    }

    private <T> void quickSort(List<T> list, Comparator<T> comparator) {
        if (list != null && comparator != null) {
            quickSort(list, comparator, 0, list.size() - 1);
        }
    }

    private <T> void quickSort(List<T> list, Comparator<T> comparator, int left, int right) {
        if (left >= right) return;

        T key = list.get(left);
        int i = left;
        int j = right;
        while (i < j) {
            while (i < j && comparator.compare(key, list.get(j)) <= 0) {
                j--;
            }
            list.set(i, list.get(j));

            while (i < j && comparator.compare(key, list.get(i)) >= 0) {
                i++;
            }
            list.set(j, list.get(i));
        }
        list.set(i, key);

        quickSort(list, comparator, left, i - 1);
        quickSort(list, comparator, i + 1, right);
    }


    public int[] nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return nums;

        int len = nums.length;
        for (int i = len - 2; i > -1; i--) {
            int numI = nums[i];
            for (int j = len - 1; j > i; j--) {
                if (numI < nums[j]) {
                    nums[i] = nums[j];
                    nums[j] = numI;
                    Arrays.sort(nums, i + 1, len);
                    return nums;
                }
            }
        }
        Arrays.sort(nums, 0, len);
        return nums;
    }



    public String reverseWords(String s) {
        if (s == null || s.isEmpty()) return s;

        StringBuilder builder = new StringBuilder();
        int len = s.length();
        char[] chars = s.toCharArray();
        for (int i = len - 1, j = len; i > -1; i--) {
            char c = chars[i];
            if (c == ' ') {
                if (j - i > 1) {
                    builder.append(chars, i + 1, j - i - 1).append(' ');
                }
                j = i;
            }
            else if (i == 0) {
                builder.append(chars, 0, j).append(' ');
            }
        }

        len = builder.length();
        if (len > 0) {
            builder.deleteCharAt(len - 1);
        }
        return builder.toString();
    }



    public int atoi(String str) {
        if (str == null) return 0;

        char sign = '\0';
        long result = 0;
        int resultLen = 0;
        boolean numFound = false;
        for (int i = 0, len = str.length(); i < len; i++) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                numFound = true;
                result = result * 10 + c - '0';
                resultLen++;
                if (resultLen == 11) {
                    break;
                }
            }
            else {
                if (numFound) {
                    break;
                }
                else if (c == '+' || c == '-') {
                    if (sign != '\0') {
                        result = 0;
                        break;
                    }
                    else {
                        sign = c;
                    }
                }
            }
        }

        result *= (sign == '-' ? -1 : 1);
        if (result > Integer.MAX_VALUE) result = Integer.MAX_VALUE;
        else if (result < Integer.MIN_VALUE) result = Integer.MIN_VALUE;
        return (int) result;
    }


    public boolean compareStrings(String strA, String strB) {
        int[] counts = new int['Z' - 'A' + 1];
        for (int i = 0; i < strA.length(); i++) {
            counts[strA.charAt(i) - 'A']++;
        }
        for (int i = 0; i < strB.length(); i++) {
            if ((--counts[strB.charAt(i) - 'A']) < 0) return false;
        }
        return true;
    }


    public int[] twoSum(int[] nums, int target) {
        int len = nums.length;
        HashMap<Integer, Integer> indexs = new HashMap<>(len);
        for (int i = 0; i < len; i++) {
            Integer existedIndex = indexs.get(target - nums[i]);
            if (existedIndex != null) {
                return new int[]{existedIndex + 1, i + 1};
            }
            else {
                indexs.put(nums[i], i);
            }
        }
        return new int[]{};
    }




    public ArrayList<ArrayList<Integer>> threeSum(int[] nums) {
        if (nums != null && nums.length >= 3) {
            Arrays.sort(nums);
            return threeSum(nums, 0, 0);
        }
        return new ArrayList<>();
    }

    private ArrayList<ArrayList<Integer>> threeSum(int[] nums, int target, int fromIndex) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int len = nums.length;
        for (int i = fromIndex; i <= len - 3; i++) {
            if (target > 0 && nums[i] > target) break;
            else if (i > fromIndex && nums[i] == nums[i - 1]) {}
            else {
                ArrayList<int[]> twos = twoSum(nums, target - nums[i], i + 1);
                int size = twos.size();
                if (size > 0) {
                    for (int[] two : twos) {
                        result.add(new ArrayList<>(Arrays.asList(
                                nums[i], two[0], two[1]
                        )));
                    }
                }
            }
        }
        return result;
    }

    private ArrayList<int[]> twoSum(int[] nums, int target, int fromIndex) {
        ArrayList<int[]> result = new ArrayList<>();
        int len = nums.length;
        HashMap<Integer, Boolean> expecteds = new HashMap<>();
        for (int i = fromIndex; i < len; i++) {
            int expected = target - nums[i];
            Boolean exp = expecteds.get(expected);
            if (exp != null) {
                if (exp) {
                    result.add(new int[]{expected, nums[i]});
                    expecteds.put(expected, false);
                }
            }
            else {
                expecteds.put(nums[i], true);
            }
        }
        return result;
    }




    public ArrayList<ArrayList<Integer>> fourSum(int[] nums, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (nums != null && nums.length >= 4) {
            Arrays.sort(nums);
            int len = nums.length;
            for (int i = 0; i <= len - 4; i++) {
                if (target > 0 && nums[i] > target) break;
                else if (i > 0 && nums[i] == nums[i - 1]) {}
                else {
                    ArrayList<ArrayList<Integer>> threes = threeSum(nums, target - nums[i], i + 1);
                    int size = threes.size();
                    if (size > 0) {
                        for (ArrayList<Integer> three : threes) {
                            ArrayList<Integer> resultItem = new ArrayList<>();
                            resultItem.add(nums[i]);
                            resultItem.addAll(three);
                            result.add(resultItem);
                        }
                    }
                }
            }
        }
        return result;
    }




    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) return 0;

        Arrays.sort(nums);

        int len = nums.length;
        int closet = nums[0] + nums[len / 2] + nums[len - 1];
        for (int i = 0; i < len - 2; i++) {
            int l = i + 1;
            int r = len - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (Math.abs(target - closet) > Math.abs(target - sum)) {
                    closet = sum;
                }
                if (sum == target) {
                    return closet;
                }
                else if (sum < target) {
                    l++;
                }
                else {
                    r--;
                }
            }
        }
        return closet;
    }




    public int searchInsert(int[] arr, int target) {
        if (arr == null) return -1;
        return searchInsert(arr, target, 0, arr.length - 1);
    }

    private int searchInsert(int[] arr, int target, int left, int right) {
        if (left >= right) {
            return left == arr.length || arr[left] >= target ? left : left + 1;
        }

        int midI = (left + right) / 2;
        int mid = arr[midI];
        if (mid == target) {
            return midI;
        }
        else if (mid > target) {
            return searchInsert(arr, target, left, midI - 1);
        }
        else return searchInsert(arr, target, midI + 1, right);
    }





    public int[] searchRange(int[] nums, int target) {
        return searchRange(nums, target, 0, nums.length - 1);
    }

    private int[] searchRange(int[] nums, int target, int left, int right) {
        if (left >= right) {
            return left == nums.length || nums[left] != target ? new int[] {-1, -1} : new int[] {left, left};
        }

        int midIndex = (left + right) / 2;
        int midVal = nums[midIndex];
        if (midVal == target) {
            int[] leftIndexs = searchRange(nums, target, left, midIndex - 1);
            int[] rightIndexs = searchRange(nums, target, midIndex + 1, right);
            return new int[] {leftIndexs[0] == -1 ? midIndex : leftIndexs[0], rightIndexs[1] == -1 ? midIndex : rightIndexs[1]};
        }
        else if (midVal > target) {
            return searchRange(nums, target, left, midIndex - 1);
        }
        else {
            return searchRange(nums, target,midIndex + 1, right);
        }
    }




    public int search(int[] nums, int target) {
        return nums == null ? -1 : search(nums, target, 0, nums.length - 1);
    }

    private int search(int[] nums, int target, int left, int right) {
        if (left >= right) {
            return left == nums.length || target != nums[left] ? -1 : left;
        }

        int midIndex = (left + right) / 2;
        int midVal = nums[midIndex];
        if (midVal == target) {
            return midIndex;
        }
        else {
            int leftVal = nums[left];
            int rightVal = nums[right];
            if (leftVal == target) {
                return left;
            }
            else if (rightVal == target) {
                return right;
            }
            else if (leftVal < target && target < midVal) {
                return search(nums, target, left + 1, midIndex - 1);
            }
            else if (midVal < target && target < rightVal) {
                return search(nums, target, midIndex + 1, right - 1);
            }
            else {
                int index;
                if ((index = search(nums, target, left + 1, midIndex - 1)) != -1
                        || (index = search(nums, target, midIndex + 1, right - 1)) != -1) {
                    return index;
                }
                else {
                    return -1;
                }
            }
        }
    }

    public boolean searchII(int[] nums, int target) {
        return nums != null && search(nums, target, 0, nums.length - 1) != -1;
    }




    public void mergeSortedArray(int[] arrA, int m, int[] arrB, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i > -1 && j > -1) {
            arrA[k--] = arrA[i] > arrB[j] ? arrA[i--] : arrB[j--];
        }

        while (j > -1) {
            arrA[k--] = arrB[j--];
        }
    }



    public double findMedianSortedArrays(int[] arrA, int[] arrB) {
        int lenAB = arrA.length + arrB.length;
        int k = lenAB / 2;
        if (lenAB % 2 == 0) {
            return (
                    findKthOfSortedArrays(arrA, 0, arrB, 0, k)
                    + findKthOfSortedArrays(arrA, 0, arrB, 0, k + 1)
                    ) / 2.0;
        }
        else return findKthOfSortedArrays(arrA, 0, arrB, 0, k + 1);
    }

    private double findKthOfSortedArrays(int[] arrA, int startA, int[] arrB, int startB, int k) {
        int lenA = arrA.length;
        int lenB = arrB.length;

        if (startA >= lenA) return arrB[startB + k - 1];
        else if (startB >= lenB) return arrA[startA + k - 1];

        if (k == 1) {
            return Math.min(arrA[startA], arrB[startB]);
        }

        int keyAIndex = startA + k / 2 - 1;
        int keyBIndex = startB + k / 2 - 1;
        int keyA = keyAIndex >= lenA ? arrA[lenA - 1] : arrA[keyAIndex];
        int keyB = keyBIndex >= lenB ? arrB[lenB - 1] : arrB[keyBIndex];
        if (keyA < keyB) {
            return findKthOfSortedArrays(arrA, keyAIndex + 1, arrB, startB, k - (keyAIndex - startA + 1));
        }
        else {
            return findKthOfSortedArrays(arrA, startA, arrB, keyBIndex + 1, k - (keyBIndex - startB + 1));
        }
    }






    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> values = new ArrayList<>();
        if (root != null) {
            Stack<TreeNode> nodes = new Stack<>();
            nodes.push(root);
            while (!nodes.isEmpty()) {
                TreeNode cur = nodes.pop();
                values.add(cur.val);

                if (cur.right != null) {
                    nodes.push(cur.right);
                }
                if (cur.left != null) {
                    nodes.push(cur.left);
                }
            }
        }
        return values;
    }

    public ArrayList<Integer> preorderTraversalIt(TreeNode root) {
        ArrayList<Integer> values = new ArrayList<>();
        preorderTraversalIt(root, values);
        return values;
    }

    private void preorderTraversalIt(TreeNode root, ArrayList<Integer> values) {
        if (root != null) {
            values.add(root.val);
            preorderTraversalIt(root.left, values);
            preorderTraversalIt(root.right, values);
        }
    }



    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> values = new ArrayList<>();
        Stack<Map.Entry<TreeNode, Boolean>> nodes = new Stack<>();
        if (root != null) {
            nodes.push(new AbstractMap.SimpleEntry<>(root, false));
            while (!nodes.isEmpty()) {
                Map.Entry<TreeNode, Boolean> entry = nodes.pop();
                TreeNode node = entry.getKey();
                if (entry.getValue()) {
                    values.add(node.val);
                }
                else {
                    if (node.right != null) {
                        nodes.push(new AbstractMap.SimpleEntry<>(node.right, false));
                    }
                    nodes.push(new AbstractMap.SimpleEntry<>(node, true));
                    if (node.left != null) {
                        nodes.push(new AbstractMap.SimpleEntry<>(node.left, false));
                    }
                }
            }
        }
        return values;
    }

    public ArrayList<Integer> inorderTraversalIt(TreeNode root) {
        ArrayList<Integer> values = new ArrayList<>();
        inorderTraversalIt(root, values);
        return values;
    }

    private void inorderTraversalIt(TreeNode root, ArrayList<Integer> values) {
        if (root != null) {
            inorderTraversalIt(root.left, values);
            values.add(root.val);
            inorderTraversalIt(root.right, values);
        }
    }





    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> values = new ArrayList<>();
        Stack<Map.Entry<TreeNode, Boolean>> nodes = new Stack<>();
        if (root != null) {
            nodes.push(new AbstractMap.SimpleEntry<>(root, false));
            while (!nodes.isEmpty()) {
                Map.Entry<TreeNode, Boolean> entry = nodes.pop();
                TreeNode node = entry.getKey();
                if (entry.getValue()) {
                    values.add(node.val);
                }
                else {
                    nodes.push(new AbstractMap.SimpleEntry<>(node, true));
                    if (node.right != null) {
                        nodes.push(new AbstractMap.SimpleEntry<>(node.right, false));
                    }
                    if (node.left != null) {
                        nodes.push(new AbstractMap.SimpleEntry<>(node.left, false));
                    }
                }
            }
        }
        return values;
    }

    public ArrayList<Integer> postorderTraversalIt(TreeNode root) {
        ArrayList<Integer> values = new ArrayList<>();
        postorderTraversalIt(root, values);
        return values;
    }

    private void postorderTraversalIt(TreeNode root, ArrayList<Integer> values) {
        if (root != null) {
            postorderTraversalIt(root.left, values);
            postorderTraversalIt(root.right, values);
            values.add(root.val);
        }
    }




    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        levelOrder(values, root, 0);
        return values;
    }

    private void levelOrder(ArrayList<ArrayList<Integer>> values, TreeNode node, int level) {
        if (node != null) {
            while (values.size() <= level) {
                values.add(new ArrayList<Integer>());
            }
            values.get(level).add(node.val);
            levelOrder(values, node.left, level + 1);
            levelOrder(values, node.right, level + 1);
        }
    }




    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        levelOrder(values, root, 0);
        Collections.reverse(values);
        return values;
    }


    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        levelOrder(values, root, 0);
        for (int i = 0; i < values.size(); i++) {
            if (i % 2 == 1) {
                Collections.reverse(values.get(i));
            }
        }
        return values;
    }





    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode buildTree(int[] inorder, int inFrom, int inTo, int[] postorder, int postFrom, int postTo) {
        if (inFrom > inTo) return null;

        int rootVal = postorder[postTo];
        TreeNode root = new TreeNode(rootVal);
        int rootInorderI = -1;
        for (int i = inFrom; i <= inTo; i++) {
            if (inorder[i] == rootVal) {
                rootInorderI = i;
                break;
            }
        }
        if (rootInorderI != -1) {
            root.left = buildTree(inorder, inFrom, rootInorderI - 1, postorder, postFrom, postFrom + rootInorderI - inFrom - 1);
            root.right = buildTree(inorder, rootInorderI + 1, inTo, postorder, postFrom + rootInorderI - inFrom,  postTo - 1);
        }
        return root;
    }




    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        return buildTreePreIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTreePreIn(int[] preorder, int preFrom, int preTo, int[] inorder, int inFrom, int inTo) {
        if (inFrom > inTo) return null;

        int rootVal = preorder[preFrom];
        TreeNode root = new TreeNode(rootVal);
        int rootInorderI = -1;
        for (int i = inFrom; i <= inTo; i++) {
            if (inorder[i] == rootVal) {
                rootInorderI = i;
                break;
            }
        }
        if (rootInorderI != -1) {
            root.left = buildTreePreIn(preorder, preFrom + 1, preFrom + rootInorderI - inFrom, inorder, inFrom, rootInorderI - 1);
            root.right = buildTreePreIn(preorder, preFrom + rootInorderI - inFrom + 1, preTo, inorder, rootInorderI + 1, inTo);
        }
        return root;
    }




    public int findPeak(int[] nums) {
        int len = nums.length;
        int start = 1;
        int end = len - 2;
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] > nums[mid + 1]) {
                end = mid;
            }
            else {
                start = mid + 1;
            }
        }

        return end;
    }






    public int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        //o(n^2)
//        int len = nums.length;
//        int[] incs = new int[len];
//        incs[0] = 1;
//        int globalMax = 1;
//        for (int i = 1; i < len; i++) {
//            incs[i] = 1;
//            int numI = nums[i];
//            for (int j = i - 1; j >= 0; j--) {
//                if (numI > nums[j]) {
//                    incs[i] = Math.max(incs[i], incs[j] + 1);
//                }
//            }
//            globalMax = Math.max(globalMax, incs[i]);
//        }
//        return globalMax;

        //o(n * log(n))
        int len = nums.length;
        int[] mins = new int[len + 1];
        mins[0] = Integer.MIN_VALUE;
        for (int i = 1; i <= len; i++) {
            mins[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < len; i++) {
            mins[prevMinIndex(mins, nums[i])] = nums[i];
        }

        for (int i = len; i >= 1; i--) {
            if (mins[i] != Integer.MAX_VALUE) {
                return i;
            }
        }

        return 0;
    }

    private int prevMinIndex(int[] mins, int num) {
        int left = 0;
        int right = mins.length - 1;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (mins[mid] < num) {
                left = mid;
            }
            else right = mid;
        }
        return mins[left] > num ? left : right;
    }







    public int longestCommonSubsequence(String strA, String strB) {
        if (strA == null || strB == null || strA.isEmpty() || strB.isEmpty()) {
            return 0;
        }

        int lenA = strA.length();
        int lenB = strB.length();
        int[][] cache = new int[lenA + 1][lenB + 1];
        for (int i = 0; i < lenA; i++) {
            char a = strA.charAt(i);
            for (int j = 0; j < lenB; j++) {
                cache[i + 1][j + 1] = Math.max(cache[i + 1][j], cache[i][j + 1]);
                if (strB.charAt(j) == a) {
                    cache[i + 1][j + 1] = Math.max(cache[i + 1][j + 1], cache[i][j] + 1);
                }
            }
        }
        return cache[lenA][lenB];
    }






    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        else if (strs.length == 1) return strs[0];

        String firstStr = strs[0];
        if (firstStr == null) {
            return "";
        }

        int lenFS = firstStr.length();
        int lenS = strs.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lenFS; i++) {
            char c = firstStr.charAt(i);
            for (int j = 1; j < lenS; j++) {
                String str = strs[j];
                if (str == null || i == str.length() || c != str.charAt(i)) {
                    return builder.toString();
                }
            }
            builder.append(c);
        }

        return builder.toString();
    }




    public int longestCommonSubstring(String strA, String strB) {
        if (strA == null || strA.isEmpty() || strB == null || strB.isEmpty()) {
            return 0;
        }

        int max = 0;
        int lenA = strA.length();
        int lenB = strB.length();
        int[][] cache = new int[lenA + 1][lenB + 1];
        for (int i = 0; i < lenA; i++) {
            char a = strA.charAt(i);
            for (int j = 0; j < lenB; j++) {
                if (strB.charAt(j) == a) {
                    cache[i + 1][j + 1] = cache[i][j] + 1;
                }
                else {
                    cache[i + 1][j + 1] = 0;
                }
                max = Math.max(max, cache[i + 1][j + 1]);
            }
        }
        return max;
    }




    public int median(int[] nums) {
        return nums == null || nums.length == 0 ? 0 : median(nums, 0, nums.length - 1, (nums.length - 1) / 2);
    }

    private int median(int[] nums, int left, int right, int target) {
        if (left >= right) {
            return nums[left];
        }

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

        if (l == target) {
            return key;
        }
        else if (target < l) {
            return median(nums, left, l - 1, target);
        }
        else {
            return median(nums, l + 1, right, target);
        }
    }



    public int[] medianII(int[] nums) {
        if (nums == null) return null;

        int len = nums.length;
        int[] medians = new int[len];
        if (len > 0) {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>((len + 1) / 2, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((len + 1) / 2, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });

            for (int i = 0; i < len; i++) {
                int numI = nums[i];
                if (maxHeap.isEmpty() || maxHeap.peek() >= numI) {
                    maxHeap.offer(numI);
                }
                else {
                    minHeap.offer(numI);
                }

                int sizeDiff;
                while ((sizeDiff = maxHeap.size() - minHeap.size()) != 0 && sizeDiff != 1) {
                    if (sizeDiff > 1) {
                        minHeap.offer(maxHeap.poll());
                    }
                    else {
                        maxHeap.offer(minHeap.poll());
                    }
                }

                medians[i] = maxHeap.peek();
            }
        }
        return medians;
    }



    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }




    public int singleNumberII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] bitCounts = new int[32];
        for (int num : nums) {
            int index = 0;
            while (num != 0) {
                bitCounts[index++] += num & 1;
                num >>>= 1;
            }
        }

        int result = 0;
        int two = 1;
        for (int count : bitCounts) {
            if (count % 3 != 0) {
                result += two;
            }
            two <<= 1;
        }
        return result;
    }




    public List<Integer> singleNumberIII(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }

        int xor = 0;
        int[] bitCounts = new int[32];
        for (int num : nums) {
            xor ^= num;
            int index = 0;
            while (num != 0) {
                bitCounts[index++] += num & 1;
                num >>>= 1;
            }
        }

        for (int i = 0; i < bitCounts.length; i++) {
            int count = bitCounts[i];
            if (count % 2 == 1) {
                int bit = 1 << i;
                int xorOther = 0;
                for (int num : nums) {
                    if ((num & bit) == 0) {
                        xorOther ^= num;
                    }
                }
                result.add(xorOther);
                result.add(xor ^ xorOther);
                return result;
            }
        }

        result.add(0);
        result.add(xor);
        return result;
    }



    public TreeNode insertNode(TreeNode root, TreeNode node) {
        if (root != null) {
            TreeNode cur = root;
            while (cur != null) {
                if (cur.val > node.val) {
                    if (cur.left == null) {
                        cur.left = node;
                        break;
                    }
                    else {
                        cur = cur.left;
                    }
                }
                else {
                    if (cur.right == null) {
                        cur.right = node;
                        break;
                    }
                    else {
                        cur = cur.right;
                    }
                }
            }
            return root;
        }
        else return node;
    }



    public TreeNode removeNode(TreeNode root, int value) {
        if (root != null) {
            TreeNode newRoot = new TreeNode(0);
            newRoot.left = root;
            TreeNode parent = newRoot;
            TreeNode cur = root;
            while (cur != null) {
                if (cur.val == value) {
                    break;
                }
                else if (cur.val > value) {
                    parent = cur;
                    cur = parent.left;
                }
                else {
                    parent = cur;
                    cur = parent.right;
                }
            }

            if (cur != null) {
                TreeNode left = cur.left;
                cur.left = null;
                TreeNode right = cur.right;
                cur.right = null;

                TreeNode replaceNode;
                if (right == null) {
                    replaceNode = left;
                }
                else {
                    TreeNode parentR = right;
                    while (parentR.left != null && parentR.left.left != null) {
                        parentR = parentR.left;
                    }
                    if (parentR.left != null) {
                        replaceNode = parentR.left;
                        parentR.left = parentR.left.right;
                        replaceNode.right = right;
                    }
                    else {
                        replaceNode = parentR;
                    }
                    replaceNode.left = left;
                }

                if (parent.left == cur) {
                    parent.left = replaceNode;
                }
                else {
                    parent.right = replaceNode;
                }
            }

            return newRoot.left;
        }
        else return null;
    }




    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode nodeA, TreeNode nodeB) {
        if (root == null || root == nodeA || root == nodeB) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, nodeA, nodeB);
        TreeNode right = lowestCommonAncestor(root.right, nodeA, nodeB);
        if (left != null && right != null) {
            return root;
        }
        else if (left != null) {
            return left;
        }
        else if (right != null) {
            return right;
        }
        return null;
    }





    public int kSum(int[] nums, int k, int target) {
        kSumCache.clear();
        return kSum(nums, k, target, nums.length - 1);
    }

    private HashMap<String, Integer> kSumCache = new HashMap<>();

    private int kSum(int[] nums, int k, int target, int endIndex) {
        String key = k + "," + target + "," + endIndex;
        Integer result = kSumCache.get(key);
        if (result != null) {
            return result;
        }

        if (k == 0 && target == 0) {
            result =  1;
        }
        else if (endIndex < 0) {
            result = 0;
        }
        else if (endIndex + 1 == k) {
            int sum = 0;
            for (int i = 0; i <= endIndex; i++) {
                sum += nums[i];
            }
            result = target == sum ? 1 : 0;
        }
        else if (endIndex + 1 < k) {
            result = 0;
        }
        else {
            result = kSum(nums, k, target, endIndex - 1) + kSum(nums, k - 1, target - nums[endIndex], endIndex - 1);
        }

        kSumCache.put(key, result);
        return result;
    }


    public ArrayList<ArrayList<Integer>> kSumII(int[] nums, int k, int target) {
        kSumIICache.clear();
        return kSumII(nums, k, target, nums.length - 1);
    }

    private HashMap<String, ArrayList<ArrayList<Integer>>> kSumIICache = new HashMap<>();

    private ArrayList<ArrayList<Integer>> kSumII(int[] nums, int k, int target, int endIndex) {
        String key = k + "," + target + "," + endIndex;
        ArrayList<ArrayList<Integer>> result = kSumIICache.get(key);
        if (result != null) {
            return result;
        }

        result = new ArrayList<>();
        if (k == 0 && target == 0) {
            result.add(new ArrayList<Integer>());
        }
        else if (endIndex < 0) {
        }
        else if (endIndex + 1 == k) {
            int sum = 0;
            for (int i = 0; i <= endIndex; i++) {
                sum += nums[i];
            }
            if (sum == target) {
                ArrayList<Integer> resultItem = new ArrayList<>();
                for (int i = 0; i <= endIndex; i++) {
                    resultItem.add(nums[i]);
                }
                result.add(resultItem);
            }
        }
        else if (endIndex + 1 < k) {
        }
        else {
            result.addAll(kSumII(nums, k, target, endIndex - 1));

            ArrayList<ArrayList<Integer>> subResult = kSumII(nums, k - 1, target - nums[endIndex], endIndex - 1);
            for (ArrayList<Integer> list : subResult) {
                ArrayList<Integer> copy = (ArrayList<Integer>) list.clone();
                copy.add(nums[endIndex]);
                result.add(copy);
            }
        }

        kSumIICache.put(key, result);
        return result;
    }




    public int MinAdjustmentCost(ArrayList<Integer> nums, int target) {
        int[][]cost = new int[nums.size() + 1][101];
        int size = nums.size();
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= 100; j++) {
                cost[i][j] = Integer.MAX_VALUE;
                for (int k = j - target; k <= j + target; k++) {
                    if (k >= 1 && k <= 100) {
                        cost[i][j] = Math.min(cost[i][j], cost[i - 1][k] + Math.abs(j - nums.get(i - 1)));
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int j = 1; j <= 100; j++) {
           min = Math.min(min, cost[size][j]);
        }
        return min;
    }




    public int backPack(int m, int[] vs) {
        int len = vs.length;
        int[][] cache = new int[len][];
        for (int i = 0; i < len; i++) {
            int[] temp = new int[m + 1];
            Arrays.fill(temp, -1);
            cache[i] = temp;
        }
        return backPack(m, len - 1, vs, cache);
    }

    private int backPack(int m, int n, int[] vs, int[][] cache) {
        if (m <= 0 || n < 0) {
            return 0;
        }

        int result = cache[n][m];
        if (result > -1) {
            return result;
        }
        else {
            result = backPack(m, n - 1, vs, cache);
            if (m >= vs[n]) {
                result = Math.max(result, backPack(m - vs[n], n - 1, vs, cache) + vs[n]);
            }
        }
        cache[n][m] = result;
        return result;
    }



    public boolean isBalanced(TreeNode root) {
        int[] check = checkBalance(root);
        return Math.abs(check[0] - check[1]) <= 1;
    }

    private int[] checkBalance(TreeNode root) {
        if (root == null) {
            return new int[] {0, 0};
        }

        int[] left = checkBalance(root.left);
        if (Math.abs(left[0] - left[1]) > 1) {
            return left;
        }

        int[] right = checkBalance(root.right);
        if (Math.abs(right[0] - right[1]) > 1) {
            return right;
        }

        return new int[] {Math.max(left[0], left[1]) + 1, Math.max(right[0], right[1]) + 1};
    }



    public int maxPathSum(TreeNode root) {
        int[] maxs = maxPathSumIt(root);
        return Math.max(maxs[0], maxs[1]);
    }

    private int[] maxPathSumIt(TreeNode root) {
        if (root == null) {
            return new int[] {Integer.MIN_VALUE, 0};
        }

        int[] left = maxPathSumIt(root.left);
        int[] right = maxPathSumIt(root.right);
        int[] result = new int[] {
                Math.max(Math.max(left[0], right[0]), Math.max(left[1] + right[1], 0) + root.val),
                Math.max(Math.max(left[1] , right[1]), 0) + root.val
        };
        return result;
    }




    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private  boolean isValidBST(TreeNode root, long min, long max) {
        if (root == null) return true;

        if (root.val <= min || root.val >= max) return false;
        return isValidBST(root.left, min, root.val) && isValidBST(root.right, root.val, max);
    }




    public ListNode partition(ListNode head, int x) {
        ListNode minHead = new ListNode(0);
        ListNode minCur = minHead;
        ListNode maxHead = new ListNode(0);
        ListNode maxCur = maxHead;
        while (head != null) {
            if (head.val < x) {
                minCur.next = head;
                minCur = head;
            }
            else {
                maxCur.next = head;
                maxCur = head;
            }
            head = head.next;
        }
        minCur.next = maxHead.next;
        maxCur.next = null;
        return minHead.next;
    }



    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }




    public ListNode sortList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode minHead = new ListNode(0);
        ListNode curMin = minHead;

        ListNode maxHead = new ListNode(0);
        ListNode curMax = maxHead;

        ListNode key = head;
        head = head.next;
        key.next = null;
        while (head != null) {
            if (head.val < key.val) {
                curMin.next = head;
                curMin = head;
            }
            else {
                curMax.next = head;
                curMax = head;
            }
            head = head.next;
        }
        curMin.next = null;
        curMax.next = null;

        minHead.next = sortList(minHead.next);
        maxHead.next = sortList(maxHead.next);

        curMin = minHead;
        while (curMin.next != null) {
            curMin = curMin.next;
        }
        curMin.next = key;
        key.next = maxHead.next;
        return minHead.next;
    }




    public void reorderList(ListNode head) {
        if (head == null) return;

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        fast = slow.next;
        slow.next = null;
        ListNode rightReverse = null;
        while (fast != null) {
            ListNode next = fast.next;
            fast.next = rightReverse;
            rightReverse = fast;
            fast = next;
        }

        slow = head;
        while (slow != null && rightReverse != null) {
            ListNode leftNext = slow.next;
            ListNode rightNext = rightReverse.next;
            slow.next = rightReverse;
            rightReverse.next = leftNext;
            slow = leftNext;
            rightReverse = rightNext;
        }
    }




    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int len = nums.length;
        if (len <= 1) {
            return len;
        }

        int i = 0;
        int j = 1;
        for (; i < len && j < len; i++) {
            int numI = nums[i];
            while (j < len && nums[j] == numI) {
                j++;
            }
            if (j < len) {
                nums[i + 1] = nums[j];
            }
        }
        return i;
    }



    public int removeDuplicatesII(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int len = nums.length;
        if (len <= 2) {
            return len;
        }

        int i = 0;
        int j = 0;
        int equal = 0;
        for (; i < len && j < len;) {
            int numI = nums[i];
            if (numI == nums[j]) {
                equal++;
                if (equal == 2) {
                    nums[i + 1] = nums[j];
                    i++;
                }
                j++;
            }
            else {
                equal = 1;
                if (j - i > 0) {
                    nums[i + 1] = nums[j];
                }
                i++;
                j++;
            }
        }
        return i + 1;
    }





    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }




    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast.next) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }



    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists == null || lists.isEmpty()) return null;
        while (lists.size() > 1) {
            ListNode list1 = lists.remove(lists.size() - 1);
            ListNode list2 = lists.remove(lists.size() - 1);
            lists.add(mergeList(list1, list2));
        }
        return lists.get(0);
    }

    private ListNode mergeList(ListNode list1, ListNode list2) {
        ListNode tempHead = new ListNode(0);
        ListNode cur = tempHead;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = list1;
                list1 = list1.next;
            }
            else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 != null ? list1 : list2;
        return tempHead.next;
    }





    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) return head;

        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode next = cur.next;
            cur.next = new RandomListNode(cur.label);
            cur.next.next = next;
            cur = next;
        }

        cur = head;
        while (cur != null) {
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }

        cur = head;
        RandomListNode newHead = head.next;
        while (cur != null) {
            RandomListNode next = cur.next.next;
            if (next != null) {
                cur.next.next = next.next;
            }
            cur.next = next;
            cur = next;
        }
        return newHead;
    }



    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;

        ListNode midPrev = null;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            midPrev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        TreeNode root = new TreeNode(slow.val);
        if (midPrev != null) {
            midPrev.next = null;
            root.left = sortedListToBST(head);
            midPrev.next = slow;
        }
        if (slow.next != null) {
            root.right = sortedListToBST(slow.next);
        }
        return root;
    }




    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null) return false;
        else if (s.isEmpty()) return true;

        Dictionary dictionary = new Dictionary(dict);
        return wordBreak(s, 0, dictionary);
    }

    //当单词很长的时候未出现栈溢出
    private boolean wordBreak(String s, int from, Set<String> dict) {
        int len = s.length();
        if (from == len) return true;

        for (int i = from; i < len; i++) {
            if (dict.contains(s.substring(from, i + 1))) {
                if (wordBreak(s, i + 1, dict)) {
                    return true;
                }
            }
        }
        return false;
    }

    //通过字典树来解决
    private boolean wordBreak(String s, int from, Dictionary dictionary) {
        int len = s.length();
        if (from == len) return true;

        while (true) {
            List<Integer> endIndexs = dictionary.search(s, from);
            if (endIndexs.isEmpty()) {
                return false;
            }
            else if (endIndexs.size() == 1) {
                int endIndex = endIndexs.get(0);
                if (endIndex + 1 == len) {
                    return true;
                }
                else {
                    from = endIndex + 1;
                }
            }
            else {
                for (Integer endIndex : endIndexs) {
                    if (wordBreak(s, endIndex + 1, dictionary)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }




    public int minCut(String str) {
        if (str == null || str.isEmpty()) return 0;

        int len = str.length();
        int[] cache = new int[len + 1];
        for (int i = 0; i <= len; i++) {
            cache[i] = i - 1;
        }

        int l;
        int r;
        for (int i = 0; i < len; i++) {
            l = i;
            r = i + 1;
            while (l > -1 && r < len && str.charAt(l) == str.charAt(r)) {
                cache[r] = Math.min(cache[r], 1 + cache[l + 1]);
                l--;
                r++;
            }
            cache[r] = Math.min(cache[r], 1 + cache[l + 1]);

            l = i - 1;
            r = i + 1;
            while (l > -1 && r < len && str.charAt(l) == str.charAt(r)) {
                cache[r] = Math.min(cache[r], 1 + cache[l + 1]);
                l--;
                r++;
            }
            cache[r] = Math.min(cache[r], 1 + cache[l + 1]);
        }
        return cache[len];
    }

    private boolean isPalindrome(String str, int from, int to) {
        if (from == to) return true;

        int i = (from + to - 1) / 2;
        int j = (from + to) / 2 + 1;
        while (i >= from && j <= to) {
            if (str.charAt(i--) != str.charAt(j++)) {
                return false;
            }
        }
        return true;
    }



    public int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) return 0;

        int levels = triangle.length;
        int maxLen = triangle[levels - 1].length;
        if (maxLen == 0) return 0;

        int[] temp = null;
        int[] lastSums = new int[maxLen];
        int[] curSums = new int[maxLen];
        for (int i = 0; i < levels; i++) {
            int len = triangle[i].length;
            for (int j = 0; j < len; j++) {
                if (j == 0) {
                    curSums[j] = lastSums[j] + triangle[i][j];
                }
                else if (j == len - 1) {
                    curSums[j] = lastSums[j - 1] + triangle[i][j];
                }
                else {
                    curSums[j] = Math.min(lastSums[j - 1], lastSums[j]) + triangle[i][j];
                }
            }
            temp = lastSums;
            lastSums = curSums;
            curSums = temp;
        }

        int minSum = lastSums[0];
        for (int i = 1; i < maxLen; i++) {
            if (minSum > lastSums[i]) {
                minSum = lastSums[i];
            }
        }
        return minSum;
    }



    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int row = grid.length;
        int col = grid[0].length;

        if (col == 0) return 0;

        int[][] cache = new int[row][col];
        for (int i = 0; i < row; i++) {
            cache[i][0] = i == 0 ? grid[i][0] : cache[i - 1][0] + grid[i][0];
        }
        for (int i = 0; i < col; i++) {
            cache[0][i] = i == 0 ? grid[0][i] : cache[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                cache[i][j] = Math.min(cache[i - 1][j], cache[i][j - 1]) + grid[i][j];
            }
        }
        return cache[row - 1][col - 1];
    }



    public int climbStairs(int n) {
        int a = 0;
        int b = 1;
        while (n-- > 0){
            b = b + a;
            a = b - a;
        }
        return b;
    }



    public ListNode deleteDuplicates(ListNode head) {
        if (head != null) {
            ListNode i = head;
            ListNode j = head.next;
            while (j != null) {
                while (j != null && i.val == j.val) {
                    j = j.next;
                }
                i.next = j;
                i = j;
            }
        }
        return head;
    }

    public ListNode deleteDuplicatesII(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode newHead = new ListNode(0);
        ListNode cur = newHead;
        ListNode i = head;
        ListNode j = head.next;
        while (i != null) {
            if (j == null || i.val != j.val) {
                cur.next = i;
                cur = i;
                i.next = null;
            }
            else {
                while (j != null && j.val == i.val) {
                    j = j.next;
                }
            }
            i = j;
            j = j == null ? null : j.next;
        }

        return newHead.next;
    }




    public int uniquePaths(int m, int n) {
        if (m == 0 || n == 0) return 1;

        int[] last = new int[n + 1];
        last[1] = 1;
        int[] cur = new int[n + 1];
        int[] temp;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cur[j] = last[j] + cur[j - 1];
            }
            temp = last;
            last = cur;
            cur = temp;
        }
        return last[n];
    }



    public int uniquePathsWithObstacles(int[][] grid) {
        int m = grid == null ? 0: grid.length;
        int n = m == 0 ? 0 : grid[0].length;

        if (m == 0 || n == 0) return 1;

        int[] last = new int[n + 1];
        last[1] = 1;
        int[] cur = new int[n + 1];
        int[] temp;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cur[j] = grid[i - 1][j - 1] == 1 ? 0 : last[j] + cur[j - 1];
            }
            temp = last;
            last = cur;
            cur = temp;
        }
        return last[n];
    }



    public boolean canJump(int[] jumps) {
        if (jumps == null || jumps.length <= 1) return true;

        int len = jumps.length;
        int cur = 0;
        int maxReach = jumps[cur];
        while (++cur <= maxReach) {
            if (maxReach >= len - 1) return true;
            maxReach = Math.max(maxReach, cur + jumps[cur]);
        }
        return false;
    }




    public int jump(int[] jumps) {
        if (jumps == null || jumps.length <= 1) return 0;

        int len = jumps.length;
        int cur = 0;
        int jumpNum = 1;
        int maxReach = cur + jumps[cur];
        while (++cur <= maxReach) {
            if (maxReach >= len - 1) {
                return jumpNum;
            }

            int temp = cur + jumps[cur];
            if (temp > maxReach) {
                jumpNum++;
                maxReach = temp;
            }
        }
        return -1;
    }




    public int numDistinct(String source, String target) {
        if (source == null || target == null) return 0;
        else if (target.isEmpty()) return 1;

        int lenS = source.length();
        int[][] cache = new int[lenS][target.length()];
        for (int i = 0; i < lenS; i++) {
            Arrays.fill(cache[i], -1);
        }
        return numDistinct(source, 0, target, 0, cache);
    }

    private int numDistinct(String source, int sFrom, String target, int tFrom, int[][] cache) {
        if (tFrom == target.length()) return 1;
        else if (sFrom == source.length()) return 0;

        int num = cache[sFrom][tFrom];
        if (num != -1) {
            return num;
        }

        num = numDistinct(source, sFrom + 1, target, tFrom, cache);
        if (source.charAt(sFrom) == target.charAt(tFrom)) {
            num += numDistinct(source, sFrom + 1, target, tFrom + 1, cache);
        }
        cache[sFrom][tFrom] = num;
        return num;
    }




    public int minDistance(String word1, String word2) {
        int len1 = word1 == null ? 0 : word1.length();
        int len2 = word2 == null ? 0 : word2.length();
        if (len1 == 0 || len2 == 0) return len1 + len2;

        int[][] cache = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            cache[i][0] = i;
        }
        for (int i = 0; i <= len2; i++) {
            cache[0][i] = i;
        }
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                cache[i + 1][j + 1] = Math.min(cache[i + 1][j], cache[i][j + 1]) + 1;
                if (word1.charAt(i) == word2.charAt(j)) {
                    cache[i + 1][j + 1] = Math.min(cache[i + 1][j + 1], cache[i][j]);
                }
                else {
                    cache[i + 1][j + 1] = Math.min(cache[i + 1][j + 1], cache[i][j] + 1);
                }
            }
        }
        return cache[len1][len2];
    }





    public int ladderLength(String start, String end, Set<String> dict) {
        if (start == null || end == null || dict == null) return 0;
        StrsUndirectedGraph graph = new StrsUndirectedGraph(dict);
        graph.addStr(start);
        graph.addStr(end);
        return graph.minDistance(start, end) + 1;
    }




    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        if (start == null || end == null || dict == null) return new ArrayList<>();
        StrsUndirectedGraph graph = new StrsUndirectedGraph(dict);
        graph.addStr(start);
        graph.addStr(end);
        return graph.minDisPaths(start, end);
    }




    public int largestRectangleArea(int[] height) {
        int max = 0;
        int len;
        if (height != null && (len = height.length) > 0) {
            int num;
            Stack<int[]> stack = new Stack<>();
            for (int i = 0; i < len; i++) {
                int h = height[i];
                num = 0;
                while (!stack.isEmpty() && stack.peek()[0] >= h) {
                    int[] hNum = stack.pop();
                    num += hNum[1];
                    max = Math.max(max, hNum[0] * num);
                }
                stack.push(new int[]{h, num + 1});
            }

            num = 0;
            for (int size = stack.size(), j = size - 1; j >= 0; j--) {
                int[] hNum = stack.get(j);
                num += hNum[1];
                max = Math.max(max, hNum[0] * num);
            }
        }
        return max;
    }




    public boolean exist(char[][] board, String word) {
        if (board == null || word == null) return false;
        if (word.isEmpty()) return true;
        if (board.length == 0) return false;

        int row = board.length;
        int col = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (exist(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, String word, int r, int c, int index) {
        int row = board.length;
        int col = board[0].length;

        char curC = word.charAt(index);
        if (board[r][c] != curC) return false;
        else if (index == word.length() - 1) return true;

        board[r][c] = '\0';
        if (r > 0 && board[r - 1][c] != '\0' && exist(board, word, r - 1, c, index + 1)) {
            board[r][c] = curC;
            return true;
        }
        if (r < row - 1 && board[r + 1][c] != '\0' && exist(board, word, r + 1, c, index + 1)) {
            board[r][c] = curC;
            return true;
        }
        if (c > 0 && board[r][c - 1] != '\0' && exist(board, word, r, c - 1, index + 1)) {
            board[r][c] = curC;
            return true;
        }
        if (c < col - 1 && board[r][c + 1] != '\0' && exist(board, word, r, c + 1, index + 1)) {
            board[r][c] = curC;
            return true;
        }
        board[r][c] = curC;
        return false;
    }



    public int longestConsecutive(int[] nums) {
        int longest = 0;
        HashSet<Integer> existed = new HashSet<>();
        for (int num : nums) {
            existed.add(num);
        }

        for (int num : nums) {
            int left = num - 1;
            while (existed.contains(left)) {
                existed.remove(left);
                left--;
            }

            int right = num + 1;
            while (existed.contains(right)) {
                existed.remove(right);
                right++;
            }
            longest = Math.max(longest, right - left - 1);
        }
        return longest;
    }


    public int backPackII(int m, int[] volumes, int[] values) {
        if (m <= 0 || volumes == null || volumes.length == 0 || values == null || values.length == 0) return 0;

        int len = volumes.length;
        int[] last = new int[m + 1];
        int[] cur = new int[m + 1];
        int[] temp;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < m; j++) {
                cur[j + 1] = last[j + 1];
                if (volumes[i] <= j + 1) {
                    cur[j + 1] = Math.max(cur[j + 1], last[j + 1 - volumes[i]] + values[i]);
                }
            }
            temp = last;
            last = cur;
            cur = temp;
        }
        return last[m];
    }



    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        if (graph != null && graph.size() > 0) {
//            return topSortByBFS(graph);
            return topSortByDFS(graph);
        }
        return null;
    }

    private ArrayList<DirectedGraphNode> topSortByBFS(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> result = new ArrayList<>();
        HashMap<DirectedGraphNode, Integer> reachCounts = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                Integer count = reachCounts.get(neighbor);
                reachCounts.put(neighbor, count == null ? 1 : count + 1);
            }
        }

        Queue<DirectedGraphNode> queue = new ArrayDeque<>();
        for (DirectedGraphNode node : graph) {
            if (!reachCounts.containsKey(node)) {
                queue.offer(node);
                result.add(node);
            }
        }

        while (!queue.isEmpty()) {
            DirectedGraphNode cur = queue.poll();
            for (DirectedGraphNode neighbor : cur.neighbors) {
                Integer count = reachCounts.get(neighbor);
                reachCounts.put(neighbor, count - 1);
                if (count == 1) {
                    result.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return result;
    }


    private ArrayList<DirectedGraphNode> topSortByDFS(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> result = new ArrayList<>();
        HashMap<DirectedGraphNode, Integer> reachCounts = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                Integer count = reachCounts.get(neighbor);
                reachCounts.put(neighbor, count == null ? 1 : count + 1);
            }
        }

        for (DirectedGraphNode node : graph) {
            if (!reachCounts.containsKey(node)) {
                result.add(node);
                for (DirectedGraphNode neighbor : node.neighbors) {
                    topSortByDFS(neighbor, result, reachCounts);
                }
            }
        }

        return result;
    }

    private void topSortByDFS(DirectedGraphNode node, ArrayList<DirectedGraphNode> result, HashMap<DirectedGraphNode, Integer> reachCounts) {
        Integer count = reachCounts.get(node);
        if (count == 1) {
            result.add(node);
            for (DirectedGraphNode neighbor : node.neighbors) {
                topSortByDFS(neighbor, result, reachCounts);
            }
        }
        if (count > 0) {
            reachCounts.put(node, count - 1);
        }
    }



    public int hashCode(char[] chars,int HASH_SIZE) {
        long hash = 0;
        for (char c : chars) {
            hash = (hash * 33 + c) % HASH_SIZE;
        }
        return (int) hash;
    }

    public ListNode[] rehashing(ListNode[] hashTable) {
        if (hashTable == null || hashTable.length == 0) return hashTable;

        int newSize = hashTable.length * 2;
        ListNode[] newHashTable = new ListNode[newSize];
        ListNode temp;
        for (ListNode node : hashTable) {
            while (node != null) {
                int hash = (node.val % newSize + newSize) % newSize;
                if (newHashTable[hash] == null) {
                    newHashTable[hash] = node;
                }
                else {
                    temp = newHashTable[hash];
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    temp.next = node;
                }
                temp = node;
                node = node.next;
                temp.next = null;
            }
        }
        return newHashTable;
    }




    public void heapify(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        int len = nums.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            checkChild(nums, i);
        }
    }

    private void checkChild(int[] heap, int index) {
        int len = heap.length;
        int childIndex;
        int temp;
        while ((childIndex = index * 2 + 1) < len) {
            if (childIndex + 1 < len && heap[childIndex] > heap[childIndex + 1]) {
                childIndex++;
            }
            if (heap[index] > heap[childIndex]) {
                temp = heap[index];
                heap[index] = heap[childIndex];
                heap[childIndex] = temp;
            }
            else {
                break;
            }
            index = childIndex;
        }
    }



    public ArrayList<ArrayList<Integer>> buildingOutline(int[][] buildings) {
        if (buildings == null || buildings.length == 0) return new ArrayList<>();

        int len = buildings.length;
        ArrayList<ArrayList<Integer>> outlines = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Edge[] starts = new Edge[len];
        for (int i = 0; i < len; i++) {
            int[] building = buildings[i];
            Edge start = new Edge(building[0], building[2], true, i);
            starts[i] = start;
            edges.add(start);
            edges.add(new Edge(building[1], building[2], false, i));
        }
        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                if (o1.x != o2.x) {
                    return o1.x - o2.x;
                }
                else if (o1.isStart == o2.isStart) {
                    return o1.y - o2.y;
                }
                else {
                    return o1.isStart ? -1 : 1;
                }
            }
        });
        IndexHeap<Edge> heap = new IndexHeap<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                if (o1.y != o2.y) {
                    return -(o1.y - o2.y);
                }
                else if (o1.x != o2.x) {
                    return -(o1.x - o2.x);
                }
                else {
                    return o1.isStart ? 1 : -1;
                }
            }
        });

        int lastX = 0;
        for (Edge edge : edges) {
            if (heap.isEmpty()) {
                heap.push(edge);
                lastX = edge.x;
            }
            else if (edge.isStart) {
                Edge top = heap.top();
                if (top.y < edge.y) {
                    addBuilding(outlines, lastX, edge.x, top.y);
                    lastX = edge.x;
                }
                heap.push(edge);
            }
            else {
                heap.remove(starts[edge.i]);
                if (heap.isEmpty() || heap.top().y < edge.y) {
                    addBuilding(outlines, lastX, edge.x, edge.y);
                    lastX = edge.x;
                }
            }
        }
        return outlines;
    }

    private class Edge extends IndexHeap.IndexT {
        public final int x;
        public final int y;
        public final boolean isStart;
        public final int i;
        public Edge(int x, int y, boolean isStart, int i) {
            this.x = x;
            this.y = y;
            this.isStart = isStart;
            this.i = i;
        }
    }

    private void addBuilding(ArrayList<ArrayList<Integer>> outlines, int left, int right, int height) {
        if (left != right) {
            ArrayList<Integer> outline = new ArrayList<>();
            outline.add(left);
            outline.add(right);
            outline.add(height);
            outlines.add(outline);
        }
    }





    public ArrayList<String> wordSearchII(char[][] board, ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();
        if (board != null && board.length > 0 && words != null && words.size() > 0) {
            int row = board.length;
            int col = board[0].length;
            if (col > 0) {
                HashSet<String> set = new HashSet<>();
                Dictionary dic = new Dictionary(words);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        Dictionary.Finder finder = dic.finder("" + board[i][j]);
                        wordSearch(board, finder, i, j, builder, set);
                    }
                }
                result.addAll(set);
            }
        }
        return result;
    }

    private void wordSearch(char[][] board, Dictionary.Finder finder, int i, int j, StringBuilder builder, HashSet<String> result) {
        if (finder.notNull) {
            char c = board[i][j];
            board[i][j] = '\0';
            builder.append(c);
            if (finder.found) {
                result.add(builder.toString());
            }

            char nextC;
            if (i > 0 && (nextC = board[i - 1][j]) != '\0') {
                wordSearch(board, finder.next(nextC), i - 1, j, builder, result);
            }

            if (i < board.length - 1 && (nextC = board[i + 1][j]) != '\0') {
                wordSearch(board, finder.next(nextC), i + 1, j, builder, result);
            }

            if (j > 0 && (nextC = board[i][j - 1]) != '\0') {
                wordSearch(board, finder.next(nextC), i, j - 1, builder, result);
            }

            if (j < board[0].length - 1 && (nextC = board[i][j + 1]) != '\0') {
                wordSearch(board, finder.next(nextC), i, j + 1, builder, result);
            }

            board[i][j] = c;
            builder.deleteCharAt(builder.length() - 1);
        }
    }




    public ArrayList<String> longestWords(String[] dictionary) {
        ArrayList<String> result = new ArrayList<>();
        if (dictionary != null && dictionary.length > 0) {
            int curMax = -1;
            for (String s : dictionary) {
                int lenS = s.length();
                if (lenS > curMax) {
                    result.clear();
                    result.add(s);
                    curMax = lenS;
                }
                else if (lenS == curMax) {
                    result.add(s);
                }
            }
        }
        return result;
    }




    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        return combinationSum(candidates, 0, target);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int fromIndex, int target) {
        int len = candidates.length;
        if (fromIndex >= len) {
            return null;
        }

        int cur = candidates[fromIndex];
        if (cur == target) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> item = new ArrayList<>();
            item.add(cur);
            result.add(item);
            return result;
        }
        else if (cur > target) {
            return null;
        }
        else {
            List<List<Integer>> result = null;
            List<List<Integer>> subResult = combinationSum(candidates, fromIndex, target - cur);
            if (subResult != null) {
                result = new ArrayList<>();
                for (List<Integer> list : subResult) {
                    list.add(0, cur);
                    result.add(list);
                }
            }

            while (++fromIndex < len && cur == candidates[fromIndex]) {}
            subResult = combinationSum(candidates, fromIndex, target);
            if (subResult != null) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.addAll(subResult);
            }
            return result;
        }
    }





    public List<List<String>> partition(String s) {
        return s == null || s.equals("") ? new ArrayList<List<String>>() : partition(s, 0);
    }

    public List<List<String>> partition(String s, int from) {
        List<List<String>> result = new ArrayList<>();
        for (int i = from, len = s.length(); i < len; i++) {
            if (isPalindromeI(s, from, i)) {
                if (i == len - 1) {
                    List<String> list = new ArrayList<>();
                    list.add(s.substring(from, i + 1));
                    result.add(list);
                }
                else {
                    List<List<String>> subResult = partition(s, i + 1);
                    for (List<String> list : subResult) {
                        list.add(0, s.substring(from, i + 1));
                        result.add(list);
                    }
                }
            }
        }
        return result;
    }

    private boolean isPalindromeI(String s, int from, int to) {
        while (from < to) {
            if (s.charAt(from++) != s.charAt(to--)) {
                return false;
            }
        }
        return true;
    }




    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return null;

        HashMap<UndirectedGraphNode, UndirectedGraphNode> cache = new HashMap<>();
        Queue<UndirectedGraphNode> queue = new ArrayDeque<>();
        queue.add(node);
        UndirectedGraphNode nodeClone = new UndirectedGraphNode(node.label);
        cache.put(node, nodeClone);

        while (queue.size() > 0) {
            UndirectedGraphNode cur = queue.poll();
            UndirectedGraphNode clone = cache.get(cur);
            UndirectedGraphNode neiClone;
            for (UndirectedGraphNode neighbor : cur.neighbors) {
                neiClone = cache.get(neighbor);
                if (neiClone == null) {
                    neiClone = new UndirectedGraphNode(neighbor.label);
                    cache.put(neighbor, neiClone);
                    queue.offer(neighbor);
                }
                clone.neighbors.add(neiClone);
            }
        }

        return nodeClone;
    }

    /**
     * 递归的方式，深度优先
     * @param node
     * @param cache
     * @return
     */
    private UndirectedGraphNode cloneGraph(UndirectedGraphNode node, HashMap<UndirectedGraphNode, UndirectedGraphNode> cache) {
        UndirectedGraphNode clone = cache.get(node);
        if (clone == null) {
            clone = new UndirectedGraphNode(node.label);
            cache.put(node, clone);

            for (UndirectedGraphNode neighbor : node.neighbors) {
                clone.neighbors.add(cloneGraph(neighbor, cache));
            }
        }
        return clone;
    }




    public ArrayList<Integer> subarraySum(int[] nums) {
        ArrayList<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> indexs = new HashMap<>();
        indexs.put(0, -1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                res.add(i);
                res.add(i);
                break;
            }
            sum += nums[i];

            Integer existed = indexs.get(sum);
            if (existed != null) {
                res.add(existed + 1);
                res.add(i);
                break;
            }
            else {
                indexs.put(sum, i);
            }
        }
        return res;
    }



    public int[] subarraySumClosest(int[] nums) {
        int[] indexs = new int[2];
        if (nums == null) return indexs;

        int len = nums.length;
        if (len == 0) return indexs;
        else if (len == 1) {
            indexs[0] = indexs[1] = 0;
            return indexs;
        }

        int[][] lSums = new int[len + 1][2];
        lSums[0][1] = -1;
        for (int i = 0; i < len; i++) {
            lSums[i + 1][0] = lSums[i][0] + nums[i];
            lSums[i + 1][1] = i;
        }

        Arrays.sort(lSums, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        int closest = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            int temp = lSums[i + 1][0] - lSums[i][0];
            if (closest > temp) {
                closest = temp;
                indexs[0] = lSums[i + 1][1];
                indexs[1] = lSums[i][1];
            }
        }
        Arrays.sort(indexs);
        indexs[0]++;
        return indexs;
    }



    public int fastPower(int a, int b, int n) {
        if (n == 0) return 1 % b;
        else if (n == 1) return a % b;
        long temp = fastPower(a, b, n / 2);
        temp = temp * temp % b;
        if (n % 2 == 0) {
            return (int) temp;
        }
        else return (int) ((a % b) * temp % b);
    }



    public int sqrt(int x) {
        long start = 1;
        long end = x;
        while (start + 1 < end) {
            long mid = (start + end) / 2;
            if (mid * mid > x) {
                end = mid;
            }
            else {
                start = mid;
            }
        }
        if (end * end > x) return (int) start;
        else return (int) end;
    }


    private static final int[] powersOf2 = {
            1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824
    } ;

    public boolean checkPowerOf2(int n) {
        for (int num : powersOf2) {
            if (num == n) return true;
        }
        return false;
    }



    public void sortColors2(int[] colors, int k) {
        if (colors != null && colors.length > 1) {
            Arrays.sort(colors);
//            sortColors2(colors, 0, colors.length - 1, 1, k);
        }
    }

    private void sortColors2(int[] colors, int left, int right, int fromC, int toC) {
        if (left < right && fromC < toC) {
            int midC = (fromC + toC) / 2;
            int l = left;
            int r = right;
            while (l < r) {
                while (l < r && colors[l] <= midC) {
                    l++;
                }
                while (l < r && colors[r] > midC) {
                    r--;
                }
                if (l < r) {
                    int temp = colors[l];
                    colors[l] = colors[r];
                    colors[r] = temp;
                    l++;
                    r--;
                }
            }
            sortColors2(colors, left, r, fromC, midC);
            sortColors2(colors, l, right, midC + 1, toC);
        }
    }




    public void insertionSort(int[] arr) {
        if (arr != null && arr.length > 1) {
            for (int i = 0, j = i, len = arr.length; i < len - 1; j = ++i) {
                int next = arr[j + 1];
                while (next < arr[j]) {
                    arr[j + 1] = arr[j];
                    if (j-- == 0) {
                        break;
                    }
                }
                arr[j + 1] = next;
            }
        }
    }



    public void rerange(int[] arr) {
        if (arr != null && arr.length > 2) {
            int len = arr.length;
            int posNum = 0;
            for (int i : arr) {
                if (i >= 0) {
                    posNum++;
                }
            }

            int flag = 1;
            if (posNum < len - posNum) {
                flag = -1;
            }

            int i = 0;
            int j = 1;
            while (i < len && j < len) {
                //找到一个偶数位错误的位置
                while (i < len && flag * arr[i] > 0) {
                    i += 2;
                }

                //找到一个奇数位错误的位置
                while (j < len && -flag * arr[j] > 0) {
                    j += 2;
                }

                if (i < len && j < len) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }



    public void sortColors(int[] nums) {
        if (nums != null && nums.length > 1) {
            int len = nums.length;
            int left = 0;
            int right = len - 1;
            for (int i = 0; i <= right; ) {
                if (nums[i] == 0) {
                    int temp = nums[left];
                    nums[left] = 0;
                    nums[i] = temp;
                    left++;
                    i++;
                }
                else if (nums[i] == 2) {
                    int temp = nums[right];
                    nums[right] = 2;
                    nums[i] = temp;
                    right--;
                }
                else {
                    i++;
                }
            }
        }
    }



    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int len = prices.length;
        int max = 0;
        int min = prices[0];
        for (int i = 1; i < len; i++) {
            if (prices[i] > min) {
                max = Math.max(prices[i] - min, max);
            }
            else {
                min = prices[i];
            }
        }
        return max;
    }


    public int maxProfitII(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int len = prices.length;
        int totalMax = 0;
        int min = prices[0];
        int max = min;
        for (int i = 1; i < len; i++) {
            if (prices[i] > max) {
                max = prices[i];
            }
            else {
                totalMax += max - min;
                min = prices[i];
                max = min;
            }

            if (i == len - 1 && max > min) {
                totalMax += max - min;
            }
        }
        return totalMax;
    }


    public int maxProfitIII(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int len = prices.length;
        int lMin = prices[0];
        int[] lMaxPs = new int[len];
        for (int i = 1; i < len; i++) {
            if (prices[i] > lMin) {
                lMaxPs[i] = Math.max(lMaxPs[i - 1], prices[i] - lMin);
            }
            else {
                lMin = prices[i];
            }
        }

        int rMax = prices[len - 1];
        int[] rMaxPs = new int[len];
        for (int i = len - 2; i > 0; i--) {
            if (prices[i] < rMax) {
                rMaxPs[i] = Math.max(rMaxPs[i + 1], rMax - prices[i]);
            }
            else {
                rMax = prices[i];
            }
        }

        int gMax = lMaxPs[len - 1];
        for (int i = 1; i < len - 2; i++) {
            gMax = Math.max(gMax, lMaxPs[i] + rMaxPs[i + 1]);
        }
        return gMax;
    }




    public List<List<Integer>> combine(int n, int k) {
        return combine(n, k, 1);
    }

    private List<List<Integer>> combine(int n, int k, int from) {
        List<List<Integer>> result = new ArrayList<>();
        if (from + k >= n + 1) {
            List<Integer> item = new ArrayList<>();
            for (int i = from; i <= n; i++) {
                item.add(i);
            }
            result.add(item);
        }
        else {
            result.addAll(combine(n, k, from + 1));
            if (k > 0) {
                List<List<Integer>> subResult = combine(n, k - 1, from + 1);
                for (List<Integer> list : subResult) {
                    list.add(0, from);
                    result.add(list);
                }
            }
        }
        return result;
    }



    public List<List<Integer>> combinationSum2(int[] num, int target) {
        if (num == null || num.length == 0) return new ArrayList<>();
        Arrays.sort(num);
        return combinationSum2(num, target, 0);
    }

    private List<List<Integer>> combinationSum2(int[] num, int target, int from) {
        List<List<Integer>> result = new ArrayList<>();
        if (num[from] == target) {
            List<Integer> item = new ArrayList<>();
            item.add(target);
            result.add(item);
            return result;
        }
        else if (num[from] > target) {
            return result;
        }

        if (from + 1 < num.length) {
            int cur = num[from];
            int next = from + 1;
            while (next < num.length && cur == num[next]) {
                next++;
            }

            ArrayList<Integer> sames = new ArrayList<>();
            for (int i = 0; i <= next - from; i++) {
                if (target == cur * i) {
                    ArrayList<Integer> clone = (ArrayList<Integer>) sames.clone();
                    result.add(clone);
                }
                else {
                    List<List<Integer>> subResult = combinationSum2(num, target - cur * i, next);
                    if (!subResult.isEmpty()) {
                        for (List<Integer> list : subResult) {
                            ArrayList<Integer> clone = (ArrayList<Integer>) sames.clone();
                            clone.addAll(list);
                            result.add(clone);
                        }
                    }
                }
                sames.add(cur);
            }
        }
        return result;
    }

    //http://www.lintcode.com/zh-cn/problem/regular-expression-matching/

    private void test() throws Exception {




//        d(combinationSum2(new int[]{
//                3,1,3,5,1,1
//        }, 8));


//        d(combine(4, 2));


//        int[] prices = {
//                2,1,2,0,1
//        };
//        System.out.println(maxProfitIII(prices));


//        int[] prices = {
//                2,1,2,0,1
//        };
//        System.out.println(maxProfitII(prices));


//        int[] prices = {
//                1,2,3,1,2
//        };
//        System.out.println(maxProfit(prices));


//        int[] arr = {2,0,0,1,2,0,2};
//        sortColors(arr);
//        d(arr);


//        int[] arr = {-1, 5, -3, 4, -2};
//        rerange(arr);
//        d(arr);


//        int[] arr = {1,8,5,6,2,5,6,2,3,4,3,1,9,8};
//        insertionSort(arr);
//        d(arr);


//        int[] arr = {1,3,2,4,1,2,1,2,1,4,1,3,1,3,3,4,1,1,1,1};
//        int k = 4;
////        StringBuilder builder = new StringBuilder();
////        for (String s : Files.readAllLines(Paths.get("./data/sort-colors-ii-13.in"), StandardCharsets.UTF_8)) {
////            for (char c : s.toCharArray()) {
////                if (c != '[' && c != ']' && c != ' ') {
////                    builder.append(c);
////                }
////            }
////        }
////        String[] strs = builder.toString().split(",");
////        int len = strs.length;
////        int[] arr = new int[len - 1];
////        int k = 0;
////        for (int i = 0; i < len; i++) {
////            if (i < len - 1) {
////                arr[i] = Integer.parseInt(strs[i]);
////            }
////            else {
////                k = Integer.parseInt(strs[i]);
////            }
////        }
//        sortColors2(arr, k);
//        d(arr);


//        d(checkPowerOf2(1024));
//        d(checkPowerOf2(1023));


//        for (int i = 0; i < 10; i++) {
//            d(sqrt(i));
//        }


//        d(fastPower(4, 6, 2));

//        d(subarraySumClosest(new int[]{
//                -3,1,1,-3,5
//        }));



//        d(subarraySum(new int[]{-3, 1, 2, -3, 4}));



//        UndirectedGraphNode n0 = new UndirectedGraphNode(0);
//        UndirectedGraphNode n1 = new UndirectedGraphNode(1);
//        UndirectedGraphNode n2 = new UndirectedGraphNode(2);
//        UndirectedGraphNode n3 = new UndirectedGraphNode(3);
//        n0.addNeighbours(n0, n1, n2);
//        n1.addNeighbours(n0, n2);
//        n2.addNeighbours(n0, n1, n3);
//        n3.addNeighbours(n2);
//        d(n0);
//        d(cloneGraph(n0));



//        d(partition("aabaa"));


//        d(combinationSum(new int[] {
//                2, 3, 4, 5, 6, 7, 8, 9, 10
//        }, 10));


//        LRUCache cache = new LRUCache(3);
//        cache.set(0, 0);
//        cache.set(1, 1);
//        cache.set(2, 2);
//        cache.set(3, 3);
//        System.out.println(cache.get(1));
//        cache.set(4, 4);
//        System.out.println(cache.get(2));



//        String[] strs = {
//                "dog",
//                "google",
//                "facebook",
//                "internationalization",
//                "blabla"
//        };
//        d(longestWords(strs));


//        String[] strs = {
//                "doaf",
//                "agai",
//                "dcan"
//        };
//        char[][] board = new char[strs.length][];
//        for (int i = 0; i < strs.length; i++) {
//            board[i] = strs[i].toCharArray();
//        }
//        ArrayList<String> words = new ArrayList<>(Arrays.asList(
//                "dog", "dad", "dgdg", "can", "again"
//        ));
//        d(wordSearchII(board, words));


//        int[][] buildings = {
//                {1,100,20},
//                {2,99,19},
//                {3,98,18}
//        };
//        d(buildingOutline(buildings));


//        int[] heap = {
//                6,3,5,4,1,2,7
//        };
//        heapify(heap);
//        d(heap);


//        ListNode[] hashTable = new ListNode[] {
//                null,
//                null,
//                ListNodeFactory.build("29->5")
//        };
//        d(rehashing(hashTable), "");


//        d(hashCode("Wrong answer or accepted?".toCharArray(), 1000000007));


//        ArrayList<DirectedGraphNode> nodes = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            nodes.add(new DirectedGraphNode(i));
//        }
//        nodes.get(0).addNeighbors(nodes.get(1), nodes.get(2));
//        nodes.get(1).addNeighbors(nodes.get(4), nodes.get(5));
//        nodes.get(2).addNeighbors(nodes.get(3));
//        nodes.get(3).addNeighbors(nodes.get(1));
//        for (DirectedGraphNode node : topSort(nodes)) {
//            System.out.print(node.label + ", ");
//        }
//        System.out.println();



//        d(backPackII(10, new int[]{
//                2, 3, 5, 7
//        }, new int[]{
//                1, 5, 2, 4
//        }));


//        d(longestConsecutive(new int[]{
//                100, 4, 200, 1, 3, 2
//        }));


//        String[] strs = {
//                "ABCE",
//                "SFCS",
//                "ADEE"
//        };
//        char[][] board = new char[strs.length][];
//        for (int i = 0; i < strs.length; i++) {
//            board[i] = strs[i].toCharArray();
//        }
//        d(exist(board, "ABCCED"));


//        d(largestRectangleArea(new int[]{
//                2,1,5,6,2,3
//        }));
//        d(largestRectangleArea(new int[]{
//                5,4,1,2
//        }));


//        String str1 = "hit";
//        String str2 = "cog";
//        Set<String> dic = new HashSet<>(Arrays.asList(
//                "hot", "dot","dog","lot","log"
//        ));
//        d(findLadders(str1, str2, dic));


//        String str1 = "hit";
//        String str2 = "cog";
//        Set<String> dic = new HashSet<>(Arrays.asList(
//                "hot", "dot","dog","lot","log"
//        ));
//        d(ladderLength(str1, str2, dic));


//        String s1 = "sea";
//        String s2 = "atae";
//        d(minDistance(s1, s2));


//        String source = "rabbbit";
//        String target = "rabbit";
//        d(numDistinct(source, target));


//        d(jump(new int[]{
//                2,3,1,27,0,1,1,1,1,1,1,1,0,1
//        }));


//        int[] jumps1 = {
//                2,3,2,1,1,1
//        };
//        d(canJump(jumps1));
//        int[] jumps2 = {
//                3,2,1,0,4
//        };
//        d(canJump(jumps2));


//        int[][] grid = {
//                {0,0,0},
//                {0,1,0},
//                {0,0,0}
//        };
//        d(uniquePathsWithObstacles(grid));


//        d(uniquePaths(4,5));


//        ListNode head = ListNodeFactory.build("1->2->3->3->3->4->4->5");
//        d(deleteDuplicatesII(head));


//        ListNode head = ListNodeFactory.build("1->2->3->3->3->4->4->5");
//        d(deleteDuplicates(head));


//        d(climbStairs(4));


//        int[][] grid = {
//                {1,1,2,3},
//                {0,1,4,1},
//        };
//        d(minPathSum(grid));


//        int[][] triangle = {
//                {2},
//                {3, 4},
//                {6, 5, 7},
//                {4, 1, 8, 3}
//        };
//        d(minimumTotal(triangle));



//        d(minCut("aabbac"));


//        List<String> lines = Files.readAllLines(Paths.get("./data/word-break-88.in"), StandardCharsets.UTF_8);
//        String s = lines.get(0);
//        Set<String> dic = new HashSet<>(lines.subList(1, lines.size()));
//        d(wordBreak(s, dic));
////        String s = "aaaaaaa";
////        Set<String> dic = new HashSet<>(Arrays.asList(
////           "aaaa", "aa"
////        ));
////        d(wordBreak(s, dic));


//        ListNode list = ListNodeFactory.build("1->2->3");
//        d(sortedListToBST(list));


//        RandomListNode listNode = new RandomListNode(-1);
//        listNode.next = new RandomListNode(1);
//        d(copyRandomList(listNode));


//        ArrayList<ListNode> lists = new ArrayList<>();
//        lists.add(ListNodeFactory.build("2->4"));
//        lists.add(ListNodeFactory.build(""));
//        lists.add(ListNodeFactory.build("-1"));
//        d(mergeKLists(lists));


//        ListNode listNode = ListNodeFactory.build("0->1->2->3->4");
//        d(detectCycle(listNode));
//        ListNodeFactory.tailConnectNodeAt(listNode, 2);
//        d(listNode);
//        d(detectCycle(listNode).val);


//        ListNode listNode = ListNodeFactory.build("0->1->2");
//        d(hasCycle(listNode));
//        listNode.next.next.next = listNode.next;
//        d(hasCycle(listNode));


//        int[] nums = new int[]{
//                1,2,3,3,4,4,4
//        };
//        d(removeDuplicatesII(nums));
//        d(nums);


//        int[] nums = new int[]{
//                1,2,2,2,5,5,6
//        };
//        d(removeDuplicates(nums));
//        d(nums);


//        ListNode listNode = ListNodeFactory.build("1->2->3->4");
//        d(listNode);
//        reorderList(listNode);
//        d(listNode);


//        ListNode listNode = ListNodeFactory.build("0->5->4->3->2->1->6");
//        d(listNode);
//        d(sortList(listNode));


//        TreeNode treeNode = TreeNodeFactory.build("1,2,3, #,#,4,5");
//        d(treeNode);
//        d(maxDepth(treeNode));


//        ListNode listNode = ListNodeFactory.build("1->4->3->2->5->2");
//        d(listNode);
//        d(partition(listNode, 3));


//        TreeNode treeNode = TreeNodeFactory.build("10,5,15,#,#,6,20");
//        d(treeNode);
//        d(isValidBST(treeNode));


//        TreeNode treeNode = TreeNodeFactory.build("-10,-20,#,#,-31,-24,-5,#,#,-6,-7,-8,-9");
//        d(treeNode);
//        d(maxPathSum(treeNode));


//        TreeNode treeNode = TreeNodeFactory.build("3,9,20,#,#,15,7,6,#,#,#,8");
//        d(treeNode);
//        d(isBalanced(treeNode));


//        d(backPack(12, new int[]{
//                2, 3, 5, 7
//        }));


//        d(MinAdjustmentCost(new ArrayList<Integer>(Arrays.asList(
//                1, 4, 2, 3
//        )), 1));


//        d(kSumII(new int[]{
//                1,2,3,4
//        }, 2, 5));


//        d(kSum(new int[]{
//                1,3,4,5,8,10,11,12,14,17,20,22,24,25,28,30,31,34,35,37,38,40,42,44,45,48,51,54,56,59,60,61,63,66
//        }, 24, 842));


//        TreeNode treeNode = TreeNodeFactory.build("20,1,40,#,#,35");
//        d(treeNode);
//        d(lowestCommonAncestor(treeNode, treeNode.right, treeNode.right.left).val);


//        TreeNode treeNode = TreeNodeFactory.build("20,1,40,#,#,35");
//        d(treeNode);
//        d(removeNode(treeNode, 20));


//        TreeNode treeNode = TreeNodeFactory.build("2,1,4,#,#,3");
//        TreeNode newNode = new TreeNode(6);
//        d(insertNode(treeNode, newNode));



//        d(singleNumberIII(new int[] {
//                -4,2,2,3,4,4,5,3
//        }));


//        d(singleNumberII(new int[] {
//                1,1,2,3,3,3,2,2,-4,1
//        }));


//        d(singleNumber(new int[] {
//                1,2,2,1,3,4,3
//        }));


//        d(medianII(new int[] {
//                2, 20, 100
//        }));


//        d(median(new int[] {
//                4,5,1,2,3
//        }));


//        d(longestCommonSubstring("DBCEFG", "ABCEFA"));


//        d(longestCommonPrefix(new String[]{
//            "ABCEFG", "ABCEFA"
//        }));


//        d(longestCommonSubsequence("ABCD", "EDCA"));


//        d(longestIncreasingSubsequence(new int[]{
//                4,2,4,5,3,7
//        }));


//        d(findPeak(new int[]{
//                1, 2, 1, 3, 4, 8, 7, 6
//        }));


//        d(buildTreePreIn(new int[]{
//                1,2,4,5,3,6,7
//        }, new int[]{
//                4,2,5,1,6,3,7
//        }));


//        d(buildTree(new int[]{
//                1,2,3
//        }, new int[]{
//                1,3,2
//        }));


//        TreeNode treeNode = TreeNodeFactory.build("3,9,20,#,#,15,7");
//        d(zigzagLevelOrder(treeNode));


//        TreeNode treeNode = TreeNodeFactory.build("3,9,20,#,#,15,7");
//        d(levelOrderBottom(treeNode));


//        TreeNode treeNode = TreeNodeFactory.build("3,9,20,#,#,15,7");
//        d(levelOrder(treeNode));


//        TreeNode treeNode = TreeNodeFactory.build("1,2,3,4,5,6");
//        d(treeNode);
//        d("前序：", preorderTraversal(treeNode));
//        d("中序：", inorderTraversal(treeNode));
//        d("后序：", postorderTraversal(treeNode));



//        d(findMedianSortedArrays(new int[]{
//                5,6,9,10
//        }, new int[]{
//                0,2,3,4
//        }));
//        d(findMedianSortedArrays(new int[]{
//                1,2,3,4,5,6
//        }, new int[]{
//                2,3,4,5
//        }));
//        d(findMedianSortedArrays(new int[]{
//
//        }, new int[]{
//                2,3
//        }));
//        d(findMedianSortedArrays(new int[]{
//                3
//        }, new int[]{
//                4
//        }));
//        d(findMedianSortedArrays(new int[]{
//                1,2,3,4
//        }, new int[]{
//                5,6,7,8,9
//        }));
//        d(findMedianSortedArrays(new int[]{
//                1,1,14,31,33,40,42,66,71,74,113,117,124,125,127,137,143,184,187,188,221,222,224,248,251,269,293,294,315,324,330,353,358,366,368,389,389,408,424,432,433,451,452,456,459,475,480,483,484,496,509,515,519,523,559,567,568,593,598,600,612,623,626,628,632,633,634,646,654,663,681,696,706,709,717,723,746,753,790,790,798,824,826,847,849,857,866,879,882,894,894,913,925,938,942,961,974,988,988,989,998
//        }, new int[]{
//                3,4,5,6,9,15,17,20,21,21,23,25,27,27,28,29,31,32,37,41,43,47,49,50,52,52,52,54,59,60,67,68,71,72,73,77,78,84,86,88,88,91,94,98,98,98,100,102,105,106,107,107,110,117,118,120,122,124,126,129,131,134,135,144,147,154,158,158,163,164,164,170,171,171,172,172,176,178,180,183,184,185,189,196,197,199,200,200,204,208,214,217,223,226,227,231,231,232,232,237,243,244,245,251,258,259,266,271,274,277,279,280,280,281,283,284,284,284,286,288,290,296,299,301,302,302,302,303,305,308,308,309,311,313,313,316,322,323,326,327,328,329,331,331,337,340,340,342,343,345,346,349,349,349,350,354,366,366,375,376,377,377,379,382,389,390,391,392,393,394,397,397,397,399,400,400,402,402,403,404,405,405,408,414,415,416,416,416,419,421,422,426,426,427,430,432,433,436,440,443,445,448,448,454,455,456,456,457,458,459,459,462,465,466,467,471,475,493,500,501,505,507,509,511,512,512,513,513,514,514,515,516,517,518,520,521,523,524,525,528,533,535,535,536,537,539,542,542,544,545,547,551,552,553,554,554,556,557,557,558,559,559,561,563,565,568,570,578,578,579,580,580,581,581,588,590,591,592,592,593,594,595,597,601,603,603,605,607,610,611,612,612,612,614,617,620,621,622,622,624,624,625,625,627,627,627,632,635,635,637,638,642,644,644,647,647,650,651,652,653,654,655,657,660,664,667,670,671,672,673,673,676,677,682,685,685,686,686,688,694,695,695,697,699,700,700,704,704,707,709,713,713,715,716,716,717,719,721,725,732,736,740,742,745,746,749,752,754,755,756,756,757,760,762,763,765,766,768,768,768,772,774,775,775,780,784,784,785,785,788,790,791,792,794,796,796,798,800,802,802,804,806,806,808,813,814,816,817,817,818,824,824,825,825,827,830,832,834,834,837,841,842,843,845,846,848,852,855,855,855,860,861,866,866,872,874,875,875,877,883,886,892,892,895,895,897,898,898,900,900,900,904,904,905,906,907,909,909,914,914,914,915,922,924,927,928,930,931,936,938,939,941,944,945,946,947,948,950,955,956,960,961,967,967,969,971,972,978,979,981,982,984,984,989,990,993,997,999,999,1001
//        }));


//        int[] arrA = {3, 4, 5, 0, 0};
//        int[] arrB = {1, 2};
//        mergeSortedArray(arrA, 3, arrB, 2);
//        d(arrA);


//        d(search(new int[]{
//                4, 0, 1, 2, 3
//        }, 0));


//        d(searchRange(new int[]{
//                5, 7, 7, 8, 9, 10, 11
//        }, 8));


//        d(searchInsert(new int[]{
//                1,3,5,6
//        }, 7));

//        d(threeSumClosest(new int[]{
//                1,2,33,23,2423,33,23,1,7,6,8787,5,33,2,3,-23,-54,-67,100,400,-407,-500,-35,-8,0,0,7,6,0,1,2,-56,-89,24,2
//        }, 148));


//        d(fourSum(new int[]{
//                -8,-0,-7,-101,-123,-1,-2,1,1,4,-2,0,-1,0,-1111,0,-1,-2,-3,-4,-5,-6,-100,-98,-111,-11
//        }, -111));


//        d(threeSum(new int[]{
//                -1, 0, 1, 2, -1, -4
//        }));


//        d(twoSum(new int[]{
//                2, 7, 11, 15
//        }, 9));


//        d(compareStrings("ABCD", "ACD"));


//        d(atoi("1234567890123456789012345678901234567890"));


//        d(reverseWords("just  test"));


//        d(nextPermutation(new int[]{1,3,2,3}));


//        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
//                1, 2, 3, 4, 5
//        ));
//        d(previousPermuation(list));
//        d(previousPermuation(list));



//        d(productExcludeItself(new ArrayList<Integer>(Arrays.asList(
//                1, 2, 3
//        ))));


//        char[] str = "abAcD".toCharArray();
//        sortLetters(str);
//        d(str);


//        System.out.println(majorityNumber(new ArrayList<>(Arrays.asList(
//                3,1,2,3,2,3,3,4,4,4
//        )), 3) + " expected: 3");

//        System.out.println(majorityNumber1_3(new ArrayList<>(Arrays.asList(
//                1,2,1,2,1,3,3
//        ))) + " expected: 1");
//        System.out.println(majorityNumber1_3(new ArrayList<>(Arrays.asList(
//                2,1,2,2,3,3,3,4,4,4,3
//        ))) + " expected: 3");
//        System.out.println(majorityNumber1_3(new ArrayList<>(Arrays.asList(
//                1,1,1,1,2,2,3,3,4,4,4
//        ))) + " expected: 1");
//        System.out.println(majorityNumber1_3(new ArrayList<>(Arrays.asList(
//                2,3,4,2,3,4,1,4,1,1,4
//        ))) + " expected: 4");


//        System.out.println(majorityNumber(new ArrayList<>(Arrays.asList(
//                44,50,24,50,5,79,50,87,50,50,64,50,69,17,6,50,19,50,55,50,26,50,50,50,16,50,81,46,50,10,19,64,50,87,16,28,50,28,16,50,42,35,50,50,65,84,81,50,50,5,50,50,67,50,76,50,91,50,44,72,50,50,50,50,72,93,85,50,85,66,53,52,50,32,4,50,7,9,50,89,58,8,44,64,50,68,50,8,65,21,38,15,95,41,55,88,76,50,50,4,32,45,50,7,79,79,35,54,50,50,50,53,71,50,50,28,50,26,50,90,34,45,50,77,50,67,73,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50
//        ))));


//        System.out.println(maxDiffSubArrays(new int[]{1, 2, -3, 1}));


//        System.out.println(minSubArray(new ArrayList<Integer>(Arrays.asList(1, -1, -2, 1))));


//        System.out.println(maxSubArray(new int[]{
//                -1,-2,-3,-100,-1,-50
//        }, 2));

//        System.out.println(maxTwoSubArrays(new ArrayList<>(Arrays.asList(1, 3, -1, 2, -1, 2))));


//        System.out.println(maxSubArray(new int[]{-2, 2, -3, 4, -1, 2, 1, -5, 3}));


//        MyQueue myQueue = new MyQueue();
//        myQueue.push(1);
//        myQueue.push(2);
//        System.out.println(myQueue.top());
//        System.out.println(myQueue.pop());



//        ArrayList<Integer> nums = new ArrayList<>();
//        nums.add(3);
//        nums.add(4);
//        nums.add(1);
//        nums.add(2);
//        recoverRotatedSortedArray(nums);
//        XYLog.d(nums);


//        int[][] matrix = {
//                {1, 3, 5, 7},
//                {2, 4, 7, 8},
//                {3, 5, 9, 10}
//        };
//        System.out.println(searchMatrixII(matrix, 3));


//        ListNode head = ListNodeFactory.build("3760->2881->7595->3904->5069->4421->8560->8879->8488->5040->5792->56->1007->2270->3403->6062");
//        System.out.println(head);
//        System.out.println(reverseBetween(head, 2, 7));


//        ListNode head = ListNodeFactory.build("1->2->3->4->5->6");
//        System.out.println(head);
//        System.out.println(reverse(head));


        // N皇问题的集中不同算法
        // http://www.tuicool.com/articles/6vYfmmj
//        long startTime = System.currentTimeMillis();
//        System.out.println(solveNQueensByBit(14).size());
//        System.out.println("solveNQueensByBit cost: " + (System.currentTimeMillis() - startTime));
//
//        startTime = System.currentTimeMillis();
//        System.out.println(solveNQueens(14).size());
//        System.out.println("solveNQueens cost: " + (System.currentTimeMillis() - startTime));
//
//        startTime = System.currentTimeMillis();
//        System.out.println(solveNQueensIt(14).size());
//        System.out.println("solveNQueensIt cost: " + (System.currentTimeMillis() - startTime));

//        for (int i = 4; i <= 15; i++) {
//            XYLog.d(i + " => " + totalNQueens(i));
//        }

//        XYLog.d(solveNQueens(4));


//        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
//        System.out.println(minWindow("abcdecf", "acc"));


//        int[] arr = new int[]{9,9,9,8,9,8,7,9,8,8,8,9,8,9,8,8,6,9};
//        System.out.println(partitionArray(arr, 9));
//        XYLog.d(arr);


//        ArrayList<Interval> intervals = new ArrayList<>();
//        intervals.add(new Interval(1, 5));
//        Interval newInterval = new Interval(0, 0);
//        XYLog.d(insert(intervals, newInterval));



//        String s1 = "aabcc";
//        String s2 = "dbbca";
//        String s3 = "aadbbcbcac";
//        System.out.println(isInterleave(s1, s2, s3));



//        int[][] matrix = {
//                {1, 3, 5, 7},
//                {10, 11, 16, 20},
//                {23, 30, 34, 50}
//        };
//        System.out.println(searchMatrix(matrix, 24));


//        String str = "3, [set(1,10),set(2,20),set(3,30),get(1),set(4,40),get(4),get(3),get(2),get(1),set(5,50),get(1),get(2),get(3),get(4),get(5)]";
//        int capacity = Integer.parseInt(str.substring(0, str.indexOf(",")));
//        String options = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
//        Pattern pattern = Pattern.compile("(set\\(([0-9]+),([0-9]+)\\))|(get\\(([0-9]+)\\))");
//        Matcher matcher = pattern.matcher(options);
//        LFUCache lfuCache = new LFUCache(capacity);
//        while (matcher.find()) {
//            if (matcher.group(1) != null) {
//                //set
//                int key = Integer.parseInt(matcher.group(2));
//                int value = Integer.parseInt(matcher.group(3));
//                lfuCache.set(key, value);
//            }
//            else {
//                System.out.println(lfuCache.get(Integer.parseInt(matcher.group(5))));
//            }
//        }


//        ArrayList<NestedInteger> list = new ArrayList<>();
//        list.add(new NestedIntegerItem(1));
//        list.add(new NestedIntegerItem(2));
//        list.add(new NestedIntegerList((List<NestedInteger>) list.clone()));
//        list.add(new NestedIntegerItem(3));
//        XYLog.d(flatten(list));


//        XYLog.d(dicesSum(15));
//        XYLog.d(dicesSumLessComputeAndMem(15));



//        int[] nums = {1,2,2};
//        XYLog.d(subsetsWithDup(nums));


//        int[] nums = {1,2,3};
//        XYLog.d(subsets(nums));



//        int[] nums = {5,4,6,2};
//        XYLog.d(permute(nums));


//        int[] nums = {1, 4, 4, 4, 7, 8, 9};
//        System.out.println(binarySearch(nums, 9));


//        String source = "abcdabcdefg";
//        String target = "abcdabcd";
////        System.out.println(strStr(source, target));
//        System.out.println(strStrKMP(source, target));


//        MinStack minStack = new MinStack();
//        minStack.push(1);
//        System.out.println(minStack.pop());
//        minStack.push(2);
//        minStack.push(3);
//        System.out.println(minStack.min());
//        minStack.push(1);
//        System.out.println(minStack.min());



//        TreeNode treeNode = TreeNodeFactory.build("20,8,22,4,12");
//        System.out.println(treeNode);
//        XYLog.d(searchRange(treeNode, 10, 22));



//        char[] str = "abcdefg".toCharArray();
//        rotateString(str, 6);
//        System.out.println(Arrays.toString(str));


//        TreeNode treeNode = TreeNodeFactory.build("3,9,20,#,#,15,7,#,12,1,#,#,4");
//        String treeNodeStr = serialize(treeNode);
//        System.out.println(treeNodeStr);
//        System.out.println(deserialize(treeNodeStr));


//        System.out.println(Arrays.toString(mergeSortedArray(new int[] {1,2,3,4}, new int[] {2,4,5,6})));


//        System.out.println(kthLargestElement(3, new int[] {9,3,2,4,8}));


//        System.out.println(nthUglyNumber(10));



//        System.out.println(digitCounts(0, 19));
//        //生成digitCountCache0 和 digitCountCache19
////        ArrayList<Integer> temp0 = new ArrayList<>();
////        ArrayList<Integer> temp1_9 = new ArrayList<>();
////        temp0.add(0);
////        temp1_9.add(0);
////        long ten = 10;
////        while (ten < Integer.MAX_VALUE) {
////            temp0.add(digitCounts(0, (int) (ten - 1)));
////            temp1_9.add(digitCounts(1, (int) (ten - 1)));
////            ten *= 10;
////        }
////        System.out.println(Arrays.toString(temp0.toArray()));
////        System.out.println(Arrays.toString(temp1_9.toArray()));
//
//        //生成digitCountCacheG
////        ArrayList<Long> tempG = new ArrayList<>();
////        tempG.add(0L);
////        long ten = 1;
////        while (ten < Integer.MAX_VALUE) {
////            tempG.add(ten + 10 * tempG.get(tempG.size() - 1));
////            ten *= 10;
////        }
////        System.out.println(Arrays.toString(tempG.toArray()));



//        System.out.println(trailingZeros(40));



//        System.out.println(aplusb(9, 2));



    }

    public static void main(String[] args) throws Exception {
        new Solution().test();
    }

}