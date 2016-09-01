package com.xiyuan.acm.model;

import java.util.*;

/**
 * Created by xiyuan_fengyu on 2016/9/1.
 */
public class TreeNode {
    public int val;
    public TreeNode left, right;
    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }

    @Override
    public String toString() {
        List<PositionChar> chars = toPositionChars();
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        Map<Integer, Integer> maxXPerLine = new HashMap<Integer, Integer>();
        for (PositionChar item: chars) {
            if (item.x < minX) {
                minX = item.x;
            }
            if (item.x > maxX) {
                maxX = item.x;
            }
            if (item.y > maxY) {
                maxY = item.y;
            }

            if (maxXPerLine.containsKey(item.y)) {
                int cur = maxXPerLine.get(item.y);
                if (cur < item.x) {
                    maxXPerLine.put(item.y, item.x);
                }
            }
            else {
                maxXPerLine.put(item.y, item.x);
            }
        }
        int xLen = maxX - minX + 1;
        int offsetX = -minX;
        char[][] charArr = new char[maxY + 1][xLen + 1];

        Set<Map.Entry<Integer, Integer>> keyVals = maxXPerLine.entrySet();
        for (Map.Entry<Integer, Integer> keyVal: keyVals) {
            charArr[keyVal.getKey()][keyVal.getValue() + offsetX + 1] = '\n';
        }

        for (PositionChar item: chars) {
            charArr[item.y][item.x + offsetX] = item.c;
        }
        StringBuilder strBld = new StringBuilder();
        strBld.append('\n');
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= xLen; j++) {
                char c = charArr[i][j];
                if (c == '\n') {
                    strBld.append('\n');
                    break;
                }
                else if (c == '\0') {
                    strBld.append(' ');
                }
                else {
                    strBld.append(c);
                }
            }
        }
        return strBld.toString();
    }

    private List<PositionChar> toPositionChars() {
        List<PositionChar> result = new ArrayList<PositionChar>();
        String valStr = "" + val;
        int valStrLen = valStr.length();
        int offset = 0;
        if (left != null || right != null) {
            List<PositionChar> leftChars = null;
            List<PositionChar> rightChars = null;
            Map<Integer, Integer> leftMax = new HashMap<Integer, Integer>();
            Map<Integer, Integer> rightMin = new HashMap<Integer, Integer>();
            if (left != null) {
                leftChars = left.toPositionChars();
                int leftOffset = ("" + left.val).length() + 1;
                for (PositionChar item: leftChars) {
                    item.x -= leftOffset;
                    item.y += 2;
                    if (leftMax.containsKey(item.y)) {
                        leftMax.put(item.y, Math.max(item.x, leftMax.get(item.y)));
                    }
                    else {
                        leftMax.put(item.y, item.x);
                    }
                }
                if (leftMax.containsKey(2)) {
                    leftChars.add(new PositionChar(leftMax.get(2) + 1, 1, '/'));
                    leftMax.put(1, leftMax.get(2) + 1);
                }
            }
            if (right != null) {
                rightChars = right.toPositionChars();
                int rightOffset = valStrLen + 1;
                for (PositionChar item: rightChars) {
                    item.x += rightOffset;
                    item.y += 2;
                    if (rightMin.containsKey(item.y)) {
                        rightMin.put(item.y, Math.min(item.x, rightMin.get(item.y)));
                    }
                    else {
                        rightMin.put(item.y, item.x);
                    }
                }
                if (rightMin.containsKey(2)) {
                    rightChars.add(new PositionChar(rightMin.get(2) - 1, 1, '\\'));
                    rightMin.put(1, rightMin.get(2) - 1);
                }
            }

            if (leftChars != null && rightChars != null) {
                boolean isCrossing = true;
                do {
                    isCrossing = false;
                    Set<Integer> keys = leftMax.keySet();
                    for (Integer key: keys) {
                        if (rightMin.containsKey(key) && leftMax.get(key) + 2 > rightMin.get(key) + offset) {
                            isCrossing = true;
                            break;
                        }
                    }

                    if (isCrossing) {
                        offset += 1;
                    }
                }
                while (isCrossing);

                for (PositionChar item: rightChars) {
                    item.x += offset;
                }
            }

            if (leftChars != null) {
                for (PositionChar item: leftChars) {
                    result.add(item);
                }
            }
            if (rightChars != null) {
                for (PositionChar item: rightChars) {
                    result.add(item);
                }
            }
        }
        for (int i = 0, len = valStr.length(); i < len; i++) {
            result.add(new PositionChar(i + (int)(offset / 2.0 + 0.5), 0, valStr.charAt(i)));
        }
        return result;
    }

    private class PositionChar {
        public int x;
        public int y;
        public char c;
        public PositionChar(int x, int y, char c) {
            this.c = c;
            this.x = x;
            this.y = y;
        }
    }

}