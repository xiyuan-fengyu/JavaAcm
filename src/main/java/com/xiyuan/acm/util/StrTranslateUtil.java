package com.xiyuan.acm.util;

import java.util.Scanner;

/**
 * Created by xiyuan_fengyu on 2016/10/28.
 */
public class StrTranslateUtil {

    public static void main(String[] args) {
        middleBracketToBig();
//        middleBracketToBigAnd01ToBool();
    }

    private static void middleBracketToBig() {
        Scanner scanner = new Scanner(System.in);
        String line;
        int innerBracketNum = 0;
        StringBuilder strBld = new StringBuilder();
        while ((line = scanner.nextLine()) != null) {
            if (!line.equals("")) {
                for (int i = 0, len = line.length(); i < len; i++) {
                    char c = line.charAt(i);
                    if (c == '[') {
                        innerBracketNum++;
                        strBld.append('{');
                    }
                    else if (c == ']') {
                        innerBracketNum--;
                        strBld.append('}');
                    }
                    else if (c == ',') {
                        strBld.append(c);
                        if (i == len - 1 || line.charAt(i + 1) != ' ') {
                            strBld.append(' ');
                        }
                    }
                    else {
                        strBld.append(c);
                    }
                }
                strBld.append('\n');
            }
            if (innerBracketNum == 0) {
                break;
            }
        }
        System.out.println(strBld.toString());
    }

    private static void middleBracketToBigAnd01ToBool() {
        Scanner scanner = new Scanner(System.in);
        String line;
        int innerBracketNum = 0;
        StringBuilder strBld = new StringBuilder();
        while ((line = scanner.nextLine()) != null) {
            if (!line.equals("")) {
                for (int i = 0, len = line.length(); i < len; i++) {
                    char c = line.charAt(i);
                    if (c == '[') {
                        innerBracketNum++;
                        strBld.append('{');
                    }
                    else if (c == ']') {
                        innerBracketNum--;
                        strBld.append('}');
                    }
                    else if (c == ',') {
                        strBld.append(c);
                        if (i == len - 1 || line.charAt(i + 1) != ' ') {
                            strBld.append(' ');
                        }
                    }
                    else if (c == '0') {
                        strBld.append("false");
                    }
                    else if (c == '1') {
                        strBld.append("true");
                    }
                    else {
                        strBld.append(c);
                    }
                }
                strBld.append('\n');
            }
            if (innerBracketNum == 0) {
                break;
            }
        }
        System.out.println(strBld.toString());
    }

}
