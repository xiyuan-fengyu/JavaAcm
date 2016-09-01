# JavaAcm


[题源](http://www.lintcode.com/zh-cn/problem/#)<br>
<br>
[答案](http://www.jiuzhang.com/solutions/)<br>
<br>
一些有用的东西：<br>
[日志打印工具](https://github.com/xiyuan-fengyu/JavaAcm/blob/master/src/main/java/com/xiyuan/util/XYLog.java)，方便打印数组，可迭代数据集，一般对象。<br>

二叉树构建工厂类以及实现了可视化字符串的二叉树:<br>
```java
    public static class TreeNodeFactory {

        public static TreeNode build(Integer[] arr) {
            java.util.Queue<TreeNode> nodeQueue = new java.util.LinkedList<TreeNode>();
            return build(arr, 0, nodeQueue);
        }

        public static TreeNode build(String str) {
            java.util.Queue<TreeNode> nodeQueue = new java.util.LinkedList<TreeNode>();
            String[] split = str.split(",");
            Integer[] arr = new Integer[split.length];
            for (int i = 0, len = split.length; i < len; i++) {
                try {
                    arr[i] = Integer.parseInt(split[i].trim());
                }
                catch (Exception e) {
                    arr[i] = null;
                }
            }
            return build(arr, 0, nodeQueue);
        }

        private static TreeNode build(Integer[] arr, int index, java.util.Queue<TreeNode> nodeQueue) {
            if (index >= arr.length) {
                return null;
            }

            if (nodeQueue.isEmpty()) {
                Integer first = arr[0];
                if (first == null) {
                    return null;
                }
                else {
                    TreeNode root = new TreeNode(first);
                    nodeQueue.offer(root);
                    build(arr, index + 1, nodeQueue);
                    return root;
                }
            }
            else {
                TreeNode curParent = nodeQueue.poll();
                if (arr[index] != null) {
                    curParent.left = new TreeNode(arr[index]);
                    nodeQueue.offer(curParent.left);
                }
                if (index + 1 < arr.length && arr[index + 1] != null) {
                    curParent.right = new TreeNode(arr[index + 1]);
                    nodeQueue.offer(curParent.right);
                }
                build(arr, index + 2, nodeQueue);
            }
            return null;
        }

    }

    public static class TreeNode {
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
            }
            int xLen = maxX - minX + 1;
            int offsetX = -minX;
            char[][] charArr = new char[maxY + 1][xLen];
            for (PositionChar item: chars) {
                charArr[item.y][item.x + offsetX] = item.c;
            }
            StringBuilder strBld = new StringBuilder();
            strBld.append('\n');
            for (int i = 0; i <= maxY; i++) {
                for (int j = 0; j < xLen; j++) {
                    char c = charArr[i][j];
                    strBld.append(c == '\0'? ' ': c);
                }
                strBld.append('\n');
            }
            return strBld.toString();
        }

        private List<PositionChar> toPositionChars() {
            List<PositionChar> result = new ArrayList<PositionChar>();
            String valStr = "" + val;
            int valStrLen = valStr.length();
            for (int i = 0, len = valStr.length(); i < len; i++) {
                result.add(new PositionChar(i, 0, valStr.charAt(i)));
            }
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
                    int offset = 0;
                    do {
                        isCrossing = false;
                        Set<Integer> keys = leftMax.keySet();
                        for (Integer key: keys) {
                            if (rightMin.containsKey(key) && leftMax.get(key) + 1 > rightMin.get(key) + offset) {
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
```


例如：<br>
```java
TreeNode root = TreeNodeFactory.build("2,1,4,#,#,3,5");
XYLog.d(root);
```
打印结果如下：<br>
![二叉树可视化打印](https://github.com/xiyuan-fengyu/JavaAcm/blob/master/image/TreeNodePrint.png)

