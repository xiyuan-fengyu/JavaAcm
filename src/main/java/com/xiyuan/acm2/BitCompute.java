package com.xiyuan.acm2;

import com.xiyuan.util.XYLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

/**
 * 位运算的一些技巧
 * Created by xiyuan_fengyu on 2017/4/12.
 */
public class BitCompute {

    public static String binaryStr(int i) {
        if (i >= 0) {
            String str = Integer.toBinaryString(i);
            char[] zeros = new char[Integer.SIZE - str.length()];
            Arrays.fill(zeros, '0');
            return new String(zeros) + str;
        }
        else {
            return Integer.toBinaryString(i);
        }
    }

    public static void printBinaryStr(int i) {
        System.out.println(binaryStr(i));
    }

    public static void printBinaryStrAndInt(int i) {
        System.out.println(binaryStr(i) + "\t" + i);
    }

    public static void and(int i1, int i2) {
        printBinaryStr(i1);
        printBinaryStr(i2);
        printBinaryStr(i1 & i2);
    }

    public static boolean isOneAtIndex(int i1, int index) {
        if (index > Integer.SIZE) {
            return false;
        }

        return (1 << index & i1) != 0;
    }

    public static ArrayList<Integer> oneIndexs(int i1) {
        ArrayList<Integer> result = new ArrayList<>();
        int index = 0;
        while (i1 != 0) {
            if ((i1 & 1) == 1) {
                result.add(index);
            }

            i1 >>>= 1;
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(isOneAtIndex(2, 1));

//        printBinaryStr(-1);
//        XYLog.d(oneIndexs(-1));


    }

}
