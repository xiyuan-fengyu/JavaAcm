package com.xiyuan.acm;

import com.xiyuan.acm.factory.ListNodeFactory;
import com.xiyuan.acm.factory.TreeNodeFactory;
import com.xiyuan.acm.model.*;
import com.xiyuan.acm.util.ArrayListUtil;
import com.xiyuan.acm.util.DataUtil;
import com.xiyuan.util.XYLog;

import java.util.*;

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






    /**
     * http://www.lintcode.com/zh-cn/problem/binary-tree-level-order-traversal-ii/
     * @param root: The root of binary tree.
     * @return: buttom-up level order a list of lists of integer
     */
    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
        visitTreeByLevelOrderBottom(root, 0, temp);

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for (int i = temp.size() - 1; i > -1; i--) {
            result.add(temp.remove(i));
        }
        return result;
    }

    public void visitTreeByLevelOrderBottom(TreeNode curNode, int curLevel, ArrayList<ArrayList<Integer>> temp) {
        if (curNode != null) {
            if (curLevel + 1 > temp.size()) {
                temp.add(new ArrayList<Integer>());
            }
            if (curLevel == 0) {
                ArrayList<Integer> curLevelList = temp.get(curLevel);
                curLevelList.add(curNode.val);
            }

            if (curNode.left != null || curNode.right != null) {
                if (curLevel + 2 > temp.size()) {
                    temp.add(new ArrayList<Integer>());
                }
                ArrayList<Integer> nextLevelList = temp.get(curLevel + 1);
                if (curNode.left != null) {
                    nextLevelList.add(curNode.left.val);
                }
                if (curNode.right != null) {
                    nextLevelList.add(curNode.right.val);
                }

                if (curNode.left != null) {
                    visitTreeByLevelOrderBottom(curNode.left, curLevel + 1, temp);
                }
                if (curNode.right != null) {
                    visitTreeByLevelOrderBottom(curNode.right, curLevel + 1, temp);
                }
            }
        }
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/binary-tree-zigzag-level-order-traversal/
     * @param root: The root of binary tree.
     * @return: A list of lists of integer include
     *          the zigzag level order traversal of its nodes' values
     */
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
        visitTreeByLevelOrderBottom(root, 0, temp);

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for (int i = 0, size = temp.size(); i < size; i++) {
            if (i % 2 == 0) {
                result.add(temp.get(i));
            }
            else {
                ArrayList<Integer> level = temp.get(i);
                ArrayList<Integer> reverse = new ArrayList<Integer>();
                for (int j = level.size() - 1; j > -1; j--) {
                    reverse.add(level.remove(j));
                }
                result.add(reverse);
            }
        }
        return result;
    }





    /**
     * 突破点：后序排列中最后一个是根节点，通过根节点可以将中序排列二分，同样要对后序排列做二分(注意中序和后序的分割方式不同)，从而对比获得子树非子根节点
     * http://www.lintcode.com/zh-cn/problem/construct-binary-tree-from-inorder-and-postorder-traversal/
     *@param inorder : A list of integers that inorder traversal of a tree
     *@param postorder : A list of integers that postorder traversal of a tree
     *@return : Root of a tree
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null
                || inorder.length == 0
                || postorder == null
                || postorder.length == 0
                || inorder.length != postorder.length) {
            return null;
        }
        else {
            return buildTree(inorder, 0, inorder.length - 1, postorder, 0, inorder.length - 1);
        }
    }

    private int findRootInorderIndex(int[] inorder, int rootVal, int left, int right) {
        for (int i = left; i <= right; i++) {
            if (inorder[i] == rootVal) {
                return i;
            }
        }
        return right;
    }

    private TreeNode buildTree(int[] inorder, int inLeft, int inRight, int[] postorder, int poLeft, int poRight) {
        if (inLeft <= inRight) {
            int rootVal = postorder[poRight];
            int rootInorderIndex = findRootInorderIndex(inorder, rootVal, inLeft, inRight);
            TreeNode tempRoot = new TreeNode(rootVal);
            if (inLeft < rootInorderIndex) {
                tempRoot.left = buildTree(inorder, inLeft, rootInorderIndex - 1, postorder, poLeft, poLeft + rootInorderIndex - 1 - inLeft);
            }
            if (inRight > rootInorderIndex) {
                tempRoot.right = buildTree(inorder, rootInorderIndex + 1, inRight, postorder, poRight - rootInorderIndex + inLeft, poRight - 1);
            }
            return tempRoot;
        }
        return null;
    }










    /**以前序为标准，最前面的为根节点，在中序中，根节点左边的构成左子树，根节点右边的构成右子树，由此二分
     * http://www.lintcode.com/zh-cn/problem/construct-binary-tree-from-preorder-and-inorder-traversal/
     *@param preorder : A list of integers that preorder traversal of a tree
     *@param inorder : A list of integers that inorder traversal of a tree
     *@return : Root of a tree
     */
    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        if (inorder == null
                || inorder.length == 0
                || preorder == null
                || preorder.length == 0
                || inorder.length != preorder.length) {
            return null;
        }
        else {
            return buildTreePreIn(inorder, 0, inorder.length - 1, preorder, 0, inorder.length - 1);
        }
    }

    private TreeNode buildTreePreIn(int[] inorder, int inLeft, int inRight, int[] preorder, int prLeft, int prRight) {
        if (inLeft <= inRight) {
            int rootVal = preorder[prLeft];
            int rootInorderIndex = findRootInorderIndex(inorder, rootVal, inLeft, inRight);
            TreeNode tempRoot = new TreeNode(rootVal);
            if (inLeft < rootInorderIndex) {
                tempRoot.left = buildTreePreIn(inorder, inLeft, rootInorderIndex - 1, preorder, prLeft + 1, prLeft + rootInorderIndex - inLeft);
            }
            if (inRight > rootInorderIndex) {
                tempRoot.right = buildTreePreIn(inorder, rootInorderIndex + 1, inRight, preorder, prRight + rootInorderIndex - inRight + 1, prRight);
            }
            return tempRoot;
        }
        return null;
    }








    /**二分法查找峰值
     * http://www.lintcode.com/zh-cn/problem/find-peak-element/
     * @param arr: An integers array.
     * @return: return any of peek positions.
     */
    public int findPeak(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        else {
            return findPeak(arr, 0, arr.length - 1);
        }
    }

    private int findPeak(int[] arr, int left, int right) {
        if (right - left < 2) {
            return -1;
        }

        int mIndex = (left + right) / 2;
        int mVal = arr[mIndex];

        int mlIndex = mIndex - 1;
        int mlVal = arr[mlIndex];

        int mrIndex = mIndex + 1;
        int mrVal = arr[mrIndex];

        if (mVal > mlVal && mVal > mrVal) {
            return mIndex;
        }
        else {
            int findInLeft = -1;
            if (mlVal > mVal) {
                findInLeft = findPeak(arr, left, mIndex);
            }
            else {
                findInLeft = findPeak(arr, left, mlIndex);
            }

            if (findInLeft != -1) {
                return findInLeft;
            }
            else {
                int findInRight = -1;
                if (mrVal > mVal) {
                    findInRight = findPeak(arr, mIndex, right);
                }
                else {
                    findInRight = findPeak(arr, mrIndex, right);
                }
                return findInRight;
            }
        }
    }










    /**查找最长升序列，序列不要求连续
     *思路：(f[n]代表以nums[n]结尾的最大升序列)
     * f[0] = 1
     * f[n] = 1 + max{nums[i] < nums[n] | f[n]} (即1 + 前n-1项中满足nums[i] < nums[n]的最大的f[n])
     * 而最终需要返回的是max{f[0], ..., f[n]},而不是f[n]
     * http://www.lintcode.com/zh-cn/problem/longest-increasing-subsequence/
     * @param nums: The integer array
     * @return: The length of LIS (longest increasing subsequence)
     */
    public int longestIncreasingSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxOfAll = 0;
        int len = nums.length;
        int[] f = new int[len];
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                f[i] = 1;
            }
            else {
                int max = 0;
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j] && f[j] > max) {
                        max = f[j];
                    }
                }

                f[i] = max + 1;
            }

            if (f[i] > maxOfAll) {
                maxOfAll = f[i];
            }
        }

        return maxOfAll;
    }

    /**
     * 采用二分算法对longestIncreasingSubsequence进行优化
     * 思路：
     * f[n]代表以nums[n]结尾的最长升序列的长度
     * m[n]代表长度为n的所有升序列中最小的结尾值
     * 例如：[10, 1, 11, 2, 12, 3, 11]
     * 步骤大概如下：
     * f[0] = 1
     * (m[1] == 0,添加新值)
     * m[1] = 10
     *
     * f[1] = 1
     * (nums[1]=1 比 m[1] = 10 小，更新值)
     * m[1] = 1 （1比10小，更新值）
     *
     * f[2] = (满足m[i] < nums[2]的最大i, 这里是1) + 1 = 2
     * m[2] = 11 (新添加值)
     *
     * f[3] = (满足m[i] < nums[3]的最大i, 这里是1) + 1 = 2
     * (nums[3]=2 比 m[2] = 11 小，更新值)
     *  m[2] = 2
     *
     * f[4] = (满足m[i] < nums[4]的最大i, 这里是2) + 1 = 3
     * (m[3] == 0,添加新值)
     * m[3] = nums[4] = 12
     *
     * 后面的步骤就不提了，二分主要用在(满足m[i] < nums[4]的最大i, 这里是2)这种步骤中, 当x < y时，m[y] < m[y]
     *
     * @param nums
     * @return
     */
    public int longestIncreasingSubsequenceWithBitch(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxOfAll = 0;
        int len = nums.length;
        int[] f = new int[len];
        int[] m = new int[len + 1];
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                f[i] = 1;
                m[1] = nums[i];
            }
            else {
                //通过二分法搜索m，来快速查找到满足m[j] < nums[i]的最大j,注意要用maxOfAll来限制右边界，因为右边界右边的全为0
                int temp = bitchSearchMaxIndex(m, nums[i], 1, maxOfAll) + 1;
                f[i] = temp;
                if (m[temp] == 0 || nums[i] < m[temp]) {
                    m[temp] = nums[i];
                }
            }

            if (f[i] > maxOfAll) {
                maxOfAll = f[i];
            }
        }

        return maxOfAll;
    }

    private int bitchSearchMaxIndex(int[] m, int numsI, int left, int right) {
        if (left <= right) {
            int mIndex = (left + right) / 2;
            if (m[mIndex] < numsI) {
                int temp = bitchSearchMaxIndex(m, numsI, mIndex + 1, right);
                if (temp == 0) {
                    return mIndex;
                }
                else {
                    return temp;
                }
            }
            else {
                return bitchSearchMaxIndex(m, numsI, left, mIndex - 1);
            }
        }

        return 0;
    }















    /**
     * http://www.lintcode.com/zh-cn/problem/longest-common-subsequence/
     * @param strA, strB: Two strings.
     * @return: The length of longest common subsequence of A and B.
     */
    public int longestCommonSubsequence(String strA, String strB) {
        if (strA == null || strA.isEmpty()
                || strB == null || strB.isEmpty()) {
            return 0;
        }

        int lenA = strA.length();
        int lenB = strB.length();
        int[] f = new int[lenA * lenB];
        Arrays.fill(f, -1);
        return f(f, strA, strB, lenA - 1, lenB - 1);
    }

    private int f(int[] f, String strA, String strB, int i, int j) {
        if (i < 0 || j < 0) {
            return 0;
        }

        int lenA = strA.length();
        int fIJ = f[i * lenA + j];
        if (fIJ != -1) {
            return fIJ;
        }

        int value = 0;
        if (strA.charAt(i) == strB.charAt(j)) {
            value = f(f, strA, strB, i - 1, j - 1) + 1;
        }
        else {
            int temp0 = f(f, strA, strB, i, j - 1);
            int temp1 = f(f, strA, strB, i - 1, j);
            value = Math.max(temp0, temp1);
        }

        f[i * lenA + j] = value;
        return value;
    }













    /**
     * http://www.lintcode.com/zh-cn/problem/longest-common-prefix/
     * @param strs: A list of strings
     * @return: The longest common prefix
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        else if (strs.length == 1) {
            return strs[0];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; ; i++) {
            boolean flag = true;

            char c = '\0';
            for (String str: strs) {
                if (str == null || str.length() <= i) {
                    flag = false;
                    break;
                }
                else if (c == '\0') {
                    c = str.charAt(i);
                }
                else if (c != str.charAt(i)) {
                    flag = false;
                    break;
                }
            }

            if (!flag) {
                break;
            }
            else {
                sb.append(c);
            }
        }

        return sb.toString();
    }













    /**
     * http://www.lintcode.com/zh-cn/problem/longest-common-substring/
     * @param strA, strB: Two string.
     * @return: the length of the longest common substring.
     */
    public int longestCommonSubstring(String strA, String strB) {
        if (strA == null || strA.isEmpty()
                || strB == null || strB.isEmpty()) {
            return 0;
        }

        int aLen = strA.length();
        int bLen = strB.length();

        int max = 0;

        int[] f = new int[aLen];
        for (int j = 0; j < bLen; j++) {
            int[] temp = Arrays.copyOf(f, aLen);
            for (int i = 0; i < aLen; i++) {
                if (strA.charAt(i) == strB.charAt(j)) {
                    if (i == 0) {
                        f[i] = 1;
                    }
                    else {
                        f[i] = temp[i - 1] + 1;
                    }

                    if (f[i] > max) {
                        max = f[i];
                    }
                }
                else {
                    f[i] = 0;
                }
            }
        }

        return max;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/median/
     * @param nums: A list of integers.
     * @return: An integer denotes the middle number of the array.
     */
    public int median(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return mediaByQuickSort(nums, 0, nums.length - 1);
    }

    private int mediaByQuickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return arr[right];
        }

        int key = arr[left];
        int i = left;
        int j = right;

        while (i < j) {
            while (i < j && key <= arr[j]) {
                j --;
            }
            arr[i] = arr[j];

            while (i < j && key >= arr[i]) {
                i ++;
            }
            arr[j] = arr[i];
        }
        arr[i] = key;

        int mIndex = (arr.length - 1) / 2;
        if (i == mIndex) {
            return key;
        }
        else if (i < mIndex) {
            return mediaByQuickSort(arr, i + 1, right);
        }
        else {
            return mediaByQuickSort(arr, left, i - 1);
        }
    }












    /**
     * http://www.lintcode.com/zh-cn/problem/data-stream-median/
     * @param nums: A list of integers.
     * @return: the median of numbers
     */
    public int[] medianII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        SortedHeap<Integer> minHeap = new SortedHeap<Integer>(new Comparator<Integer>() {
            @Override
            boolean lt(Integer t1, Integer t2) {
                return t1 < t2;
            }
        });

        SortedHeap<Integer> maxHeap = new SortedHeap<Integer>(new Comparator<Integer>() {
            @Override
            boolean lt(Integer t1, Integer t2) {
                return t1 > t2;
            }
        });

        int len = nums.length;
        int[] ms = new int[len];
        for (int i = 0; i < len; i++) {
            int cur = nums[i];
            int maxCount = maxHeap.count();
            int minCount = minHeap.count();
            if (maxCount == 0) {
                maxHeap.push(cur);
            }
            else if (maxCount <= minCount) {
                if (minHeap.root() >= cur) {
                    maxHeap.push(cur);
                }
                else {
                    maxHeap.push(minHeap.pop());
                    minHeap.push(cur);
                }
            }
            else {
                if (maxHeap.root() >= cur) {
                    minHeap.push(maxHeap.pop());
                    maxHeap.push(cur);
                }
                else {
                    minHeap.push(cur);
                }
            }
            ms[i] = maxHeap.root();
        }

        return ms;
    }

    class SortedHeap<T> {

        private ArrayList<T> nodes;

        private Comparator<T> comparator;

        public SortedHeap(Comparator<T> comparator) {
            this.nodes = new ArrayList<T>();
            this.comparator = comparator;
        }

        public int count() {
            return nodes.size();
        }

        public T push(T t) {
            nodes.add(t);
            checkParent(count() - 1);
            return root();
        }

        private void checkParent(int index) {
            int parentIndex = getParentIndex(index);
            if (parentIndex != -1) {
                T cur = nodes.get(index);
                T parent = nodes.get(parentIndex);
                if (comparator.lt(cur, parent)) {
                    nodes.set(index, parent);
                    nodes.set(parentIndex, cur);

                    checkChildren(parentIndex);
                    checkParent(parentIndex);
                }
            }
        }

        private void checkChildren(int index) {
            T cur = nodes.get(index);
            int leftIndex = getLeftChildIndex(index);
            if (leftIndex != -1) {
                T left = nodes.get(leftIndex);
                T right = null;
                int rightIndex = getRightChildIndex(index);
                if (rightIndex == -1 || comparator.lt(left, nodes.get(rightIndex))) {
                    if (comparator.lt(left, cur)) {
                        nodes.set(index, left);
                        nodes.set(leftIndex, cur);

                        checkChildren(leftIndex);
                    }
                }
                else {
                    right = nodes.get(rightIndex);
                    if (comparator.lt(right, cur)) {
                        nodes.set(index, right);
                        nodes.set(rightIndex, cur);

                        checkChildren(rightIndex);
                    }
                }
            }
        }

        public T pop() {
            if (isEmpty()) {
                return null;
            }
            else if (count() == 1) {
                return nodes.remove(0);
            }

            int lastIndex = count() - 1;
            T root = nodes.get(0);
            T last = nodes.get(lastIndex);
            nodes.set(0, last);
            nodes.remove(lastIndex);
            if (lastIndex - 1 >= 0) {
                checkChildren(0);
            }
            return root;
        }

        public T root() {
            if (isEmpty()) {
                return null;
            }
            return nodes.get(0);
        }

        public boolean isEmpty() {
            return nodes.isEmpty();
        }

        private int getParentIndex(int index) {
            if (index == 0) {
                return -1;
            }
            return (index - 1) / 2;
        }

        private int getLeftChildIndex(int index) {
            int temp = index * 2 + 1;
            if (temp >= count()) {
                return -1;
            }
            return temp;
        }

        private int getRightChildIndex(int index) {
            int temp = index * 2 + 2;
            if (temp >= count()) {
                return -1;
            }
            return temp;
        }

    }

    abstract class Comparator<T> {
        abstract boolean lt(T t1, T t2);
    }








    /**
     * http://www.lintcode.com/problem/single-number
     *@param arr : an integer array
     *return : a integer
     */
    public int singleNumber(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int result = 0;
        for (int i: arr) {
            result ^= i;
        }
        return result;
    }










    /**
     * http://www.lintcode.com/zh-cn/problem/single-number-ii/
     * @param arr : An integer array
     * @return : An integer
     */
    public int singleNumberII(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int[] pArr = new int[32];
        int[] nArr = new int[32];
        for (int i = 0, lastI = arr.length - 1; i <= lastI; i++) {
            int a = arr[i];
            boolean b = a >= 0;
            a = a >= 0? a: -a;
            int temp = 1;
            int index = 0;
            while (temp <= a) {
                int t = temp;
                temp <<= 1;

                if (temp == 0) {
                    break;
                }
                else {
                    int mod = a % temp;
                    a -= mod;
                    int indexVal = mod / t;
                    if (b) {
                        pArr[index++] += indexVal;
                    }
                    else {
                        nArr[index++] += indexVal;
                    }
                }

            }
        }

        int single = 0;
        for (int i = 0, temp = 1; i < 32; i++) {
            if (pArr[i] % 3 != 0) {
                single += temp;
            }
            else if (nArr[i] % 3 != 0) {
                single -= temp;
            }
            temp <<= 1;
        }
        return single;
    }













    /**
     * http://www.lintcode.com/zh-cn/problem/single-number-iii/
     * @param arr : An integer array
     * @return : Two integers
     */
    public List<Integer> singleNumberIII(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }

        ArrayList<Integer> result = new ArrayList<Integer>();

        int[] pArr = new int[32];
        int[] nArr = new int[32];
        for (int a: arr) {
            if (a == 0) {
                continue;
            }
            int[] tempArr = a >= 0?pArr: nArr;
            int aAbs = a >= 0?a: -a;
            int index = 0;
            while (aAbs > 0) {
                tempArr[index] += aAbs & 1;
                aAbs >>= 1;
                index += 1;
            }
        }

        //找到一个单数位
        boolean isPositive = true;
        int singleIndex = 33;
        for (int i = 0, len = pArr.length; i < len; i++) {
            if (pArr[i] % 2 == 1) {
                singleIndex = i;
                break;
            }
        }

        if (singleIndex == 33) {
            for (int i = 0, len = nArr.length; i < len; i++) {
                if (nArr[i] % 2 == 1) {
                    singleIndex = i;
                    isPositive = false;
                    break;
                }
            }
        }

        for (int a: arr) {
            if (a == 0) {
                continue;
            }

            int aAbs = a >= 0?a: -a;
            if (isPositive && a > 0 && (aAbs >> singleIndex & 1) == 0) {
                int index = 0;
                while (aAbs > 0) {
                    pArr[index] -= aAbs & 1;
                    aAbs >>= 1;
                    index += 1;
                }
            }
            else if (!isPositive && a < 0 && (aAbs >> singleIndex & 1) == 0) {
                int index = 0;
                while (aAbs > 0) {
                    nArr[index] -= aAbs & 1;
                    aAbs >>= 1;
                    index += 1;
                }
            }
        }

        int single = 0;
        for (int i = 0, len = pArr.length, temp = 1; i < len; i++) {
            if (pArr[i] % 2 == 1) {
                single += temp;
            }
            temp <<= 1;
        }

        if (single == 0) {
            for (int i = 0, len = nArr.length, temp = 1; i < len; i++) {
                if (nArr[i] % 2 == 1) {
                    single -= temp;
                }
                temp <<= 1;
            }
        }
        result.add(single);

        int anotherSingle = 0;
        for (int a: arr) {
            if (a != single) {
                anotherSingle ^= a;
            }
        }
        result.add(anotherSingle);

        return result;
    }





















    /**
     * http://www.lintcode.com/zh-cn/problem/insert-node-in-a-binary-search-tree/
     * @param root: The root of the binary search tree.
     * @param node: insert this node into the binary search tree
     * @return: The root of the new binary search tree.
     */
    public TreeNode insertNode(TreeNode root, TreeNode node) {
        if (node != null) {
            if (root == null) {
                return node;
            }

            if (node.val < root.val) {
                if (root.left == null) {
                    root.left = node;
                }
                else {
                    insertNode(root.left, node);
                }
            }
            else {
                if (root.right == null) {
                    root.right = node;
                }
                else {
                    insertNode(root.right, node);
                }
            }
        }
        return root;
    }

















    /**
     * http://www.lintcode.com/zh-cn/problem/dices-sum/
     * @param n an integer
     * @return a list of Map.Entry<sum, probability>
     */
    public List<Map.Entry<Integer, Double>> dicesSum(int n) {
        List<Map.Entry<Integer, Double>> result = new ArrayList<Map.Entry<Integer, Double>>();

        //从要求的问题开始动态规划求解,运行时间7026
//        HashMap<String, Double> map = new HashMap<String, Double>();
//        for (int i = n, max = n * 6; i <= max; i++) {
//            result.add(new AbstractMap.SimpleEntry<Integer, Double>(i, dicesSum(n, i, map)));
//        }


        //采用正向计算，但是用map来存储中间结果，比直接用数组节省空间，且更快,运行时间6943
        HashMap<String, Double> map = new HashMap<String, Double>();
        for (int i = 1; i <= 6; i++) {
            map.put(1 + " " + i, 1 / 6.0);
        }

        for (int i = 2; i <= n; i++) {
            for (int j = i, max = i * 6; j <= max; j++) {
                String key = i + " " + j;
                map.put(key, 0.0);
                for (int k = 1; k <= 6; k++) {
                    if (j > k) {
                        String otherKey = (i - 1) + " " + (j - k);
                        if (map.containsKey(otherKey)) {
                            map.put(key, map.get(key) + map.get(otherKey));
                        }
                    }
                }
                map.put(key, map.get(key) / 6.0);
            }
        }

        for (int i = n, max = n * 6; i <= max; i++) {
            result.add(new AbstractMap.SimpleEntry<Integer, Double>(i, map.get(n + " " + i)));
        }

        //由于动态规划的中间结果全部都会使用到，所以由子问题推向最终问题会好一点，避免使用递归,但是占用空间更多，当n比较大的时候，开辟大数组的时间消耗会使得算法效率打折扣，运行时间8060
//         double[][] f = new double[n + 1][6 * n + 1];
//         for (int i = 1; i <= 6; i++) {
//             f[1][i] = 1 / 6.0;
//         }
//
//         for (int i = 2; i <= n; i++) {
//             for (int j = i, max = i * 6; j <= max; j++) {
//                 for (int k = 1; k <= 6; k++) {
//                     if (j > k) {
//                         f[i][j] += f[i - 1][j - k];
//                     }
//                 }
//                 f[i][j] *= 1 / 6.0;
//             }
//         }
//
//         for (int i = n, max = n * 6; i <= max; i++) {
//             result.add(new AbstractMap.SimpleEntry<Integer, Double>(i, f[n][i]));
//         }

        return result;
    }


    private double dicesSum(int n, int s, HashMap<String, Double> map) {
        if (s < n || s > 6 * n || n <= 0) {
            return 0;
        }
        else if (n == 1) {
            map.put(n + "," + s, 1 / 6.0);
            return 1 / 6.0;
        }
        else {
            String key = n + "," + s;
            if (map.containsKey(key)) {
                return map.get(key);
            }
            else {
                double temp = 0;
                for (int i = 1; i <= 6; i++) {
                    temp += dicesSum(1,i, map) * dicesSum(n - 1, s - i, map);
                }
                map.put(key, temp);
                return temp;
            }
        }
    }














    /**
     * http://www.lintcode.com/zh-cn/problem/binary-search-tree-iterator/
     * @param root
     */
    public List<Integer> listBinarySearchTree(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        BSTIterator it = new BSTIterator(root);
        while (it.hasNext()) {
            list.add(it.next().val);
        }
        return list;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/remove-node-in-binary-search-tree/
     * @param root: The root of the binary search tree.
     * @param value: Remove the node with given value.
     * @return: The root of the binary search tree after removal.
     */
    public TreeNode removeNode(TreeNode root, int value) {
        if (root != null) {
            TreeNode newRoot = new TreeNode(0);
            newRoot.left = root;

            TreeNode parent = newRoot;
            TreeNode cur = root;
            while (cur != null && cur.val != value) {
                parent = cur;
                if (value < cur.val) {
                    cur = cur.left;
                }
                else {
                    cur = cur.right;
                }
            }

            if (cur != null) {
                if (cur.right == null) {
                    parent.left = cur.left;
                }
                else {
                    TreeNode p = cur;
                    TreeNode c = cur.right;
                    while (c.left != null) {
                        p = c;
                        c = c.left;
                    }
                    if (parent.left == cur) {
                        parent.left = c;
                    }
                    else {
                        parent.right = c;
                    }
                    if (cur.right != c) {
                        p.left = c.right;
                        c.right = cur.right;
                    }
                    else {
                        p.left = null;
                    }
                    c.left = cur.left;
                }
            }

            return newRoot.left;
        }
        return null;
    }











    /**
     * http://www.lintcode.com/zh-cn/problem/lowest-common-ancestor/
     * @param root: The root of the binary search tree.
     * @param nodeA and nodeB: two nodes in a Binary.
     * @return: Return the least common ancestor(LCA) of the two nodes.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode nodeA, TreeNode nodeB) {
        if (root == null || nodeA == null || nodeB == null) {
            return null;
        }

        Stack<TreeNode> nodeAFinder = new Stack<TreeNode>();
        Stack<TreeNode> nodeBFinder = new Stack<TreeNode>();
        findNodes(root, nodeA, nodeB, nodeAFinder, nodeBFinder);

        int lcaIndex = -1;
        for (int i = 0, lenA = nodeAFinder.size(), lenB = nodeBFinder.size(); i < lenA && i < lenB; i++) {
            if (nodeAFinder.get(i) == nodeBFinder.get(i)) {
                lcaIndex = i;
            }
            else {
                break;
            }
        }

        if (lcaIndex != -1) {
            return nodeAFinder.get(lcaIndex);
        }
        return null;
    }

    private void findNodes(TreeNode cur, TreeNode nodeA, TreeNode nodeB, Stack<TreeNode> nodeAFinder, Stack<TreeNode> nodeBFinder) {
        int needMoreSearch = 0;

        if (nodeAFinder.empty() || nodeAFinder.peek() != nodeA) {
            nodeAFinder.push(cur);
            needMoreSearch += 1;
        }

        if (nodeBFinder.empty() || nodeBFinder.peek() != nodeB) {
            nodeBFinder.push(cur);
            needMoreSearch += 1;
        }

        if (needMoreSearch > 0) {
            if (cur.left != null) {
                findNodes(cur.left, nodeA, nodeB, nodeAFinder, nodeBFinder);
            }
            if (cur.right != null) {
                findNodes(cur.right, nodeA, nodeB, nodeAFinder, nodeBFinder);
            }
        }

        if (!nodeAFinder.empty() && nodeAFinder.peek() != nodeA) {
            nodeAFinder.pop();
        }

        if (!nodeBFinder.empty() && nodeBFinder.peek() != nodeB) {
            nodeBFinder.pop();
        }
    }













    /**
     * http://www.lintcode.com/zh-cn/problem/k-sum/
     * @param arr: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return an integer
     */
    public int kSum(int[] arr, int k, int target) {
        if (arr != null && arr.length > 0 && arr.length >= k) {
            int len = arr.length;
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (int i = 0; i < len; i++) {
                for (int j = i; j < len; j++) {
                    map.put(j + "," + 1 + "," + arr[i], 1);
                }
            }

            int result = ikSum(arr, len - 1, k, target, map);
            return result;
        }
        return 0;
    }

    private int ikSum(int[] arr, int i, int k, int sum, HashMap<String, Integer> map) {
        String key = i + "," + k + "," + sum;
        if (map.containsKey(key)) {
            return map.get(key);
        }
        else if (k == 1 || i < 0) {
            return  0;
        }

        int num = ikSum(arr, i - 1, k - 1, sum - arr[i], map) + ikSum(arr, i - 1, k, sum, map);
        map.put(key, num);
        return num;
    }










    /**
     * http://www.lintcode.com/zh-cn/problem/k-sum-ii/
     * @param arr: an integer array.
     * @param k: a positive integer (k <= length(A))
     * @param target: a integer
     * @return a list of lists of integer
     */
    public ArrayList<ArrayList<Integer>> kSumII(int[] arr, int k, int target) {
        if (arr != null && arr.length > 0 && arr.length >= k) {
            int len = arr.length;
            HashMap<String, ArrayList<ArrayList<Integer>>> map = new HashMap<String, ArrayList<ArrayList<Integer>>>();
            for (int i = 0; i < len; i++) {
                ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
                ArrayList<Integer> item = new ArrayList<Integer>();
                item.add(arr[i]);
                list.add(item);
                for (int j = i; j < len; j++) {
                    map.put(j + "," + 1 + "," + arr[i], list);
                }
            }

            return ikSumII(arr, len - 1, k, target, map);
        }
        return new ArrayList<ArrayList<Integer>>();
    }

    private ArrayList<ArrayList<Integer>> ikSumII(int[] arr, int i, int k, int sum, HashMap<String, ArrayList<ArrayList<Integer>>> map) {
        ArrayList<ArrayList<Integer>> result = null;
        String key = i + "," + k + "," + sum;
        if (map.containsKey(key)) {
            return map.get(key);
        }
        else if (k == 1 || i < 0) {
            return  null;
        }

        ArrayList<ArrayList<Integer>> result0 = ikSumII(arr, i - 1, k - 1, sum - arr[i], map);
        ArrayList<ArrayList<Integer>> result1 = ikSumII(arr, i - 1, k, sum, map);
        if (result0 != null || result1 != null) {
            result = new ArrayList<ArrayList<Integer>>();

            if (result0 != null) {
                for (ArrayList<Integer> item: result0) {
                    ArrayList<Integer> copyItem = new ArrayList<Integer>();
                    copyItem.addAll(item);
                    copyItem.add(arr[i]);
                    result.add(copyItem);
                }
            }

            if (result1 != null) {
                for (ArrayList<Integer> item: result1) {
                    ArrayList<Integer> copyItem = new ArrayList<Integer>();
                    copyItem.addAll(item);
                    result.add(copyItem);
                }
            }

            map.put(key, result);
        }
        return result;
    }













    /**
     * http://www.lintcode.com/zh-cn/problem/minimum-adjustment-cost/
     * @param list: An integer array.
     * @param target: An integer.
     */
    public int MinAdjustmentCost(ArrayList<Integer> list, int target) {
        int adjustNum = Integer.MAX_VALUE;
        if (list != null && list.size() > 1) {
            int size = list.size();
            //f[i][j] 表示将第i个数调整为j的最小代价
            int[][] f = new int[size][101];
            int list0 = list.get(0);
            for (int i = 0; i < 101; i++) {
                f[0][i] = Math.abs(i - list0);
            }

            for (int i = 1; i < size; i++) {
                for (int j = 0;j < 101; j++) {
                    f[i][j] = Integer.MAX_VALUE;
                    //list(i)调整为j的代价
                    int delta = Math.abs(j - list.get(i));
                    //list(i - 1)的可调整范围
                    int upper = Math.min(j + target, 100);
                    int lower = Math.max(j - target, 0);
                    //计算list(i - 1)在lower，upper范围类调整的最小值,加上delta就是f[i][j]
                    for (int k = lower; k <= upper; k++) {
                        f[i][j] = Math.min(f[i][j], f[i - 1][k] + delta);
                    }
                }
            }

            for (int i = 0; i < 101; i++) {
                adjustNum = Math.min(adjustNum, f[size - 1][i]);
            }
        }

        return adjustNum;
    }











    /**
     * http://www.lintcode.com/zh-cn/problem/backpack/
     * @param m: An integer m denotes the size of a backpack
     * @param arr: Given n items with size arr[i]
     * @return: The maximum size
     */
    public int backPack(int m, int[] arr) {
        if (arr != null && arr.length > 0) {
            int len = arr.length;
            //f[i][j]表示将前i个物品装入容量为j的背包的最大重量
            int[][] f = new int[len][m + 1];
            int w0 = arr[0];
            for (int i = 0; i <= m; i++) {
                f[0][i] = w0 <= i? w0: 0;
            }

            for (int i = 1; i < len; i++) {
                int wi = arr[i];
                for (int j = 0; j <= m; j++) {
                    if (wi > j) {
                        f[i][j] = f[i - 1][j];
                    }
                    else if (wi == j) {
                        f[i][j] = wi;
                    }
                    else {
                        f[i][j] = Math.max(wi + f[i - 1][j - wi], f[i - 1][j]);
                    }
                }
            }
            return f[len - 1][m];
        }
        return 0;
    }



















    /**
     * http://www.lintcode.com/zh-cn/problem/balanced-binary-tree/
     * @param root: The root of binary tree.
     * @return: True if this Binary tree is Balanced, or false.
     */
    public boolean isBalanced(TreeNode root) {
        return balacneCheck(root).isBalance;
    }

    private TreeBalanceInfo balacneCheck(TreeNode cur) {
        if (cur == null) {
            return new TreeBalanceInfo(0, true);
        }
        else {
            TreeBalanceInfo left = balacneCheck(cur.left);
            TreeBalanceInfo right = balacneCheck(cur.right);
            if (left.isBalance && right.isBalance && Math.abs(left.depth - right.depth) <= 1) {
                return new TreeBalanceInfo(Math.max(left.depth, right.depth) + 1, true);
            }
            else {
                return new TreeBalanceInfo(0, false);
            }
        }
    }

    private class TreeBalanceInfo {
        public boolean isBalance;
        public int depth;
        public TreeBalanceInfo(int depth, boolean isBalance) {
            this.depth = depth;
            this.isBalance = isBalance;
        }
    }













    /**
     * http://www.lintcode.com/zh-cn/problem/binary-tree-maximum-path-sum/
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    public int maxPathSum(TreeNode root) {
        //思路
        //（左树单枝最大长度 + 根节点 + 右树单枝最大长度），（左树双枝最大长度），（右树双枝最大长度）三者的最大长度
        if (root != null) {
            ArrayList<Integer> maxes = new ArrayList<Integer>();
            measurePathSum(root, maxes);
            Collections.sort(maxes);
            return maxes.get(maxes.size() - 1);
        }

        return 0;
    }

    /**
     * @param cur
     * @return 单枝最大路径长度
     */
    private int measurePathSum(TreeNode cur, ArrayList<Integer> maxes) {
        if (cur == null) {
            return 0;
        }
        else if (cur.left == null && cur.right == null) {
            maxes.add(cur.val);
            return cur.val;
        }
        else {
            int left = measurePathSum(cur.left, maxes);
            int right = measurePathSum(cur.right, maxes);
            maxes.add(Math.max(left + right + cur.val, cur.val));
            return Math.max(left, right) + cur.val;
        }
    }










    /**
     * http://www.lintcode.com/zh-cn/problem/validate-binary-search-tree/
     * @param root: The root of binary tree.
     * @return: True if the binary tree is BST, or false
     */
    public boolean isValidBST(TreeNode root) {
        if (root != null) {
            return checkBST(root).isBST;
        }
        return true;
    }

    private CheckBSTResult checkBST(TreeNode cur) {
        if (cur.left == null && cur.right == null) {
            return new CheckBSTResult(true, cur.val, cur.val);
        }
        else if (cur.left != null && cur.right == null) {
            if (cur.val > cur.left.val) {
                CheckBSTResult temp = checkBST(cur.left);
                return new CheckBSTResult(temp.isBST && temp.maxVal < cur.val, temp.minVal, cur.val);
            }
        }
        else if (cur.left == null && cur.right != null) {
            if (cur.val < cur.right.val) {
                CheckBSTResult temp = checkBST(cur.right);
                return new CheckBSTResult(temp.isBST && temp.minVal > cur.val, cur.val, temp.maxVal);
            }
        }
        else {
            CheckBSTResult tempL = checkBST(cur.left);
            CheckBSTResult tempR = checkBST(cur.right);
            if (tempL.isBST && tempR.isBST && tempL.maxVal < cur.val && tempR.minVal > cur.val) {
                return new CheckBSTResult(true, tempL.minVal, tempR.maxVal);
            }
        }
        return new CheckBSTResult(false, 0, 0);
    }

    private class CheckBSTResult {
        public boolean isBST;
        public int minVal;
        public int maxVal;
        public CheckBSTResult(boolean isBST, int minVal, int maxVal) {
            this.isBST = isBST;
            this.minVal = minVal;
            this.maxVal =  maxVal;
        }
    }











    /**
     * http://www.lintcode.com/zh-cn/problem/partition-list/
     * @param head: The first node of linked list.
     * @param x: an integer
     * @return: a ListNode
     */
    public ListNode partition(ListNode head, int x) {
        ListNode headL = new ListNode(0);
        ListNode headGE = new ListNode(0);

        ListNode curL = headL;
        ListNode curGE = headGE;
        ListNode cur = head;
        while (cur != null) {
            ListNode nextTemp = cur.next;
            cur.next = null;
            if (cur.val >= x) {
                curGE.next = cur;
                curGE = curGE.next;
            }
            else {
                curL.next = cur;
                curL = curL.next;
            }
            cur = nextTemp;
        }
        curL.next = headGE.next;
        return headL.next;
    }











    /**
     * http://www.lintcode.com/zh-cn/problem/maximum-depth-of-binary-tree/
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        else {
            int leftDepth = maxDepth(root.left);
            int rightDepth = maxDepth(root.right);
            return 1 + Math.max(leftDepth, rightDepth);
        }
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/sort-list/
     * @param head: The head of linked list.
     * @return: You should return the head of the sorted linked list,
    using constant space complexity.
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = findMedian(head);
        ListNode leftDummy = new ListNode(0), leftTail = leftDummy;
        ListNode rightDummy = new ListNode(0), rightTail = rightDummy;
        ListNode middleDummy = new ListNode(0), middleTail = middleDummy;
        while (head != null) {
            if (head.val < mid.val) {
                leftTail.next = head;
                leftTail = head;
            } else if (head.val > mid.val) {
                rightTail.next = head;
                rightTail = head;
            } else {
                middleTail.next = head;
                middleTail = head;
            }
            head = head.next;
        }

        leftTail.next = null;
        middleTail.next = null;
        rightTail.next = null;

        ListNode left = sortList(leftDummy.next);
        ListNode right = sortList(rightDummy.next);

        return concat(left, middleDummy.next, right);
    }

    private ListNode findMedian(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode concat(ListNode left, ListNode middle, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        tail.next = left;
        tail = getTail(tail);

        tail.next = middle;
        tail = getTail(tail);

        tail.next = right;
        return dummy.next;
    }

    private ListNode getTail(ListNode head) {
        if (head == null) {
            return null;
        }

        while (head.next != null) {
            head = head.next;
        }
        return head;
    }














    /**
     * http://www.lintcode.com/zh-cn/problem/reorder-list/
     * @param head: The head of linked list.
     * @return: void
     */
    public void reorderList(ListNode head) {
        if (head != null && head.next != null) {
            ListNode newHead = new ListNode(0);
            ListNode cur = head;
            while (cur != null) {
                newHead.next = cur;
                newHead = cur;

                ListNode tempNext = cur.next;
                newHead.next = popTail(tempNext);
                newHead = newHead.next;

                if (tempNext == newHead) {
                    cur = null;
                }
                else {
                    cur = tempNext;
                }
            }
        }
    }

    private ListNode popTail(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        else {
            while (head.next.next != null) {
                head = head.next;
            }
            ListNode tail = head.next;
            head.next = null;
            return tail;
        }
    }












    /**
     * http://www.lintcode.com/zh-cn/problem/remove-duplicates-from-sorted-array/
     * @param nums: a array of integers
     * @return : return an integer
     */
    public int removeDuplicates(int[] nums) {
        int k = 0;
        if (nums != null && nums.length > 1) {
            int j = 1;
            int len = nums.length;
            while (j < len) {
                if (nums[k] != nums[j]) {
                    nums[++k] = nums[j];
                }
                if (k != j) {
                    nums[j] = 0;
                }
                j ++;
            }
            return k + 1;
        }
        return nums == null? 0: nums.length;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/remove-duplicates-from-sorted-array-ii/
     * @param nums: a array of integers
     * @return : return an integer
     */
    public int removeDuplicatesII(int[] nums) {
        int k = 0;
        if (nums != null && nums.length > 2) {
            int j = 1;
            int dumplict = 0;
            int len = nums.length;
            while (j < len) {
                if (nums[k] == nums[j]) {
                    dumplict++;
                    if (dumplict < 2) {
                        k++;
                        nums[k] = nums[j];
                    }
                }
                else {
                    nums[++k] = nums[j];
                    dumplict = 0;
                }

                j ++;
            }
            return k + 1;
        }
        return nums == null? 0: nums.length;
    }
















    /**
     * http://www.lintcode.com/zh-cn/problem/linked-list-cycle/
     * @param head: The first node of linked list.
     * @return: True if it has a cycle, or false
     */
    public boolean hasCycle(ListNode head) {
        if (head != null) {
            ListNode tail = findCircleTail(head);
            while (head != null) {
                if (head == tail && tail.next != null) {
                    return true;
                }
                head = head.next;
            }
        }
        return false;
    }

    private ListNode findCircleTail(ListNode node) {
        if (node.next != null) {
            ListNode next = node.next;
            node.next = null;
            ListNode tail = findCircleTail(next);
            node.next = next;
            return tail;
        }
        else {
            return node;
        }
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/linked-list-cycle-ii/
     * @param head: The first node of linked list.
     * @return: The node where the cycle begins.
     *           if there is no cycle, return null
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode tail = findCircleTail(head);
        if (tail.next == null) {
            return null;
        }
        else {
            return tail;
        }
    }











    /**
     * http://www.lintcode.com/zh-cn/problem/merge-k-sorted-lists/
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {
        if (lists != null && lists.size() != 0) {
            while (lists.size() != 1) {
                for (int i = 0, size = lists.size(), newSize = (size  + 1) / 2; i < newSize; i++) {
                    ListNode list0 = lists.get(i * 2);
                    ListNode list1 = i * 2 + 1 == size? null: lists.get(i * 2 + 1);
                    lists.set(i, merageTwoLists(list0, list1));
                }
                for (int size = lists.size(), newSize = (size  + 1) / 2, i = size - 1; i >= newSize; i--) {
                    lists.remove(i);
                }
            }
            return lists.get(0);
        }
        return null;
    }

    private ListNode merageTwoLists(ListNode list0, ListNode list1) {
        ListNode newHead = new ListNode(0);
        ListNode cur = newHead;
        while (list0 != null && list1 != null) {
            if (list0.val < list1.val) {
                cur.next = list0;
                list0 = list0.next;
            }
            else {
                cur.next = list1;
                list1 = list1.next;
            }
            cur = cur.next;
        }

        while (list0 != null) {
            cur.next = list0;
            list0 = list0.next;
            cur = cur.next;
        }
        while (list1 != null) {
            cur.next = list1;
            list1 = list1.next;
            cur = cur.next;
        }
        return newHead.next;
    }

















    /**
     * http://www.lintcode.com/zh-cn/problem/copy-list-with-random-pointer/
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        copyNext(head);
        copyRandom(head);
        return splitList(head);
    }


    private void copyNext(RandomListNode head) {
        while (head != null) {
            RandomListNode newNode = new RandomListNode(head.label);
            newNode.random = head.random;
            newNode.next = head.next;
            head.next = newNode;
            head = head.next.next;
        }
    }

    private void copyRandom(RandomListNode head) {
        while (head != null) {
            if (head.next.random != null) {
                head.next.random = head.random.next;
            }
            head = head.next.next;
        }
    }

    private RandomListNode splitList(RandomListNode head) {
        RandomListNode newHead = head.next;
        while (head != null) {
            RandomListNode temp = head.next;
            head.next = temp.next;
            head = head.next;
            if (temp.next != null) {
                temp.next = temp.next.next;
            }
        }
        return newHead;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/convert-sorted-list-to-balanced-bst/
     * @param head: The first node of linked list.
     * @return: a tree node
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode middle = findMiddleAndSplit(head);
        ListNode left = middle == head? null: head;
        ListNode right = middle == null? null: middle.next;
        middle.next = null;

        TreeNode root = new TreeNode(middle.val);
        root.left = sortedListToBST(left);
        root.right = sortedListToBST(right);

        return root;
    }

    private ListNode findMiddleAndSplit(ListNode head) {
        ListNode fast = head.next;
        while (fast != null) {
            if (fast.next != null) {
                fast = fast.next.next;
            }
            else {
                fast = null;
            }

            ListNode next = head.next;
            if (fast == null) {
                head.next = null;
            }
            head = next;
        }
        return head;
    }













    /**
     * http://www.lintcode.com/zh-cn/problem/word-break/
     * @param s: A string s
     * @param dict: A dictionary of words dict
     */
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || s.length() == 0) {
            return true;
        }

        int maxLength = getMaxLength(dict);
        boolean[] canSegment = new boolean[s.length() + 1];

        canSegment[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            canSegment[i] = false;
            for (int lastWordLength = 1;
                 lastWordLength <= maxLength && lastWordLength <= i;
                 lastWordLength++) {
                if (!canSegment[i - lastWordLength]) {
                    continue;
                }
                String word = s.substring(i - lastWordLength, i);
                if (dict.contains(word)) {
                    canSegment[i] = true;
                    break;
                }
            }
        }

        return canSegment[s.length()];
    }

    private int getMaxLength(Set<String> dict) {
        int maxLength = 0;
        for (String word : dict) {
            maxLength = Math.max(maxLength, word.length());
        }
        return maxLength;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/palindrome-partitioning-ii/
     * @param s a string
     * @return an integer
     */
    public int minCut(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int len = s.length();
        int[] f = new int[len];
        for (int i = 0; i < len; i++) {
            if (isPalindrome(s, 0, i)) {
                f[i] = 0;
            }
            else {
                int tempMinCut = Integer.MAX_VALUE;
                for (int j = i - 1; j > -1; j--) {
                    if (isPalindrome(s, j + 1, i) && f[j] + 1 < tempMinCut) {
                        tempMinCut = f[j] + 1;
                    }
                }
                f[i] = tempMinCut;
            }
        }
        return f[len - 1];
    }

    /**
     * s的left到right部分是否回文(aba,aabbaa,abcba)
     * @return
     */
    private boolean isPalindrome(String s, int left, int right) {
        if (left == right) {
            return true;
        }

        while (left <= right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/triangle/
     * @param triangle: a list of lists of integers.
     * @return: An integer, minimum path sum.
     */
    public int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) {
            return 0;
        }

        int maxLevel = triangle.length;
        int maxWidth = triangle[maxLevel - 1].length;
        int[] f = new int[maxWidth];
        int[] lastF = new int[maxWidth];
        f[0] = triangle[0][0];

        for (int i = 1; i < maxLevel; i++) {
            for (int j = 0, width = i + 1; j < width; j++) {
                lastF[j] = f[j];
                if (j == 0) {
                    f[j] = lastF[j] + triangle[i][j];
                }
                else if (j == i) {
                    f[j] = lastF[j - 1] + triangle[i][j];
                }
                else {
                    f[j] = Math.min(lastF[j - 1], lastF[j]) + triangle[i][j];
                }
            }
        }

        int minTotal = Integer.MAX_VALUE;
        for (int i = 0; i < maxWidth; i++) {
            if (f[i] < minTotal) {
                minTotal = f[i];
            }
        }
        return minTotal;
    }












    /**
     * http://www.lintcode.com/zh-cn/problem/valid-sudoku/
     * @param board: the board
     @return: wether the Sudoku is valid
     */
    public boolean isValidSudoku(char[][] board) {
        HashMap<Character, Boolean> rowExiste = new HashMap<Character, Boolean>();
        HashMap<Character, Boolean> colExiste = new HashMap<Character, Boolean>();
        //检测每一行/列
        for (int i = 0; i < 9; i++) {
            rowExiste.clear();
            colExiste.clear();
            for (int j = 0; j < 9; j++) {
                char rowC = board[i][j];
                if (isNumChar(rowC)) {
                    if (rowExiste.containsKey(rowC)) {
                        return false;
                    }
                    else {
                        rowExiste.put(rowC, true);
                    }
                }

                char colC = board[j][i];
                if (isNumChar(colC)) {
                    if (colExiste.containsKey(colC)) {
                        return false;
                    }
                    else {
                        colExiste.put(colC, true);
                    }
                }
            }
        }

        //检查每一个九宫格
        for (int i = 0; i < 9; i++) {
            rowExiste.clear();

            int startIndex = i / 3 * 9 + i % 3 * 3;
            int startX = startIndex / 9;
            int startY = startIndex % 9;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    char c = board[startX + j][startY + k];
                    if (isNumChar(c)) {
                        if (rowExiste.containsKey(c)) {
                            return false;
                        }
                        else {
                            rowExiste.put(c, true);
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isNumChar(char c) {
        return c >= '1' && c <= '9';
    }




    /**
     * http://www.lintcode.com/zh-cn/problem/expression-tree-build/
     * @param expression: A string array
     * @return: The root of expression tree
     */
    public ExpressionTreeNode build(String[] expression) {
        if (expression == null || expression.length == 0) {
            return null;
        }
        List<Object> exps = new ArrayList<Object>();
        Collections.addAll(exps, expression);
        return buildExpTree(exps);
    }

    private ExpressionTreeNode buildExpTree(List<Object> exps) {
        int leftBracketIndex = -1;
        int rightBracketIndex = -1;
        int leftBracketNum = 0;
        int rightBracketNum = 0;

        //处理所有的括号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("(")) {
                    leftBracketNum++;
                    if (leftBracketNum == 1) {
                        leftBracketIndex = i;
                    }
                }
                else if (itemStr.equals(")")) {
                    rightBracketNum++;
                    if (rightBracketNum == leftBracketNum) {
                        rightBracketIndex = i;
                        //将leftBracketIndex + 1到rightBracketIndex - 1之间的表达式转换为ExpressionTreeNode
                        List<Object> subExps = exps.subList(leftBracketIndex + 1, rightBracketIndex);
                        ExpressionTreeNode newNode = buildExpTree(subExps);
                        if (exps.size() > leftBracketIndex) {
                            exps.set(leftBracketIndex, newNode);
                            exps.remove(leftBracketIndex + 1);
                            if (newNode != null) {
                                exps.remove(leftBracketIndex + 1);
                            }
                        }

                        i = leftBracketIndex;
                        leftBracketIndex = -1;
                        rightBracketIndex = -1;
                        leftBracketNum = 0;
                        rightBracketNum = 0;
                    }
                }
            }
            i++;
        }

        //处理所有的乘号或除号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("*") || itemStr.equals("/")) {
                    exps.set(i - 1, buildSimpleTree(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }
            i++;
        }

        //处理所有的加号或减号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("+") || itemStr.equals("-")) {
                    exps.set(i - 1, buildSimpleTree(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }

            i++;
        }

        if (exps.size() > 0) {
            Object item0 = exps.get(0);
            if (item0 instanceof String) {
                return new ExpressionTreeNode((String) item0);
            }
            else {
                return (ExpressionTreeNode) exps.get(0);
            }
        }
        return null;
    }

    private ExpressionTreeNode buildSimpleTree(Object leftExp, String option, Object rightExp) {
        ExpressionTreeNode left = null;
        if (leftExp instanceof ExpressionTreeNode) {
            left = (ExpressionTreeNode) leftExp;
        }
        else {
            left = new ExpressionTreeNode((String) leftExp);
        }

        ExpressionTreeNode right = null;
        if (rightExp instanceof ExpressionTreeNode) {
            right = (ExpressionTreeNode) rightExp;
        }
        else {
            right = new ExpressionTreeNode((String) rightExp);
        }
        ExpressionTreeNode newNode = new ExpressionTreeNode(option);
        newNode.left = left;
        newNode.right = right;
        return newNode;
    }




    /**
     * http://www.lintcode.com/zh-cn/problem/minimum-path-sum/
     * @param grid: a list of lists of integers.
     * @return: An integer, minimizes the sum of all numbers along its path
     */
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int h = grid.length;
        int w = grid[0].length;
        int[][] f = new int[h][w];
        f[0][0] = grid[0][0];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (i == 0) {
                    if (j > 0) {
                        f[i][j] = f[i][j - 1] + grid[i][j];
                    }
                }
                else {
                    if (j == 0) {
                        f[i][j] = f[i - 1][j] + grid[i][j];
                    }
                    else {
                        f[i][j] = Math.min(f[i - 1][j], f[i][j - 1]) + grid[i][j];
                    }
                }
            }
        }
        return f[h - 1][w - 1];
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/climbing-stairs/
     * @param n: An integer
     * @return: An integer
     */
    public int climbStairs(int n) {
        if (n == 0) {
            return 1;
        }

        int a = 1;
        int b = 1;
        for (int i = 2; i < n + 1; i++) {
            b += a;
            a = b - a;
        }
        return b;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/convert-expression-to-reverse-polish-notation/
     * @param expression: A string array
     * @return: The Reverse Polish notation of this expression
     */
    public ArrayList<String> convertToRPN(String[] expression) {
        if (expression == null || expression.length == 0) {
            return null;
        }
        List<Object> exps = new ArrayList<Object>();
        Collections.addAll(exps, expression);
        Expression exp = buildExpression(exps);
        return expressionToRpnList(exp);
    }

    private ArrayList<String> expressionToRpnList(Expression exp) {
        ArrayList<String> result = new ArrayList<String>();
        if (exp != null) {
            if (exp.left != null && exp.right != null) {
                ArrayList<String> left = expressionToRpnList(exp.left);
                ArrayList<String> right = expressionToRpnList(exp.right);
                result.addAll(left);
                result.addAll(right);
            }
            result.add(exp.val);
        }
        return result;
    }

    private Expression buildExpression(List<Object> exps) {
        int leftBracketIndex = -1;
        int rightBracketIndex = -1;
        int leftBracketNum = 0;
        int rightBracketNum = 0;

        //处理所有的括号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("(")) {
                    leftBracketNum++;
                    if (leftBracketNum == 1) {
                        leftBracketIndex = i;
                    }
                }
                else if (itemStr.equals(")")) {
                    rightBracketNum++;
                    if (rightBracketNum == leftBracketNum) {
                        rightBracketIndex = i;
                        //将leftBracketIndex + 1到rightBracketIndex - 1之间的表达式转换为ExpressionTreeNode
                        List<Object> subExps = exps.subList(leftBracketIndex + 1, rightBracketIndex);
                        Expression newNode = buildExpression(subExps);
                        if (exps.size() > leftBracketIndex) {
                            exps.set(leftBracketIndex, newNode);
                            exps.remove(leftBracketIndex + 1);
                            if (newNode != null) {
                                exps.remove(leftBracketIndex + 1);
                            }
                        }

                        i = leftBracketIndex;
                        leftBracketIndex = -1;
                        rightBracketIndex = -1;
                        leftBracketNum = 0;
                        rightBracketNum = 0;
                    }
                }
            }
            i++;
        }

        //处理所有的乘号或除号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("*") || itemStr.equals("/")) {
                    exps.set(i - 1, buildSimpleExpression(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }
            i++;
        }

        //处理所有的加号或减号
        for (int i = 0; ; ) {
            if (i >= exps.size()) {
                break;
            }

            Object item = exps.get(i);
            if (item instanceof String) {
                String itemStr = (String) item;
                if (itemStr.equals("+") || itemStr.equals("-")) {
                    exps.set(i - 1, buildSimpleExpression(exps.get(i - 1), itemStr, exps.get(i + 1)));
                    exps.remove(i);
                    exps.remove(i);
                    i = i - 1;
                }
            }

            i++;
        }

        if (exps.size() > 0) {
            Object item0 = exps.get(0);
            if (item0 instanceof String) {
                return new Expression((String) item0);
            }
            else {
                return (Expression) exps.get(0);
            }
        }
        return null;
    }

    private Expression buildSimpleExpression(Object leftExp, String option, Object rightExp) {
        Expression left = null;
        if (leftExp instanceof Expression) {
            left = (Expression) leftExp;
        }
        else {
            left = new Expression((String) leftExp);
        }

        Expression right = null;
        if (rightExp instanceof Expression) {
            right = (Expression) rightExp;
        }
        else {
            right = new Expression((String) rightExp);
        }
        Expression newNode = new Expression(option);
        newNode.left = left;
        newNode.right = right;
        return newNode;
    }

    private class Expression {

        public Expression left;

        public Expression right;

        public String val;

        public Expression(String val) {
            this.val = val;
        }
    }











    //思路很容易理解，很容易实现，但是数据量很大后会超时
    /**
     * http://www.lintcode.com/zh-cn/problem/building-outline/
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public ArrayList<ArrayList<Integer>> buildingOutline0(int[][] buildings) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (buildings != null && buildings.length > 0) {
            Set<Integer> xs = new HashSet<Integer>();
            for (int i = 0, lenI = buildings.length; i< lenI; i++) {
                int[] building = buildings[i];
                int startX = building[0];
                int endX = building[1];
                xs.add(startX);
                xs.add(endX);
            }

            Object[] xsObjs = xs.toArray();
            Arrays.sort(xsObjs);
            for (int i = 1, lenI = xsObjs.length; i < lenI; i++) {
                result.add(outline((Integer) xsObjs[i - 1], (Integer) xsObjs[i], 0));
            }

            for (int i = 0, lenI = buildings.length; i< lenI; i++) {
                int[] building = buildings[i];
                int startX = building[0];
                int endX = building[1];
                int h = building[2];
                for (ArrayList<Integer> item: result) {
                    if (item.get(1) <= startX) {

                    }
                    else if (item.get(0) >= startX && item.get(1) <= endX && item.get(2) < h) {
                        item.set(2, h);
                    }
                    else if (item.get(0) >= endX) {
                        break;
                    }
                }
            }

            for (int i = 1; i < result.size();) {
                ArrayList<Integer> item1 = result.get(i);
                if (item1.get(2) == 0) {
                    result.remove(i);
                }
                else {
                    ArrayList<Integer> item0 = result.get(i - 1);
                    if (item0.get(1).equals(item1.get(0)) && item0.get(2).equals(item1.get(2))) {
                        item0.set(1, item1.get(1));
                        result.remove(i);
                    }
                    else {
                        i++;
                    }
                }
            }
        }
        return result;
    }




//    /**
//     * 仍然超时
//     * http://www.lintcode.com/zh-cn/problem/building-outline/
//     * 利用对的思想，实现十分复杂
//     * @param buildings: A list of lists of integers
//     * @return: Find the outline of those buildings
//     */
//    public ArrayList<ArrayList<Integer>> buildingOutline1(int[][] buildings) {
//        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
//        if (buildings != null && buildings.length > 0) {
//            int len = buildings.length;
//            int i = (len - 1) / 2;
//            int j = i + 1;
//            OutlineHeap heap = new OutlineHeap();
//
//            while (j < len) {
//                {
//                    int[] building = buildings[i];
//                    heap.push(new Outline(building[0], building[1], building[2]));
//                }
//
//                {
//                    int[] building = buildings[j];
//                    heap.push(new Outline(building[0], building[1], building[2]));
//                }
//                i--;
//                j++;
//            }
//
//            if (i == 0) {
//                int[] building = buildings[i];
//                heap.push(new Outline(building[0], building[1], building[2]));
//            }
//
//            heap.visitAll(result);
//        }
//        return result;
//    }
//
//    private class OutlineHeap {
//
//        private Outline root = null;
//
//        public void visitAll(ArrayList<ArrayList<Integer>> nodes) {
//            visitAll(nodes, root);
//            Collections.sort(nodes, new java.util.Comparator<ArrayList<Integer>>() {
//                public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
//                    return o1.get(0) - o2.get(0);
//                }
//            });
//        }
//
//        private void visitAll(ArrayList<ArrayList<Integer>> nodes, Outline curRoot) {
//            if (curRoot != null) {
//                nodes.add(curRoot.toArrayList());
//                visitAll(nodes, curRoot.left);
//                visitAll(nodes, curRoot.right);
//            }
//        }
//
//        private void visitAllOutline(ArrayList<Outline> nodes, Outline curRoot) {
//            if (curRoot != null) {
//                nodes.add(curRoot);
//                Outline left = curRoot.left;
//                Outline right = curRoot.left;
//                curRoot.left = null;
//                curRoot.right = null;
//                visitAllOutline(nodes, left);
//                visitAllOutline(nodes, right);
//            }
//        }
//
//        private int i = 0;
//
//        public void push(Outline item) {
//            XYLog.d("插入：", item);
//            if (i == 31) {
//                int ii = 1;
//            }
//            if (root == null) {
//                root = item;
//            }
//            else {
//                push(root, item);
//            }
//
//            ExpressionTreeNode treeRoot = toTreeNode(root);
//            XYLog.d(i, treeRoot);
//            i++;
//        }
//
//        private ExpressionTreeNode toTreeNode(Outline node) {
//            if (node != null) {
//                ExpressionTreeNode treeNode = new ExpressionTreeNode(" " + node.xStart + "," + node.xEnd + "," + node.height + " ");
//                ExpressionTreeNode left = toTreeNode(node.left);
//                ExpressionTreeNode right = toTreeNode(node.right);
//                treeNode.left = left;
//                treeNode.right = right;
//                return  treeNode;
//            }
//            else {
//                return null;
//            }
//        }
//
//
//
//        private void push(Outline curRoot, Outline item) {
//            if (curRoot.xStart > item.xEnd) {
//                addToLeft(curRoot, item);
//            }
//            else if (curRoot.xEnd < item.xStart) {
//                addToRight(curRoot, item);
//            }
//            else if (curRoot.xStart == item.xEnd) {
//                if (curRoot.height == item.height) {
//                    curRoot.xStart = item.xStart;
//                    //TODO 检测左子树
//                    checkLeftChild(curRoot);
//                }
//                else {
//                    addToLeft(curRoot, item);
//                }
//            }
//            else if (curRoot.xEnd == item.xStart) {
//                if (curRoot.height == item.height) {
//                    curRoot.xEnd = item.xEnd;
//                    //TODO 检测右子树
//                    checkRightChild(curRoot);
//                }
//                else {
//                    addToRight(curRoot, item);
//                }
//            }
//            else if (item.xStart < curRoot.xStart && item.xEnd > curRoot.xStart && item.xEnd < curRoot.xEnd) {
//                //item 在 curRoot 的左边，相交
//                if (item.height < curRoot.height) {
//                    item.xEnd = curRoot.xStart;
//                    addToLeft(curRoot, item);
//                }
//                else if (item.height == curRoot.height) {
//                    curRoot.xStart = item.xStart;
//                    //TODO 检测左子树
//                    checkLeftChild(curRoot);
//
//                }
//                else {
//                    curRoot.xStart = item.xEnd;
//                    addToLeft(curRoot, item);
//                }
//            }
//            else if (curRoot.xStart < item.xStart && curRoot.xEnd > item.xStart && item.xEnd > curRoot.xEnd) {
//                //item 在 curRoot 的右边，相交
//                if (item.height < curRoot.height) {
//                    item.xStart = curRoot.xEnd;
//                    addToRight(curRoot, item);
//                }
//                else if (item.height == curRoot.height) {
//                    curRoot.xEnd = item.xEnd;
//                    //TODO 检测右子树
//                    checkRightChild(curRoot);
//
//                }
//                else {
//                    curRoot.xEnd = item.xStart;
//                    addToRight(curRoot, item);
//                }
//            }
//            else if (item.xStart <= curRoot.xStart && item.xEnd >= curRoot.xEnd) {
//                //item 在x轴完全包括cur
//                if (item.height >= curRoot.height) {
//                    int oldXStart = curRoot.xStart;
//                    int oldXEnd = curRoot.xEnd;
//                    curRoot.xStart = item.xStart;
//                    curRoot.xEnd = item.xEnd;
//                    curRoot.height = item.height;
//
//                    ArrayList<Outline> cutList = new ArrayList<Outline>();
//                    if (oldXStart > curRoot.xStart) {
//                        //TODO 检测左子树
//                        checkLeftChild(curRoot);
//                        //TODO 检查左子树的右子树中是否有范围冲突的，如果有,则剪枝，并将减下来的重新加入
//                        Outline tempParent = curRoot;
//                        Outline tempChild = curRoot.left;
//                        while (tempChild != null) {
//                            if (tempChild.xEnd > curRoot.xStart || (tempChild.xEnd == curRoot.xStart && tempChild.height == curRoot.height)) {
//                                tempParent.right = null;//剪枝
//                                visitAllOutline(cutList, tempChild);
//                                break;
//                            }
//                            else if (tempChild.right != null) {
//                                tempParent = tempChild;
//                                tempChild = tempParent.right;
//                            }
//                            else {
//                                break;
//                            }
//                        }
//
//                    }
//                    if (oldXEnd < curRoot.xEnd) {
//                        //TODO 检测右子树
//                        checkRightChild(curRoot);
//                        //TODO 检查右子树的左子树中是否有范围冲突的，如果有,则剪枝，并将减下来的重新加入
//                        Outline tempParent = curRoot;
//                        Outline tempChild = curRoot.right;
//                        while (tempChild != null) {
//                            if (tempChild.xStart < curRoot.xEnd || (tempChild.xStart == curRoot.xEnd && tempChild.height == curRoot.height)) {
//                                tempParent.left = null;//剪枝
//                                visitAllOutline(cutList, tempChild);
//                                break;
//                            }
//                            else if (tempChild.left != null) {
//                                tempParent = tempChild;
//                                tempChild = tempParent.left;
//                            }
//                            else {
//                                break;
//                            }
//                        }
//                    }
//
//                    if (!cutList.isEmpty()) {
//                        for (Outline outline: cutList) {
//                            push(curRoot, outline);
//                        }
//                    }
//                }
//                else {
//                    addToLeft(curRoot, new Outline(item.xStart, curRoot.xStart, item.height));
//                    addToRight(curRoot, new Outline(curRoot.xEnd, item.xEnd, item.height));
//                }
//            }
//            else {
//                //cur 在x轴完全包括item
//                if (item.height > curRoot.height) {
//                    Outline newL = new Outline(curRoot.xStart, item.xStart, curRoot.height);
//                    Outline newR = new Outline(item.xEnd, curRoot.xEnd, curRoot.height);
//                    curRoot.xStart = item.xStart;
//                    curRoot.xEnd = item.xEnd;
//                    curRoot.height = item.height;
//                    addToLeft(curRoot, newL);
//                    addToRight(curRoot, newR);
//                }
//            }
//
//        }
//
//        private void checkLeftChild(Outline curRoot) {
//            if (curRoot != null && curRoot.left != null) {
//                Outline leftChild = curRoot.left;
//                if (leftChild.xEnd < curRoot.xStart) {
//                    //相离
//                }
//                else if (leftChild.xEnd == curRoot.xStart) {
//                    //相邻
//                    if (leftChild.height == curRoot.height) {
//                        //高度相同，将其与父节融合，然后从最左子节点找一个节点替代当前节点，然后检查该节点的左子树
//                        //TODO 节点替换和检测
//                        mergeAndReplaceWithChild(curRoot, leftChild);
//                        checkChild(curRoot);
//                    }
//                }
//                else if (leftChild.xEnd > curRoot.xStart && leftChild.xStart < curRoot.xStart) {
//                    //相交
//                    if (leftChild.height == curRoot.height) {
//                        //高度相同，将其与父节融合，然后从最左子节点找一个节点替代当前节点，然后检查该节点的左子树
//                        //TODO 节点替换和检测
//                        mergeAndReplaceWithChild(curRoot, leftChild);
//                        checkChild(curRoot);
//                    }
//                    else if (leftChild.height < curRoot.height) {
//                        leftChild.xEnd = curRoot.xStart;
//                    }
//                    else {
//                        curRoot.xStart = leftChild.xEnd;
//                    }
//                }
//                else if (leftChild.xStart >= curRoot.xStart) {
//                    //curRoot 包含 item
//                    if (leftChild.height <= curRoot.height) {
//                        //高度相同，将其与父节融合，然后从最左子节点找一个节点替代当前节点，然后检查该节点的左子树
//                        //TODO 节点替换和检测
//                        mergeAndReplaceWithChild(curRoot, leftChild);
//                        checkChild(curRoot);
//                    }
//                    else {
//                        curRoot.xStart = leftChild.xEnd;
//                        addToLeft(leftChild, new Outline(curRoot.xStart, leftChild.xStart, curRoot.height));
//                    }
//                }
//            }
//        }
//
//        private void checkRightChild(Outline curRoot) {
//            if (curRoot != null && curRoot.right != null) {
//                Outline rightChild = curRoot.right;
//                if (rightChild.xStart > curRoot.xEnd) {
//                    //相离
//                }
//                else if (rightChild.xStart == curRoot.xEnd) {
//                    //相邻
//                    if (rightChild.height == curRoot.height) {
//                        //高度相同，将其与父节融合，然后从最左子节点找一个节点替代当前节点，然后检查该节点的左子树
//                        //TODO 节点替换和检测
//                        mergeAndReplaceWithChild(curRoot, rightChild);
//                        checkChild(curRoot);
//                    }
//                }
//                else if (curRoot.xEnd > rightChild.xStart && rightChild.xEnd > curRoot.xEnd) {
//                    //相交
//                    if (rightChild.height == curRoot.height) {
//                        //高度相同，将其与父节融合，然后从最左子节点找一个节点替代当前节点，然后检查该节点的左子树
//                        //TODO 节点替换和检测
//                        mergeAndReplaceWithChild(curRoot, rightChild);
//                        checkChild(curRoot);
//                    }
//                    else if (rightChild.height < curRoot.height) {
//                        rightChild.xStart = curRoot.xEnd;
//                    }
//                    else {
//                        curRoot.xEnd = rightChild.xStart;
//                    }
//                }
//                else if (rightChild.xEnd <= curRoot.xEnd) {
//                    //curRoot 包含 item
//                    if (rightChild.height <= curRoot.height) {
//                        //高度相同，将其与父节融合，然后从最左子节点找一个节点替代当前节点，然后检查该节点的左子树
//                        //TODO 节点替换和检测
//                        mergeAndReplaceWithChild(curRoot, rightChild);
//                        checkChild(curRoot);
//                    }
//                    else {
//                        curRoot.xEnd = rightChild.xStart;
//                        addToRight(rightChild, new Outline(rightChild.xEnd, curRoot.xEnd, curRoot.height));
//                    }
//                }
//            }
//        }
//
//        private void checkChild(Outline curRoot) {
//            if (curRoot != null) {
//                if (curRoot.left != null) {
//                    checkLeftChild(curRoot);
//                }
//                if (curRoot.right != null) {
//                    checkRightChild(curRoot);
//                }
//            }
//        }
//
//        private void mergeAndReplaceWithChild(Outline curRoot, Outline child) {
//            curRoot.xStart = Math.min(curRoot.xStart, child.xStart);
//            curRoot.xEnd = Math.max(curRoot.xEnd, child.xEnd);
//
//            Outline tempRoot = null;
//            Outline tempChild = null;
//            if (child.left != null) {
//                tempRoot = child.left;
//                tempChild = tempRoot.left;
//            }
//            else if (child.right != null) {
//                tempRoot = child.right;
//                tempChild = tempRoot.left;
//            }
//
//            if (tempRoot != null) {
//                while (tempChild != null && tempChild.left != null) {
//                    tempRoot = tempChild;
//                    tempChild = tempRoot.left;
//                }
//
//                if (tempChild == null) {
//                    if (curRoot.left == child) {
//                        curRoot.left = tempRoot;
//                    }
//                    else {
//                        curRoot.right = tempRoot;
//                    }
//                }
//                else {
//                    tempRoot.left = tempChild.right;
//
//                    tempChild.left = child.left;
//                    tempChild.right = child.right;
//                    if (curRoot.left == child) {
//                        curRoot.left = tempChild;
//                    }
//                    else {
//                        curRoot.right = tempChild;
//                    }
//                }
//            }
//            else {
//                if (curRoot.left == child) {
//                    curRoot.left = null;
//                }
//                else {
//                    curRoot.right = null;
//                }
//            }
//        }
//
//        private void addToLeft(Outline curRoot, Outline item) {
//            if (item.xStart >= item.xEnd) {
//                return;
//            }
//            if (curRoot.left == null) {
//                curRoot.left = item;
//            }
//            else {
//                push(curRoot.left, item);
//            }
//        }
//
//        private void addToRight(Outline curRoot, Outline item) {
//            if (item.xStart >= item.xEnd) {
//                return;
//            }
//            if (curRoot.right == null) {
//                curRoot.right = item;
//            }
//            else {
//                push(curRoot.right, item);
//            }
//        }
//
//    }
//
//    private class Outline {
//        public int xStart;
//        public int xEnd;
//        public int height;
//
//        public Outline left;
//        public Outline right;
//
//        public Outline(int xStart, int xEnd, int height) {
//            this.xEnd = xEnd;
//            this.xStart = xStart;
//            this.height = height;
//        }
//
//        public ArrayList<Integer> toArrayList() {
//            ArrayList<Integer> result = new ArrayList<Integer>();
//            result.add(xStart);
//            result.add(xEnd);
//            result.add(height);
//            return result;
//        }
//
//        @Override
//        public String toString() {
//            return "(" + xStart + "," + xEnd + "," + height + ")";
//        }
//    }






    /**
     * http://www.lintcode.com/zh-cn/problem/building-outline/
     * 利用一个维护x边界和高度的最大堆，用一个整数记录上一次输出轮廓的边界值。当新到来的边界和堆顶元素等高，且该元素是终止边界，则从堆中移除顶部元素，
     * 之后如果堆顶元素高度小于新元素，则输出一个轮廓，否则不输出；当新元素更高的时候，将新元素添加到堆里面，
     * 并输出一个轮廓（必须要堆中有元素，否则不输出轮廓）；如果遇到终止边界，且高度小于堆顶元素，则从堆中移除一个和终止边界等高的起始边界。
     * 可以用一个HashMap来存储对应关系，用高度+边界类型作为key
     *
     * Heap的实现上一定要避免递归父子检查，一定要改用循环，否则会超时
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public ArrayList<ArrayList<Integer>> buildingOutline(int[][] buildings) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (buildings != null && buildings.length > 0) {
            int len = buildings.length;

            ArrayList<Edge> edges = new ArrayList<Edge>();
            for (int i = 0; i < len; i++) {
                int[] building = buildings[i];
                edges.add(new Edge(building[0], building[2], true, i));
                edges.add(new Edge(building[1], building[2], false, i));
            }
            Collections.sort(edges, new java.util.Comparator<Edge>() {
                public int compare(Edge item1, Edge item2) {
                    if (item1.x != item2.x) {
                        return item1.x - item2.x;
                    }
                    else if (item1.isStart == item2.isStart) {
                        return item1.h - item2.h;
                    }
                    else {
                        return item1.isStart? -1: 1;
                    }
                }
            });

            EdgeHeap heap = new EdgeHeap();
            int lastX = 0;
            for (Edge item: edges) {
                if (heap.isEmpty()) {
                    heap.push(item);
                    lastX = item.x;
                }
                else if (item.isStart) {
                    Edge top = heap.top();
                    if (item.h > top.h) {
                        addOutlineToResult(result, lastX, item.x, top.h);
                        lastX = item.x;
                    }
                    heap.push(item);
                }
                else {
                    heap.remove(item.id);
                    if (heap.isEmpty() || item.h > heap.top().h) {
                        addOutlineToResult(result, lastX, item.x, item.h);
                        lastX = item.x;
                    }
                }
            }
        }
        return result;
    }

    private void addOutlineToResult(ArrayList<ArrayList<Integer>> result, int xStart, int xEnd, int height) {
        if (xStart != xEnd) {
            result.add(outline(xStart, xEnd, height));
        }
    }

    private ArrayList<Integer> outline(int xStart, int xEnd, int height) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(xStart);
        result.add(xEnd);
        result.add(height);
        return result;
    }

    private class EdgeHeap {

        private ArrayList<Edge> datas = new ArrayList<Edge>();
        private HashMap<Integer, Edge> edgeMap = new HashMap<Integer, Edge>();

        public void push(Edge item) {
            datas.add(item);
            edgeMap.put(item.id, item);
            checkParent(datas.size() - 1);
        }

        public Edge pop() {
            int size = datas.size();
            if (size == 0) {
                return null;
            }
            else if (size == 1) {
                return datas.remove(0);
            }
            else {
                swape(0, size - 1);
                Edge top = datas.remove(size - 1);
                edgeMap.remove(top.id);
                checkChild(0);
                return top;
            }
        }

        public Edge remove(int id) {
            Edge item = edgeMap.get(id);
            if (item != null) {
                int size = datas.size();
                int index = datas.indexOf(item);
                if (index == size - 1) {
                    datas.remove(size - 1);
                    edgeMap.remove(item.id);
                }
                else {
                    swape(index, size - 1);
                    datas.remove(size - 1);
                    edgeMap.remove(item.id);
                    checkChild(index);
                    checkParent(index);
                }
            }
            return item;
        }

        private void checkParent(int childIndex) {
            int parentIndex = 0;
            while ((parentIndex = parentIndex(childIndex)) > -1) {
                Edge child = datas.get(childIndex);
                Edge parent = datas.get(parentIndex);
                if (gt(child, parent)) {
                    swape(childIndex, parentIndex);
                }
                else {
                    break;
                }
                childIndex = parentIndex;
            }
        }

        private void checkChild(int parentIndex) {
            int size = datas.size();
            int lIndex;
            while ((lIndex = leftChildIndex(parentIndex)) < size) {
                int rIndex = rightChildIndex(parentIndex);
                if (lIndex < size) {
                    Edge parent = datas.get(parentIndex);
                    if (rIndex >= size) {
                        Edge left = datas.get(lIndex);
                        if (gt(left, parent)) {
                            swape(lIndex, parentIndex);
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        Edge left = datas.get(lIndex);
                        Edge right = datas.get(rIndex);
                        if (gt(left, right)) {
                            if (gt(left, parent)) {
                                swape(lIndex, parentIndex);
                                parentIndex = lIndex;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            if (gt(right, parent)) {
                                swape(rIndex, parentIndex);
                                parentIndex = rIndex;
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
                else {
                    break;
                }
            }
        }

        private void swape(int index1, int index2) {
            Edge item1 = datas.get(index1);
            Edge item2 = datas.get(index2);
            datas.set(index1, item2);
            datas.set(index2, item1);
        }

        private boolean gt(Edge item1, Edge item2) {
            if (item1.h != item2.h) {
                return item1.h > item2.h;
            }
            else if (item1.x != item2.x) {
                return item1.x < item2.x;
            }
            else {
                return item1.isStart;
            }
        }

        public Edge top() {
            if (isEmpty()) {
                return null;
            }
            else {
                return datas.get(0);
            }
        }

        public boolean isEmpty() {
            return datas.isEmpty();
        }

        private int parentIndex(int index) {
            return index == 0? -1: (index - 1) / 2;
        }

        private int leftChildIndex(int index) {
            return index * 2 + 1;
        }

        private int rightChildIndex(int index) {
            return index * 2 + 2;
        }

    }

    private class Edge {
        public int x;
        public int h;
        public boolean isStart;
        public Integer id;

        public Edge(int x, int h, boolean isStart, int id) {
            this.h = h;
            this.isStart = isStart;
            this.x = x;
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (this == obj) {
                return true;
            }
            return obj instanceof Edge && ((Edge) obj).id.equals(this.id);
        }

        @Override
        public String toString() {
            return " " + x + "," + h + "," + isStart + " id=" + id;
        }
    }



    /**
     * http://www.lintcode.com/zh-cn/problem/building-outline/
     * 使用重新实现的BasicHeap
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public ArrayList<ArrayList<Integer>> buildingOutline2(int[][] buildings) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (buildings != null && buildings.length > 0) {
            int len = buildings.length;

            ArrayList<Edge> edges = new ArrayList<Edge>();
            for (int i = 0; i < len; i++) {
                int[] building = buildings[i];
                edges.add(new Edge(building[0], building[2], true, i));
                edges.add(new Edge(building[1], building[2], false, i));
            }
            Collections.sort(edges, new java.util.Comparator<Edge>() {
                public int compare(Edge item1, Edge item2) {
                    if (item1.x != item2.x) {
                        return item1.x - item2.x;
                    }
                    else if (item1.isStart == item2.isStart) {
                        return item1.h - item2.h;
                    }
                    else {
                        return item1.isStart? -1: 1;
                    }
                }
            });

            BasicHeap<Edge> heap = new BasicHeap<>(new java.util.Comparator<Edge>() {
                @Override
                public int compare(Edge item1, Edge item2) {
                    if (item1.h != item2.h) {
                        return item2.h - item1.h;
                    }
                    else if (item1.x != item2.x) {
                        return item2.x - item1.x;
                    }
                    else {
                        return item1.isStart? 1: -1;
                    }
                }
            });
            int lastX = 0;
            for (Edge item: edges) {
                if (heap.isEmpty()) {
                    heap.push(item);
                    lastX = item.x;
                }
                else if (item.isStart) {
                    Edge top = heap.top();
                    if (item.h > top.h) {
                        addOutlineToResult(result, lastX, item.x, top.h);
                        lastX = item.x;
                    }
                    heap.push(item);
                }
                else {
                    heap.remove(item);
                    if (heap.isEmpty() || item.h > heap.top().h) {
                        addOutlineToResult(result, lastX, item.x, item.h);
                        lastX = item.x;
                    }
                }
            }
        }
        return result;
    }





    private ExpressionTreeNode edgeHeapToTreeNode(EdgeHeap heap) {
        if (heap == null || heap.datas.isEmpty()) {
            return null;
        }

        return edgeHeapToTreeNode(heap, 0);
    }

    private ExpressionTreeNode edgeHeapToTreeNode(EdgeHeap heap, int index) {
        if (index >= heap.datas.size()) {
            return null;
        }

        ExpressionTreeNode treeNode = new ExpressionTreeNode(heap.datas.get(index).toString());
        treeNode.left = edgeHeapToTreeNode(heap, index * 2 + 1);
        treeNode.right = edgeHeapToTreeNode(heap, index * 2 + 2);
        return treeNode;
    }












    /**
     * http://www.lintcode.com/zh-cn/problem/remove-duplicates-from-sorted-list/
     * @param head is the head of the linked list
     * @return: ListNode head of linked list
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head != null) {
            ListNode last = head;
            ListNode cur = head.next;
            last.next = null;
            while (cur != null) {
                if (cur.val == last.val) {
                    cur = cur.next;
                }
                else {
                    last.next = cur;
                    cur = cur.next;
                    last = last.next;
                    last.next = null;
                }
            }
        }

        return head;
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/remove-duplicates-from-sorted-list-ii/
     * @param head is the head of the linked list
     * @return: ListNode head of the linked list
     */
    public ListNode deleteDuplicatesII(ListNode head) {
        ListNode newHead = new ListNode(0);
        if (head != null) {
            ListNode cur0 = newHead;
            ListNode cur1 = head;
            ListNode cur2 = head.next;
            while (cur1 != null) {
                if (cur2 == null || cur1.val != cur2.val) {
                    cur0.next = cur1;
                    cur0 = cur0.next;
                    cur1 = cur2;
                    if (cur2 != null) {
                        cur2 = cur2.next;
                    }
                    cur0.next = null;
                }
                else {
                    int val = cur1.val;
                    cur1 = cur2.next;
                    while (cur1 != null && cur1.val == val) {
                        cur1 = cur1.next;
                    }
                    if (cur1 != null) {
                        cur2 = cur1.next;
                    }
                }
            }
        }
        return newHead.next;
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/unique-paths/
     * @param n, m: positive integer (1 <= n ,m <= 100)
     * @return an integer
     */
    public int uniquePaths(int m, int n) {
        if (m > 0 && n > 0) {
            int[] f = new int[n];
            f[0] = 1;
            for (int i = 0; i < m; i ++) {
                int prev = 0;
                for (int j = 0; j < n; j++) {
                    f[j] += prev;
                    prev = f[j];
                }
            }
            return f[n- 1];
        }
        return 0;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/unique-paths-ii/
     * @param grid: A list of lists of integers
     * @return: An integer
     */
    public int uniquePathsWithObstacles(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        if (m > 0 && n > 0) {
            int[] f = new int[n];
            f[0] = 1;
            for (int i = 0; i < m; i ++) {
                int prev = 0;
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        f[j] = 0;
                    }
                    else {
                        f[j] += prev;
                    }
                    prev = f[j];
                }
            }
            return f[n- 1];
        }
        return 0;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/jump-game/
     * @param arr list of integers
     * @return: The boolean answer
     */
    public boolean canJump(int[] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        return arr.length == 1 || canJump(arr, 0);
    }

    private boolean canJump(int[] arr, int startIndex) {
        int maxStep = arr[startIndex];
        int lastIndex = arr.length - 1;
        if (maxStep == 0) {
            return false;
        }

        for (int i = maxStep; i > 0; i--) {
            if (i + startIndex >= lastIndex) {
                return true;
            }
            else {
                if (canJump(arr, startIndex + i)) {
                    return true;
                }
            }
        }
        return false;
    }






    /**
     * http://www.lintcode.com/problem/jump-game-ii
     * @param arr: A list of lists of integers
     * @return: An integer
     */
    public int jump(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        return jump(arr, 0);
    }

    private int jump(int[] arr, int startIndex) {
        int maxStep = arr[startIndex];
        int lastIndex = arr.length - 1;
        if (maxStep == 0) {
            return -1;
        }

        if (maxStep + startIndex >= lastIndex) {
            return 1;
        }
        else {
            ArrayList<CurAndNextStep> steps = new ArrayList<>();
            for (int i = 1; i <= maxStep; i++) {
                steps.add(new CurAndNextStep(i, arr[startIndex + i]));
            }
            Collections.sort(steps, new java.util.Comparator<CurAndNextStep>() {
                @Override
                public int compare(CurAndNextStep o1, CurAndNextStep o2) {
                    return (o2.cur + o2.next) - (o1.cur + o1.next);
                }
            });

            for (int i = 0, size = steps.size(); i< size; i++) {
                int num = jump(arr, startIndex + steps.get(i).cur);
                if (num > 0) {
                    return num + 1;
                }
            }
        }

        return -1;
    }

    private class CurAndNextStep {
        public int cur;
        public int next;
        public CurAndNextStep(int cur, int next) {
            this.cur = cur;
            this.next = next;
        }
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/distinct-subsequences/
     * @param s, t: Two string.
     * @return: Count the number of distinct subsequences
     */
    public int numDistinct(String s, String t) {
        if (s == null || t == null) {
            return 0;
        }

        int sLen = s.length();
        int tLen = t.length();
        if (sLen < tLen || (sLen == tLen && !s.equals(t))) {
            return 0;
        }

        if (tLen == 0) {
            return 1;
        }

        int[][] f = new int[sLen][tLen];
        for (int i = 0; i < sLen; i++) {
            for (int j = 0; j <= i && j < tLen; j++) {
                if (i == 0) {
                    if (s.charAt(i) == t.charAt(j)) {
                        f[i][j] = 1;
                    }
                }
                else {
                    if (s.charAt(i) == t.charAt(j)) {
                        if (j == 0) {
                            f[i][j] = 1 + f[i - 1][j];
                        }
                        else {
                            f[i][j] = f[i - 1][j - 1];
                            if (i - 1 >= j) {
                                f[i][j] += f[i - 1][j];
                            }
                        }
                    }
                    else if (i - 1 >= j) {
                        f[i][j] = f[i - 1][j];
                    }
                }
            }
        }

        return f[sLen - 1][tLen - 1];
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/edit-distance/
     * @param word1 & word2: Two string.
     * @return: The minimum number of steps.
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        }
        else if (word1 == null || word1.isEmpty()) {
            return word2.length();
        }
        else if (word2 == null || word2.isEmpty()) {
            return word1.length();
        }

        int len1 = word1.length();
        int len2 = word2.length();
        /**
         f[i][j]表示word1[0,i]与word2[0,j]的最小编辑距离
         */
        int[][] f = new int[len1][len2];

        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    if (i == 0 && j == 0) {
                        f[i][j] = 0;
                    }
                    else if (i == 0){
                        f[i][j] = j;
                    }
                    else if (j == 0) {
                        f[i][j] = i;
                    }
                    else {
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
                else {
                    if (i == 0 && j == 0) {
                        f[i][j] = 1;
                    }
                    else if (i == 0) {
                        f[i][j] = f[i][j - 1] + 1;
                    }
                    else if (j == 0) {
                        f[i][j] = f[i - 1][j] + 1;
                    }
                    else {
                        int temp0 = f[i - 1][j - 1] + 1;
                        int temp1 = f[i][j - 1] + 1;
                        int temp2 = f[i - 1][j] + 1;
                        f[i][j] = Math.min(Math.min(temp0, temp1), temp2);
                    }
                }
            }
        }

        return f[len1 - 1][len2 - 1];
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/word-ladder/
     * @param start, a string
     * @param end, a string
     * @param dict, a set of string
     * @return an integer
     */
    public int ladderLength(String start, String end, Set<String> dict) {
        if (start != null && end != null) {
            if (start.equals(end)) {
                return 1;
            }
            else if (dict.contains(end) && canStrTranslate(start, end)) {
                return 2;
            }
            else {
                ArrayList<String> dictArr = new ArrayList<>();
                dictArr.addAll(dict);
                ArrayList<TreeLevel> queue = new ArrayList<>();
                queue.add(new TreeLevel(start, 1));
                while (!queue.isEmpty()) {
                    TreeLevel cur = queue.remove(0);
                    int curLevel = cur.level;
                    if (canStrTranslate(cur.val, end)) {
                        return curLevel + 1;
                    }
                    else if (!dictArr.isEmpty()) {
                        for (int i = 0; i < dictArr.size(); i++) {
                            String item = dictArr.get(i);
                            if (item.equals(cur.val)) {
                                dictArr.remove(i);
                            }
                            else if (canStrTranslate(cur.val, item)) {
                                queue.add(new TreeLevel(item, curLevel + 1));
                                dictArr.remove(i);
                            }
                        }
                    }
                }
            }
        }

        return 0;
    }

    private class TreeLevel {
        public String val;
        public int level;
        public ArrayList<String> history;

        public TreeLevel(String val, int level) {
            this(val, level, false);
        }

        public TreeLevel(String val, int level, boolean hasHistory) {
            this.level = level;
            this.val = val;
            if (hasHistory) {
                history = new ArrayList<>();
            }
        }

        @Override
        public String toString() {
            return "val=" + val + ",level=" + level;
        }
    }

    private boolean canStrTranslate(String from, String to) {
        int diffNum = 0;
        for (int i = 0, len = from.length(); i < len; i++) {
            if (from.charAt(i) != to.charAt(i)) {
                diffNum += 1;
                if (diffNum > 1) {
                    return false;
                }
            }
        }
        return true;
    }




    /**
     * http://www.lintcode.com/zh-cn/problem/word-ladder-ii/
     * @param start, a string
     * @param end, a string
     * @param dict, a set of string
     * @return a list of lists of string
     */
    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        List<List<String>> result = new ArrayList<>();
        if (start != null && end != null) {
            if (start.equals(end)) {
                List<String> tempList = new ArrayList<>();
                tempList.add(start);
                result.add(tempList);
            }
            else if (dict.contains(end) && canStrTranslate(start, end)) {
                List<String> tempList = new ArrayList<>();
                tempList.add(start);
                tempList.add(end);
                result.add(tempList);
            }
            else {
                dict.remove(start);
                dict.remove(end);
                ArrayList<String> dictArr = new ArrayList<>();
                dictArr.addAll(dict);
                ArrayList<TreeLevel> queue = new ArrayList<>();
                TreeLevel startLevel = new TreeLevel(start, 1, true);
                startLevel.history.add(start);
                queue.add(startLevel);

                int minLevel = Integer.MAX_VALUE;

                int lastLevel = 1;
                Set<String> lastLevelWord = new HashSet<>();

                while (!queue.isEmpty()) {
                    TreeLevel cur = queue.remove(0);
                    int curLevel = cur.level;
                    if (curLevel >= minLevel) {
                        break;
                    }

                    if (lastLevel != curLevel) {
                        lastLevel = curLevel;
                        dictArr.removeAll(lastLevelWord);
                        lastLevelWord.clear();
                    }

                    if (canStrTranslate(cur.val, end)) {
                        if (minLevel > curLevel + 1) {
                            minLevel = curLevel + 1;
                        }

                        ArrayList<String> resultItem = new ArrayList<>();
                        resultItem.addAll(cur.history);
                        resultItem.add(end);
                        result.add(resultItem);
                    }
                    else if (!dictArr.isEmpty()) {
                        for (String item: dictArr) {
                            if (canStrTranslate(cur.val, item)) {
                                TreeLevel tempLevel = new TreeLevel(item, curLevel + 1, true);
                                tempLevel.history.addAll(cur.history);
                                tempLevel.history.add(item);
                                queue.add(tempLevel);
                                lastLevelWord.add(item);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }










//    /**超时
//     * http://www.lintcode.com/zh-cn/problem/largest-rectangle-in-histogram/
//     * @param heights: A list of integer
//     * @return: The area of largest rectangle in the histogram
//     */
//    public int largestRectangleArea(int[] heights) {
//        if (heights == null || heights.length == 0) {
//            return 0;
//        }
//
//        int maxArea = 0;
//        int tempArea = 0;
//        HashMap<Integer, Boolean> isComputed = new HashMap<>();
//        for (int height: heights) {
//            if (!isComputed.containsKey(height)) {
//                isComputed.put(height, true);
//
//                tempArea = 0;
//                for (int h: heights) {
//                    if (h >= height) {
//                        tempArea += height;
//                        if (tempArea > maxArea) {
//                            maxArea = tempArea;
//                        }
//                    }
//                    else {
//                        tempArea = 0;
//                    }
//                }
//            }
//        }
//
//        return maxArea;
//    }

    /**
     * http://www.lintcode.com/zh-cn/problem/largest-rectangle-in-histogram/
     * @param heights: A list of integer
     * @return: The area of largest rectangle in the histogram
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int maxArea = 0;
        ArrayList<HeightAndTotal> heightAndTotals = new ArrayList<>();
        for (int i = 0, len = heights.length; i < len; i++) {
            int height = heights[i];
            if (height == 0) {
                heightAndTotals.clear();
            }
            else {
                if (heightAndTotals.isEmpty()) {
                    heightAndTotals.add(new HeightAndTotal(height, height));
                    if (maxArea < height) {
                        maxArea = height;
                    }
                }
                else {
                    HeightAndTotal newItem = new HeightAndTotal(height, height);

                    int geNum = 0;
                    for (int j = heightAndTotals.size() - 1; j > -1; j--) {
                        HeightAndTotal tempItem = heightAndTotals.get(j);
                        if (tempItem.height >= height) {
                            geNum = tempItem.total / tempItem.height;
                            heightAndTotals.remove(j);
                        }
                        else {
                            tempItem.total += tempItem.height;
                            if (tempItem.total > maxArea) {
                                maxArea = tempItem.total;
                            }
                        }
                    }

                    if (geNum > 0) {
                        newItem.total += height * geNum;
                    }
                    if (newItem.total > maxArea) {
                        maxArea = newItem.total;
                    }
                    heightAndTotals.add(newItem);
                }
            }

        }

        return maxArea;
    }

    private class HeightAndTotal {
        public int height;
        public int total;
        public HeightAndTotal(int height, int total) {
            this.height = height;
            this.total = total;
        }

        @Override
        public String toString() {
            return height + ", " + total;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/word-search/
     * @param board: A list of lists of character
     * @param word: A string
     * @return: A boolean
     */
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0) {
            return word == null || word.isEmpty();
        }

        int lenI = board.length;
        int lenJ = board[0].length;
        for (int i = 0; i < lenI; i++) {
            for (int j = 0; j < lenJ; j++) {
                if (exist(board, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean exist(char[][] board, String word, int i, int j, int index) {
        int lenI = board.length;
        int lenJ = board[0].length;
        if (i < 0 || i >= lenI
                || j < 0 || j >= lenJ) {
            return false;
        }

        char c = board[i][j];
        if (c == '\0') {
            return false;
        }

        int lenW = word.length();
        if (c == word.charAt(index)) {
            if (index + 1 == lenW) {
                return true;
            }

            board[i][j] = '\0';

            //检查上下左右
            if (exist(board, word, i - 1, j, index + 1)) {
                board[i][j] = c;
                return true;
            }
            if (exist(board, word, i + 1, j, index + 1)) {
                board[i][j] = c;
                return true;
            }
            if (exist(board, word, i, j - 1, index + 1)) {
                board[i][j] = c;
                return true;
            }
            if (exist(board, word, i, j + 1, index + 1)) {
                board[i][j] = c;
                return true;
            }
            board[i][j] = c;
        }
        return false;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/longest-consecutive-sequence/
     * @param nums: A list of integers
     * @return an integer
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int i: nums) {
            map.put(i, false);
        }

        int max = 0;

        Set<Map.Entry<Integer, Boolean>> keyVals = map.entrySet();
        for (Map.Entry<Integer, Boolean> keyVal: keyVals) {
            if (Boolean.FALSE.equals(keyVal.getValue())) {
                Integer key = keyVal.getKey();
                map.put(key, true);

                int tempMax = 1;
                int tempKey = key - 1;
                while (Boolean.FALSE.equals(map.get(tempKey))) {
                    map.put(tempKey, true);
                    tempMax++;
                    tempKey--;
                }

                tempKey = key + 1;
                while (Boolean.FALSE.equals(map.get(tempKey))) {
                    map.put(tempKey, true);
                    tempMax++;
                    tempKey++;
                }

                if (tempMax > max) {
                    max = tempMax;
                }
            }
        }
        return max;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/backpack-ii/
     * @param maxW: An integer m denotes the size of a backpack
     * @param ws & vs: Given n items with size ws[i] and value vs[i]
     * @return: The maximum value
     */
    public int backPackII(int maxW, int[] ws, int[] vs) {
        if (ws == null || ws.length == 0) {
            return 0;
        }

        HashMap<String, Integer> map = new HashMap<>();
        return backPackMaxVal(map, ws, vs, ws.length - 1, maxW);
    }

    private int backPackMaxVal(HashMap<String, Integer> map, int[] ws, int[] vs, int i, int j) {
        String key = i + "," + j;
        if (map.containsKey(key)) {
            return map.get(key);
        }

        int result = 0;
        if (i < 0 || j == 0) {
            result = 0;
        }
        else if (ws[i] <= j) {
            int temp0 = backPackMaxVal(map, ws, vs, i - 1, j);
            int temp1 = backPackMaxVal(map, ws, vs, i - 1, j - ws[i]) + vs[i];
            result = temp0 >= temp1? temp0: temp1;
        }
        else {
            result = backPackMaxVal(map, ws, vs, i - 1, j);
        }
        map.put(key, result);
        return result;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/topological-sorting/
     * @param graph: A list of Directed graph node
     * @return: Any topological order for the given graph.
     */
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> result = new ArrayList<>();
        if (graph != null && graph.size() != 0) {
            final HashMap<DirectedGraphNode, Integer> map = new HashMap<>();
            for (DirectedGraphNode node: graph) {
                directedGraphVisit(node, map);
            }

            for(Map.Entry<DirectedGraphNode, Integer> keyVal: map.entrySet()) {
                result.add(keyVal.getKey());
            }
            Collections.sort(result, new java.util.Comparator<DirectedGraphNode>() {
                @Override
                public int compare(DirectedGraphNode o1, DirectedGraphNode o2) {
                    return map.get(o2) - map.get(o1);
                }
            });
        }
        return result;
    }

    private int directedGraphVisit(DirectedGraphNode node, HashMap<DirectedGraphNode, Integer> map) {
        if (!map.containsKey(node)) {
            int index = 0;
            if (node.neighbors.isEmpty()) {
                index = 0;
            }
            else {
                for (DirectedGraphNode child: node.neighbors) {
                    int temp = directedGraphVisit(child, map);
                    if (index < temp) {
                        index = temp;
                    }
                }
                index += 1;
            }

            map.put(node, index);
            return index;
        }
        else {
            return map.get(node);
        }
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/hash-function/
     * @param key: A String you should hash
     * @param HASH_SIZE: An integer
     * @return an integer
     */
    public int hashCode(char[] key,int HASH_SIZE) {
        if (key == null || key.length == 0 || HASH_SIZE <= 0) {
            return 0;
        }

        //(a + b) % c = (a % c + b % c) % c = (a % c + b) % c
        //如果不利用这个公式，则很有可能在计算的过程中溢出
        //hash也必须是long，否则也会溢出
        long hash = 0;
        for(int i = 0, len = key.length; i < len; i++) {
            hash = (hash * 33 + key[i]) % HASH_SIZE;
        }
        return (int) hash;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/rehashing/
     * @param hashTable: A list of The first node of linked list
     * @return: A list of The first node of linked list which have twice size
     */
    public ListNode[] rehashing(ListNode[] hashTable) {
        if (hashTable == null || hashTable.length == 0) {
            return null;
        }

        int newLen = hashTable.length * 2;
        ListNode[] newHashTable = new ListNode[newLen];
        for (ListNode listNode: hashTable) {
            ListNode temp = listNode;
            while (temp != null) {
                int hash = hash(temp.val, newLen);
                if (newHashTable[hash] == null) {
                    newHashTable[hash] = temp;
                }
                else {
                    ListNode tail = newHashTable[hash];
                    while (tail.next != null) {
                        tail = tail.next;
                    }
                    tail.next = temp;
                }
                ListNode tempNext = temp.next;
                temp.next = null;
                temp = tempNext;
            }
        }
        return newHashTable;
    }


    private int hash(int key, int size) {
        if (key >= 0) {
            return key % size;
        }
        else {
            return (key % size + size) % size;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/heapify/
     * @param arr: Given an integer array
     * @return: void
     */
    public void heapify(int[] arr) {
        if (arr != null && arr.length > 0) {
            for (int i = arr.length / 2; i > -1; i--) {
                siftdown(arr, i);
            }
        }
    }

    private void swap(int[] arr, int i0, int i1) {
        int temp = arr[i0];
        arr[i0] = arr[i1];
        arr[i1] = temp;
    }

    private void siftdown(int[] arr, int parent) {
        int len = arr.length;
        while (parent < len) {
            int smallIndex = parent;
            int lIndex = parent * 2 + 1;
            int rIndex = parent * 2 + 2;
            if (lIndex < arr.length && arr[lIndex] < arr[smallIndex]) {
                smallIndex = lIndex;
            }
            if (rIndex < arr.length && arr[rIndex] < arr[smallIndex]) {
                smallIndex = rIndex;
            }

            if (smallIndex == parent) {
                break;
            }

            swap(arr, parent, smallIndex);
            parent = smallIndex;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/word-search-ii/
     * @param board: A list of lists of character
     * @param words: A list of string
     * @return: A list of string
     */
    public ArrayList<String> wordSearchII(char[][] board, ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();
        if (board != null && board.length != 0) {
            DicTreeNode dicRoot = DicTreeNode.buildFrom(words);
            int row = board.length;
            int column = board[0].length;

            HashSet<String> resultSet = new HashSet<>();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    findWord(dicRoot, dicRoot, board, i, j, resultSet);
                }
            }

            for (String word: words) {
                if (resultSet.contains(word)) {
                    result.add(word);
                }
            }
        }
        return result;
    }

    private void findWord(DicTreeNode dicRoot, DicTreeNode curNode, char[][] board, int i, int j, HashSet<String> result) {
        if (i >= 0 && j >= 0) {
            int row = board.length;
            int column = board[0].length;
            if (i < row && j < column) {
                char c = board[i][j];

                if (c != '\0') {
                    board[i][j] = '\0';
                    DicTreeNode node = curNode.child(c);
                    if (node != null) {
                        if (node.hasTailChild()) {
                            String word = node.getTailChild().word;
                            result.add(word);
                            dicRoot.deleteWord(word);
                        }

                        //检索上下左右
                        findWord(dicRoot, node, board, i, j - 1, result);
                        findWord(dicRoot, node, board, i, j + 1, result);
                        findWord(dicRoot, node, board, i - 1, j, result);
                        findWord(dicRoot, node, board, i + 1, j, result);
                    }
                    //对矩阵的位置信息进行回溯
                    board[i][j] = c;
                }

            }
        }
    }


    private static class DicTreeNode {

        //0: root
        //1: normal node
        //-1: tail
        private final int nodeType;

        //仅当 nodeType = -1 的时候有值
        private final String word;

        private final char value;

        private final HashMap<String, DicTreeNode> children = new HashMap<>();

        public static DicTreeNode newRoot() {
            return new DicTreeNode(0);
        }

        public static DicTreeNode newNode(char value) {
            return new DicTreeNode(value);
        }

        public static DicTreeNode newTail(String word) {
            return new DicTreeNode(word);
        }

        private DicTreeNode(int nodeType) {
            this.nodeType = nodeType;
            value = '\0';
            word = null;
        }

        private DicTreeNode(char value) {
            nodeType = 1;
            this.value = value;
            word = null;
        }

        private DicTreeNode(String word) {
            nodeType = -1;
            value = '\0';
            this.word = word;
        }

        public void addChild(DicTreeNode child) {
            children.put(child.value + "", child);
        }

        public DicTreeNode child(char value) {
            String key = value + "";
            if (children.containsKey(key)) {
                return children.get(key);
            }
            else return null;
        }

        private DicTreeNode tailChild;

        public boolean hasTailChild() {
            tailChild = children.get("\0");
            return tailChild != null;
        }

        public DicTreeNode getTailChild() {
            if (tailChild != null) {
                return tailChild;
            }
            else return children.get("\0");
        }

        public void deleteWord(String word) {
            checkNode(word, 0);
        }

        private boolean checkNode(String word, int index) {
            if (index == word.length()) {
                children.remove("\0");
                return children.isEmpty();
            }

            DicTreeNode child = child(word.charAt(index));
            if (child != null && child.checkNode(word, index + 1)) {
                children.remove("" + child.value);
                return children.isEmpty();
            }
            else return false;
        }

        public static DicTreeNode buildFrom(ArrayList<String> dics) {
            DicTreeNode root = newRoot();

            for (String dic: dics) {
                DicTreeNode curNode = root;
                DicTreeNode temp;
                for (int i = 0, len = dic.length(); i < len; i++) {
                    char c = dic.charAt(i);
                    if ((temp = curNode.child(c)) != null) {
                        curNode = temp;
                    }
                    else {
                        DicTreeNode newChild = newNode(c);
                        curNode.addChild(newChild);
                        curNode = newChild;
                    }
                }
                curNode.addChild(newTail(dic));
            }

            return root;
        }

    }


    /**
     * http://www.lintcode.com/zh-cn/problem/longest-words/
     * @param dictionary an array of strings
     * @return
     */
    public ArrayList<String> longestWords(String[] dictionary) {
        ArrayList<String> result = new ArrayList<>();

        if (dictionary != null && dictionary.length > 0) {
            int maxLen = 0;
            for (String word: dictionary) {
                int wLen = word.length();
                if (wLen > maxLen) {
                    maxLen = word.length();
                    result.clear();
                    result.add(word);
                }
                else if (wLen == maxLen) {
                    result.add(word);
                }
            }
        }

        return result;
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/combination-sum/
     * @param arr: A list of integers
     * @param target:An integer
     * @return: A list of lists of integers
     */
    public List<List<Integer>> combinationSum(int[] arr, int target) {
        List<List<Integer>> result = new ArrayList<>();

        if (arr != null && arr.length != 0) {
            Arrays.sort(arr);
            findCombinationSum(arr, target, new Stack<Integer>(), result);
            HashMap<String, Boolean> existed = new HashMap<>();
            for (int i = 0; i < result.size();) {
                List<Integer> resultItem = result.get(i);
                StringBuilder strBld = new StringBuilder();
                for(Integer j: resultItem) {
                    strBld.append(j).append(',');
                }
                String key = strBld.toString();
                if (existed.containsKey(key)) {
                    result.remove(i);
                }
                else {
                    existed.put(key, true);
                    i++;
                }
            }
        }
        return result;
    }

    private void findCombinationSum(int[] arr, int target, Stack<Integer> stack, List<List<Integer>> result) {
        if ((stack.isEmpty() || stack.peek() <= target) && binarySearchFrom(arr, target, 0, arr.length - 1)) {
            stack.push(target);
            ArrayList<Integer> resultItem = new ArrayList<>();
            resultItem.addAll(stack);
            result.add(resultItem);
            stack.pop();
        }

        if (target > arr[0]) {
            for (int i = 0, len = arr.length; i < len; i++) {
                int item = arr[i];
                if (item > target) {
                    break;
                }
                else if (stack.isEmpty() || stack.peek() <= item) {
                    stack.push(item);
                    findCombinationSum(arr, target - item, stack, result);
                    stack.pop();
                }
            }
        }
    }

    private boolean binarySearchFrom(int[] arr, int target, int left, int right) {
        if (left > right) {
            return false;
        }

        int middle = (left + right) / 2;
        int middleVal = arr[middle];
        if (middleVal == target) {
            return true;
        }
        else if (middleVal > target) {
            return binarySearchFrom(arr, target, left, middle - 1);
        }
        else {
            return binarySearchFrom(arr, target, middle + 1, right);
        }
    }








    /**
     * http://www.lintcode.com/zh-cn/problem/palindrome-partitioning/
     * @param str: A string
     * @return: A list of lists of string
     */
    public List<List<String>> partition(String str) {
        List<List<String>> result = new ArrayList<>();
        if (str != null && str.length() > 0) {
            for (int i = 0, len = str.length(); i < len; i++) {
                if (isSubStrBackWord(str, 0, i)) {
                    if (i < len - 1) {
                        List<List<String>> subResult = partition(str.substring(i + 1));
                        String subStr = str.substring(0, i + 1);
                        for (List<String> resultItem: subResult) {
                            resultItem.add(0, subStr);
                            result.add(resultItem);
                        }
                    }
                    else {
                        List<String> resultItem = new ArrayList<>();
                        resultItem.add(str);
                        result.add(resultItem);
                    }
                }
            }
        }
        return result;
    }

    private boolean isSubStrBackWord(String str, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }
        return true;
    }




    /**
     * http://www.lintcode.com/zh-cn/problem/clone-graph/
     * @param node: A undirected graph node
     * @return: A undirected graph node
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node != null) {
            HashMap<UndirectedGraphNode, UndirectedGraphNode> existed = new HashMap<>();
            return cloneGraph(node, existed);
        }
        else return null;
    }

    private UndirectedGraphNode cloneGraph(UndirectedGraphNode node, HashMap<UndirectedGraphNode, UndirectedGraphNode> existed) {
        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
        existed.put(node, newNode);
        for (UndirectedGraphNode neighbour: node.neighbors) {
            if (existed.containsKey(neighbour)) {
                newNode.neighbors.add(existed.get(neighbour));
            }
            else {
                newNode.neighbors.add(cloneGraph(neighbour, existed));
            }
        }
        return newNode;
    }

    private static class UndirectedGraphNode {
        public int label;
        public ArrayList<UndirectedGraphNode> neighbors;
        public UndirectedGraphNode(int label) {
            this.label = label;
            this.neighbors = new ArrayList<>();
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/subarray-sum/
     * @param nums: A list of integers
     * @return: A list of integers includes the index of the first number
     *          and the index of the last number
     */
    public ArrayList<Integer> subarraySum(int[] nums) {
        ArrayList<Integer> result = new ArrayList<>();
        if (nums != null && nums.length > 0) {
            HashMap<Integer, Integer> sumMap = new HashMap<>();
            int sum = 0;
            for (int i = 0, len = nums.length; i < len; i++) {
                int num = nums[i];
                if (num == 0) {
                    result.add(i);
                    result.add(i);
                    return result;
                }
                else {
                    sum += num;
                    if (sum == 0) {
                        result.add(0);
                        result.add(i);
                        return result;
                    }
                    else if (sumMap.containsKey(sum)) {
                        result.add(sumMap.get(sum) + 1);
                        result.add(i);
                        return result;
                    }
                    else {
                        sumMap.put(sum, i);
                    }
                }
            }
        }
        return result;
    }






    /**
     * @param nums: A list of integers
     * @return: A list of integers includes the index of the first number
     *          and the index of the last number
     */
    public int[] subarraySumClosest(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        else if (nums.length == 1) {
            return new int[]{0, 0};
        }

        int[] result = {0,0};
        ArrayList<IndexAndSum> indexAndSumArr = new ArrayList<>();
        int total = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            total += nums[i];
            if (total == 0) {
                result[1] = i;
                return result;
            }
            else {
                indexAndSumArr.add(new IndexAndSum(i, total));
            }
        }
        Collections.sort(indexAndSumArr, new java.util.Comparator<IndexAndSum>() {
            @Override
            public int compare(IndexAndSum o1, IndexAndSum o2) {
                return o1.sum - o2.sum;
            }
        });

        int min = Integer.MAX_VALUE;
        for (int i = 0, size = indexAndSumArr.size(); i < size - 1; i++) {
            IndexAndSum cur = indexAndSumArr.get(i);
            IndexAndSum next = indexAndSumArr.get(i + 1);
            int temp = Math.abs(cur.sum - next.sum);
            if (temp < min) {
                min = temp;
                result[0] = Math.min(cur.index, next.index) + 1;
                result[1] = Math.max(cur.index, next.index);
            }
        }

        return result;
    }

    private class IndexAndSum {
        public final int index;
        public final int sum;
        public IndexAndSum(int index, int sum) {
            this.index = index;
            this.sum = sum;
        }
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/fast-power/
     * @param a, b, n: 32bit integers
     * @return: An integer
     */
    public int fastPower(int a, int b, int n) {
        if (a == 0) {
            return 0;
        }
        else if (n == 0) {
            return 1 % b;
        }
        else if (n == 1) {
            return a % b;
        }
        else {
            int temp = fastPower(a, b, n / 2);
            temp = modMult(temp, temp, b);
            if (n % 2 == 0) {
                return temp;
            }
            else {
                return modMult(a, temp, b);
            }
        }
    }

    private int modMult(int a, int b, int m) {
        if (a == 0 || b == 0) {
            return 0;
        }

        if (Integer.MAX_VALUE / Math.abs(b) >= Math.abs(a)) {
            return a * b % m;
        }
        else {
            int temp = modMult(a / 2, b, m);
            temp = modSum(temp, temp, m);
            if (a % 2 == 0) {
                return temp;
            }
            else {
                return modSum(temp, b, m);
            }
        }
    }

    private int modSum(int a, int b, int m) {
        int tempA = Math.max(a, b);
        int tempB = Math.min(a, b);
        int absM = Math.abs(m);
        a = tempA;
        b = tempB;
        if (a >= 0) {
            while (b > Integer.MAX_VALUE - a) {
                b -= absM;
            }
        }
        else if (a < 0) {
            while (b < Integer.MIN_VALUE - a) {
                b += absM;
            }
        }
        return (a + b) % m;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/sqrtx/
     * @param x: An integer
     * @return: The sqrt of x
     */
    public int sqrt(int x) {
        return findSqrt(x, 0, x);
    }

    private int findSqrt(int x, int left, int right) {
        if (left == right) {
            return left;
        }

        int middle = (left + right) / 2;
        if (middle > 0 && Integer.MAX_VALUE / middle < middle) {
            return findSqrt(x, left, middle - 1);
        }

        int mm = middle * middle;
        if (mm == x) {
            return middle;
        }
        else if (mm < x) {
            if ((middle + 1) * (middle + 1) > x) {
                return middle;
            }
            else return findSqrt(x, middle + 1, right);
        }
        else {
            if ((middle - 1) * (middle - 1) <= x) {
                return middle - 1;
            }
            else return findSqrt(x, left, middle - 1);
        }
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/o1-check-power-of-2/
     * @param n: An integer
     * @return: True or false
     */
    public boolean checkPowerOf2(int n) {
        int totalOf1 = 0;
        while (n > 0) {
            totalOf1 += n & 1;
            n >>= 1;
        }
        return totalOf1 == 1;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/sort-colors-ii/
     * @param colors: A list of integer
     * @param k: An integer
     * @return: nothing
     */
    public void sortColors2(int[] colors, int k) {
        int sorted = 0;
        int start = 0;
        int end = colors.length - 1;
        while (sorted < k) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (int i = start; i <= end; i++) {
                min = Math.min(min, colors[i]);
                max = Math.max(max, colors[i]);
            }

            int left = start;
            int right = end;
            int cur = left;
            while (cur <= right) {
                int curColor = colors[cur];
                if (curColor == min) {
                    swapColor(colors, left, cur);
                    cur++;
                    left++;
                }
                else if (curColor == max) {
                    swapColor(colors, right, cur);
                    right--;
                }
                else {
                    cur++;
                }
            }

            sorted += 2;
            start = left;
            end = right;
        }
    }

    private void swapColor(int[] colors, int i, int j) {
        int temp = colors[i];
        colors[i] = colors[j];
        colors[j] = temp;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/interleaving-positive-and-negative-numbers/
     * @param arr: An integer array.
     * @return: void
     */
    public void rerange(int[] arr) {
        if (arr != null && arr.length > 1) {
            int len = arr.length;
            for (int i = 0; i < len - 1; i++) {
                int temp = arr[i + 1];
                if (!isDiffTag(arr[i], temp)) {
                    for (int j = i + 2; j < len; j++) {
                        if (isDiffTag(temp, arr[j])) {
                            swapRerange(arr, i + 1, j);
                            break;
                        }
                    }
                }
            }

            if (!isDiffTag(arr[len - 2], arr[len - 1])) {
                for (int i = 0; i < len - 2; i++) {
                    swapRerange(arr, i, i + 1);
                }
            }
        }
    }

    private boolean isDiffTag(int i, int j) {
        return (i > 0 && j < 0) || (i <0 && j > 0);
    }

    private void swapRerange(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/sort-colors/
     * @param nums: A list of integer which is 0, 1 or 2
     * @return: nothing
     */
    public void sortColors(int[] nums) {
        if (nums != null && nums.length != 0) {
            int len = nums.length;
            int left = 0;
            int right = len - 1;
            for (int i = left; i <= right;) {
                int cur = nums[i];
                if (cur == 0) {
                    swapColorI(nums, left, i);
                    left++;
                    i++;
                }
                else if (cur == 2) {
                    swapColorI(nums, right, i);
                    right--;
                }
                else {
                    i++;
                }
            }
        }
    }

    private void swapColorI(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/best-time-to-buy-and-sell-stock/
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        int maxDelta = 0;
        if (prices != null && prices.length > 0) {
            int min = Integer.MAX_VALUE;
            for (int i = 0, len = prices.length; i < len; i++) {
                int cur = prices[i];

                if (min > cur) {
                    min = cur;
                }
                if (cur - min > maxDelta) {
                    maxDelta = cur - min;
                }
            }
        }
        return maxDelta;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/best-time-to-buy-and-sell-stock-ii/
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfitII(int[] prices) {
        int total = 0;
        if (prices != null && prices.length > 0) {
            int min = prices[0];
            for (int i = 0, len = prices.length; i < len - 1; i++) {
                int cur = prices[i];
                int next = prices[i + 1];
                if (cur > next) {
                    total += cur - min;
                    min = next;
                }
                else if (i == len - 2) {
                    total += next - min;
                }
            }
        }
        return total;
    }






    /**
     * http://www.lintcode.com/zh-cn/problem/best-time-to-buy-and-sell-stock-iii/
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfitIII(int[] prices) {
        int max = 0;
        if (prices != null && prices.length > 0) {
            int len = prices.length;
            if (len >= 2) {
                for (int i = 0; i < len - 1; i++) {
                    int cur = prices[i];
                    int next = prices[i + 1];
                    if (cur > next) {
                        int temp = maxProfit(prices, 0, i) + maxProfit(prices, i + 1, len - 1);
                        if (temp > max) {
                            max = temp;
                        }
                    }
                    else {
                        int temp = maxProfit(prices, 0, i + 1);
                        if (temp > max) {
                            max = temp;
                        }
                    }
                }
            }
        }
        return max;
    }

    public int maxProfit(int[] prices, int left, int right) {
        int maxDelta = 0;
        if (prices != null && prices.length > 0) {
            int min = Integer.MAX_VALUE;
            for (int i = left; i <= right; i++) {
                int cur = prices[i];

                if (min > cur) {
                    min = cur;
                }
                if (cur - min > maxDelta) {
                    maxDelta = cur - min;
                }
            }
        }
        return maxDelta;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/combinations/
     * @param n: Given the range of numbers
     * @param k: Given the numbers of combinations
     * @return: All the combinations of k numbers out of 1..n
     */
    public List<List<Integer>> combine(int n, int k) {
        if (k > 0 && n >= k) {
            return combine(1, n, k);
        }
        return new ArrayList<>();
    }

    public List<List<Integer>> combine(int left, int right, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (k > 0 && right - left + 1 >= k) {
            List<List<Integer>> tempResult0 = combine(left + 1, right, k - 1);
            for (List<Integer> resultItem: tempResult0) {
                resultItem.add(0, left);
                result.add(resultItem);
            }

            List<List<Integer>> tempResult1 = combine(left + 1, right, k);
            if (tempResult1.size() > 0) {
                result.addAll(tempResult1);
            }
        }
        else if (k == 0) {
            result.add(new ArrayList<Integer>());
        }
        return result;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/combination-sum-ii/
     * @param arr: Given the candidate numbers
     * @param target: Given the target number
     * @return: All the combinations that sum to target
     */
    public List<List<Integer>> combinationSum2(int[] arr, int target) {
        if (arr == null) {
            return new ArrayList<>();
        }
        Arrays.sort(arr);
        List<List<Integer>> result = findCombination(arr, target, 0);
        HashMap<String, Boolean> existed = new HashMap<>();
        for (int i = 0; i < result.size();) {
            StringBuilder strBld = new StringBuilder();
            List<Integer> item = result.get(i);
            for (Integer j: item) {
                strBld.append(j).append(',');
            }
            String key = strBld.toString();
            if (existed.containsKey(key)) {
                result.remove(i);
            }
            else {
                existed.put(key, true);
                i++;
            }
        }
        return result;
    }

    private List<List<Integer>> findCombination(int[] arr, int target, int left) {
        List<List<Integer>> result = new ArrayList<>();
        int len = arr.length;
        if (target != 0 && left < len && arr[left] <= target) {
            List<List<Integer>> tempResult0 = findCombination(arr, target - arr[left], left + 1);
            for (List<Integer> resultItem: tempResult0) {
                resultItem.add(0, arr[left]);
                result.add(resultItem);
            }

            List<List<Integer>> tempResult1 = findCombination(arr, target, left + 1);
            if (tempResult1.size() > 0) {
                result.addAll(tempResult1);
            }
        }
        else if (target == 0){
            result.add(new ArrayList<Integer>());
        }
        return result;
    }





    /**
     * http://www.lintcode.com/zh-cn/problem/regular-expression-matching/
     * @param s: A string
     * @param p: A string includes "." and "*"
     * @return: A boolean
     */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        return isMatch(s, 0, p, 0);
    }

    public boolean isMatch(String s, int sIndex, String p, int pIndex) {
        int sLen = s.length();
        int pLen = p.length();
        if (pIndex == pLen) {
            return sIndex == sLen;
        }
        else if (sIndex == sLen) {
            return false;
        }

        String nextP = nextPattern(p, pIndex);
        int nextPLen = nextP.length();
        char nextC = nextP.charAt(0);
        if (nextPLen == 1) {
            if (nextC == '.') {
                return isMatch(s, sIndex + 1, p, pIndex + 1);
            }
            else {
                if (s.charAt(sIndex) == nextC) {
                    return isMatch(s, sIndex + 1, p, pIndex + 1);
                }
                else return false;
            }
        }
        else {
            if (nextC == '.') {
                for (int i = sIndex; i <= sLen; i++) {
                    boolean temp = isMatch(s, i, p, pIndex + 2);
                    if (temp) {
                        return true;
                    }
                }
            }
            else {
                for (int i = sIndex - 1; i < sLen; i++) {
                    if (i == sIndex - 1 || nextC == s.charAt(i)) {
                        boolean temp1 = isMatch(s, i + 1, p, pIndex + 2);
                        if (temp1) {
                            return true;
                        }
                    }
                    else break;
                }
            }
        }
        return false;
    }

    private String nextPattern(String p, int start) {
        int len = p.length();
        if (start == len - 1) {
            return p.charAt(start) + "";
        }
        else {
            char curC = p.charAt(start);
            char next = p.charAt(start + 1);
            if (next == '*') {
                return "" + curC + next;
            }
            else return "" + curC;
        }
    }









    /**
     * http://www.lintcode.com/zh-cn/problem/minimum-depth-of-binary-tree/
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        curMinDepth = Integer.MAX_VALUE;
        computeMinDepth(root, 1);
        return curMinDepth;
    }

    private int curMinDepth = Integer.MAX_VALUE;

    private void computeMinDepth(TreeNode node, int curDepth) {
        if (node != null && curDepth < curMinDepth) {
            if (node.left == null && node.right == null) {
                if (curMinDepth > curDepth) {
                    curMinDepth = curDepth;
                }
            }
            else {
                computeMinDepth(node.left, curDepth + 1);
                computeMinDepth(node.right, curDepth + 1);
            }
        }
    }








//    /**
//     * 采用边界和栈来合并，不满足挑战要求
//     * http://www.lintcode.com/zh-cn/problem/merge-intervals/
//     * @param intervals, a collection of intervals
//     * @return: A new sorted interval list.
//     */
//    public List<Interval> merge(List<Interval> intervals) {
//        if (intervals == null) {
//            return null;
//        }
//
//        List<IntervalEdge> edges = new ArrayList<>();
//        for (Interval item: intervals) {
//            edges.add(new IntervalEdge(item.start, true));
//            edges.add(new IntervalEdge(item.end, false));
//        }
//        Collections.sort(edges, new java.util.Comparator<IntervalEdge>() {
//            @Override
//            public int compare(IntervalEdge o1, IntervalEdge o2) {
//                if (o1.index != o2.index) {
//                    return o1.index - o2.index;
//                }
//                else return o2.isStart? 1: -1;
//            }
//        });
//
//        Stack<IntervalEdge> stack = new Stack<>();
//        List<Interval> result = new ArrayList<>();
//        for (IntervalEdge edge: edges) {
//            if (edge.isStart) {
//                stack.push(edge);
//            }
//            else {
//                IntervalEdge top = stack.pop();
//                if (stack.isEmpty()) {
//                    result.add(new Interval(top.index, edge.index));
//                }
//            }
//        }
//        return result;
//    }
//
//    private class IntervalEdge {
//        public final int index;
//        public final boolean isStart;
//        public IntervalEdge(int index, boolean isStart) {
//            this.index = index;
//            this.isStart = isStart;
//        }
//    }

    /**
     * http://www.lintcode.com/zh-cn/problem/merge-intervals/
     * @param intervals, a collection of intervals
     * @return: A new sorted interval list.
     */
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null) {
            return null;
        }

        Collections.sort(intervals, new java.util.Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                if (o1.start != o2.start) {
                    return o1.start - o2.start;
                }
                else return o2.end - o1.end;
            }
        });
        for (int i = 1; i < intervals.size();) {
            Interval last = intervals.get(i - 1);
            Interval cur = intervals.get(i);
            if (last.end >= cur.start) {
                last.end = Math.max(last.end, cur.end);
                intervals.remove(i);
            }
            else {
                i++;
            }
        }
        return intervals;
    }







    /**
     * http://www.lintcode.com/zh-cn/problem/unique-characters/
     * @param str: a string
     * @return: a boolean
     */
    public boolean isUnique(String str) {

        return false;
    }

    public static void main(String[] args) {
        Solutions solutions = new Solutions();

        /**
         判断字符串是否没有重复字符   [容易]
         实现一个算法确定字符串中的字符是否均唯一出现
         样例
         给出"abc"，返回 true
         给出"aab"，返回 false
         挑战
         如果不使用额外的存储空间，你的算法该如何改变？
         */
        String str = "abc";
        XYLog.d(str, "中", solutions.isUnique(str)? "所有字母唯一": "存在字母重复");




        /**
         合并区间   [容易]
         给出若干闭合区间，合并所有重叠的部分。
         样例
         给出的区间列表 => 合并后的区间列表：
         [                     [
         [1, 3],               [1, 6],
         [2, 6],      =>       [8, 10],
         [8, 10],              [15, 18]
         [15, 18]            ]
         ]
         挑战
         O(n log n) 的时间和 O(1) 的额外空间。
         */
//        List<Interval> intervals = ArrayListUtil.build(
//                new Interval(1, 3),
//                new Interval(2, 6),
//                new Interval(8, 10),
//                new Interval(5, 18)
//        );
//        XYLog.d(intervals);
//        XYLog.d("合并之后为：\n", solutions.merge(intervals));



        /**
         二叉树的最小深度   [容易]
         给定一个二叉树，找出其最小深度。
         二叉树的最小深度为根节点到最近叶子节点的距离。
         */
//        TreeNode root = TreeNodeFactory.build("1,2,3,#,#,4,5");
//        XYLog.d(root, "的最小深度为：\n", solutions.minDepth(root));





        /**
         正则表达式匹配   [困难]
         实现支持'.'和'*'的正则表达式匹配。
         '.'匹配任意一个字母。
         '*'匹配零个或者多个前面的元素。
         匹配应该覆盖整个输入字符串，而不仅仅是一部分。
         需要实现的函数是：boolean isMatch(String s, String p)
         样例
         isMatch("aa","a") → false
         isMatch("aa","aa") → true
         isMatch("aa", ".*") → true
         isMatch("ab", "a.*") → true
         */
//        String str = "acaabbaccbbacaabbbb";
//        String regexStr = "a*.*b*.*a*aa*a*";
//        XYLog.d("isMatch(\"" + str + "\", \"" + regexStr + "\") = ", solutions.isMatch(str, regexStr));



        /**
         数字组合 II  [中等]
         给出一组候选数字(C)和目标数字(T),找出C中所有的组合，使组合中数字的和为T。C中每个数字在每个组合中只能使用一次。
         注意事项
         所有的数字(包括目标数字)均为正整数。
         元素组合(a1, a2, … , ak)必须是非降序(ie, a1 ≤ a2 ≤ … ≤ ak)。
         解集不能包含重复的组合。
         您在真实的面试中是否遇到过这个题？ Yes
         样例
         给出一个例子，候选数字集合为[10,1,6,7,2,1,5] 和目标数字 8  ,
         解集为：[[1,7],[1,2,5],[2,6],[1,1,6]]
         */
//        int[] arr = {10,1,6,7,2,1,5};
//        int target = 8;
//        XYLog.d(arr);
//        XYLog.d("中所有和为 ", target, " 的组合有：\n", solutions.combinationSum2(arr, target));


        /**
         组合  [中等]
         组给出两个整数n和k，返回从1......n中选出的k个数的组合。
         样例
         例如 n = 4 且 k = 2
         返回的解为：
         [[2,4],[3,4],[2,3],[1,2],[1,3],[1,4]]
         */
//        int n = 4;
//        int k = 2;
//        XYLog.d("从 1 到 ", n, " 中取出 ", k, " 个不同数，有这些不同的组合：\n", solutions.combine(n, k));




        /**
         买卖股票的最佳时机 III   [中等]
         假设你有一个数组，它的第i个元素是一支给定的股票在第i天的价格。设计一个算法来找到最大的利润。你最多可以完成两笔交易。
         注意事项
         你不可以同时参与多笔交易(你必须在再次购买前出售掉之前的股票)
         样例
         给出一个样例数组 [4,4,6,1,1,4,2,5], 返回 6
         */
//        int[] prices = {1,3,2,1,3,2,5};
//        XYLog.d(prices, "的最大交易收益为：", solutions.maxProfitIII(prices));



        /**
         买卖股票的最佳时机 II   [中等]
         假设有一个数组，它的第i个元素是一个给定的股票在第i天的价格。设计一个算法来找到最大的利润。你可以完成尽可能多的交易(多次买卖股票)。然而,你不能同时参与多个交易(你必须在再次购买前出售股票)。
         样例
         给出一个数组样例[2,1,2,0,1], 返回 2
         */
//        int[] prices = {1,3,2,5,6,1,0,3};
//        XYLog.d(prices, "的最大交易收益为：", solutions.maxProfitII(prices));




        /**
         买卖股票的最佳时机   [中等]
         假设有一个数组，它的第i个元素是一支给定的股票在第i天的价格。如果你最多只允许完成一次交易(例如,一次买卖股票),设计一个算法来找出最大利润。
         样例
         给出一个数组样例 [3,2,3,1,2], 返回 1
         */
//        int[] prices = {3,1,3,1,4};
//        XYLog.d(prices, "的最大交易收益为：", solutions.maxProfit(prices));



        /**
         颜色分类   [中等]
         给定一个包含红，白，蓝且长度为 n 的数组，将数组元素进行分类使相同颜色的元素相邻，并按照红、白、蓝的顺序进行排序。
         我们可以使用整数 0，1 和 2 分别代表红，白，蓝。
         注意事项
         不能使用代码库中的排序函数来解决这个问题。
         排序需要在原数组中进行。
         样例
         给你数组 [1, 0, 1, 2], 需要将该数组原地排序为 [0, 1, 1, 2]。
         挑战
         一个相当直接的解决方案是使用计数排序扫描2遍的算法。
         首先，迭代数组计算 0,1,2 出现的次数，然后依次用 0,1,2 出现的次数去覆盖数组。
         你否能想出一个仅使用常数级额外空间复杂度且只扫描遍历一遍数组的算法？
         */
//        int[] colors = {2,0,0,1,2,0,2,0,1};
//        XYLog.d(colors);
//        solutions.sortColors(colors);
//        XYLog.d("重排后：");
//        XYLog.d(colors);





        /**
         交错正负数   [中等]
         给出一个含有正整数和负整数的数组，重新排列成一个正负数交错的数组。
         样例
         给出数组[-1, -2, -3, 4, 5, 6]，重新排序之后，变成[-1, 5, -2, 4, -3, 6]或者其他任何满足要求的答案
         */
////        int[] arr = {-33,-19,30,26,21,-9};
//        int[] arr = DataUtil.getIntArr("./data/interleaving-positive-and-negative-numbers-65.in");
//        XYLog.d(arr);
//        solutions.rerange(arr);
//        XYLog.d("交错重后：");
//        XYLog.d(arr);



        /**
         排颜色 II   [中等]
         给定一个有n个对象（包括k种不同的颜色，并按照1到k进行编号）的数组，将对象进行分类使相同颜色的对象相邻，并按照1,2，...k的顺序进行排序。
         注意事项
         不能使用代码库中的排序函数来解决这个问题
         样例
         给出colors=[3, 2, 2, 1, 4]，k=4, 你的代码应该在原地操作使得数组变成[1, 2, 2, 3, 4]
         */
//        int[] colors = {3, 2, 2, 1, 4};
//        int k = 4;
//        XYLog.d(colors);
//        solutions.sortColors2(colors, k);
//        XYLog.d("重排序后：");
//        XYLog.d(colors);


        /**
         O(1)时间检测2的幂次   [容易]
         用 O(1) 时间检测整数 n 是否是 2 的幂次。
         注意事项
         O(1) 时间复杂度

         样例
         n=4，返回 true;
         n=5，返回 false.
         */
//        int n = 8;
//        XYLog.d(n, solutions.checkPowerOf2(n)? "是": "不是", "2的冥次");






        /**
         x的平方根   [容易]
         实现 int sqrt(int x) 函数，计算并返回 x 的平方根。
         样例
         sqrt(3) = 1
         sqrt(4) = 2
         sqrt(5) = 2
         */
//        int x = 2147483647;
//        XYLog.d("sqrt(", x, ") = ", solutions.sqrt(x));




        /**
         快速幂   [中等]
         计算a ^ n % b，其中a，b和n都是32位的整数。
         样例
         例如 2 ^ 31 % 3 = 2
         例如 100 ^ 1000 % 1000 = 0
         挑战
         O(logn)
         */
//        int[] arr = {2147483647, 2147483645, 214748364};
//        int a = arr[0];
//        int b = arr[1];
//        int n = arr[2];
//        XYLog.d(a + " ^ " + n + " % " + b + " = " + solutions.fastPower(a, b, n));





        /**
         最接近零的子数组和   [中等]
         给定一个整数数组，找到一个和最接近于零的子数组。返回第一个和最有一个指数。你的代码应该返回满足要求的子数组的起始位置和结束位置
         样例
         给出[-3, 1, 1, -3, 5]，返回[0, 2]，[1, 3]， [1, 1]， [2, 2] 或者 [0, 4]。
         挑战
         O(nlogn)的时间复杂度
         */
//        int[] nums = {-3, 1, 1, -3, 5};
//        XYLog.d(nums, "中和最接近0的子数组的其中一个的起始，终止下标为：\n", solutions.subarraySumClosest(nums));




        /**
         子数组之和   [容易]
         给定一个整数数组，找到和为零的子数组。你的代码应该返回满足要求的子数组的起始位置和结束位置
         样例
         给出 [-3, 1, 2, -3, 4]，返回[0, 2] 或者 [1, 3].
         */
//        int[] nums = {-3, 1, 2, -3, 4};
//        XYLog.d(nums, "中和为0的子数组的其中一个的起始，终止下标为：\n", solutions.subarraySum(nums));





        /**
         克隆图   [中等]
         克隆一张无向图，图中的每个节点包含一个 label 和一个列表 neighbors。
         数据中如何表示一个无向图？http://www.lintcode.com/help/graph/
         你的程序需要返回一个经过深度拷贝的新图。这个新图和原图具有同样的结构，并且对新图的任何改动不会对原图造成任何影响。
         */
//        UndirectedGraphNode node0 = new UndirectedGraphNode(0);
//        UndirectedGraphNode node1 = new UndirectedGraphNode(1);
//        UndirectedGraphNode node2 = new UndirectedGraphNode(2);
//        ArrayListUtil.addTo(node0.neighbors, node1, node2);
//        ArrayListUtil.addTo(node1.neighbors, node0, node2);
//        ArrayListUtil.addTo(node2.neighbors, node0, node1, node2);
//        UndirectedGraphNode node0Copy = solutions.cloneGraph(node0);
//        System.out.println(node0Copy);



        /**
         分割回文串   [中等]
         给定一个字符串s，将s分割成一些子串，使每个子串都是回文串。
         返回s所有可能的回文串分割方案。
         样例
         给出 s = "aab"，返回
         [
         ["aa", "b"],
         ["a", "a", "b"]
         ]
         */
//        String str = "aab";
//        XYLog.d(str, "的所有回文串分割方案有：\n", solutions.partition(str));




        /**
         数字组合   [中等]
         给出一组候选数字(C)和目标数字(T),找到C中所有的组合，使找出的数字和为T。C中的数字可以无限制重复被选取。
         例如,给出候选数组[2,3,6,7]和目标数字7，所求的解为：
         [7]，
         [2,2,3]
         注意事项
         所有的数字(包括目标数字)均为正整数。
         元素组合(a1, a2, … , ak)必须是非降序(ie, a1 ≤ a2 ≤ … ≤ ak)。
         解集不能包含重复的组合。
         样例
         给出候选数组[2,3,6,7]和目标数字7
         返回 [[7],[2,2,3]]
         */
//        int[] arr = {2,3,6,7};
//        int target = 7;
//        XYLog.d("在\n", arr, "\n中所有和为", target, "的组合有：\n", solutions.combinationSum(arr, target));





        /**
         LRU缓存策略   [困难]
         http://www.lintcode.com/zh-cn/problem/longest-words/
         为最近最少使用（LRU）缓存策略设计一个数据结构，它应该支持以下操作：获取数据（get）和写入数据（set）。
         获取数据get(key)：如果缓存中存在key，则获取其数据值（通常是正数），否则返回-1。
         写入数据set(key, value)：如果key还没有在缓存中，则写入其数据值。当缓存达到上限，它应该在写入新数据之前删除最近最少使用的数据用来腾出空闲位置。
         */
//        int capacity = 2;
//        Lru lru = new Lru(capacity);
//        lru.set(2, 1);
//        lru.set(1, 1);
//        XYLog.d(lru.get(2));
//        lru.set(4, 1);
//        XYLog.d(lru.get(1));
//        XYLog.d(lru.get(2));




        /**
         最长单词   [容易]
         给一个词典，找出其中所有最长的单词。

         样例
         在词典
         {
         "dog",
         "google",
         "facebook",
         "internationalization",
         "blabla"
         }
         中, 最长的单词集合为 ["internationalization"]

         在词典
         {
         "like",
         "love",
         "hate",
         "yes"
         }
         中，最长的单词集合为 ["like", "love", "hate"]

         挑战
         遍历两次的办法很容易想到，如果只遍历一次你有没有什么好办法？
         */
//        String[] strs = {
//                "dog",
//                "google",
//                "facebook",
//                "internationalization",
//                "blabla"
//        };
//        XYLog.d(strs, "\n中最长的单词是这些：\n", solutions.longestWords(strs));





        /**
         单词搜索 II   [困难]
         给出一个由小写字母组成的矩阵和一个字典。找出所有同时在字典和矩阵中出现的单词。一个单词可以从矩阵中的任意位置开始，可以向左/右/上/下四个相邻方向移动。
         样例
         给出矩阵：
         doaf
         agai
         dcan
         和字典：
         {"dog", "dad", "dgdg", "can", "again"}
         返回 {"dog", "dad", "can", "again"}

         dog:
         [d][o]af
         a[g]ai
         dcan

         dad:
         doaf
         agai
         dcan

         can:
         doaf
         agai
         dcan

         again:
         doaf
         agai
         dcan

         注意
         每个位置的字母只能使用一次

         挑战
         使用单词查找树来实现你的算法
          */
////        String[] boardStrs = {
////                "doaf",
////                "agai",
////                "dcan"
////        };
////        ArrayList<String> words = ArrayListUtil.build("dog", "dad", "dgdg", "can", "again");
//        String[] boardStrs = {
//                "abce",
//                "sfcs",
//                "adee"
//        };
//        ArrayList<String> words = ArrayListUtil.build("abcb","ninechapter","lintcode");
//        char[][] board = new char[boardStrs.length][];
//        for (int i = 0, len = board.length; i < len; i++) {
//            board[i] = boardStrs[i].toCharArray();
//        }
//        XYLog.d(words, "中出现在矩阵", board, "中的单词有：\n", solutions.wordSearchII(board, words));




        /**
         堆化   [中等]
         给出一个整数数组，堆化操作就是把它变成一个最小堆数组。
         对于堆数组A，A[0]是堆的根，并对于每个A[i]，A [i * 2 + 1]是A[i]的左儿子并且A[i * 2 + 2]是A[i]的右儿子。
         什么是堆？
         堆是一种数据结构，它通常有三种方法：push， pop 和 top。其中，“push”添加新的元素进入堆，“pop”删除堆中最小/最大元素，“top”返回堆中最小/最大元素。
         什么是堆化？
         把一个无序整数数组变成一个堆数组。如果是最小堆，每个元素A[i]，我们将得到A[i * 2 + 1] >= A[i]和A[i * 2 + 2] >= A[i]
         如果有很多种堆化的结果？
         返回其中任何一个。
         样例
         给出 [3,2,1,4,5]，返回[1,2,3,4,5] 或者任何一个合法的堆数组
         */
//        int[] arr = {42,30,27,93,8,34,47,64,82,76,70,79};
//        XYLog.d(arr, "\n堆化之后：");
//        solutions.heapify(arr);
//        XYLog.d(arr, "");



        /**
         重哈希   [中等]
         哈希表容量的大小在一开始是不确定的。如果哈希表存储的元素太多（如超过容量的十分之一），我们应该将哈希表容量扩大一倍，并将所有的哈希值重新安排。假设你有如下一哈希表：
         size=3, capacity=4
         [null, 21, 14, null]
               ↓    ↓
                9   null
               ↓
              null
         哈希函数为：
         int hashcode(int key, int capacity) {
         return key % capacity;
         }
         这里有三个数字9，14，21，其中21和9共享同一个位置因为它们有相同的哈希值1(21 % 4 = 9 % 4 = 1)。我们将它们存储在同一个链表中。
         重建哈希表，将容量扩大一倍，我们将会得到：
         size=3, capacity=8
         index:   0    1    2    3     4    5    6   7
         hash : [null, 9, null, null, null, 21, 14, null]
         给定一个哈希表，返回重哈希后的哈希表。

         注意事项

         哈希表中负整数的下标位置可以通过下列方式计算：

         C++/Java：如果你直接计算-4 % 3，你会得到-1，你可以应用函数：a % b = (a % b + b) % b得到一个非负整数。
         Python：你可以直接用-1 % 3，你可以自动得到2。
         您在真实的面试中是否遇到过这个题？ Yes
         样例
         给出 [null, 21->9->null, 14->null, null]
         返回 [null, 9->null, null, null, null, 21->null, 14->null, null]
         */
//        ListNode[] hashTable = new ListNode[4];
//        hashTable[1] = ListNodeFactory.build("21->9");
//        hashTable[2] = ListNodeFactory.build("14");
//        XYLog.d(hashTable, "\n扩容重排之后：");
//        XYLog.d(solutions.rehashing(hashTable), "");




        /**
         哈希函数   [容易]
         在数据结构中，哈希函数是用来将一个字符串（或任何其他类型）转化为小于哈希表大小且大于等于零的整数。一个好的哈希函数可以尽可能少地产生冲突。一种广泛使用的哈希函数算法是使用数值33，假设任何字符串都是基于33的一个大整数，比如：
         hashcode("abcd") = (ascii(a) * 33^3 + ascii(b) * 33^2 + ascii(c) *33 + ascii(d)) % HASH_SIZE
         = (97* 33^3 + 98 * 33^2 + 99 * 33^1 +100 * 33 ^ 0) % HASH_SIZE
         = 3595978 % HASH_SIZE
         其中HASH_SIZE表示哈希表的大小(可以假设一个哈希表就是一个索引0 ~ HASH_SIZE-1的数组)。
         给出一个字符串作为key和一个哈希表的大小，返回这个字符串的哈希值。
         注意事项
         对于这个问题你不需要自己设计hash算法，你只需要实现上述描述的hash算法即可。

         样例
         对于key="abcd" 并且 size=100， 返回 78
         */
//        String str = "Wrong answer or accepted?";
//        int size = 1000000007;
//        XYLog.d("大小为", size, "的hash表中，", str, "的hash值为：", solutions.hashCode(str.toCharArray(), size));




        /**
         拓扑排序   [中等]
         给定一个有向图，图节点的拓扑排序被定义为：
         对于每条有向边A--> B，则A必须排在B之前　　
         拓扑排序的第一个节点可以是任何在图中没有其他节点指向它的节点　　
         找到给定图的任一拓扑排序

         注意事项
         你可以假设图中至少存在一种拓扑排序
         */
//        DirectedGraphNode node1 = new DirectedGraphNode(1);
//        DirectedGraphNode node2 = new DirectedGraphNode(2);
//        DirectedGraphNode node3 = new DirectedGraphNode(3);
//        DirectedGraphNode node4 = new DirectedGraphNode(4);
//        DirectedGraphNode node5 = new DirectedGraphNode(5);
//        node1.addNeighbors(node2, node3);
//        node2.addNeighbors(node4);
//        node3.addNeighbors(node2, node5);
//        node4.addNeighbors(node5);
//        ArrayList<DirectedGraphNode> nodes = ArrayListUtil.build(node1, node2, node3, node4, node5);
//        XYLog.d(nodes, "的拓扑排序为：", solutions.topSort(nodes));



        /**
         背包问题 II   [中等]
         给出n个物品的体积A[i]和其价值V[i]，将他们装入一个大小为m的背包，最多能装入的总价值有多大？
         注意事项
         A[i], V[i], n, m均为整数。你不能将物品进行切分。你所挑选的物品总体积需要小于等于给定的m。

         样例
         对于物品体积[2, 3, 5, 7]和对应的价值[1, 5, 2, 4], 假设背包大小为10的话，最大能够装入的价值为9。
         */
//        int maxW = 10;
//        int[] ws = {2, 3, 5, 7};
//        int[] vs = {1, 5, 2, 4};
//        XYLog.d("将体积和价值分别为：", ws, vs, "\n的物品装入最大容量为 ",  maxW, " 的背包中，能够装入的最大价值为：", solutions.backPackII(maxW, ws, vs));






        /**
         最长连续序列   [中等]
         给定一个未排序的整数数组，找出最长连续序列的长度。
         说明
         要求你的算法复杂度为O(n)

         样例
         给出数组[100, 4, 200, 1, 3, 2]，这个最长的连续序列是 [1, 2, 3, 4]，返回所求长度 4
         */
//        int[] arr = {100, 4, 200, 1, 3, 2};
//        XYLog.d(arr, "中最长连续序列的长度为：", solutions.longestConsecutive(arr));





        /**
         单词搜索   [中等]
         给出一个二维的字母板和一个单词，寻找字母板网格中是否存在这个单词。
         单词可以由按顺序的相邻单元的字母组成，其中相邻单元指的是水平或者垂直方向相邻。每个单元中的字母最多只能使用一次。

         样例
         给出board =
         [
         "ABCE",
         "SFCS",
         "ADEE"
         ]
         word = "ABCCED"， ->返回 true,
         word = "SEE"，-> 返回 true,
         word = "ABCB"， -> 返回 false.
         */
//        String[] boardStrs = {
//                "ABCE",
//                "SFCS",
//                "ADEE"
//        };
//        String word = "ABAB";
//        char[][] board = new char[boardStrs.length][];
//        for (int i = 0, len = boardStrs.length; i < len; i++) {
//            board[i] = boardStrs[i].toCharArray();
//        }
//        XYLog.d("在", board, "中\n", solutions.exist(board, word)? "": "不", "可以找到 ", word);




        /**
         直方图最大矩形覆盖  [困难]
         给出的n个非负整数表示每个直方图的高度，每个直方图的宽均为1，在直方图中找到最大的矩形面积。
         http://www.leetcode.com/wp-content/uploads/2012/04/histogram.png
         以上直方图宽为1，高度为[2,1,5,6,2,3]。
         http://www.leetcode.com/wp-content/uploads/2012/04/histogram_area.png
         最大矩形面积如图阴影部分所示，含有10单位
         样例
         给出 height = [2,1,5,6,2,3]，返回 10
         */
//        int[] heights = {6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3};
//        XYLog.d("直方图", heights, "的最大单个矩形覆盖面积为：\n", solutions.largestRectangleArea(heights));



        /**
         单词接龙 II   [困难]
         给出两个单词（start和end）和一个字典，找出所有从start到end的最短转换序列
         比如：
         每次只能改变一个字母。
         变换过程中的中间单词必须在字典中出现。
         注意事项
         所有单词具有相同的长度。
         所有单词都只包含小写字母。

         样例
         给出数据如下：
         start = "hit"
         end = "cog"
         dict = ["hot","dot","dog","lot","log"]
         返回
         [
         ["hit","hot","dot","dog","cog"],
         ["hit","hot","lot","log","cog"]
         ]
         */
//        String start = "qa";
//        String end = "sq";
//        Set<String> dict = new HashSet<>();
//        String[] dictArr = {"si","go","se","cm","so","ph","mt","db","mb","sb","kr","ln","tm","le","av","sm","ar","ci","ca","br","ti","ba","to","ra","fa","yo","ow","sn","ya","cr","po","fe","ho","ma","re","or","rn","au","ur","rh","sr","tc","lt","lo","as","fr","nb","yb","if","pb","ge","th","pm","rb","sh","co","ga","li","ha","hz","no","bi","di","hi","qa","pi","os","uh","wm","an","me","mo","na","la","st","er","sc","ne","mn","mi","am","ex","pt","io","be","fm","ta","tb","ni","mr","pa","he","lr","sq","ye"};
//        dict.addAll(Arrays.asList(dictArr));

//        ArrayList<String> strArr = DataUtil.getStringArr("data/word-ladder-ii-83.in");
//        String start = strArr.remove(0);
//        String end = strArr.remove(0);
//        Set<String> dict = new HashSet<>();
//        dict.addAll(strArr);
//        XYLog.d("利用词典", strArr, "\n", start, " 转换到 ", end, " 的最短序列为 ");
//        XYLog.d(solutions.findLadders(start, end, dict));




        /**
         单词接龙   [中等]
         给出两个单词（start和end）和一个字典，找到从start到end的最短转换序列
         比如：
         每次只能改变一个字母。
         变换过程中的中间单词必须在字典中出现。
         注意事项
         如果没有转换序列则返回0。
         所有单词具有相同的长度。
         所有单词都只包含小写字母。

         样例
         给出数据如下：
         start = "hit"
         end = "cog"
         dict = ["hot","dot","dog","lot","log"]
         一个最短的变换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog"，
         返回它的长度 5
         */
//        String start = "hot";
//        String end = "dog";
//        Set<String> dict = new HashSet<>();
//        String[] dictArr = {"hot","cog","dog","tot","hog","hop","pot","dot"};
//        dict.addAll(Arrays.asList(dictArr));
//        XYLog.d("利用词典", dict, "\n", start, " 转换到 ", end, " 的最少次数为 ");
//        XYLog.d(solutions.ladderLength(start, end, dict));





        /**
         编辑距离   [中等]
         给出两个单词word1和word2，计算出将word1 转换为word2的最少操作次数。
         你总共三种操作方法：
         插入一个字符
         删除一个字符
         替换一个字符

         样例
         给出 work1="mart" 和 work2="karma"
         返回 3
         */
//        String word1 = "ab";
//        String word2 = "a";
//        XYLog.d("\n", word1, "\n",  word2, "\n编辑距离为：", solutions.minDistance(word1, word2));






        /**
         不同的子序列 [中等]
         给出字符串S和字符串T，计算S的不同的子序列中T出现的个数。
         子序列字符串是原始字符串通过删除一些(或零个)产生的一个新的字符串，并且对剩下的字符的相对位置没有影响。(比如，“ACE”是“ABCDE”的子序列字符串,而“AEC”不是)。

         样例
         给出S = "rabbbit", T = "rabbit"
         返回 3
         */
//        String s = "abcbc";
//        String t = "abc";
//        XYLog.d(s, "中有 ", solutions.numDistinct(s, t), " 个 ", t, " 子序列");



        /**
         跳跃游戏 II    [中等]
         给出一个非负整数数组，你最初定位在数组的第一个位置。
         数组中的每个元素代表你在那个位置可以跳跃的最大长度。　　　
         你的目标是使用最少的跳跃次数到达数组的最后一个位置。

         样例
         给出数组A = [2,3,1,1,4]，最少到达数组最后一个位置的跳跃次数是2(从数组下标0跳一步到数组下标1，然后跳3步到数组的最后一个位置，一共跳跃2次)
         */
//        int[] arr = {2,3,1,1,4};
////        int[] arr = {3,2,1,0,4};
//        XYLog.d("在\n", arr, "\n中，从起点", "最少跳", solutions.jump(arr), "步可以跳到终点");





        /**
         跳跃游戏   [中等]
         给出一个非负整数数组，你最初定位在数组的第一个位置。　　　
         数组中的每个元素代表你在那个位置可以跳跃的最大长度。　　　　
         判断你是否能到达数组的最后一个位置。

         样例
         arr = [2,3,1,1,4]，返回 true.
         arr = [3,2,1,0,4]，返回 false.
         */
//        int[] arr = {2,3,1,1,4};
////        int[] arr = {3,2,1,0,4};
//        XYLog.d("在\n", arr, "\n中，从起点", solutions.canJump(arr)? "": "不", "可以跳到终点");






        /**
         不同的路径 II  [容易]
         "不同的路径" 的跟进问题：
         现在考虑网格中有障碍物，那样将会有多少条不同的路径？
         网格中的障碍和空位置分别用 1 和 0 来表示。
         注意事项
         m 和 n 均不超过100

         样例
         如下所示在3x3的网格中有一个障碍物：
         [
         [0,0,0],
         [0,1,0],
         [0,0,0]
         ]
         一共有2条不同的路径从左上角到右下角。
         */
//        int[][] grid = DataUtil.getTwoDimensArr("data/unique-paths-ii-test.in", 3);
//        XYLog.d("在网格\n", grid, "\n从左上角走到右下角一共 ", solutions.uniquePathsWithObstacles(grid) + " 种不同途径");






        /**
         不同的路径  [容易]
         有一个机器人的位于一个M×N个网格左上角（下图中标记为'Start'）。
         机器人每一时刻只能向下或者向右移动一步。机器人试图达到网格的右下角（下图中标记为'Finish'）。
         问有多少条不同的路径？

         注意事项
         n和m均不超过100
         */
//        int m = 2;
//        int n = 62;
//        XYLog.d(m + " * " + n + " 的网格，从左上角走到右下角一共 ", solutions.uniquePaths(m, n) + " 种不同途径");








        /**
         删除排序链表中的重复数字 II    [中等]
         给定一个排序链表，删除所有重复的元素只留下原链表中没有重复的元素。

         样例
         给出 1->2->3->3->4->4->5->null，返回 1->2->5->null
         给出 1->1->1->2->3->null，返回 2->3->null
         */
//        ListNode head = ListNodeFactory.build("1->2->3->3->4->4->5");
//        XYLog.d(head);
//        XYLog.d("去重后：", solutions.deleteDuplicatesII(head));




        /**
         删除排序链表中的重复元素   [容易]
         给定一个排序链表，删除所有重复的元素每个元素只留下一个。

         样例
         给出 1->1->2->null，返回 1->2->null
         给出 1->1->2->3->3->null，返回 1->2->3->null
         */
//        ListNode head = ListNodeFactory.build("1->1->2->3->3");
//        XYLog.d(head);
//        XYLog.d("去重后：", solutions.deleteDuplicates(head));




        /**
         大楼轮廓   [超难]
         水平面上有 N 座大楼，每座大楼都是矩阵的形状，可以用三个数字表示 (start, end, height)，分别代表其在x轴上的起点，终点和高度。大楼之间从远处看可能会重叠，求出 N 座大楼的外轮廓线。
         外轮廓线的表示方法为若干三元组，每个三元组包含三个数字 (start, end, height)，代表这段轮廓的起始位置，终止位置和高度。
         注意事项
         请注意合并同样高度的相邻轮廓，不同的轮廓线在x轴上不能有重叠。

         样例
         给出三座大楼：
         [
         [1, 3, 3],
         [2, 4, 4],
         [5, 6, 1]
         ]
         外轮廓线为：
         [
         [1, 2, 3],
         [2, 4, 4],
         [5, 6, 1]
         ]
         */
////        int[][] buildings = {{1,3,3},{2,4,4},{5,6,1},{3,5,8}};//
////        int[][] buildings = {{1,5,9},{2,10,3},{7,14,9},{12,18,3},{16,20,9}};
//        int[][] buildings = {{3,7,78},{4,5,313},{5,8,401},{6,10,242},{7,8,600},{8,12,466},{9,14,528},{10,13,370},{11,13,642},{12,15,895},{13,16,733},{14,17,360},{15,16,272},{16,21,22},{17,21,605},{18,19,767},{19,22,901},{20,24,942},{21,25,416},{22,27,704},{23,25,497},{24,27,967},{25,30,459},{26,27,414},{27,28,208},{28,29,327},{29,31,773},{30,34,94},{31,35,409},{32,36,156},{33,35,195},{34,37,666},{35,39,156},{36,37,538},{37,38,777},{38,42,186},{39,41,108},{40,41,998},{41,45,660},{42,46,922},{43,47,978},{44,48,927},{45,48,583},{46,49,802},{47,49,210},{48,52,514},{49,51,580},{50,51,479},{51,56,857},{52,57,242},{53,58,753},{54,56,418},{55,56,440},{56,61,25},{57,62,529},{58,60,249},{59,64,619},{60,65,507},{61,66,682},{62,64,152},{63,66,45},{64,68,867},{65,68,383},{66,70,34},{67,69,678},{68,71,176},{69,73,230},{70,73,292},{71,73,211},{72,74,293},{73,74,27},{74,78,287},{75,77,478},{76,79,145},{77,79,178},{78,82,731},{79,80,702},{80,81,696},{81,85,614},{82,87,887},{83,88,71},{84,86,194},{85,87,244},{86,89,414},{87,89,244},{88,90,828}};
//        int[][] buildings = DataUtil.getTwoDimensArr("data/building-outline-95.in", 3);
//        XYLog.d(solutions.buildingOutline(buildings));
//        XYLog.d(solutions.buildingOutline2(buildings));
        //对比两组结果是否完全一致
//        ArrayList<ArrayList<Integer>> result = solutions.buildingOutline(buildings);
//        ArrayList<ArrayList<Integer>> result2 = solutions.buildingOutline2(buildings);
//        for (int i = 0, size = result.size(); i < size; i++) {
//            ArrayList<Integer> item = result.get(i);
//            ArrayList<Integer> item2 = result2.get(i);
//            if (!item.get(0).equals(item2.get(0))
//                    || !item.get(1).equals(item2.get(1))
//                    || !item.get(2).equals(item2.get(2))) {
//                XYLog.d(i + ": ", item, "\t", item2);
//            }
//        }



        /**
         将表达式转换为逆波兰表达式
         给定一个表达式字符串数组，返回该表达式的逆波兰表达式（即去掉括号）。

         样例
         对于 [3 - 4 + 5]的表达式（该表达式可表示为["3", "-", "4", "+", "5"]），返回 [3 4 - 5 +]（该表达式可表示为 ["3", "4", "-", "5", "+"]）。
         */
//        String[] exps = {"3", "-", "4", "*", "5"};
//        XYLog.d(solutions.convertToRPN(exps));







        /**
         爬楼梯    实际上是一个1,1开头的斐波拉切数列,f[0] = 1, f[1] = 1, f[2] = 2, f[3] = 3...
         假设你正在爬楼梯，需要n步你才能到达顶部。但每次你只能爬一步或者两步，你能有多少种不同的方法爬到楼顶部？

         样例
         比如n=3，1+1+1=1+2=2+1=3，共有3中不同的方法
         返回 3
         */
//        int n = 3;
//        XYLog.d("爬一个", n , "阶的台阶，一次可以爬1或2阶，一共有", solutions.climbStairs(n), "种方式");






        /**
         最小路径和
         给定一个只含非负整数的m*n网格，找到一条从左上角到右下角的可以使数字和最小的路径
         注意事项
         你在同一时间只能向下或者向右移动一步

         样例
         1  2  3
         4  5  6
         7  8  9
         从左上角1走到右下角的最短路径为1+2+3+6+9=21
         */
//        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        XYLog.d("在", grid, "中，从左上角到右下角的最短路径和为：", solutions.minPathSum(grid));



        /**
         表达树构造
         表达树是一个二叉树的结构，用于衡量特定的表达。所有表达树的叶子都有一个数字字符串值。而所有表达树的非叶子都有另一个操作字符串值。
         给定一个表达数组，请构造该表达的表达树，并返回该表达树的根。

         样例
         对于 (2*6-(23+7)/(1+2)) 的表达（可表示为 ["2" "*" "6" "-" "(" "23" "+" "7" ")" "/" "(" "1" "+" "2" ")"]).
         其表达树如下：
                    -
                 /    \
                *      /
              / \   /   \
             2   6 +     +
                  / \   / \
                23   7 1   2
         */
////        String[] expression = {"(","(","(","(","(",")",")",")",")",")"};
//        String[] expression = {"2","*","6","-","(","(","23","+","7",")","/","(","1","+","2",")", "-", "12", ")"};
//        XYLog.d("表达式", expression, "的表达树为：", solutions.build(expression));




        /**
         判断数独是否合法
         请判定一个数独是否有效。
         该数独可能只填充了部分数字，其中缺少的数字用 . 表示。
         注意事项
         一个合法的数独（仅部分填充）并不一定是可解的。我们仅需使填充的空格有效即可。

         什么是 数独？
         http://sudoku.com.au/TheRules.aspx
         http://baike.baidu.com/subview/961/10842669.htm
         */
//        String[] strs = {"..5.....6","....14...",".........",".....92..","5....2...",".......3.","...54....","3.....42.","...27.6.."};
//        Sudoku sudoku = new Sudoku(strs);
//        XYLog.d(sudoku.board, "\n", solutions.isValidSudoku(sudoku.board)? "": "不", "是合法的数独");




        /**
         数字三角形
         给定一个数字三角形，找到从顶部到底部的最小路径和。每一步可以移动到下面一行的相邻数字上。
         注意事项
         如果你只用额外空间复杂度O(n)的条件下完成可以获得加分，其中n是数字三角形的总行数。

         样例
         比如，给出下列数字三角形：
         [
             [2],
            [3,4],
          [6,5,7],
         [4,1,8,3]
         ]
         从顶到底部的最小路径和为11 ( 2 + 3 + 5 + 1 = 11)。
         */
//        int[][] triangle = {
//                {2},
//                {3,4},
//                {6,5,7},
//                {4,1,8,3}
//        };
//        XYLog.d(triangle, "的最小路径和为：", solutions.minimumTotal(triangle));



        /**
         分割回文串 II
         给定一个字符串s，将s分割成一些子串，使每个子串都是回文。
         返回s符合要求的的最少分割次数。

         样例
         比如，给出字符串s = "aab"，
         返回 1， 因为进行一次分割可以将字符串s分割成["aa","b"]这样两个回文子串
         */
//        String str = "abcbaaba";
//        XYLog.d(str, "经过", solutions.minCut(str), "次分割就能全部变成回文串");



        /**
         单词切分
         给出一个字符串s和一个词典，判断字符串s是否可以被空格切分成一个或多个出现在字典中的单词。

         样例
         给出 s = "lintcode" ,dict = ["lint","code"]
         返回 true 因为"lintcode"可以被空格切分成"lint code"
         */
//        String s = "lintcode";
//        Set<String> dict = new HashSet<String>();
//        dict.add("lint");
//        dict.add("code");
//        XYLog.d(s, solutions.wordBreak(s, dict)? "": "不", "可以被", dict, "切分");



        /**
         排序列表转换为平衡二叉查找树
         给出一个所有元素以升序排序的单链表，将它转换成一棵高度平衡的二分查找树
         */
//        ListNode head = ListNodeFactory.build("1->2->3->4->5->6");
//        XYLog.d(head);
//        XYLog.d("转换为二叉查找树之后是：", solutions.sortedListToBST(head));





        /**
         (一脸懵逼,lintcode上面的检验也没有做好)
         复制带随机指针的链表
         给出一个链表，每个节点包含一个额外增加的随机指针可以指向链表中的任何节点或空的节点。
         返回一个深拷贝的链表。
         */






        /**
         合并k个排序链表
         合并k个排序链表，并且返回合并后的排序链表。尝试分析和描述其复杂度。

         样例
         给出3个排序链表[2->4->null,null,-1->null]，返回 -1->2->4->null
         */
//        ArrayList<ListNode> lists = new ArrayList<ListNode>();
//        lists.add(ListNodeFactory.build("null"));
//        lists.add(ListNodeFactory.build("-1->5->11->null"));
//        lists.add(ListNodeFactory.build("null"));
//        lists.add(ListNodeFactory.build("6->10->null"));
//        XYLog.d(lists, "合并排序之后：");
//        XYLog.d(solutions.mergeKLists(lists));


        /**
         带环链表 II
         给定一个链表，如果链表中存在环，则返回到链表中环的起始节点的值，如果没有环，返回null。

         样例
         给出 -21->10->4->5, tail connects to node index 1，返回10
         */
//        ListNode head = new ListNode(-20);
//        head.next = new ListNode(10);
//        head.next.next = new ListNode(4);
//        head.next.next.next = new ListNode(5);
//        head.next.next.next.next = head.next;
//        XYLog.d(head);
//        XYLog.d(solutions.detectCycle(head));





        /**
         带环链表
         给定一个链表，判断它是否有环。

         样例
         给出 -21->10->4->5, tail connects to node index 1，返回 true
         */
//        ListNode head = new ListNode(-20);
//        head.next = new ListNode(10);
//        head.next.next = new ListNode(4);
//        head.next.next.next = new ListNode(5);
//        head.next.next.next.next = null;
//        XYLog.d(head, solutions.hasCycle(head)? "带环": "不带环");




        /**
         删除排序数组中的重复数字 II
         跟进“删除重复数字”：
         如果可以允许出现两次重复将如何处理？

         样例
         给出数组A =[1,1,1,2,2,3]，你的函数应该返回长度5，此时A=[1,1,2,2,3]。
         */
//        int[] arr = {-14,-14,-14,-14,-14,-14,-14,-13,-13,-13,-13,-12,-11,-11,-11,-9,-9,-9,-7,-7,-7,-6,-6,-5,-5,-5,-4,-4,-4,-3,-3,-3,-2,-2,-2,-1,-1,0,0,0,0,1,1,1,2,2,2,2,3,3,3,3,3,4,4,4,5,5,6,6,6,7,7,7,7,8,8,8,8,9,9,10,10,11,11,11,11,11,12,12,12,12,13,13,13,13,14,14,15,16,17,18,18,18,20,20,21,21,21,21,21,22,22,22,22,23,24,24,25};
//        XYLog.d(arr, "去重后：");
//        XYLog.d("长度：", solutions.removeDuplicatesII(arr), arr);






        /**
         删除排序数组中的重复数字
         给定一个排序数组，在原数组中删除重复出现的数字，使得每个元素只出现一次，并且返回新的数组的长度。
         不要使用额外的数组空间，必须在原地没有额外空间的条件下完成。

         样例
         给出数组A =[1,1,2]，你的函数应该返回长度2，此时A=[1,2]。
         */
//        int[] arr = {-10,0,1,2,3};
//        XYLog.d(arr, "去重后：");
//        XYLog.d("长度：", solutions.removeDuplicates(arr), arr);





        /**
         重排链表
         给定一个单链表L: L0→L1→…→Ln-1→Ln,
         重新排列后为：L0→Ln→L1→Ln-1→L2→Ln-2→…
         必须在不改变节点值的情况下进行原地操作。

         样例
         给出链表 1->2->3->4->null，重新排列后为1->4->2->3->null。
         */
//        ListNode head = ListNodeFactory.build("1->2->3->4->null");
//        XYLog.d(head, "重排后：");
//        solutions.reorderList(head);
//        XYLog.d(head);




        /**
         链表排序
         在 O(n log n) 时间复杂度和常数级的空间复杂度下给链表排序。

         样例
         给出 1->3->2->null，给它排序变成 1->2->3->null.
         */
//        ListNode head = ListNodeFactory.build("1->3->2->null");
//        XYLog.d(head, "排序之后是");
//        XYLog.d(solutions.sortList(head));






        /**
         二叉树的最大深度/二叉树高度
         给定一个二叉树，找出其最大深度。
         二叉树的深度为根节点到最远叶子节点的距离。

         样例
         给出一棵如下的二叉树:
            1
          / \
         2   3
            / \
           4   5
         这个二叉树的最大深度为3.
         */
////        TreeNode root = TreeNodeFactory.build("1,2,3,#,#,4,5");
//        TreeNode root = TreeNodeFactory.build("1,2,3,4,5,6,7,8,9,#,10,11,#,12,13,14,15");
//        XYLog.d(root, "的高度为：", solutions.maxDepth(root));





        /**
         链表划分
         给定一个单链表和数值x，划分链表使得所有小于x的节点排在大于等于x的节点之前。
         你应该保留两部分内链表节点原有的相对顺序。

         样例
         给定链表 1->4->3->2->5->2->null，并且 x=3
         返回 1->2->2->4->3->5->null
         */
//        ListNode head = ListNodeFactory.build("1->4->3->2->5->2->null");
//        int x = 3;
//        XYLog.d(head, "通过" + x + "分割，得到", solutions.partition(head, x));



        /**
         验证二叉查找树
         给定一个二叉树，判断它是否是合法的二叉查找树(BST)
         一棵BST定义为：
         节点的左子树中的值要严格小于该节点的值。
         节点的右子树中的值要严格大于该节点的值。
         左右子树也必须是二叉查找树。
         一个节点的树也是二叉查找树。

         样例
         一个例子：
                2
              / \
             1   4
               / \
              3   5
         上述这棵二叉树序列化为 {2,1,4,#,#,3,5}.
         */
//        TreeNode root = TreeNodeFactory.build("2,1,4,#,#,3,5");
//        XYLog.d(root, solutions.isValidBST(root)? "是": "不是", "二叉查找树");



        /**
         二叉树中的最大路径和
         给出一棵二叉树，寻找一条路径使其路径和最大，路径可以在任一节点中开始和结束（路径和为两个节点之间所在路径上的节点权值之和）

         样例
         给出一棵二叉树：
            1
          / \
         2   3
         返回 6
         */
//        TreeNode root = TreeNodeFactory.build("-10,-20,#,#,-31,-24,-5,#,#,-6,-7,-8,-9");
//        XYLog.d(root, "最大路径和为：", solutions.maxPathSum(root));


        /**
         平衡二叉树
         给定一个二叉树,确定它是高度平衡的。对于这个问题,一棵高度平衡的二叉树的定义是：一棵二叉树中每个节点的两个子树的深度相差不会超过1。

         样例
         给出二叉树 A={3,9,20,#,#,15,7}, B={3,#,20,15,7}
         二叉树A是高度平衡的二叉树，但是B不是
         */
//        TreeNode rootA = TreeNodeFactory.build("3,9,20,#,#,15,7");
//        TreeNode rootB = TreeNodeFactory.build("3,#,20,15,7");
//        XYLog.d(rootA, solutions.isBalanced(rootA)? "平衡": "不平衡");
//        XYLog.d(rootB, solutions.isBalanced(rootB)? "平衡": "不平衡");









        /**
         背包问题
         在n个物品中挑选若干物品装入背包，最多能装多满？假设背包的大小为m，每个物品的大小为A[i]
         注意事项
         你不可以将物品进行切割。

         样例
         如果有4个物品[2, 3, 5, 7]
         如果背包的大小为11，可以选择[2, 3, 5]装入背包，最多可以装满10的空间。
         如果背包的大小为12，可以选择[2, 3, 7]装入背包，最多可以装满12的空间。
         函数需要返回最多能装满的空间大小。
         */
//        int[] arr = {2, 3, 5, 7};
//        int backSize = 12;
//        XYLog.d("将不可分割的几个物品", arr, "装入容量为", backSize, "的背包中，能放下的最大重量为：", solutions.backPack(backSize, arr));





        /**
         最小调整代价
         给一个整数数组，调整每个数的大小，使得相邻的两个数的差小于一个给定的整数target，调整每个数的代价为调整前后的差的绝对值，求调整代价之和最小是多少。
         注意事项
         你可以假设数组中每个整数都是正整数，且小于等于100。
         样例
         对于数组[1, 4, 2, 3]和target=1，最小的调整方案是调整为[2, 3, 2, 3]，调整代价之和是2。返回2。
         */
//        int[] arr = {20,25,35,45,55,65,75,85,25,35,45,55,15,15,15,7,2,11,15,11,15};
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        for (int i: arr) {
//            list.add(i);
//        }
//        int target = 10;
//        XYLog.d(list, "的最小调整代价为：");
//        XYLog.d(solutions.MinAdjustmentCost(list, target));



        /**
         k数和 II
         给定n个不同的正整数，整数k（1<= k <= n）以及一个目标数字。　　　　
         在这n个数里面找出K个数，使得这K个数的和等于目标数字，你需要找出所有满足要求的方案。
         样例
         给出[1,2,3,4]，k=2， target=5，返回 [[1,4],[2,3]]
         */
//        int[] arr = {1,2,3,4};
//        int k = 2;
//        int sum = 5;
//        XYLog.d(arr, "中找出", k, "个数之和为", sum, "的所有方案为：", solutions.kSumII(arr, k, sum));




        /**
         k数和
         给定n个不同的正整数，整数k（k < = n）以及一个目标数字。　　　　
         在这n个数里面找出K个数，使得这K个数的和等于目标数字，求问有多少种方案？

         样例
         给出[1,2,3,4]，k=2， target=5，[1,4] and [2,3]是2个符合要求的方案
         */
//        int[] arr = {1,2,3,4};
//        int k = 2;
//        int sum = 5;
//        XYLog.d(arr, "中找出", k, "个数之和为", sum, "的方案总数为：", solutions.kSum(arr, k, sum));



        /**
         最近公共祖先
         给定一棵二叉树，找到两个节点的最近公共父节点(LCA)。
         最近公共祖先是两个节点的公共的祖先节点且具有最大深度。
         注意事项
         假设给出的两个节点都在树中存在

         样例
         对于下面这棵二叉树
                4
              / \
            3    7
               / \
              5   6
         LCA(3, 5) = 4
         LCA(5, 6) = 7
         LCA(6, 7) = 7
         */
//        String buildStr = "4, 3, 7, #, #, 5, 6";
//        TreeNode root = TreeNodeFactory.build(buildStr);
//        TreeNode nodeA = root.left;
//        TreeNode nodeB = root.right.left;
//        XYLog.d("在", root, "中", nodeA, "和", nodeB, "的最近公共祖先为", solutions.lowestCommonAncestor(root, nodeA, nodeB));









        /**
         删除二叉查找树的节点
         给定一棵具有不同节点值的二叉查找树，删除树中与给定值相同的节点。如果树中没有相同值的节点，就不做任何处理。你应该保证处理之后的树仍是二叉查找树。
         给出如下二叉查找树：
                5
            /      \
           3        6
         /   \
         2    4

         删除节点3之后，你可以返回：
                 5
             /      \
            2        6
             \
              4
         或者：
               5
           /      \
          4        6
         /
         2
         */
////        TreeNode root = new TreeNode(5);
////        root.left = new TreeNode(3);
////        root.left.left = new TreeNode(2);
////        root.left.right = new TreeNode(4);
////        root.right = new TreeNode(6);
////        XYLog.d(root);
////        XYLog.d("移除3之后：", solutions.removeNode(root, 3));
//        TreeNode root = new TreeNode(20);
//        root.left = new TreeNode(1);
//        root.right = new TreeNode(40);
//        root.right.left = new TreeNode(35);
//        XYLog.d(root);
//        XYLog.d("移除20之后：", solutions.removeNode(root, 20));





        /**
         二叉查找树迭代器
         设计实现一个带有下列属性的二叉查找树的迭代器：
         元素按照递增的顺序被访问（比如中序遍历）
         next()和hasNext()的询问操作要求均摊时间复杂度是O(1)

         样例
         对于下列二叉查找树，使用迭代器进行中序遍历的结果为 [1, 6, 10, 11, 12]
            10
         /    \
         1      11
          \       \
           6       12
         */
//        TreeNode root = new TreeNode(10);
//        root.left = new TreeNode(1);
//        root.left.right = new TreeNode(6);
//        root.right = new TreeNode(11);
//        root.right.right = new TreeNode(12);
//        XYLog.d(root, "通过" + BSTIterator.class.getSimpleName() + "遍历，得到的序列为：", solutions.listBinarySearchTree(root));


        /**
         骰子求和
         扔 n 个骰子，向上面的数字之和为 S。给定 Given n，请列出所有可能的 S 值及其相应的概率。

         样例
         给定 n = 1，返回 [ [1, 0.17], [2, 0.17], [3, 0.17], [4, 0.17], [5, 0.17], [6, 0.17]]。
         */
//        int n = 2;
//        XYLog.d("抛掷" + n + "枚骰子", "所有骰子出现和的概率为:", solutions.dicesSum(n));



        /**
         在二叉查找树中插入节点
         给定一棵二叉查找树和一个新的树节点，将节点插入到树中。
         你需要保证该树仍然是一棵二叉查找树。

         样例
         给出如下一棵二叉查找树，在插入节点6之后这棵二叉查找树可以是这样的：
               2             2
             / \           / \
            1   4   -->   1   4
               /             / \
              3             3   6
         */
//        TreeNode root = new TreeNode(2);
//        root.left = new TreeNode(1);
//        root.right = new TreeNode(4);
//        root.right.left = new TreeNode(3);
//        XYLog.d(root);
//        TreeNode newNode = new TreeNode(6);
//        XYLog.d("插入", newNode);
//        solutions.insertNode(root, newNode);
//        XYLog.d(root);





        /**
         落单的数 III
         给出2*n + 2个的数字，除其中两个数字之外其他每个数字均出现两次，找到这两个数字。
         样例
         给出 [1,2,2,3,4,4,5,3]，返回 1和5
         */
//        int[] arr = {1,2,2,3,4,4,5,3};
//        XYLog.d(arr, "中落单的数为：", solutions.singleNumberIII(arr));



        /**
         落单的数 II
         给出3*n + 1 个的数字，除其中一个数字之外其他每个数字均出现三次，找到这个数字。

         样例
         给出 [1,1,2,3,3,3,2,2,4,1] ，返回 4
         */
////        int[] arr = {1,1,2,3,3,3,2,2,4,1};
//        int[] arr = {43,16,45,89,45,-2147483648,45,2147483646,-2147483647,-2147483648,43,2147483647,-2147483646,-2147483648,89,-2147483646,89,-2147483646,-2147483647,2147483646,-2147483647,16,16,2147483646,43};
//        XYLog.d(arr, "中落单的数为：", solutions.singleNumberII(arr));




        /**
         落单的数
         给出2*n + 1 个的数字，除其中一个数字之外其他每个数字均出现两次，找到这个数字。

         样例
         给出 [1,2,2,1,3,4,3]，返回 4
         挑战
         一次遍历，常数级的额外空间复杂度
         思路
         由于亦或运算满足结合律，即a1 ^ a2 ^ a1 = a1 ^ a1 ^ a2 = 0 ^ a2 = a2,最终结果就是落单的
         */
//        int[] arr = {1,2,2,1,3,4,3};
//        XYLog.d(arr, "中，落单的数为：", solutions.singleNumber(arr));


        /**
         数据流中位数

         数字是不断进入数组的，在每次添加一个新的数进入数组的同时返回当前新数组的中位数。
         中位数的定义：
         中位数是排序后数组的中间值，如果有数组中有n个数，则中位数为A[(n-1)/2]。
         比如：数组A=[1,2,3]的中位数是2，数组A=[1,19]的中位数是1。
         样例
         持续进入数组的数的列表为：[1, 2, 3, 4, 5]，则返回[1, 1, 2, 2, 3]
         持续进入数组的数的列表为：[4, 5, 1, 3, 2, 6, 0]，则返回 [4, 4, 4, 3, 3, 3, 3]
         持续进入数组的数的列表为：[2, 20, 100]，则返回[2, 2, 20]

         堆 http://blog.csdn.net/genios/article/details/8157031
         */
//        int[] arr = {2,20,13,18,15,8,3,5,4,25};
//        XYLog.d(arr, " 的流数据中位数为：", solutions.medianII(arr));





        /**
         中位数

         给定一个未排序的整数数组，找到其中位数。
         中位数是排序后数组的中间值，如果数组的个数是偶数个，则返回排序后数组的第N/2个数。
         样例
         给出数组[4, 5, 1, 2, 3]， 返回 3
         给出数组[7, 9, 4, 5]，返回 5
         */
//        int[] arr = {4, 5, 1, 2, 3};
//        XYLog.d(arr, " 的中位数为：", solutions.median(arr));






        /**
         最长公共子串

         给出两个字符串，找到最长公共子串，并返回其长度。
         样例
         给出A=“ABCD”，B=“CBCE”，返回 2

         思路
         http://my.oschina.net/leejun2005/blog/117167
         */
//        String strA = "abccccccccccde";
//        String strB = "dbccccccabccde";
//        XYLog.d(strA, " 和 ", strB, " 的最长公共子串长度为：", solutions.longestCommonSubstring(strA, strB));






        /**
         最长公共前缀

         给k个字符串，求出他们的最长公共前缀(LCP)
         样例
         在 "ABCD" "ABEF" 和 "ACEF" 中,  LCP 为 "A"
         在 "ABCDEFG", "ABCEFG", "ABCEFA" 中, LCP 为 "ABC"
         */
//        String[] strs = {"ABCDEFG", "ABCEFG", "ABCEFA"};
//        XYLog.d(strs, "的最长公共前缀为：", solutions.longestCommonPrefix(strs));




        /**
         最长公共子序列

         给出两个字符串，找到最长公共子序列(LCS)，返回LCS的长度。
         最长公共子序列的定义：
         最长公共子序列问题是在一组序列（通常2个）中找到最长公共子序列（注意：不同于子串，LCS不需要是连续的子串）。
         该问题是典型的计算机科学问题，是文件差异比较程序的基础，在生物信息学中也有所应用。
         样例
         给出"ABCD" 和 "EDCA"，这个LCS是 "A" (或 D或C)，返回1
         给出 "ABCD" 和 "EACB"，这个LCS是"AC"返回 2
         */
//        String strA = "ABCD";
//        String strB = "EACB";
//        XYLog.d(strA, " 和 ", strB, " 的最长公共子序列长度为：", solutions.longestCommonSubsequence(strA, strB));


        /**
         最长上升子序列

         给定一个整数序列，找到最长上升子序列（LIS），返回LIS的长度。
         最长上升子序列的定义：
         最长上升子序列问题是在一个无序的给定序列中找到一个尽可能长的由低到高排列的子序列，这种子序列不一定是连续的或者唯一的。
         https://en.wikipedia.org/wiki/Longest_increasing_subsequence
         样例
         给出 [5,4,1,2,3]，LIS 是 [1,2,3]，返回 3
         给出 [4,2,4,5,3,7]，LIS 是 [2,4,5,7]，返回 4
         */
//        int[] arr = {88,4,24,82,86,1,56,74,71,9,8,18,26,53,77,87,60,27,69,17,76,23,67,14,98,13,10,83,20,43,39,29,92,31,0,30,90,70,37,59};
////        int[] arr = {1, 1,1, 1, 1,1};
//        XYLog.d(arr, "的最长升序列长度为：", solutions.longestIncreasingSubsequence(arr));
//        XYLog.d(arr, "的最长升序列长度为(采用二分法优化)：", solutions.longestIncreasingSubsequenceWithBitch(arr));


        /**
         寻找峰值

         你给出一个整数数组(size为n)，其具有以下特点：
         相邻位置的数字是不同的
         A[0] < A[1] 并且 A[n - 2] > A[n - 1]
         假定P是峰值的位置则满足A[P] > A[P-1]且A[P] > A[P+1]，返回数组中任意一个峰值的位置。
         给出数组[1, 2, 1, 3, 4, 5, 7, 6]返回1, 即数值 2 所在位置, 或者6, 即数值 7 所在位置.
         */
//        int[] arr = {1,2,4,5,6,7,8,6};
//        int peakIndex = solutions.findPeak(arr);
//        XYLog.d(arr, "的一个峰值为：", arr[peakIndex], "，index=", peakIndex);



        //根据前序遍历和中序遍历树构造二叉树.
//        int[][] arrs = {{1, 2, 4, 8, 5, 3, 6, 7},{8, 4, 2, 5, 1, 6, 3, 7}};
////        int[][] arrs = {{1, 2, 3, 4, 5},{2, 4, 5, 3, 1}};
//        XYLog.d(solutions.buildTreePreIn(arrs[0], arrs[1]));


        //根据中序遍历和后序遍历树构造二叉树
//        int[][] arrs = {{8, 4, 2, 5, 1, 6, 3, 7},{8, 4, 5, 2, 6, 7, 3, 1}};
////        int[][] arrs = {{2,4,5,3,1},{5,4,3,2,1}};
//        XYLog.d(solutions.buildTree(arrs[0], arrs[1]));



        //给出一棵二叉树，返回其节点值的锯齿形层次遍历（先从左往右，下一层再从右往左，层与层之间交替进行）
//        TreeNode root = new TreeNode(3);
//        root.left = new TreeNode(9);
//        root.right = new TreeNode(20);
//        root.right.left = new TreeNode(15);
//        root.right.right = new TreeNode(7);
//        XYLog.d(solutions.zigzagLevelOrder(root));



        //给出一棵二叉树，返回其节点值从底向上的层次序遍历（按从叶节点所在层到根节点所在的层遍历，然后逐层从左往右遍历）
//        TreeNode root = new TreeNode(3);
//        root.left = new TreeNode(9);
//        root.right = new TreeNode(20);
//        root.right.left = new TreeNode(15);
//        root.right.right = new TreeNode(7);
//        XYLog.d(solutions.levelOrderBottom(root));



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
            return "(" + start + ", " + end + ")";
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

    public static class BSTIterator {

        private List<TreeNode> nodeStack = new ArrayList<TreeNode>();

        private int iteratorIndexMax = 0;

        private int iteratorIndex = 0;

        public BSTIterator(TreeNode root) {
            insertIntoStack(root);
            iteratorIndexMax = nodeStack.size();
        }

        private void insertIntoStack(TreeNode node) {
            if (node != null) {
                if (node.left != null) {
                    insertIntoStack(node.left);
                }

                nodeStack.add(node);

                if (node.right != null) {
                    insertIntoStack(node.right);
                }
            }
        }

        public boolean hasNext() {
            return iteratorIndex < iteratorIndexMax;
        }

        public TreeNode next() {
            return nodeStack.get(iteratorIndex++);
        }
    }

}
