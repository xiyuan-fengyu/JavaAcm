package com.xiyuan.acm2;

import com.xiyuan.acm.factory.ListNodeFactory;
import com.xiyuan.acm.factory.TreeNodeFactory;
import com.xiyuan.acm.model.ListNode;
import com.xiyuan.acm.model.TreeNode;
import com.xiyuan.acm2.model.*;
import com.xiyuan.util.XYLog;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiyuan.util.XYLog.d;

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


    private void test() {

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

    public static void main(String[] args) {
        new Solution().test();
    }

}