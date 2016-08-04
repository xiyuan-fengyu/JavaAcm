package com.xiyuan.acm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by xiyuan_fengyu on 2016/8/4.
 */
public class Solutions {

    public int sumOfArray(int[] nums)
    {
        int total = 0;
        for (int i: nums) {
            total += i;
        }
        return total;
    }












    /* http://www.lintcode.com/zh-cn/problem/a-b-problem/#
     * param a: The first integer
     * param b: The second integer
     * return: The sum of a and b
     */
    public int aplusb(int a, int b) {
        // write your code here, try to do it without arithmetic operators.
        if(0 == b)
        {
            return a;
        }
        else {
            return aplusb(a ^ b, (a&b) << 1);
        }
    }











    /* http://www.lintcode.com/zh-cn/problem/trailing-zeros/
     * param n: As desciption
     * return: An integer, denote the number of trailing zeros in n!
     */
    public long trailingZeros(long n) {
        // write your code here
        //末尾的0是由于2 * 5造成的，而且因子2的个数一定多于5，所以因子5的个数就是末尾0的个数
        //公式：f(n) = k + f(k),其中k = n / 5;当1<=n<=4,f(n) = 0;
        //应为n!总可以写成这样的形式：5k * 5(k - 1) * 5(k - 2) * ... * 10 * 5 * a,5k是n!中最大的那个可以被5整除的因数，所以k就是n/5取整。
        if(n < 5)
        {
            return 0;
        }
        else {
            long m = n / 5;
            return m + trailingZeros(m);
        }
    }














    /* http://www.lintcode.com/zh-cn/problem/digit-counts/
     * http://bbs.csdn.net/topics/290042871
     * param k : As description.
     * param n : As description.
     * return: An integer denote the count of digit k in 1..n
     */
    int digitCounts(int k, int n) {
        // write your code here
        /**
         * 设n=a*10^m + b, f(n, k)表示0~n这个区间，数字k出现的次数
         * 则a是n的最高位，b是n的所有较低位组成的数字，那么可以把0~n的数分为两部分：0 ~ (10^m-1) 和 10^m ~ n,也即有最高位的数字和没有最高位的部分
         * 例如4325=4*10^3 + 325, a=4, b=325, 0~999是没有最高位的部分(相对于4325来说),1000~4325是有高位的部分
         * 那么f(n, k)=f(a*10^m + b, k)=f(10^m - 1, k) +(有最高位的数字区间的k出现次数)
         * f(10^m - 1, k)就是(没有最高位的数字区间的k出现次数)
         * 现在已经可以看出一个地推公式的雏形了
         *
         * 那么 (有最高位的数字区间的k出现次数)该怎么计算呢
         * 同样分为两部分：(最高位提供的k的次数)和(较低位提供的k的次数)
         *
         * (最高位提供的k的次数)分为四种情况：k == 0, 0(因为0不可能出现在十位或更高位); 后三种k != 0, a>k, 10^m; a==k, b + 1(为是0~b); a < k, 0
         *
         * (较低位提供的k的次数)分为两部分：最高位为a,则较低位出现k的次数为f(b, k);最高位小于a,较低位出现k的次数为(a - 1) *m * (10^(m - 1))
         *
         * 把所有部分加起来，得到
         * f(n, k)=f(a*10^m + b, k)=f(10^m - 1, k) + (k == 0?0: (a > k? 10^m: (a == k?(b + 1): 0))) + f(b, k) + (a - 1) *m * (10^(m - 1));
         * 递归的结束条件为： f(n, k)=(n <=9 && k <= n?1: 0);
         */
        if(n <= 9)
        {
            return k <= n?1: 0;
        }
        int numSize = getNumSize(n);
        long tempTenSqr = tenSqr[numSize];
        int a = (int) (n / tempTenSqr);
        int b = (int) (n - a * tempTenSqr);
        long aNum = 0;
        if(a > k && k != 0)
        {
            aNum = tempTenSqr;
        }
        else if(a == k)
        {
            aNum = b + 1;
        }
        return (int) (aNum + digitCounts(k, (int) (tempTenSqr - 1)) + digitCounts(k, b) + (a - 1) * numSize * tenSqr[numSize - 1]);
    }

