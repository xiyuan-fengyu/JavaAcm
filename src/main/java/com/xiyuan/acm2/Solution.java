package com.xiyuan.acm2;

import com.xiyuan.acm.factory.TreeNodeFactory;
import com.xiyuan.acm.model.TreeNode;
import com.xiyuan.acm2.model.MinStack;
import com.xiyuan.util.XYLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2017/4/7.
 */
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

    private void test() {

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