package com.xiyuan.acm;

import com.xiyuan.acm.model.LFUCache;

import java.util.Arrays;
import java.util.HashMap;
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


    public static void main(String[] args) {
        Solution3 solution = new Solution3();

        /*
        字符串置换
        给定两个字符串，请设计一个方法来判定其中一个字符串是否为另一个字符串的置换。
        置换的意思是，通过改变顺序可以使得两个字符串相等。
        样例
        "abc" 为 "cba" 的置换。
        "aabc" 不是 "abcc" 的置换。
         */
        String str1 = "abc";
        String str2 = "cba";
        System.out.println(solution.stringPermutation(str1, str2));


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
