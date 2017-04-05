package com.xiyuan.acm;

/**
 * Created by xiyuan_fengyu on 2017/4/5.
 */
public class Solution565_800 {

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
            if (num1 > 0 && num2 > 0 && !existed[num1] && !existed[num2]) {
                existed[num1] = true;
                existed[num2] = true;
                if (curIndex == len - 2) {
                    return true;
                }
                else {
                    boolean temp = findMissing2(n, str, curIndex + 2, existed);
                    if (!temp) {
                        existed[num1] = false;
                        existed[num2] = false;
                    }
                    else return true;
                }
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


    public static void main(String[] args) {
        Solution565_800 solution = new Solution565_800();

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
        int n = 20;
        String str = "19201234567891011121314151618";
//        int n = 12;
//        String str = "1245678931012";
        System.out.println(solution.findMissing2(n, str));

    }

}
