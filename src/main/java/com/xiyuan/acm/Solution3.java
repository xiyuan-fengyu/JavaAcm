package com.xiyuan.acm;

import com.xiyuan.acm.factory.TreeNodeFactory;
import com.xiyuan.acm.model.DoublyListNode;
import com.xiyuan.acm.model.Heap;
import com.xiyuan.acm.model.LFUCache;
import com.xiyuan.acm.model.TreeNode;
import com.xiyuan.util.XYLog;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiyuan_fengyu on 2017/4/5.
 */
public class Solution3 {

    /**
     * http://www.lintcode.com/zh-cn/problem/find-the-missing-number-ii/
     * @param n an integer
     * @param str a string with number from 1-n
     *            in random order and miss one number
     * @return an integer
     */
    public int findMissing2(int n, String str) {
        boolean[] existed = new boolean[n + 1];
        existed[0] = true;
        findMissing2(n, str, 0, existed);
        for (int i = 0; i < existed.length; i++) {
            if (!existed[i]) {
                return i;
            }
        }
        return -1;
    }

    private boolean findMissing2(int n, String str, int curIndex, boolean[] existed) {
        int num1 = str.charAt(curIndex) - '0';
        if (num1 == 0) {
            return false;
        }

        int len = str.length();
        if (curIndex == len - 1) {
            if (!existed[num1]) {
                existed[num1] = true;
                return true;
            }
            else {
                return false;
            }
        }
        else {
            int num2 = str.charAt(curIndex + 1) - '0';

            //分开
            if (num1 > 0 && num2 > 0 && !existed[num1]) {
                existed[num1] = true;
                boolean temp = findMissing2(n, str, curIndex + 1, existed);
                if (!temp) {
                    existed[num1] = false;
                }
                else return true;
            }

            //合在一起
            int num = num1 * 10 + num2;
            if (num <= n && !existed[num]) {
                existed[num] = true;
                if (curIndex == len - 2) {
                    return true;
                }
                else {
                    boolean temp = findMissing2(n, str, curIndex + 2, existed);
                    if (!temp) {
                        existed[num] = false;
                    }
                    else return true;
                }
            }

        }
        return false;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/string-permutation/
     * @param strA a string
     * @param strB a string
     * @return a boolean
     */
    public boolean stringPermutation(String strA, String strB) {
        if (strA == null || strB == null) {
            return false;
        }

        int len = strA.length();
        if (len != strB.length()) {
            return false;
        }

        //9329 ms
//        char[] charsA = strA.toCharArray();
//        char[] charsB = strB.toCharArray();
//
//        Arrays.sort(charsA);
//        Arrays.sort(charsB);
//
//        for (int i = 0; i < len; i++) {
//            if (charsA[i] != charsB[i]) {
//                return false;
//            }
//        }
//        return true;

        //总耗时: 8554 ms
        HashMap<Character, Integer> charCountA = new HashMap<>();
        HashMap<Character, Integer> charCountB = new HashMap<>();
        for (int i = 0; i < len; i++) {
            char charA = strA.charAt(i);
            Integer countA = charCountA.get(charA);
            charCountA.put(charA, countA == null ? 1 : countA + 1);

            char charB = strB.charAt(i);
            Integer countB = charCountB.get(charB);
            charCountB.put(charB, countB == null ? 1 : countB + 1);
        }

        if (charCountA.size() != charCountB.size()) {
            return false;
        }

        for (Character character : charCountA.keySet()) {
            if (!charCountA.get(character).equals(charCountB.get(character))) {
                return false;
            }
        }

        return true;
    }




    /**
     * http://www.lintcode.com/zh-cn/problem/binary-tree-path-sum/
     * @param root the root of binary tree
     * @param target an integer
     * @return all valid paths
     */
    public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
        if (root == null) {
            return new ArrayList<>();
        }

        return visiteTree(root, target, 0);
    }

