package com.xiyuan.acm2;

import java.util.ArrayList;
import java.util.Arrays;
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


    private void test() {

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