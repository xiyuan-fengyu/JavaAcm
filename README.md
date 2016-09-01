# JavaAcm


[题源](http://www.lintcode.com/zh-cn/problem/#)<br>
<br>
[答案](http://www.jiuzhang.com/solutions/)<br>
<br>
一些有用的东西：<br>
[日志打印工具](https://github.com/xiyuan-fengyu/JavaAcm/blob/master/src/main/java/com/xiyuan/util/XYLog.java)，方便打印数组，可迭代数据集，一般对象。<br>

[二叉树构建工厂类](https://github.com/xiyuan-fengyu/JavaAcm/blob/master/src/main/java/com/xiyuan/acm/factory/TreeNodeFactory.java)以及[实现了可视化字符串的二叉树](https://github.com/xiyuan-fengyu/JavaAcm/blob/master/src/main/java/com/xiyuan/acm/model/TreeNode.java)<br>
例如：<br>
```java
TreeNode root = TreeNodeFactory.build("2,1,4,#,#,3,5");
XYLog.d(root);
```
打印结果如下：<br>
![二叉树可视化打印](https://github.com/xiyuan-fengyu/JavaAcm/blob/master/image/TreeNodePrint.png)