    private List<List<Integer>> visiteTree(TreeNode curNode, int target, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        if (curNode.left == null && curNode.right == null) {
            if (sum + curNode.val == target) {
                result.add(new ArrayList<Integer>());
            }
        }
        else {
            if (curNode.left != null) {
                result.addAll(visiteTree(curNode.left, target, sum + curNode.val));
            }

            if (curNode.right != null) {
                result.addAll(visiteTree(curNode.right, target, sum + curNode.val));
            }
        }

        for (List<Integer> list : result) {
            list.add(0, curNode.val);
        }

        return result;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/convert-binary-search-tree-to-doubly-linked-list/
     * @param root: The root of tree
     * @return: the head of doubly list node
     */
    public DoublyListNode bstToDoublyList(TreeNode root) {
        if (root == null) {
            return null;
        }

        DoublyListNode curNode = new DoublyListNode(root.val);
        DoublyListNode head = curNode;
        if (root.left != null) {
            DoublyListNode subRes = bstToDoublyList(root.left);
            head = subRes;
            while (subRes.next != null) {
                subRes = subRes.next;
            }
            subRes.next = curNode;
            curNode.prev = subRes;
        }

        if (root.right != null) {
            DoublyListNode subRes = bstToDoublyList(root.right);
            while (subRes.prev != null) {
                subRes = subRes.prev;
            }
            subRes.prev = curNode;
            curNode.next = subRes;
        }

        return head;
    }




    /**
     * http://www.lintcode.com/zh-cn/problem/sliding-window-median/
     * @param nums: A list of integers.
     * @return: The median of the element inside the window at each moving.
     */
    public ArrayList<Integer> medianSlidingWindow(int[] nums, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        if (k == 0) {
            //
        }
        else if (k == 1) {
            for (int num : nums) {
                result.add(num);
            }
        }
        else {
            int len = nums.length;
            if (k > len) {
                k = len;
            }

            //最小堆，用来存储较大的一半， k为偶数时最大堆堆顶为中位数， k为奇数时最小堆堆顶为中位数， （最小堆的元素个数 - 最大堆的元素个数） >= 0 且 <= 1
            PriorityQueue<Integer> minHeap = new PriorityQueue<>((k + 1) / 2, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });

            //最大堆，用来存储较小的一半
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((k + 1) / 2, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });

            PriorityQueue<Integer> resultHeap = k % 2 == 0? maxHeap : minHeap;

            for (int i = 0; i < len; i++) {
                if (i >= k) {
                    int remove = nums[i - k];
                    Integer maxTop = maxHeap.peek();
                    if (maxTop != null && remove <= maxTop) {
                        maxHeap.remove(remove);
                    }
                    else {
                        minHeap.remove(remove);
                    }
                }

                Integer minTop = minHeap.peek();
                if (minTop == null || minTop <= nums[i]) {
                    minHeap.offer(nums[i]);
                }
                else {
                    maxHeap.offer(nums[i]);
                }

                int numDiff = minHeap.size() - maxHeap.size();
                while (numDiff < 0 || numDiff > 1) {
                    if (numDiff < 0) {
                        minHeap.offer(maxHeap.poll());
                    }
                    else {
                        maxHeap.offer(minHeap.poll());
                    }
                    numDiff = minHeap.size() - maxHeap.size();
                }

                if (i + 1 >= k) {
                    result.add(resultHeap.peek());
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution3 solution = new Solution3();

        /*
        http://www.lintcode.com/zh-cn/problem/sliding-window-median/
        滑动窗口的中位数(题目的中文描述有误)
         */
////        int[] arr = {1,2,7,8,5};
////        int k = 3;
//        int[] arr = {603,1882,1565,307,1458,578,253,515,1938,853,1295,238,1184,1109,1048,1680,1507,310,884,854,1109,278,648,1286,1428,200,1534,855,1021,999,258,129,1877,690,988,871,1253,1372,855,1481,1965,525,749,1909,522,1579,1198,724,1495,1496,783,1714,1214,1957,1798,1423,932,1559,1249,978,634,1648,108,812,1163,1712,1671,735,719,1272,720,732,507,115,1644,413,1111,552,144,353,1515,614,1050,39,40,354,1042,599,1548,1946,1671,1339,1250,907,1305,1164,898,36,1001,446};
//        int k = 84;
//        System.out.println(solution.medianSlidingWindow(arr, k));


        /*
        http://www.lintcode.com/zh-cn/problem/convert-binary-search-tree-to-doubly-linked-list/
        将二叉查找树转换成双链表
         */
//        TreeNode tree = TreeNodeFactory.build("4, 2, 5, 1, 3");
//        System.out.println(solution.bstToDoublyList(tree));



        /*
        http://www.lintcode.com/zh-cn/problem/binary-tree-path-sum/
         二叉树的路径和
         给定一个二叉树，找出所有路径中各节点相加总和等于给定 目标值 的路径。
         一个有效的路径，指的是从根节点到叶节点的路径。
         */
//        TreeNode tree = TreeNodeFactory.build("1, 2, 4, 2, 3");
//        System.out.println(tree);
//        int target = 5;
//        XYLog.d(solution.binaryTreePathSum(tree, target));



        /*
        字符串置换
        给定两个字符串，请设计一个方法来判定其中一个字符串是否为另一个字符串的置换。
        置换的意思是，通过改变顺序可以使得两个字符串相等。
        样例
        "abc" 为 "cba" 的置换。
        "aabc" 不是 "abcc" 的置换。
         */
//        String str1 = "abc";
//        String str2 = "cba";
//        System.out.println(solution.stringPermutation(str1, str2));


        /*
        http://www.lintcode.com/zh-cn/problem/lfu-cache/
        LFU缓存
        LFU是一个著名的缓存算法
        实现LFU中的set 和 get（set和get操作都增加一个访问次数）
         */
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


        /*
         http://www.lintcode.com/zh-cn/problem/find-the-missing-number-ii/
        寻找丢失的数 II
        给一个由 1 - n 的整数随机组成的一个字符串序列，其中丢失了一个整数，请找到它。
        注意事项
        n <= 30
        样例
        给出 n = 20, str = 19201234567891011121314151618
        丢失的数是 17 ，返回这个数。
         */
//        int n = 11;
//        String str = "111098765432";
////        int n = 12;
////        String str = "1245678931012";
//        System.out.println(solution.findMissing2(n, str));

    }

}