    private static final long[] tenSqr
            = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L};

    private static int getNumSize(long a){
        int n = 0;
        while (tenSqr[n] <= a)
        {
            n++;
        }
        return n-1;
    }










    /**
     * http://www.lintcode.com/zh-cn/problem/ugly-number/
     * @param k: The number k.
     * @return: The kth prime number as description.
     */
    public long kthPrimeNumber(int k) {
        // write your code here
        if(PRIME_NUMS == null)
        {
            PRIME_NUMS = new ArrayList<Long>();
            PRIME_NUMS.addAll(Arrays.asList(PRIME_NUMS_STATIC));
        }
        if(k <= PRIME_NUMS.size())
        {
            return PRIME_NUMS.get(k - 1);
        }

        long curNum = 0;
        int curIndex = PRIME_NUMS.size() - 1;
        while (true) {
            int tempIndex = PRIME_NUMS.size() - 1;
            long tempMax = PRIME_NUMS.get(tempIndex);
            long tempMaxMin = 0;

            for(int i: int357)
            {
                while ((curNum = PRIME_NUMS.get(tempIndex) * i) > tempMax) {
                    tempIndex --;
                }
                tempIndex ++;
                if(tempMaxMin == 0)
                {
                    tempMaxMin = PRIME_NUMS.get(tempIndex) * i;
                }
                else {
                    tempMaxMin = Math.min(tempMaxMin, PRIME_NUMS.get(tempIndex) * i);
                }
            }

            PRIME_NUMS.add(tempMaxMin);
            curIndex ++;

            if(curIndex == k)
            {
                break;
            }
        }

        return curNum;
    }

    public static int[] int357 = {3, 5, 7};

    public static final Long[] PRIME_NUMS_STATIC = {3L,5L,7L,9L,15L,21L,25L,27L,35L,45L,49L,63L,75L,81L,105L,125L,135L,147L,175L,189L,225L,243L,245L,315L,343L,375L,405L,441L,525L,567L,625L,675L,729L,735L,875L,945L,1029L,1125L,1215L,1225L,1323L,1575L,1701L,1715L,1875L,2025L,2187L,2205L,2401L,2625L,2835L,3087L,3125L,3375L,3645L,3675L,3969L,4375L,4725L,5103L,5145L,5625L,6075L,6125L,6561L,6615L,7203L,7875L,8505L,8575L,9261L,9375L,10125L,10935L,11025L,11907L,12005L,13125L,14175L,15309L,15435L,15625L,16807L,16875L,18225L,18375L,19683L,19845L,21609L,21875L,23625L,25515L,25725L,27783L,28125L,30375L,30625L,32805L,33075L,35721L,36015L,39375L,42525L,42875L,45927L,46305L,46875L,50421L,50625L,54675L,55125L,59049L,59535L,60025L,64827L,65625L,70875L,76545L,77175L,78125L,83349L,84035L,84375L,91125L,91875L,98415L,99225L,107163L,108045L,109375L,117649L,118125L,127575L,128625L,137781L,138915L,140625L,151263L,151875L,153125L,164025L,165375L,177147L,178605L,180075L,194481L,196875L,212625L,214375L,229635L,231525L,234375L,250047L,252105L,253125L,273375L,275625L,295245L,297675L,300125L,321489L,324135L,328125L,352947L,354375L,382725L,385875L,390625L,413343L,416745L,420175L,421875L,453789L,455625L,459375L,492075L,496125L,531441L,535815L,540225L,546875L,583443L,588245L,590625L,637875L,643125L,688905L,694575L,703125L,750141L,756315L,759375L,765625L,820125L,823543L,826875L,885735L,893025L,900375L,964467L,972405L,984375L,1058841L,1063125L,1071875L,1148175L,1157625L,1171875L,1240029L,1250235L,1260525L,1265625L,1361367L,1366875L,1378125L,1476225L,1488375L,1500625L,1594323L,1607445L,1620675L,1640625L,1750329L,1764735L,1771875L,1913625L,1929375L,1953125L,2066715L,2083725L,2100875L,2109375L,2250423L,2268945L,2278125L,2296875L,2460375L,2470629L,2480625L,2657205L,2679075L,2701125L,2734375L,2893401L,2917215L,2941225L,2953125L,3176523L,3189375L,3215625L,3444525L,3472875L,3515625L,3720087L,3750705L,3781575L,3796875L,3828125L,4084101L,4100625L,4117715L,4134375L,4428675L,4465125L,4501875L,4782969L,4822335L,4862025L,4921875L,5250987L,5294205L,5315625L,5359375L,5740875L,5764801L,5788125L,5859375L,6200145L,6251175L,6302625L,6328125L,6751269L,6806835L,6834375L,6890625L,7381125L,7411887L,7441875L,7503125L,7971615L,8037225L,8103375L,8203125L,8680203L,8751645L,8823675L,8859375L,9529569L,9568125L,9646875L,9765625L,10333575L,10418625L,10504375L,10546875L,11160261L,11252115L,11344725L,11390625L,11484375L,12252303L,12301875L,12353145L,12403125L,13286025L,13395375L,13505625L,13671875L,14348907L,14467005L,14586075L,14706125L,14765625L,15752961L,15882615L,15946875L,16078125L,17222625L,17294403L,17364375L,17578125L,18600435L,18753525L,18907875L,18984375L,19140625L,20253807L,20420505L,20503125L,20588575L,20671875L,22143375L,22235661L,22325625L,22509375L,23914845L,24111675L,24310125L,24609375L,26040609L,26254935L,26471025L,26578125L,26796875L,28588707L,28704375L,28824005L,28940625L,29296875L,31000725L,31255875L,31513125L,31640625L,33480783L,33756345L,34034175L,34171875L,34453125L,36756909L,36905625L,37059435L,37209375L,37515625L,39858075L,40186125L,40353607L,40516875L,41015625L,43046721L,43401015L,43758225L,44118375L,44296875L,47258883L,47647845L,47840625L,48234375L,48828125L,51667875L,51883209L,52093125L,52521875L,52734375L,55801305L,56260575L,56723625L,56953125L,57421875L,60761421L,61261515L,61509375L,61765725L,62015625L,66430125L,66706983L,66976875L,67528125L,68359375L,71744535L,72335025L,72930375L,73530625L,73828125L,78121827L,78764805L,79413075L,79734375L,80390625L,85766121L,86113125L,86472015L,86821875L,87890625L,93002175L,93767625L,94539375L,94921875L,95703125L,100442349L,101269035L,102102525L,102515625L,102942875L,103359375L,110270727L,110716875L,111178305L,111628125L,112546875L,119574225L,120558375L,121060821L,121550625L,123046875L,129140163L,130203045L,131274675L,132355125L,132890625L,133984375L,141776649L,142943535L,143521875L,144120025L,144703125L,146484375L,155003625L,155649627L,156279375L,157565625L,158203125L,167403915L,168781725L,170170875L,170859375L,172265625L,182284263L,183784545L,184528125L,185297175L,186046875L,187578125L,199290375L,200120949L,200930625L,201768035L,202584375L,205078125L,215233605L,217005075L,218791125L,220591875L,221484375L,234365481L,236294415L,238239225L,239203125L,241171875L,244140625L,257298363L,258339375L,259416045L,260465625L,262609375L,263671875L,279006525L,281302875L,282475249L,283618125L,284765625L};

    public static List<Long> PRIME_NUMS;








    /* http://www.lintcode.com/zh-cn/problem/kth-largest-element/
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        // write your code here
        int size = nums.length;
        k = size - k;

        quickSortKth(nums, 0, size - 1, k);

        return nums[k];
    }

    //只为找到正确的a[k]的排序
    public void quickSortKth(int[] a, int left, int right, int k)
    {
        if(left >= right)
        {
            return ;
        }
        int i = left;
        int j = right;
        int key = a[left];
        while(i < j)
        {
            while(i < j && key <= a[j])
            {
                j--;
            }

            a[i] = a[j];

            while(i < j && key >= a[i])
            {
                i++;
            }

            a[j] = a[i];
        }

        a[i] = key;
        if(i > k)
        {
            quickSortKth(a, left, i - 1, k);
        }
        else if(i < k)
        {
            quickSortKth(a, i + 1, right, k);
        }
    }


    //标准的快速排序
    public void quickSort(int[] a, int left, int right)
    {
        if(left >= right)
        {
            return ;
        }
        int i = left;
        int j = right;
        int key = a[left];
        while(i < j)
        {
            while(i < j && key <= a[j])
            {
                j--;
            }

            a[i] = a[j];

            while(i < j && key >= a[i])
            {
                i++;
            }

            a[j] = a[i];
        }

        a[i] = key;
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }

    public void swape(int[] arr, int n, int m)
    {
        int temp = arr[n];
        arr[n] = arr[m];
        arr[m] = temp;
    }













    /**http://www.lintcode.com/zh-cn/problem/merge-sorted-array-ii/
     * @param A and B: sorted integer array A and B.
     * @return: A new sorted integer array
     */
    public int[] mergeSortedArray(int[] A, int[] B) {
        // Write your code here
        int[] C = new int[A.length + B.length];
        int a = 0;
        int b = 0;
        int index = 0;
        while (a < A.length && b < B.length) {
            if(A[a] <= B[b])
            {
                C[index++] = A[a ++];
            }
            else
            {
                C[index++] = B[b ++];
            }
        }
        if(a < A.length)
        {
            while (a < A.length) {
                C[index++] = A[a ++];
            }
        }
        else {
            while (b < B.length) {
                C[index++] = B[b ++];
            }
        }
        return C;
    }











    /**http://www.lintcode.com/zh-cn/problem/binary-tree-serialization/
     * This method will be invoked first, you should design your own algorithm
     * to serialize a binary tree which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public String serialize(TreeNode root) {
        // write your code here
        if(root == null)
        {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        serialize(root, sb);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public void serialize(TreeNode node, StringBuffer sb) {
        sb.append(node.val).append(',');
        if(node.left != null)
        {
            serialize(node.left, sb);
        }
        else
        {
            sb.append('#').append(',');
        }

        if(node.right != null)
        {
            serialize(node.right, sb);
        }
        else
        {
            sb.append('#').append(',');
        }
    }

    /**http://www.lintcode.com/zh-cn/problem/binary-tree-serialization/
     * This method will be invoked second, the argument data is what exactly
     * you serialized at method "serialize", that means the data is not given by
     * system, it's given by your own serialize method. So the format of data is
     * designed by yourself, and deserialize it here as you serialize it in
     * "serialize" method.
     */
    public TreeNode deserialize(String data) {
        // write your code here
        if(data == null || data.equals(""))
        {
            return null;
        }
        String[] valStrs = data.split(",");
        if(valStrs[0].equals("#"))
        {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(valStrs[0]));
        deserialize(root, valStrs, 1);
        return root;
    }

    public int deserialize(TreeNode node, String[] valStrs, int index)
    {
        if(index + 1 < valStrs.length)
        {
            if(valStrs[index].equals("#"))
            {
                node.left = null;
                index ++;
            }
            else {
                node.left = new TreeNode(Integer.parseInt(valStrs[index]));
                index = deserialize(node.left, valStrs, index + 1);
            }

            if(valStrs[index].equals("#"))
            {
                node.right = null;
                index ++;
            }
            else {
                node.right = new TreeNode(Integer.parseInt(valStrs[index]));
                index = deserialize(node.right, valStrs, index + 1);
            }
        }
        return index;
    }














    /**http://www.lintcode.com/zh-cn/problem/rotate-string/
     * @param str: an array of char
     * @param offset: an integer
     * @return: nothing
     */
    public void rotateString(char[] str, int offset) {
        // write your code here
        int size = str.length;
        if(size == 0)
        {
            return;
        }

        offset %= size;
        if(offset != 0)
        {
            int tempOffset = 0;
            int totalSwape = 0;
            while (tempOffset < offset) {
                totalSwape += swapeCharWithOffset(str, tempOffset, tempOffset, offset, str[tempOffset]);
                if(totalSwape == size)
                {
                    break;
                }
                tempOffset ++;
            }
        }
    }

    public int swapeCharWithOffset(char[] str, int startIndex, int curIndex, int offset, char lastVal)
    {
        int swapeNum = 0;
        char curVal = str[curIndex];
        str[curIndex] = lastVal;

        if((curIndex + offset) % str.length == startIndex)
        {
            str[startIndex] = curVal;
            swapeNum ++;
        }
        else {
            swapeNum += 1 + swapeCharWithOffset(str, startIndex, (curIndex + offset) % str.length, offset, curVal);
        }
        return swapeNum;
    }













    /**http://www.lintcode.com/zh-cn/problem/fizz-buzz/
     * param n: As description.
     * return: A list of strings.
     */
    public ArrayList<String> fizzBuzz(int n) {
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                results.add("fizz buzz");
            } else if (i % 5 == 0) {
                results.add("buzz");
            } else if (i % 3 == 0) {
                results.add("fizz");
            } else {
                results.add(String.valueOf(i));
            }
        }
        return results;
    }




    /**http://www.lintcode.com/zh-cn/problem/search-range-in-binary-search-tree/#
     * @param root: The root of the binary search tree.
     * @param k1 and k2: range k1 to k2.
     * @return: Return all keys that k1<=key<=k2 in ascending order.
     */
    public ArrayList<Integer> searchRange(TreeNode root, int k1, int k2) {
        // write your code here
        ArrayList<Integer> arr = new ArrayList<Integer>();
        searchRange(root, k1, k2, arr);
        quickSortArrayList(arr, 0, arr.size() - 1);
        return arr;
    }

    public void searchRange(TreeNode node, int k1, int k2, List<Integer> arr)
    {
        if(node != null)
        {
            if(node.val >= k1 && node.val <= k2)
            {
                arr.add(node.val);
            }

            if(node.left != null)
            {
                searchRange(node.left, k1, k2, arr);
            }
            if(node.right != null)
            {
                searchRange(node.right, k1, k2, arr);
            }
        }
    }

    public void quickSortArrayList(ArrayList<Integer> arr, int left, int right)
    {
        if(left >= right)
        {
            return ;
        }

        int l = left;
        int r = right;
        int key = arr.get(l);

        while (l < r) {
            while (l < r && arr.get(r) >= key) {
                r --;
            }
            arr.set(l, arr.get(r));

            while (l < r && arr.get(l) <= key) {
                l ++;
            }
            arr.set(r, arr.get(l));
        }
        arr.set(l, key);

        quickSortArrayList(arr, left, l - 1);
        quickSortArrayList(arr, l + 1, right);
    }















    /**http://www.lintcode.com/zh-cn/problem/strstr/
     * Returns a index to the first occurrence of target in source,
     * or -1  if target is not part of source.
     * @param s string to be scanned.
     * @param p string containing the sequence of characters to match.
     */
    public int strStr(String s, String p) {
        if(s == null || p == null)
        {
            return -1;
        }
        else if(p.equals("")) {
            return 0;
        }

        int sSize = s.length();
        int pSize = p.length();
        int i = 0;
        int j =0;
        int[] next = getNext(p);
        while (i < sSize) {
            if(j == -1 || s.charAt(i) == p.charAt(j))
            {
                i ++;
                j ++;
            }
            else
            {
                j = next[j];
            }
            if(j == pSize)
            {
                return i - pSize;
            }
        }
        return -1;
    }

    public int[] getNext(String str)
    {
        int j = 0;
        int k = -1;
        int size = str.length();
        int[] next = new int[size + 1];
        next[0] = -1;

        while(j < size)
        {
            if(k == -1 || str.charAt(j) == str.charAt(k))
            {
                j++;
                k++;
                next[j] = k;
            }
            else
            {
                k = next[k];
            }
        }
        return next;
    }

    public int strStrNormal(String source, String pattern)
    {
        int next = 0;
        int s = 0;
        int p = 0;
        int sourceSize = source.length();
        int patternSize = pattern.length();
        while (next + patternSize <= sourceSize) {
            s = next;
            p = 0;
            while (source.charAt(s) == pattern.charAt(p)) {
                s ++;
                p ++;
                if(p == patternSize)
                {
                    return next;
                }
            }
            next ++;
        }
        return -1;
    }

















    /**http://www.lintcode.com/zh-cn/problem/first-position-of-target/
     * @param nums: The integer array.
     * @param target: Target to find.
     * @return: The first position of target. Position starts from 0.
     */
    public int binarySearch(int[] nums, int target) {
        //由于可能出现重复的元素，且要找到第一次出现的位子，所以不是正规的二分搜索，还需要做一些调整;
        //正常的而非搜索判定结束的条件为target == nums[mid],
        //而此题的要求则要改为target == nums[left],相应的其他两个分支也要做调整
        if(nums.length == 0)
        {
            return -1;
        }
        return binarySearchLeftFirst(nums, target, 0, nums.length - 1);
    }

    public int binarySearchLeftFirst(int[] nums, int target, int left, int right) {
        int mid = (left + right) / 2;
        if(target == nums[left])
        {
            return left;
        }
        else if(target > nums[left] && target <= nums[mid]){
            return binarySearchLeftFirst(nums, target, left, mid);
        }
        else if(target > nums[mid] && target <= nums[right])
        {
            return binarySearchLeftFirst(nums, target, mid + 1, right);
        }
        return -1;
    }

    //最常规的二分搜索
    public int binarySearch(int[] nums, int target, int left, int right) {
        int mid = (left + right) / 2;
        if(target >= nums[left] && target < nums[mid]){
            return binarySearch(nums, target, left, mid - 1);
        }
        else if(target == nums[mid])
        {
            return mid;
        }
        else if(target > nums[mid] && target <= nums[right])
        {
            return binarySearch(nums, target, mid + 1, right);
        }
        return -1;
    }












    /**http://www.lintcode.com/zh-cn/problem/permutations/
     * @param nums: A list of integers.
     * @return: A list of permutations.
     */
    public ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if(nums != null && nums.size() != 0)
        {
            quickSortArrayList(nums, 0, nums.size() - 1);
            result.add(nums);

            ArrayList<Integer> clone = new ArrayList<Integer>(nums);
            while (true) {
                nextDicSort(clone);
                if(clone.size() > 0)
                {
                    result.add(clone);
                    clone = new ArrayList<Integer>(clone);
                }
                else {
                    break;
                }
            }
        }
        return result;
    }

    //递归使用下一个排列来找到所有排列，当找最后一个排列的下一个排列时，清空arrayList，已告诉所有排列都找到了
    public void nextDicSort(ArrayList<Integer> arr)
    {
        int size = arr.size();
        for (int i = size - 1; i > -1; i--) {
            for (int j = size - 1; j > i; j--) {
                int temp = arr.get(i);
                if(temp< arr.get(j))
                {
                    arr.set(i, arr.get(j));
                    arr.set(j, temp);
                    quickSortArrayList(arr, i + 1, size - 1);
                    return;
                }
            }
        }
        arr.clear();
    }

    //正常的下一个排列算法实现
    public void nextDicSort(int[] arr)
    {
        int size = arr.length;
        for(int i = size - 1; i > -1; i --)
        {
            for(int j = size - 1; j > i; j --)
            {
                if(arr[i] < arr[j])
                {
                    swape(arr, i, j);
                    quickSort(arr, i + 1, size - 1);
                    return;
                }
            }
        }
    }













    /**http://www.lintcode.com/zh-cn/problem/subsets/
     * @param nums: A set of numbers.
     * @return: A list of lists. All valid subsets.
     */
    public ArrayList<ArrayList<Integer>> subsets(int[] nums) {
        if(nums == null)
        {
            return null;
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        result.add(new ArrayList<Integer>());
        if(nums.length == 0)
        {
            return result;
        }

        Arrays.sort(nums);
        getSubset(nums, 0, new ArrayList<Integer>(), result);
        return result;
    }

    public void getSubset(int[] nums, int curIndex, ArrayList<Integer> curArr, ArrayList<ArrayList<Integer>> subsets)
    {
        for (int i = curIndex; i < nums.length; i++) {
            ArrayList<Integer> clone = new ArrayList<Integer>(curArr);
            clone.add(nums[i]);
            subsets.add(clone);
            getSubset(nums, i + 1, clone, subsets);
        }
    }









    /**http://www.lintcode.com/zh-cn/problem/subsets-ii/
     * @param nums: A set of numbers.
     * @return: A list of lists. All valid subsets.
     */
    public ArrayList<ArrayList<Integer>> subsetsWithDup(ArrayList<Integer> nums) {
        if(nums == null)
        {
            return null;
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        result.add(new ArrayList<Integer>());
        if(nums.size() == 0)
        {
            return result;
        }

        Collections.sort(nums);
        getSubsetWithDup(nums, 0, new ArrayList<Integer>(), result);
        return result;
    }

    public void getSubsetWithDup(ArrayList<Integer> nums, int curIndex, ArrayList<Integer> curArr, ArrayList<ArrayList<Integer>> subsets)
    {
        for (int i = curIndex, size = nums.size(); i < size;) {
            ArrayList<Integer> clone = new ArrayList<Integer>(curArr);
            int cur = nums.get(i);
            clone.add(cur);
            subsets.add(clone);
            getSubsetWithDup(nums, i + 1, clone, subsets);

            do {
                i ++;
            } while (i < size && cur == nums.get(i));
        }
    }

















    /**http://www.lintcode.com/zh-cn/problem/search-a-2d-matrix/
     * @param matrix, a list of lists of integers
     * @param target, an integer
     * @return a boolean, indicate whether matrix contains target
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
        {
            return false;
        }

        for(int i = 0, xSize = matrix.length; i < xSize; i ++)
        {
            if((i + 1 < xSize && matrix[i][0] <= target && matrix[i + 1][0] > target)
                    || (i + 1 == xSize && matrix[i][0] <= target))
            {
                return binarySearch(matrix[i], target, 0, matrix[i].length - 1) > -1;
            }
        }

        return false;
    }

















    /**http://www.lintcode.com/zh-cn/problem/interleaving-string/
     * Determine whether s3 is formed by interleaving of s1 and s2.
     * @param s1, s2, s3: As description.
     * @return: true or false.
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if(s1 == null || s2 == null || s3 == null)
        {
            return false;
        }
        else if(s1.equals("") && s2.equals(s3))
        {
            return true;
        }
        else if(s2.equals("") && s1.equals(s3))
        {
            return true;
        }
        else if(s1.length() + s2.length() != s3.length())
        {
            return false;
        }

        return checkInterleave(s1, s2, s3, 0, 0);
    }

    public boolean checkInterleave(String str1, String str2, String str3, int i, int j)
    {
        return (i >= str1.length() && j >= str2.length())
                || ((i < str1.length() && str1.charAt(i) == str3.charAt(i + j)) && checkInterleave(str1, str2, str3, i + 1, j))
                || ((j < str2.length() && str2.charAt(j) == str3.charAt(i + j)) && checkInterleave(str1, str2, str3, i, j + 1));
    }














    /**http://www.lintcode.com/zh-cn/problem/insert-interval/
     * Insert newInterval into intervals.
     * @param intervals: Sorted interval list.
     * @param newInterval: A new interval.
     * @return: A new sorted interval list.
     */
    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        if(newInterval == null)
        {
            return intervals;
        }

        ArrayList<Interval> result = new ArrayList<Interval>();
        for(int i = 0, size = intervals.size(); i < size; i ++)
        {
            Interval cur = intervals.get(i);
            if(newInterval != null)
            {
                if(cur.start <= newInterval.start && cur.end >= newInterval.start)
                {
                    cur.end = Math.max(cur.end, newInterval.end);
                    newInterval = null;
                }
                else if(newInterval.start <= cur.start && newInterval.end >= cur.start){
                    cur.start = newInterval.start;
                    cur.end = Math.max(cur.end, newInterval.end);
                    newInterval = null;
                }
                else if (newInterval.end < cur.start) {
                    appendInterval(result, newInterval);
                    newInterval = null;
                }
                appendInterval(result, cur);
            }
            else {
                appendInterval(result, cur);
            }
        }

        if(newInterval != null)
        {
            result.add(newInterval);
        }
        return result;
    }

    public void appendInterval(ArrayList<Interval> intervals, Interval cur)
    {
        int resultSize = intervals.size();
        Interval lastInResult = resultSize == 0?null: intervals.get(resultSize - 1);
        if(lastInResult == null)
        {
            intervals.add(cur);
        }
        else {
            if(cur.start <= lastInResult.end)
            {
                lastInResult.end = Math.max(cur.end, lastInResult.end);
            }
            else
            {
                intervals.add(cur);
            }
        }
    }












    /**http://www.lintcode.com/zh-cn/problem/partition-array/
     *@param nums: The integer array you should partition
     *@param k: As description
     *return: The index after partition
     */
    public int partitionArray(int[] nums, int k) {
        if(nums == null)
        {
            return -1;
        }
        else if(nums.length == 0)
        {
            return 0;
        }

        quickSortPartition(nums, 0, nums.length - 1, k);
        int index = 0;
        while (index < nums.length && nums[index] < k) {
            index ++;
        }
        return index;
    }

    public void quickSortPartition(int[] nums, int left, int right, int k)
    {
        int l = left;
        int r = right;
        if(l >= r)
        {
            return;
        }
        int key = nums[l];

        while (l < r) {
            while (l < r && key <= nums[r]) {
                r --;
            }
            nums[l] = nums[r];

            while (l < r && key >= nums[l]) {
                l ++;
            }
            nums[r] = nums[l];
        }

        nums[l] = key;

        if(key >= k)
        {
            quickSortPartition(nums, left, l - 1, k);
        }
        else {
            quickSortPartition(nums, l + 1, right, k);
        }
    }
















    /**http://www.lintcode.com/zh-cn/problem/minimum-window-substring/
     * @param source: A string
     * @param target: A string
     * @return: A string denote the minimum window
     *          Return "" if there is no such a string
     */
    public String minWindow(String source, String target) {
        if(source == null || target == null || source.equals("") || target.equals(""))
        {
            return "";
        }

        int targetLen = target.length();
        int sourceLen = source.length();
        int winSize = targetLen;

        for (int i = 0; i < targetLen; i++) {
            char c = target.charAt(i);
            Integer count = charCount.get(c);
            if(count == null)
            {
                count = 0;
            }
            count ++;
            charCount.put(c, count);
        }

        while (winSize <= sourceLen) {
            for (int i = 0; i + winSize <= sourceLen;) {
                if(isContain(source, target, i, i + winSize - 1))
                {
                    return source.substring(i, i + winSize);
                }
                do {
                    i ++;
                } while (i < sourceLen && charCount.get(source.charAt(i)) == null);
            }
            winSize ++;
        }
        return "";
    }

    private Map<Character, Integer> charCount = new HashMap<Character, Integer>();

    public boolean isContain(String source, String target, int left, int right)
    {
        int targetLen = target.length();
        if(right - left + 1 < targetLen)
        {
            return false;
        }

        Map<Character, Integer> tempCount = new HashMap<Character, Integer>();
        for (int i = left; i <= right; i++) {
            char c = source.charAt(i);
            Integer count = tempCount.get(c);
            if(count == null)
            {
                count = 0;
            }
            count ++;
            tempCount.put(c, count);
        }

        Set<Character> cs = charCount.keySet();
        for(Character c: cs)
        {
            Integer cCount = tempCount.get(c);
            if(cCount == null || cCount < charCount.get(c))
            {
                return false;
            }
        }
        return true;
    }















    /**http://www.lintcode.com/zh-cn/problem/n-queens/
     * Get all distinct N-Queen solutions
     * @param n: The number of queens
     * @return: All distinct solutions
     * For example, A string '...Q' shows a queen on forth position
     */
    public ArrayList<ArrayList<String>> solveNQueens(int n) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        if(n > 0)
        {
            //不采用回溯,消耗更多的空间，速度会快一点，n=14时，耗时6秒
//    		char[] pos = new char[n * n];
//        	computeNQueen(n, pos, 0, result);

            //采用回溯，只使用一个数组来存储方案，空间开销少，但是速度慢，n=14时，耗时14秒左右
//    		char[] pos = new char[n * n];
//    		for (int i = 0, len = pos.length; i < len; i++) {
//				pos[i] = '.';
//			}
//    		columnNotValid = new boolean[n];
//    		slantValid = new HashMap<String, Boolean>();
//    		computeNQueenBackSearch(n, pos, 0, result);

            //采用位运算来减少空间占用，n=14时，耗时250毫秒左右
            upperlim = (upperlim << n) - 1;
            computeNQueenBit(0, 0, 0);
            System.out.println(sum);
        }
        return result;
    }

    //这种方法没有采用回溯，对于每一种情况都是克隆当前的数组，并改变新数组的值，舍弃旧的数组，这样会浪费很多的空间资源
    public void computeNQueen(int len, char[] pos, int row, ArrayList<ArrayList<String>> result)
    {
        if(row >= len)
        {
            ArrayList<String> solution = new ArrayList<String>();
            for(int i = 0; i < len; i ++)
            {
                solution.add(String.valueOf(pos, len * i, len));
            }
            result.add(solution);
            return;
        }

        for(int i = 0; i < len; i ++)
        {
            if(row == 0)
            {
                char[] clone = Arrays.copyOf(pos, pos.length);
                clone[len * row + i] = 'Q';
                setPosInvalid(clone, row, i);
                computeNQueen(len, clone, row + 1, result);
            }
            else {
                if(pos[len * row + i] == 0)
                {
                    char[] clone = Arrays.copyOf(pos, pos.length);
                    clone[len * row + i] = 'Q';
                    setPosInvalid(clone, row, i);
                    computeNQueen(len, clone, row + 1, result);
                }
                else {
                    continue;
                }
            }
        }
    }

    public void setPosInvalid(char[] pos, int row, int cloumn)
    {
        int size = (int) Math.pow(pos.length, 0.5f);
        for (int i = 0; i < size; i++) {
            if(i != row)
            {
                pos[size * i + cloumn] = '.';
            }
            if(i != cloumn)
            {
                pos[size * row + i] = '.';
            }
        }

        for(int i = row + 1; i < size; i ++)
        {
            int leftC = cloumn - (i - row);
            if(leftC > -1)
            {
                pos[size * i + leftC] = '.';
            }

            int rightC = cloumn + (i - row);
            if(rightC < size)
            {
                pos[size * i + rightC] = '.';
            }
        }

    }

    //采用回溯
    public void computeNQueenBackSearch(int len, char[] pos, int row, ArrayList<ArrayList<String>> result)
    {
        if(row >= len)
        {
            ArrayList<String> solution = new ArrayList<String>();
            for(int i = 0; i < len; i ++)
            {
                solution.add(String.valueOf(pos, len * i, len));
            }
            result.add(solution);
            return;
        }

        for(int i = 0; i < len; i ++)
        {
            if(isPosValid(pos, row, i))
            {
                columnNotValid[i] = true;
                slantValid.put("1_" + (row - i), false);
                slantValid.put("-1_" + (row + i), false);
                pos[len * row + i] = 'Q';

                computeNQueenBackSearch(len, pos, row + 1, result);

                //回溯
                columnNotValid[i] = false;
                slantValid.remove("1_" + (row - i));
                slantValid.remove("-1_" + (row + i));
                pos[len * row + i] = '.';
            }
        }

    }

    public boolean[] columnNotValid;

    public Map<String, Boolean> slantValid;

    public boolean isPosValid(char[] pos, int row, int column)
    {
        return columnNotValid[column] == false && slantValid.get("1_" + (row - column)) == null && slantValid.get("-1_" + (row + column)) == null;
    }

    //采用位运算来进一步减少空间占用
    long sum = 0, upperlim = 1;

    //试探算法从最右边的列开始。
    void computeNQueenBit(long row, long ld, long rd)
    {
        if (row != upperlim)
        {
            // row，ld，rd进行“或”运算，求得所有可以放置皇后的列,对应位为0，
            // 然后再取反后“与”上全1的数，来求得当前所有可以放置皇后的位置，对应列改为1
            // 也就是求取当前哪些列可以放置皇后
            long pos = upperlim & ~(row | ld | rd);
            while (pos != 0)    // 0 -- 皇后没有地方可放，回溯
            {
                // 拷贝pos最右边为1的bit，其余bit置0
                // 也就是取得可以放皇后的最右边的列
                long p = pos & -pos;

                // 将pos最右边为1的bit清零
                // 也就是为获取下一次的最右可用列使用做准备，
                // 程序将来会回溯到这个位置继续试探
                pos -= p;

                // row + p，将当前列置1，表示记录这次皇后放置的列。
                // (ld + p) << 1，标记当前皇后左边相邻的列不允许下一个皇后放置。
                // (ld + p) >> 1，标记当前皇后右边相邻的列不允许下一个皇后放置。
                // 此处的移位操作实际上是记录对角线上的限制，只是因为问题都化归
                // 到一行网格上来解决，所以表示为列的限制就可以了。显然，随着移位
                // 在每次选择列之前进行，原来N×N网格中某个已放置的皇后针对其对角线
                // 上产生的限制都被记录下来了
                computeNQueenBit(row + p, (ld + p) << 1, (rd + p) >> 1);
            }
        }
        else
        {
            // row的所有位都为1，即找到了一个成功的布局，回溯
            sum++;
        }
    }


















    /**http://www.lintcode.com/zh-cn/problem/reverse-linked-list/
     * @param head: The head of linked list.
     * @return: The new head of reversed linked list.
     */
    public ListNode reverse(ListNode head) {
        ListNode temp = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = temp;
            temp = cur;
            cur = next;
        }
        return temp;
    }

    public ListNode reverseBetween(ListNode head, int m , int n) {
        if(m == n)
        {
            return head;
        }

        ListNode newHead = new ListNode(-1);
        newHead.next = head;
        ListNode left = null;
        ListNode right = null;
        ListNode temp = null;
        ListNode cur = newHead;
        ListNode next = null;
        int index = 0;
        while (cur != null) {
            if(index < m - 1)
            {
                cur = cur.next;
            }
            else if(index == m - 1)
            {
                left = cur;
                temp = null;
                cur = cur.next;
                right = cur;
                next = null;
                left.next = null;
            }
            else if(index >= m && index < n){
                next = cur.next;
                cur.next = temp;
                temp = cur;
                cur = next;
            }

            if(index == n || cur == null)
            {
                next = cur.next;
                cur.next = temp;
                temp = cur;
                left.next = temp;
                if(right != null)
                {
                    right.next = next;
                }
                cur = null;
            }
            index ++;
        }
        return newHead.next;
    }















    /**http://www.lintcode.com/zh-cn/problem/search-a-2d-matrix-ii/#
     * @param matrix: A list of lists of integers
     * @param: A number you want to search in the matrix
     * @return: An integer indicate the occurrence of target in the given matrix
     */
    public int searchMatrix1(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0)
        {
            return 0;
        }
        if (matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        return searchMatrix1(matrix, target, 0, 0, matrix[0].length - 1, matrix.length - 1);
    }


    public int searchMatrix1(int[][] matrix, int target, int startX, int startY, int endX, int endY)
    {
        int total = 0;
        for(int i = startX, j = startY; i <= endX && j <= endY;)
        {
            int cur = matrix[j][i];
            if(cur >= target)
            {
                if(cur == target)
                {
                    total ++;
                }
                total += searchMatrix1(matrix, target, i, startY, endX, j - 1);
                total += searchMatrix1(matrix, target, startX, j, i - 1, endY);
                break;
            }
            if(i == endX && j == endY)
            {
                break;
            }

            if(i != endX)
            {
                i ++;
            }
            if(j != endY)
            {
                j ++;
            }
        }

        return total;
    }

    public int matrixSearch(int[][] matrix, int target)
    {
        int row = matrix.length;
        int column = matrix[0].length;
        int r = row - 1;
        int c = 0;
        int count = 0;

        while (r >= 0 && c < column) {
            if (matrix[r][c] < target) {
                c++;
            } else if (matrix[r][c] > target) {
                r--;
            } else {
                count++;
                r--;
                c++;
            }
        }
        return count;
    }












    /**http://www.lintcode.com/zh-cn/problem/recover-rotated-sorted-array/
     * @param nums: The rotated sorted array
     * @return: void
     */
    public void recoverRotatedSortedArray(ArrayList<Integer> nums) {
        if(nums == null || nums.size() <= 1)
        {
            return;
        }
        int size = nums.size();

        int minIndex = 0;
        int index = 0;
        while (index < size - 1) {
            if(nums.get(index) > nums.get(index + 1))
            {
                minIndex = index + 1;
            }
            index++;
        }
        if(minIndex == 0)
        {
            return;
        }

        int offset = (size - minIndex) % size;


        if(offset != 0)
        {
            int tempOffset = 0;
            int totalSwape = 0;
            while (tempOffset < offset) {
                totalSwape += swapeIntWithOffset(nums, tempOffset, tempOffset, offset, nums.get(tempOffset));
                if(totalSwape == size)
                {
                    break;
                }
                tempOffset ++;
            }
        }
    }

    public int swapeIntWithOffset(ArrayList<Integer> nums, int startIndex, int curIndex, int offset, int lastVal)
    {
        int swapeNum = 0;
        int curVal = nums.get(curIndex);
        nums.set(curIndex, lastVal);

        int size = nums.size();
        if((curIndex + offset) % size == startIndex)
        {
            nums.set(startIndex, curVal);
            swapeNum ++;
        }
        else {
            swapeNum += 1 + swapeIntWithOffset(nums, startIndex, (curIndex + offset) % size, offset, curVal);
        }
        return swapeNum;
    }


















    /**http://www.lintcode.com/zh-cn/problem/maximum-subarray/
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(int[] nums) {
        if(nums == null)
        {
            return 0;
        }

        int total = 0;
        int max = nums.length > 0?nums[0]: 0;
        for(int i: nums)
        {
            total = Math.max(i + total, i);
            if(total > max)
            {
                max = total;
            }
        }
        return max;
    }














    /**http://www.lintcode.com/zh-cn/problem/maximum-subarray-ii/
     * @param nums: A list of integers
     * @return: An integer denotes the sum of max two non-overlapping subarrays
     */
    public int maxTwoSubArrays(ArrayList<Integer> nums) {
        int size = 0;
        if(nums == null || (size = nums.size()) < 2)
        {
            return 0;
        }

        int[] leftMaxs = new int[size];
        int[] rightMaxs = new int[size];
        int maxLeft = nums.get(0);
        int maxRight = nums.get(size - 1);
        int totalLeft = 0;
        int totalRight = 0;
        for (int i = 0; i < size; i++) {
            int left = nums.get(i);
            totalLeft = Math.max(left + totalLeft, left);
            maxLeft = Math.max(maxLeft, totalLeft);
            leftMaxs[i] = maxLeft;

            int right = nums.get(size - i - 1);
            totalRight = Math.max(right + totalRight, right);
            maxRight = Math.max(maxRight, totalRight);
            rightMaxs[i] = maxRight;
        }

        int max = leftMaxs[0] + rightMaxs[0];
        for(int i = 0; i < size - 1; i ++)
        {
            max = Math.max(max, leftMaxs[i] + rightMaxs[size - i - 2]);
        }

        return max;
    }














    /**http://www.lintcode.com/zh-cn/problem/maximum-subarray-iii/#
     * @param nums: A list of integers
     * @param k: An integer denote to find k non-overlapping subarrays
     * @return: An integer denote the sum of max k non-overlapping subarrays
     */
    public int maxSubArray(int[] nums, int k) {
        if(nums == null)
        {
            return 0;
        }

        int len = nums.length;
        if(len < k)
        {
            return 0;
        }

        int[] positiveInfo = findPositiveInfo(nums);

        if(positiveInfo[0] >= k)
        {
            if(positiveInfo[2] <= k)
            {
                //正数个数比k多，但正数区间比k少，则返回所有正数之和
                return positiveInfo[1];
            }
            else {
                ArrayList<Interval> freeIs = new ArrayList<Interval>();
                ArrayList<Interval> maxIs = new ArrayList<Interval>();
                freeIs.add(new Interval(0, nums.length - 1));
                maxSubArrSum(nums,  k, freeIs, maxIs);
                int total = 0;
                for (Interval interval : maxIs) {
                    total += maxOneSubArr(nums, interval.start, interval.end).max;
                }
                return total;
            }
        }
        else {
            //正数比k少，则找最大的(k - 正数数量)个非正数，返回这些数和所有正数的和
            int maxNegativeSum = 0;
            for(int i = 0; i < k - positiveInfo[0]; i ++)
            {
                maxNegativeSum += positiveInfo[3 + i];
            }
            return maxNegativeSum + positiveInfo[1];
        }

        //直接对数组进行k组分组枚举，并找到其中和最大的分组，当数组很长，k很大时，会超时
//    	int arrSum = sumOfArray(nums);
//    	if(k == len)
//    	{
//    		return arrSum;
//    	}
//    	else {
//			int[] max = new int[1];
//			max[0] = arrSum;
//			findMaxSubArray(nums, k, 0, 0, max);
//			return max[0];
//		}
    }

    public int[] findPositiveInfo(int[] nums)
    {
        int positiveNum = 0;
        int positiveSum = 0;
        int intervalNum = 0;
        int lastPosIndex = -1;
        int[] nagetives = new int[nums.length];
        for (int i = 0, j = 0, len = nums.length; i < len; i++) {
            int temp = nums[i];
            if(temp > 0)
            {
                positiveNum ++;
                positiveSum += temp;
                if(lastPosIndex == -1 || lastPosIndex + 1 != i)
                {
                    intervalNum ++;
                }
                lastPosIndex = i;
            }
            else {
                nagetives[j] = temp;
                j ++;
            }
        }
        int[] result = new int[3 + nums.length - positiveNum];
        result[0] = positiveNum;
        result[1] = positiveSum;
        result[2] = intervalNum;
        Arrays.sort(nagetives, 0, nums.length - positiveNum);
        for (int i = 0, max = nums.length - positiveNum - 1; i <= max; i++) {
            result[3 + i] = nagetives[max - i];
        }
        return result;
    }

    public void maxSubArrSum(int[] nums, int k, ArrayList<Interval> freeIs, ArrayList<Interval> maxIs)
    {
        int intervalType = 1;
        int maxDelta = Integer.MIN_VALUE;
        Interval maxInterval = null;

        for (Interval interval : freeIs) {
            MaxSubArray temp = maxOneSubArr(nums, interval.start, interval.end);
            if(temp.max > maxDelta)
            {
                maxDelta = temp.max;
                intervalType = 1;
                maxInterval = interval;
            }
        }
        for (Interval interval : maxIs) {
            MaxSubArray temp = maxTwoSubArr(nums, interval.start, interval.end);
            MaxSubArray temp0 = maxOneSubArr(nums, interval.start, interval.end);
            if(temp.max != Integer.MIN_VALUE && temp.max - temp0.max > maxDelta)
            {
                maxDelta = temp.max - temp0.max;
                intervalType = 2;
                maxInterval = interval;
            }
        }
        if(maxInterval != null)
        {
            MaxSubArray temp = null;
            if(intervalType == 1)
            {
                temp = maxOneSubArr(nums, maxInterval.start, maxInterval.end);
                freeIs.remove(maxInterval);
            }
            else {
                temp = maxTwoSubArr(nums, maxInterval.start, maxInterval.end);
                maxIs.remove(maxInterval);
            }
            freeIs.addAll(temp.free);
            maxIs.addAll(temp.maxInterval);
        }

        k --;
        if(k > 0)
        {
            maxSubArrSum(nums, k, freeIs, maxIs);
        }
    }

    public static class MaxSubArray {
        public ArrayList<Interval> maxInterval;
        public ArrayList<Interval> free;
        public int max;
        public MaxSubArray() {
            maxInterval = new ArrayList<Interval>();
            free = new ArrayList<Interval>();
            max = Integer.MIN_VALUE;
        }

        @Override
        public String toString() {
            return "max: " + max + "\n" +
                    "maxInterval:" + maxInterval.toString() + "\n" +
                    "free:" + free.toString();
        }
    }

    public Map<String, MaxSubArray> maxOneSubArrs = new HashMap<String, MaxSubArray>();

    public Map<String, MaxSubArray> maxTwoSubArrs = new HashMap<String, MaxSubArray>();

    public MaxSubArray maxOneSubArr(int[] nums, int left, int right)
    {
        MaxSubArray maxSubArray = maxOneSubArrs.get(left + "_" + right);
        if(maxSubArray == null)
        {
            maxSubArray = new MaxSubArray();

            int l = 0;
            int r = 0;
            int maxL = 0;
            int maxR = 0;
            int total = 0;
            int max = Integer.MIN_VALUE;
            for(int i = left; i <= right; i++)
            {
                int temp = nums[i];
                if(temp + total > temp)
                {
                    total += temp;
                    r = i;
                }
                else {
                    total = temp;
                    l = i;
                    r = i;
                }
                if(total > max)
                {
                    max = total;
                    maxL = l;
                    maxR = r;
                }
            }
            maxSubArray.max = max;
            maxSubArray.maxInterval.add(new Interval(maxL, maxR));
            if(maxL - left >= 2)
            {
                maxSubArray.free.add(new Interval(left, maxL - 1));
            }
            if(right - maxR >= 2)
            {
                maxSubArray.free.add(new Interval(maxR + 1, right));
            }
            maxOneSubArrs.put(left + "_" + right, maxSubArray);
        }

        return maxSubArray;
    }

    public MaxSubArray maxTwoSubArr(int[] nums, int left, int right)
    {
        MaxSubArray maxSubArray = maxTwoSubArrs.get(left + "_" + right);
        if(maxSubArray == null)
        {
            maxSubArray = new MaxSubArray();

            MaxSubArray one = null;
            MaxSubArray two = null;
            MaxSubArray oneM = null;
            MaxSubArray twoM = null;
            int max = Integer.MIN_VALUE;
            for(int i = left + 1; i <= right - 1; i++)
            {
                one = maxOneSubArr(nums, left, i);
                two = maxOneSubArr(nums, i + 1, right);
                if(one.maxInterval.size() > 0 && two.maxInterval.size() > 0
                        && (one.max + two.max > max))
                {
                    max = one.max + two.max;
                    oneM = one;
                    twoM = two;
                }
            }

            if(oneM != null && twoM != null)
            {
                maxSubArray.max = max;
                Interval oneInterval = oneM.maxInterval.get(0);
                Interval twoInterval = twoM.maxInterval.get(0);
                maxSubArray.maxInterval.add(oneInterval);
                maxSubArray.maxInterval.add(twoInterval);

                int oneL = oneInterval.start;
                int oneR = oneInterval.end;
                int twoL = twoInterval.start;
                int twoR = twoInterval.end;
                if(oneL - left >= 2)
                {
                    maxSubArray.free.add(new Interval(left, oneL - 1));
                }
                if(twoL - oneR >= 3)
                {
                    maxSubArray.free.add(new Interval(oneR + 1, twoL - 1));
                }
                if(right - twoR >= 2)
                {
                    maxSubArray.free.add(new Interval(twoR + 1, right));
                }
            }

            maxTwoSubArrs.put(left + "_" + right, maxSubArray);
        }

        return maxSubArray;
    }



















    /**http://www.lintcode.com/zh-cn/problem/minimum-subarray/
     * @param nums: a list of integers
     * @return: A integer indicate the sum of minimum subarray
     */
    public int minSubArray(ArrayList<Integer> nums) {
        if(nums == null || nums.size() == 0)
        {
            return 0;
        }

        int size = nums.size();
        int[] numsArr = new int[size];
        for (int i = 0; i < size; i++) {
            numsArr[i] = -nums.get(i);
        }
        return -maxSubArray(numsArr);
    }


















    /**http://www.lintcode.com/zh-cn/problem/maximum-subarray-difference/
     * @param nums: A list of integers
     * @return: An integer indicate the value of maximum difference between two
     *          Subarrays
     */
    public int maxDiffSubArrays(int[] nums) {
        if(nums == null || nums.length == 0)
        {
            return 0;
        }
        else if(nums.length == 1)
        {
            return nums[0];
        }

        int len = nums.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len - 1; i++) {
            int leftMax = maxSubArray(nums, 0, i);
            int rightMin = minSubArray(nums, i + 1, len - 1);
            int temp = Math.abs(leftMax - rightMin);
            if(temp > max)
            {
                max = temp;
            }

            int leftMin = minSubArray(nums, 0, i);
            int rightMax = maxSubArray(nums, i + 1, len - 1);
            temp = Math.abs(leftMin - rightMax);
            if(temp > max)
            {
                max = temp;
            }
        }

        return max;
    }

    public int maxSubArray(int[] nums, int left, int right)
    {
        int total = 0;
        int max = Integer.MIN_VALUE;
        for(int i = left; i <= right; i++)
        {
            int temp = nums[i];
            if(temp + total > temp)
            {
                total += temp;
            }
            else {
                total = temp;
            }
            if(total > max)
            {
                max = total;
            }
        }
        return max;
    }

    public int minSubArray(int[] nums, int left, int right)
    {
        int total = 0;
        int min = Integer.MAX_VALUE;
        for(int i = left; i <= right; i++)
        {
            int temp = nums[i];
            if(temp + total < temp)
            {
                total += temp;
            }
            else {
                total = temp;
            }
            if(total < min)
            {
                min = total;
            }
        }
        return min;
    }





















    /**http://www.lintcode.com/zh-cn/problem/majority-number/
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    public int majorityNumber(ArrayList<Integer> nums) {
        if(nums == null || nums.size() == 0)
        {
            return 0;
        }

        int size = nums.size();
        int x = 0;
        int cnt = 0;
        for(int i = 0; i < size; i++)
        {
            if(cnt==0)
            {
                x = nums.get(i);
                cnt = 1;
            }
            else {
                if(nums.get(i) == x)
                {
                    cnt++;
                }
                else {
                    cnt--;
                }
            }
        }

        return x;
    }

















    /**http://www.lintcode.com/zh-cn/problem/majority-number-ii/
     * @param nums: A list of integers
     * @return: The majority number that occurs more than 1/3
     */
    public int majorityNumberK(ArrayList<Integer> nums, int k) {
        int size = 0;
        if(nums == null || (size = nums.size()) == 0)
        {
            return 0;
        }

        HashMap<Integer, Integer> times = new HashMap<Integer, Integer>();

        for(int i = 0; i < size; i++)
        {
            int temp = nums.get(i);
            if(times.size() < k || times.containsKey(temp))
            {
                Integer time = times.get(temp);
                if(time == null)
                {
                    time = 0;
                }
                time ++;
                times.put(temp, time);
            }
            else {
                int minTime = Integer.MAX_VALUE;
                int minTimeNum = 0;
                for (Iterator<Map.Entry<Integer, Integer>> iterator = times.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry<Integer, Integer> keyVal = iterator.next();
                    if(keyVal.getValue() < minTime)
                    {
                        minTime = keyVal.getValue();
                        minTimeNum = keyVal.getKey();
                    }
                }
                times.remove(minTimeNum);
                times.put(temp, 1);
            }
        }

        int maxTime = Integer.MIN_VALUE;
        int maxTimeNum = 0;
        for (Iterator<Map.Entry<Integer, Integer>> iterator = times.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<Integer, Integer> keyVal = iterator.next();
            if(keyVal.getValue() > maxTime)
            {
                maxTime = keyVal.getValue();
                maxTimeNum = keyVal.getKey();
            }
        }
        return maxTimeNum;
    }






















    /**http://www.lintcode.com/zh-cn/problem/sort-letters-by-case/
     *@param chars: The letter array you should sort by Case
     *@return: void
     */
    public void sortLetters(char[] chars) {
        int len = 0;
        if(chars == null || (len = chars.length) <= 1)
        {
            return;
        }

        int i = 0;
        int j = len - 1;
        while (i < j) {
            while (i < j && chars[i] >= 'a') {
                i ++;
            }
            while (i < j && chars[j] <= 'Z') {
                j --;
            }
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
    }


















    /**http://www.lintcode.com/zh-cn/problem/product-of-array-exclude-itself/
     * @param A: Given an integers array A
     * @return: A Long array B and B[i]= A[0] * ... * A[i-1] * A[i+1] * ... * A[n-1]
     */
    public ArrayList<Long> productExcludeItself(ArrayList<Integer> A) {
        int size = 0;
        if(A == null || (size = A.size()) == 0)
        {
            return null;
        }
        ArrayList<Long> resultArr = new ArrayList<Long>();
        if(size == 1)
        {
            resultArr.add(1L);
            return resultArr;
        }

        int halfSize = size / 2;
        long[] arr0 = new long[size];
        long[] arr1 = new long[size];
        Long[] result = new Long[size];
        arr0[0] = A.get(0);
        arr1[size - 1] = A.get(size - 1);
        for(int i = 1; i < size - 1; i ++)
        {
            arr0[i] = A.get(i) * arr0[i - 1];
            arr1[size - 1 - i] = A.get(size - 1 - i) * arr1[size - i];
            if(i >= halfSize)
            {
                result[i] = arr0[i - 1] * arr1[i + 1];
                result[size - i - 1] = arr0[size - i - 2] * arr1[size - i];
            }
        }

        result[0] = arr1[1];
        result[size - 1] = arr0[size - 2];
        for (Long l : result) {
            resultArr.add(l);
        }
        return resultArr;
    }






















    /**http://www.lintcode.com/zh-cn/problem/previous-permutation/
     * @param nums: A list of integers
     * @return: A list of integers that's previous permuation
     */
    public ArrayList<Integer> previousPermuation(ArrayList<Integer> nums) {
        int size = 0;
        if(nums == null || (size = nums.size()) == 0)
        {
            return null;
        }
        else if(size == 1)
        {
            return nums;
        }

        for (int i = size - 2; i > -1; i--) {
            for (int j = size - 1; j > i; j--) {
                int temp = nums.get(i);
                if(temp > nums.get(j))
                {
                    nums.set(i, nums.get(j));
                    nums.set(j, temp);
                    quickSortReverse(nums, i + 1, size - 1);
                    return nums;
                }
            }
        }

        quickSortReverse(nums, 0, size - 1);
        return nums;
    }

    public void quickSortReverse(ArrayList<Integer> nums, int left, int right)
    {
        if(nums == null || left >= right)
        {
            return;
        }

        int i = left;
        int j = right;
        int key = nums.get(i);
        while (i < j) {
            while (i < j && nums.get(j) <= key) {
                j --;
            }
            nums.set(i, nums.get(j));

            while (i < j && nums.get(j) >= key) {
                i ++;
            }
            nums.set(j, nums.get(i));
        }
        nums.set(i, key);

        quickSortReverse(nums, left, i - 1);
        quickSortReverse(nums, i + 1, right);
    }
















    /**http://www.lintcode.com/zh-cn/problem/reverse-words-in-a-string/
     * @param s : A string
     * @return : A string
     */
    public String reverseWords(String s) {
        StringBuffer sb = new StringBuffer();
        int index = 0;
        for (int i = s.length() - 1; i > -1; i--) {
            char c = s.charAt(i);
            if(c == ' ')
            {
                if(sb.length() > 0)
                {
                    index = sb.length();
                    sb.insert(index, c);
                    index ++;
                }
            }
            else {
                sb.insert(index, c);
            }
        }
        return sb.toString();
    }















    /**http://www.lintcode.com/zh-cn/problem/string-to-integer-ii/
     * @param str: A string
     * @return An integer
     */
    public int atoi(String str) {
        str = str.split("\\.")[0];
        str = str.trim();
        long result = 0;
        long tenPow = 1;
        char c;
        int len = str.length();
        if(len == 0)
        {
            return 0;
        }

        int plusIndex = -1;
        int subIndex = -1;
        int firstNumIndex = -1;
        int lastNumIndex = len - 1;
        for(int i = 0; i < len; i ++)
        {
            c = str.charAt(i);
            if(c == '+' && firstNumIndex == -1 && subIndex == -1)
            {
                plusIndex = i;
            }
            else if(c == '-' && firstNumIndex == -1 && plusIndex == -1){
                subIndex = i;
            }
            else if(c >= '0' && c <= '9')
            {
                if(firstNumIndex == -1)
                {
                    firstNumIndex = i;
                }
            }
            else if((plusIndex == -1 || subIndex == -1) && firstNumIndex != -1){
                lastNumIndex = i - 1;
                break;
            }
            else {
                return 0;
            }
        }

        if(firstNumIndex == -1)
        {
            return 0;
        }

        for (int i = lastNumIndex; i >= firstNumIndex; i--) {
            c = str.charAt(i);
            if(c >= '0' && c <= '9')
            {
                result += tenPow * ((int)c - 48);
                if(result > Integer.MAX_VALUE)
                {
                    return str.charAt(0) == '-'?Integer.MIN_VALUE: Integer.MAX_VALUE;
                }
                tenPow *= 10;
            }
        }

        return str.charAt(0) == '-'?(int)(-result): (int)result;
    }




















    /**http://www.lintcode.com/zh-cn/problem/compare-strings/
     * @param A : A string includes Upper Case letters
     * @param B : A string includes Upper Case letter
     * @return :  if string A contains all of the characters in B return true else return false
     */
    public boolean compareStrings(String A, String B) {
        if(A == null)
        {
            return false;
        }
        else if(B == null || B.length() == 0)
        {
            return true;
        }

        int lenA = A.length();
        int lenB = B.length();
        if(lenA < lenB)
        {
            return false;
        }

        int count = 0;
        boolean[] found = new boolean[lenB];
        for (int i = 0; i < lenA; i++) {
            char c = A.charAt(i);
            for (int j = 0; j < lenB; j++) {
                if(!found[j] && c == B.charAt(j))
                {
                    count ++;
                    found[j] = true;
                    if(count == lenB)
                    {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }
















    /**http://www.lintcode.com/zh-cn/problem/two-sum/
     * @param nums : An array of Integer
     * @param target : target = numbers[index1] + numbers[index2]
     * @return : [index1 + 1, index2 + 1] (index1 < index2)
     */
    public int[] twoSum(int[] nums, int target) {
        int len = 0;
        if(nums == null || (len = nums.length) == 0)
        {
            int[] result = {0, 0};
            return result;
        }

        ValAndIndex[] valAndIndexs = new ValAndIndex[len];
        for (int i = 0; i < len; i ++) {
            valAndIndexs[i] = new ValAndIndex(nums[i], i + 1);
        }

        quickSort(valAndIndexs, 0, len - 1);
        int i = 0;
        int j = len - 1;
        while (i < j) {
            int sum = valAndIndexs[i].val + valAndIndexs[j].val;
            if(sum == target)
            {
                break;
            }
            else if(sum > target)
            {
                j --;
            }
            else
            {
                i ++;
            }
        }

        int index0 = valAndIndexs[i].index;
        int index1 = valAndIndexs[j].index;
        int[] result = new int[2];
        if(index0 < index1)
        {
            result[0] = index0;
            result[1] = index1;
        }
        else {
            result[0] = index1;
            result[1] = index0;
        }
        return result;
    }

    public static class ValAndIndex
    {
        public int val;
        public int index;
        public ValAndIndex(int val, int index) {
            this.val = val;
            this.index = index;
        }
    }

    public void quickSort(ValAndIndex[] a, int left, int right)
    {
        if(left >= right)
        {
            return ;
        }
        int i = left;
        int j = right;
        ValAndIndex key = a[left];
        while(i < j)
        {
            while(i < j && key.val <= a[j].val)
            {
                j--;
            }

            a[i] = a[j];

            while(i < j && key.val >= a[i].val)
            {
                i++;
            }

            a[j] = a[i];

        }

        a[i] = key;
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }



















    /**http://www.lintcode.com/zh-cn/problem/3sum/
     * @param nums : Give an array numbers of n integer
     * @return : Find all unique triplets in the array which gives the sum of zero.
     */
    public ArrayList<ArrayList<Integer>> threeSum(int[] nums) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        int len = 0;
        if(nums != null && (len = nums.length) >= 3)
        {
            quickSort(nums, 0, len - 1);

            for (int i = 0; i < len - 2 && nums[i] <= 0; i++) {
                for(int j = len - 1; j - i > 1 && nums[j] >= 0; j --)
                {
                    int leftVal = nums[i];
                    int rightVal = nums[j];
                    if(!checked.containsKey(leftVal + "_" + rightVal))
                    {
                        checked.put(leftVal + "_" + rightVal, true);
                        int k = -(leftVal + rightVal);
                        if(k > rightVal)
                        {
                            break;
                        }
                        else if(k >= leftVal)
                        {
                            int temp = binarySearch(nums, k, i + 1, j - 1);
                            if(temp != -1)
                            {
                                ArrayList<Integer> tempArr = new ArrayList<Integer>();
                                tempArr.add(nums[i]);
                                tempArr.add(nums[temp]);
                                tempArr.add(nums[j]);
                                result.add(tempArr);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public HashMap<String, Boolean> checked = new HashMap<String, Boolean>();




















    /**http://www.lintcode.com/zh-cn/problem/4sum/
     * @param nums : Give an array numbersbers of n integer
     * @param target : you need to find four elements that's sum of target
     * @return : Find all unique quadruplets in the array which gives the sum of
     *           zero.
     */
    public ArrayList<ArrayList<Integer>> fourSum(int[] nums, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        int len = 0;

        if(nums != null && (len = nums.length) >= 4)
        {
            quickSort(nums, 0, len - 1);

            HashMap<Integer, HashSet<TwoIndex>> sumOfTwo = new HashMap<Integer, HashSet<TwoIndex>>();
            for (int i = 2; i < len - 1; i++) {
                int c = nums[i];
                for (int j = i + 1; j < len; j++) {
                    int d = nums[j];
                    HashSet<TwoIndex> tempSet = sumOfTwo.get(c + d);
                    if(tempSet == null)
                    {
                        tempSet = new HashSet<TwoIndex>();
                        sumOfTwo.put(c + d, tempSet);
                    }
                    TwoIndex tempTwoIndex = new TwoIndex(c, i, d, j);
                    if (tempSet.contains(tempTwoIndex)) {
                        tempSet.remove(tempTwoIndex);
                    }
                    tempSet.add(tempTwoIndex);
                }
            }

            HashMap<String, Boolean> existed = new HashMap<String, Boolean>();
            for (int i = 0; i < len - 3; i++) {
                int a = nums[i];
                for (int j = i + 1; j < len - 2; j++) {
                    int b = nums[j];
                    HashSet<TwoIndex> tempSet = sumOfTwo.get(target - a - b);
                    if(tempSet != null)
                    {
                        for (TwoIndex twoIndex : tempSet) {
                            if(twoIndex.min >= b && twoIndex.minIndex > j && existed.get(a +"_" + b +"_" + twoIndex.min +"_" + twoIndex.max) == null)
                            {
                                ArrayList<Integer> solution = new ArrayList<Integer>();
                                solution.add(a);
                                solution.add(b);
                                solution.add(twoIndex.min);
                                solution.add(twoIndex.max);
                                result.add(solution);
                                existed.put(a +"_" + b +"_" + twoIndex.min +"_" + twoIndex.max, true);
                            }
                        }
                    }
                }
            }

        }
        return result;
    }

    public static class TwoIndex
    {
        public int min;

        public int minIndex;

        public int max;

        public int maxIndex;

        public TwoIndex(int min, int minIndex, int max, int maxIndex) {
            super();
            this.min = min;
            this.minIndex = minIndex;
            this.max = max;
            this.maxIndex = maxIndex;
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public String toString() {
            return min + "_" + max;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof TwoIndex))
            {
                return false;
            }

            TwoIndex temp = (TwoIndex) obj;
            return min == temp.min && max == temp.max;
        }

    }















    /**http://www.lintcode.com/zh-cn/problem/3sum-closest/
     * @param num: Give an array numbers of n integer
     * @param target : An integer
     * @return : return the sum of the three integers, the sum closest target.
     */
    public int threeSumClosest(int[] num, int target) {
        // Note: The Solution object is instantiated only once and is reused by
        // each test case.
        if (num == null || num.length < 3) {
            return Integer.MAX_VALUE;
        }
        Arrays.sort(num);
        int closet = Integer.MAX_VALUE / 2;
        for (int i = 0; i < num.length - 2; i++) {
            int left = i + 1;
            int right = num.length - 1;
            while (left < right) {
                int sum = num[i] + num[left] + num[right];
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
                closet = Math.abs(sum - target) < Math.abs(closet - target) ? sum : closet;
            }
        }
        return closet;
    }






















    /**http://www.lintcode.com/zh-cn/problem/search-insert-position/
     * param A : an integer sorted array
     * param target :  an integer to be inserted
     * return : an integer
     */
    public int searchInsert(int[] A, int target) {
        int len = 0;
        if(A == null || (len = A.length) == 0)
        {
            return 0;
        }

        return binarySearchAndInsert(A, target, 0, len - 1);
    }

    public int binarySearchAndInsert(int[] nums, int target, int l, int r)
    {
        if(l == r)
        {
            if(nums[l] >= target)
            {
                return l;
            }
            else {
                return l + 1;
            }
        }

        int mid = (l + r) / 2;
        if(nums[l] > target)
        {
            return l;
        }
        else if(nums[l] <= target && target < nums[mid])
        {
            return binarySearchAndInsert(nums, target, l, mid - 1);
        }
        else if(target == nums[mid])
        {
            return mid;
        }
        else if(nums[mid] < target && target <= nums[r]) {
            return binarySearchAndInsert(nums, target, mid + 1, r);
        }
        return r + 1;
    }























    /**http://www.lintcode.com/zh-cn/problem/search-for-a-range/
     *@param nums : an integer sorted array
     *@param target :  an integer to be inserted
     *return : a list of length 2, [index1, index2]
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        int len = 0;
        if(nums == null || (len = nums.length) == 0)
        {
            return result;
        }

        binarySearchRange(nums, target, 0, len - 1, result);
        return result;
    }

    public void binarySearchRange(int[] nums, int target, int l, int r, int[] result)
    {
        if(l >= r)
        {
            if(nums[l] == target)
            {
                if(l < result[0] || result[0] == -1)
                {
                    result[0] = l;
                }
                if(l > result[1])
                {
                    result[1] = l;
                }
            }
            return;
        }

        int mid = (l + r) / 2;
        if(nums[l] <= target && target < nums[mid])
        {
            binarySearchRange(nums, target, l, mid - 1, result);
        }
        else if(target == nums[mid])
        {
            if(result[0] == -1 || mid < result[0])
            {
                result[0] = mid;
            }
            if(result[1] == -1 || mid > result[1]){
                result[1] = mid;
            }
            binarySearchRange(nums, target, l, mid - 1, result);
            binarySearchRange(nums, target, mid + 1, r, result);
        }
        else if(nums[mid] < target && target <= nums[r])
        {
            binarySearchRange(nums, target, mid + 1, r, result);
        }
    }

















    /**http://www.lintcode.com/zh-cn/problem/search-in-rotated-sorted-array/
     *@param nums : an integer rotated sorted array
     *@param target :  an integer to be searched
     *return : an integer
     */
    public int search(int[] nums, int target) {
        int len = 0;
        if(nums == null || (len = nums.length) == 0)
        {
            return -1;
        }

        return binarySearchRotated(nums, target, 0, len - 1);
    }

    public int binarySearchRotated(int[] nums, int target, int left, int right)
    {
        if(left > right)
        {
            return - 1;
        }

        int mid = (left + right) / 2;
        if((nums[left] > nums[mid] && (nums[left] <= target || target < nums[mid]))
                || (nums[left] < nums[mid] && nums[left] <= target && target < nums[mid]))
        {
            return binarySearchRotated(nums, target, left, mid - 1);
        }
        else if(target == nums[mid]){
            return mid;
        }
        else {
            return binarySearchRotated(nums, target, mid + 1, right);
        }
    }
















    /**http://www.lintcode.com/zh-cn/problem/search-in-rotated-sorted-array-ii/
     * param A : an integer ratated sorted array and duplicates are allowed
     * param target :  an integer to be search
     * return : a boolean
     */
    public boolean searchDup(int[] nums, int target) {
        int len = 0;
        if(nums == null || (len = nums.length) == 0)
        {
            return false;
        }

        //在这种情况下，二分搜索和线性搜索的效率一样
        for (int i : nums) {
            if(i == target)
            {
                return true;
            }
        }
        return false;

//    	return binarySearchDupRotated(nums, target, 0, len - 1);
    }

    public boolean binarySearchDupRotated(int[] nums, int target, int left, int right)
    {
        if(left > right)
        {
            return false;
        }

        int mid = (left + right) / 2;
        if(nums[mid] == target)
        {
            return true;
        }
        else if(nums[mid] != nums[left] && nums[mid] == nums[right])
        {
            return binarySearchDupRotated(nums, target, left, mid - 1);
        }
        else if(nums[mid] != nums[right] && nums[mid] == nums[left])
        {
            return binarySearchDupRotated(nums, target, mid + 1, right);
        }
        else {
            return binarySearchDupRotated(nums, target, left, mid - 1) ||
                    binarySearchDupRotated(nums, target, mid + 1, right);
        }
    }



















    /**http://www.lintcode.com/zh-cn/problem/merge-sorted-array/
     * @param a: sorted integer array A which has m elements,
     *           but size of A is m+n
     * @param b: sorted integer array B which has n elements
     * @return: void
     */
    public void mergeSortedArray(int[] a, int m, int[] b, int n) {
        int i = m - 1;
        int j = n - 1;
        int index = m + n - 1;
        while(i > -1 && j > -1)
        {
            if(a[i] >= b[j])
            {
                a[index--] = a[i--];
            }
            else
            {
                a[index--] = b[j--];
            }
        }

        while(j > -1)
        {
            a[index--] = b[j--];
        }
    }




















    /**http://www.lintcode.com/zh-cn/problem/median-of-two-sorted-arrays/
     * @param A: An integer array.
     * @param B: An integer array.
     * @return: a double whose format is *.5 or *.0
     */
    public double findMedianSortedArrays(int A[], int B[]) {
        int len = A.length + B.length;
        if (len % 2 == 1) {
            return findKth(A, 0, B, 0, len / 2 + 1);
        }
        return (
                findKth(A, 0, B, 0, len / 2) + findKth(A, 0, B, 0, len / 2 + 1)
        ) / 2.0;
    }

    // find kth number of two sorted array
    public static int findKth(int[] A, int A_start,
                              int[] B, int B_start,
                              int k){
        if (A_start >= A.length) {
            return B[B_start + k - 1];
        }
        if (B_start >= B.length) {
            return A[A_start + k - 1];
        }

        if (k == 1) {
            return Math.min(A[A_start], B[B_start]);
        }

        int A_key = A_start + k / 2 - 1 < A.length
                ? A[A_start + k / 2 - 1]
                : Integer.MAX_VALUE;
        int B_key = B_start + k / 2 - 1 < B.length
                ? B[B_start + k / 2 - 1]
                : Integer.MAX_VALUE;

        if (A_key < B_key) {
            return findKth(A, A_start + k / 2, B, B_start, k - k / 2);
        } else {
            return findKth(A, A_start, B, B_start + k / 2, k - k / 2);
        }
    }








    public static void main(String[] args) {
        Solutions solutions = new Solutions();
        //利用位运算实现加法
//		int a = (int)(Math.random() * 100);
//		int b = (int)(Math.random() * 100);
//		System.out.println(a + " + " + b + " = " + solutions.aplusb(a, b));



        //计算n!的末尾有多少个0，例如5!末尾有1个0
//		System.out.println(5555550000000L + " = " + solutions.trailingZeros(5555550000000L));



        //0~n中k出现的次数，例如0~11,1出现了4次
//		System.out.println(solutions.digitCounts(0, 19));



        //找到第n个357丑数
//        System.out.println("solutions.kthPrimeNumber(1500) = " + solutions.kthPrimeNumber(1500));//4359476953125



        //利用快速排序找到第k大的数
//		final int[] TEST_NUMS = {3, 2, 5, 1, 4};
//		System.out.println(solutions.kthLargestElement(2, TEST_NUMS));



        //已排序数组的合并
//		int[] A = {1,2,3,4};
//		int[] B = {2,4,5,6};
//		System.out.println(Arrays.toString(solutions.mergeSortedArray(A, B)));



        //二叉树的序列化和反序列化
//		TreeNode root = new TreeNode(3);
//		root.left = new TreeNode(9);
//		root.right = new TreeNode(20);
//		root.right.left = new TreeNode(15);
//		root.right.right = new TreeNode(7);
//		String treeStr = solutions.serialize(root);
//		System.out.println("序列化结果：" + treeStr);
//		TreeNode newTreeRoot = solutions.deserialize(treeStr);
//		System.out.println("newTreeRoot.val = " + newTreeRoot.val);
//		System.out.println("newTreeRoot.right.right.val = " + newTreeRoot.right.right.val);



        //字符串偏移
//		String str = "timelimiterror";
//		char[] chars = str.toCharArray();
//		solutions.rotateString(chars, 1000000000);
//		System.out.println(chars);



        //二叉树搜索
//		TreeNode root = new TreeNode(3);
//		root.left = new TreeNode(9);
//		root.right = new TreeNode(20);
//		root.right.left = new TreeNode(15);
//		root.right.right = new TreeNode(7);
//		System.out.println(solutions.searchRange(root, 8, 16));



        //实现一个支持push，pop，min的最小栈
//		MinStack minStack = new MinStack();//应该输出1，2，1
//		minStack.push(1);
//		System.out.println(minStack.pop());
//		minStack.push(2);
//		minStack.push(3);
//		System.out.println(minStack.min());
//		minStack.push(1);
//		System.out.println(minStack.min());



        //字符串首次出现位置检索
//		final String source = "System.out.println(\"solutions.kthPrimeNumber(1500) = \" + solutions.kthPrimeNumber(1500));//4359476953125";
//		final String target = "solutions.kthPrimeNumber(1500)";
//		//采用KMP
//		System.out.println(solutions.strStr(source, target));
//		//普通思路
//		System.out.println(solutions.strStrNormal(source, target));



        //利用二分搜索在已排序的数组中找到第一个出现的k
//		int[] A = {1,4,4,5,7,7,8,9,9,10};
//		System.out.println(solutions.binarySearch(A, 1));



        //下一个字典排列
//		int[] A = {1,2,3,5,4};
//		solutions.nextDicSort(A);
//		System.out.println(Arrays.toString(A));



        //全排列
//		ArrayList<Integer> arr = new ArrayList<Integer>();
//		arr.add(1);
//		arr.add(2);
//		arr.add(2);
//		System.out.println(solutions.permute(arr));



        //给定一个含不同整数的集合，返回其所有的子集
//		int[] A = {1,2,3};
//		System.out.println(solutions.subsets(A));



        //给定一个可能具有重复数字的列表，返回其所有可能的子集
//		ArrayList<Integer> arr = new ArrayList<Integer>();
//		arr.add(1);
//		arr.add(2);
//		arr.add(2);
//		arr.add(2);
//		arr.add(3);
//		System.out.println(solutions.subsetsWithDup(arr));


        /**
         * 搜索 m × n矩阵中的值。
         * 这个矩阵具有以下特性：
         * 每行中的整数从左到右是排序的。
         * 每行的第一个数大于上一行的最后一个整数。
         */
//		int[][] matrix = {
//		        		  {1, 3, 5, 7},
//		        		  {10, 11, 16, 20},
//		        		  {23, 30, 34, 50}
//						};
//		System.out.println(solutions.searchMatrix(matrix, 14));



        //给出三个字符串:s1、s2、s3，判断s3是否由s1和s2交叉构成。
//		String s1 = "aba";
//		String s2 = "a";
//		String s3 = "aaba";
//		System.out.println(solutions.isInterleave(s1, s2, s3));



//		给出一个无重叠的按照区间起始端点排序的区间列表。
//		在列表中插入一个新的区间，你要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
//		ArrayList<Interval> intervals = new ArrayList<Interval>();
//		intervals.add(new Interval(1, 2));
//		intervals.add(new Interval(5, 9));
//		System.out.println(solutions.insert(intervals, new Interval(3, 4)));



        //划分数组
//		int[] arr = {5,2,2,2,1};
//		int k = 3;
//		System.out.println(solutions.partitionArray(arr, k));



        //给定一个字符串source和一个目标字符串target，在字符串source中找到包括所有目标字符串字母的最短子串。
//		String source = "abc";
//		String target = "ac";
//		System.out.println(solutions.minWindow(source, target));



        //皇后问题
        //TODO 利用位运算的那个算法还没有看懂，有待继续研究
//        System.out.println(solutions.solveNQueens(4).size());



        //链表翻转
//		ListNode cur = null;
//		for (int i = 10; i > 0; i--) {
//			ListNode temp = new ListNode(i);
//			temp.next = cur;
//			cur = temp;
//		}
//		System.out.println(cur);
////		System.out.println(solutions.reverse(cur));
//		System.out.println(solutions.reverseBetween(cur, 1, 10));



        //矩阵搜索2
//		int[][] matrix = {
//		                  {1, 3, 4, 7},
//		                  {2, 4, 7, 8},
//		                  {4, 5, 9, 10}
//		                 };
//		System.out.println(solutions.searchMatrix1(matrix, 4));



        //恢复被旋转过的ArrayList
//		int[] arr = {4, 5, 1, 2, 3};
//		ArrayList<Integer> arrayList = new ArrayList<Integer>();
//		for (int i : arr) {
//			arrayList.add(i);
//		}
//		solutions.recoverRotatedSortedArray(arrayList);
//		System.out.println(arrayList);



        //用两个占来实现Queen(先进先出)
//		Queue queue = new Queue();
//		queue.push(164);
//		System.out.println(queue.top());
//		System.out.println(queue.pop());
//		queue.push(161);
//		System.out.println(queue.top());
//		queue.push(162);
//		System.out.println(queue.top());
//		queue.push(163);
//		System.out.println(queue.top());
//		queue.push(171);
//		System.out.println(queue.pop());
//		System.out.println(queue.pop());
//		System.out.println(queue.top());



        //求元素之和最大的子数组的和
//		int[] arr = {-2, 2, -3, 4, -1, 2, 1, -5, 3};
//		System.out.println(solutions.maxSubArray(arr));



        //给定一个整数数组，找出两个不重叠子数组使得它们的和最大。
//		Integer[] arr = {1, 3, -1, 2, -1, 2};
//		ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(arr));
//		System.out.println(solutions.maxTwoSubArrays(arrayList));



        //给定一个整数数组，找出k个不重叠子数组使得它们的和最大。
//		int[] arr = {-42,81,-43,97,-82,20,-33,49,-62,2,-43,18,-54,52,-29,31,-70,87,-75,47,-22,42,-56,97,-100,54,-33,14,-89,34,-81,60,-66,75,-99,91,-93,70,-10,30,-26,72,-95,66,-41,23,-23,31,-14,78,-74,92,-20,25,-57,41,-72,58,-46,44,-52,53,-85,73,-37,96,-91,85,-77,62,-9,73,-64,63,-12,18,-61,24,-75,95,-54,89,-61,63,-19,24,-46,87,-87,69,-98,26,-92,26,-70,40,-63,20,-10,18,-64,26,-23,84,-35,65,-81,26,-55,92,-72,15,-99,18,-84,95,-50,77,-44,20,-20,94,-98,62,-67,17,-23,23,-75,33,-90,1,-1,86,-31,96,-80,100,-65,93,-51,48,-47,81,-63,100,-84,3,-15,59,-53,99,-67,12,-94,24,-98,74,-24,4,-34,79,-19,35,-54,36,-42,60,-68,18,-62,12,-50,44,-22,61,-21,27,-14,48,0,78,-39,70,-46,1,-86,77,-98,55,-93,81,-70,48,-3,0,-46,71,-50,11};
//		System.out.println(solutions.maxSubArray(arr, 47));



        //给定一个整数数组，找到一个具有最小和的子数组。返回其最小和。
//		Integer[] arr = {1, -1, -2, 1};
//		ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(arr));
//		System.out.println(solutions.minSubArray(arrayList));



        //给定一个整数数组，找出两个不重叠的子数组A和B，使两个子数组和的差的绝对值|SUM(A) - SUM(B)|最大。
//		int[] arr = {-5,-4};
//		System.out.println(solutions.maxDiffSubArrays(arr));




        //给定一个整型数组，找出主元素，它在数组中的出现次数严格大于数组元素个数的二分之一。
//		Integer[] nums = {1,1,1,1,2,2,2};
//		ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(nums));
//		System.out.println(solutions.majorityNumber(arrayList));



        //给定一个整型数组，找到主元素，它在数组中的出现次数严格大于数组元素个数的三分之一。
//		Integer[] nums = {1,1,2,2,2};
//		ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(nums));
//		System.out.println(solutions.majorityNumberK(arrayList, 2));



        //给定一个只包含字母的字符串，按照先小写字母后大写字母的顺序进行排序。
//		char[] chars = "aBcDeFg".toCharArray();
//		solutions.sortLetters(chars);
//		System.out.println(Arrays.toString(chars));



        //数组剔除元素后的乘积
//		Integer[] arr = {1,2,3,4,5};
//		System.out.println(solutions.productExcludeItself(new ArrayList<Integer>(Arrays.asList(arr))));



        //给定一个整数数组来表示排列，找出其上一个排列。
//		Integer[] arr = {3,1,2};
//		ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(arr));
//		System.out.println(solutions.previousPermuation(arrayList));



        //给定一个字符串，逐个翻转字符串中的每个单词。
//		String str = "the sky is blue";
//		System.out.println(solutions.reverseWords(str));



        //实现字符串转整数
//		String str = "15+4";
//		System.out.println(solutions.atoi(str));



        //比较字符串 A是否包含B中所有字符
//		String A = "ABCA";
//		String B = "ABAC";
//		System.out.println(solutions.compareStrings(A, B));



        //给一个整数数组，找到两个数使得他们的和等于一个给定的数target。
//		int[] nums = {1,0,-1};
//		System.out.println(Arrays.toString(solutions.twoSum(nums, -1)));



        //给出一个有n个整数的数组S，在S中找到三个整数a, b, c，找到所有使得a + b + c = 0的三元组
//		int[] nums = {1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99,1,2,5,6,7,3,5,8,-33,-5,-72,12,-34,100,99};
//		System.out.println(solutions.threeSum(nums));



        //给一个包含n个数的整数数组S，在S中找到所有使得和为给定整数target的四元组(a, b, c, d)。四元组(a, b, c, d)中，需要满足a <= b <= c <= d,答案中不可以包含重复的四元组。
//		int[] nums = {1,1,1,1};
//		System.out.println(solutions.fourSum(nums, 4));



        //给一个包含n个整数的数组S, 找到和与给定整数target最接近的三元组，返回这三个数的和。
//		int[] nums = {-1, 2, 1, -4};
//		System.out.println(solutions.threeSumClosest(nums, 1));



        //给定一个排序数组和一个目标值，如果在数组中找到目标值则返回索引。如果没有，返回到它将会被按顺序插入的位置。
//		int[] nums = {1,3,5,6,8,9};
//		System.out.println(solutions.searchInsert(nums, 7));



        //给定一个包含 n 个整数的排序数组，找出给定目标值 target 的起始和结束位置。
//		int[] nums = {5,5,5,5,5,5,5,5,5,5};
//		System.out.println(Arrays.toString(solutions.searchRange(nums, 5)));



        //假设有一个排序的按未知的旋转轴旋转的数组(比如，0 1 2 4 5 6 7 可能成为4 5 6 7 0 1 2)。给定一个目标值进行搜索，如果在数组中找到目标值返回数组中的索引位置，否则返回-1。
//		int[] nums = {1, 2, 3};
//		System.out.println(solutions.search(nums, 1));



        //跟进“搜索旋转排序数组”，假如有重复元素又将如何？
//		int[] nums = {3,2,1,1};
//		System.out.println(solutions.searchDup(nums, 1));



        //合并两个排序的整数数组A和B变成一个新的数组。
//		int[] a = {1, 5, 7, 9, 0, 0, 0, 0};
//		int[] b = {2, 4, 5, 8};
//		solutions.mergeSortedArray(a, 4, b, 4);
//		System.out.println(Arrays.toString(a));



        //两个排序的数组A和B分别含有m和n个数，找到两个排序数组的中位数，要求时间复杂度应为O(log (m+n))。
//        int[] a = {1,2,3,4,5,6};
//        int[] b = {2,3,4,5};
//        System.out.println(solutions.findMedianSortedArrays(a, b));




    }

    public static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    public static class MinStack {

        private List<Integer> stack;

        private Integer min;

        public MinStack() {
            stack = new ArrayList<Integer>();
        }

        public void push(int number) {
            if(min == null)
            {
                stack.add(number);
                min = number;
            }
            else if(number >= min){
                stack.add(number);
            }
            else {
                stack.add(2 * number - min);
                min = number;
            }
        }

        public int pop() {
            int index = stack.size() - 1;
            int pop = 0;
            if(index > -1)
            {
                pop = stack.get(index);
                stack.remove(index);
                if(pop < min)
                {
                    int top = pop;
                    pop = min;
                    min = 2 * min - top;
                }
                else if(pop == min && index == 0) {
                    min = null;
                }
            }
            return pop;
        }

        public int min() {
            return min;
        }

    }

    public static class MinStackNormal {

        private List<Integer> stack;

        private List<Integer> minStack;

        public MinStackNormal() {
            stack = new ArrayList<Integer>();
            minStack = new ArrayList<Integer>();
        }

        public void push(int number) {
            stack.add(number);
            int size = minStack.size();
            if(size == 0 || number <= minStack.get(size - 1))
            {
                minStack.add(number);
            }
        }

        public int pop() {
            int index = stack.size() - 1;
            int pop = 0;
            if(index > -1)
            {
                pop = stack.get(index);
                stack.remove(index);
                int size = minStack.size();
                if(pop == minStack.get(size - 1))
                {
                    minStack.remove(size - 1);
                }
            }
            return pop;
        }

        public int min() {
            return minStack.get(minStack.size() - 1);
        }

    }

    public static class Interval {
        int start;
        int end;
        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end + "]";
        }
    }


    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            ListNode cur = this;
            while (cur != null) {
                sb.append(cur.val);
                cur = cur.next;
                if(cur != null)
                {
                    sb.append(" -> ");
                }
            }
            return sb.toString();
        }
    }

    public static class Queue {
        private Stack<Integer> stack1;
        private Stack<Integer> stack2;
        private Integer top;

        public Queue() {
            stack1 = new Stack<Integer>();
            stack2 = new Stack<Integer>();
        }

        public void push(int element) {
            if(stack2.size() == 0)
            {
                if(stack1.size() == 0)
                {
                    top = element;
                }
            }
            else {
                int size = stack2.size();
                while (size > 0) {
                    stack1.push(stack2.pop());
                    size --;
                }
            }
            stack1.add(element);
        }

        public int pop() {
            if(stack2.size() == 0)
            {
                int size = stack1.size();
                top = null;
                while (size > 1) {
                    if(size == 2)
                    {
                        top = stack1.pop();
                        stack2.push(top);
                    }
                    else {
                        stack2.push(stack1.pop());
                    }
                    size --;
                }
                return stack1.pop();
            }
            else
            {
                int pop = stack2.pop();
                top = null;
                if(stack2.size() > 0)
                {
                    top = stack2.lastElement();
                }
                return pop;
            }
        }

        public int top() {
            return top == null?0: top;
        }
    }

}
