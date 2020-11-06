# [1356. 根据数字二进制下 1 的数目排序](https://leetcode-cn.com/problems/sort-integers-by-the-number-of-1-bits/)

难度：<span style='color:#5AB726;'>简单</span>

****

给你一个整数数组 `arr` 。请你将数组中的元素按照其二进制表示中数字 1 的数目升序排序。

如果存在多个数字二进制中 `1` 的数目相同，则必须将它们按照数值大小升序排列。

请你返回排序后的数组。

##### 示例 1：

> 输入：arr = [0,1,2,3,4,5,6,7,8]
  输出：[0,1,2,4,8,3,5,6,7]
  解释：[0] 是唯一一个有 0 个 1 的数。
  [1,2,4,8] 都有 1 个 1 。
  [3,5,6] 有 2 个 1 。
  [7] 有 3 个 1 。
  按照 1 的个数排序得到的结果数组为 [0,1,2,4,8,3,5,6,7]

##### 示例 2：

> 输入：arr = [1024,512,256,128,64,32,16,8,4,2,1]
  输出：[1,2,4,8,16,32,64,128,256,512,1024]
  解释：数组中所有的整数二进制下都只有 1 个 1 ，所以你需要按照数值大小将它们排序。

##### 示例 3：

> 输入：arr = [10000,10000]
  输出：[10000,10000]
  
##### 示例 4：

> 输入：arr = [2,3,5,7,11,13,17,19]
  输出：[2,3,5,17,7,11,13,19]

##### 示例 5：

> 输入：arr = [10,100,1000,10000]
输出：[10,100,10000,1000]

##### 提示：

* 1 <= arr.length <= 500
* 0 <= arr[i] <= 10^4

## Solution01

```java
public class Solution01 {
    /**
     * 根据数字二进制下 1 的数目排序
     * @param arr 需排序的数组
     * @return 排序后的数组
     */
    public int[] sortByBits(int[] arr) {
        int n = arr.length;
        // 各数组元素二进制下 1 的个数
        int[] counts = new int[n];
        for (int i = 0; i < n; i++) {
            int count = 0, element = arr[i];
            while (element > 0) {
                if (element % 2 == 1) {
                    count++;
                }
                // 数组元素除以 2，相当于 element /= 2;
                element >>= 1;
            }
            counts[i] = count;
        }
        // 从索引 1 开始遍历，将当前索引的元素与前一个索引的元素进行比较，符合条件则进行下一循环，不符合条件则与前一元素进行交换
        for (int i = 1; i < n; i++) {
            // 从当前元素不断向前比较，直至符合条件
            for (int j = i; j > 0; j--) {
                boolean judgeCount = counts[j - 1] > counts[j];
                boolean judgeNum = counts[j - 1] == counts[j] && arr[j - 1] > arr[i];
                if (judgeCount || judgeNum) {
                    // 交换数组 arr 和 counts 中两元素所在的位置
                    swap(arr, j - 1, j);
                    swap(counts, j - 1, j);
                }
            }
        }
        return arr;
    }

    /**
     * 交换数组中的元素
     * @param array 要交换元素的数组
     * @param p1 元素 1 的索引
     * @param p2 元素 2 的索引
     */
    void swap(int[] array, int p1, int p2) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }
}
```

## Solution02

```java
public class Solution02 {
    public int[] sortByBits(int[] arr) {
        // 遍历数组
        for (int i = 0; i < arr.length; i++) {
            // 通过 Integer.bitCount() 方法获取各个元素的二进制中 1 的个数，并将其乘以 [10001, 165190419] 范围内的任意一个数
            // 再加上元素本身的值，最后存回数组
            // [0, 10000] 范围内二进制中 1 的个数最多的数为 8191 ，1 的个数为 13 个
            // 乘上的值小于 10001 时，接下来获取元素的原值时会出现错误，超过 165190419 时会造成溢出
            arr[i] = Integer.bitCount(arr[i]) * 100000 + arr[i];
        }
        // 排序更新后的数组
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            // 除以之前乘上的数求余数，余数即为元素的原值
            arr[i] = arr[i] % 100000;
        }
        return arr;
    }
}
```

## Solution

```java
public class Solution03 {
    public int[] sortByBits(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int x : arr) {
            list.add(x);
        }
        int n = 10001;
        int[] bit = new int[n];
        for (int i = 0; i < n; i++) {
            // 预处理，先将 [0, 10000] 范围内所有元素的二进制中 1 的个数求出来并存入数组
            // i >> 1 将 i 的二进制表示右移 1 位，相当于 i / 2
            // i & 1 i 的二进制与 1 做位与运算，同 1 为 1，否则为 0
            // 如 {0,1，2，3，4，5，6，7，8，9} 操作之后 数组 bit 为 {0,1,1,2,1,2,2,3,1,2}
            // bit[0] = bit[0 / 2] + (0 & 1) -> bit[0] = bit[0] + 0 -> bit[0] = 0
            // bit[1] = bit[1 / 2] + (1 & 1) -> bit[1] = bit[0] + 1 -> bit[1] = 1
            // bit[2] = bit[2 / 2] + (10 & 01) -> bit[2] = bit[1] + 00 -> bit[2] = 1
            // bit[3] = bit[3 / 2] + (11 & 01) -> bit[3] = bit[1] + 01 -> bit[3] = 2
            // bit[4] = bit[4 / 2] + (100 & 001) -> bit[4] = bit[2] + 000 -> bit[4] = 1
            // bit[5] = bit[5 / 2] + (101 & 001) -> bit[5] = bit[2] + 001 -> bit[5] = 2
            // bit[6] = bit[6 / 2] + (110 & 001) -> bit[6] = bit[3] + 000 -> bit[6] = 2
            // bit[7] = bit[7 / 2] + (111 & 001) -> bit[7] = bit[3] + 001 -> bit[7] = 3
            // bit[8] = bit[8 / 2] + (1000 & 0001) -> bit[8] = bit[4] + 0000 -> bit[8] = 1
            // bit[9] = bit[9 / 2] + (1001 & 0001) -> bit[9] = bit[4] + 0001 -> bit[9] = 2
            bit[i] = bit[i >> 1] + (i & 1);
        }
        // 重写比较器，比较 list 中的元素 i1 和 i2
        list.sort((i1, i2) -> {
            if (bit[i1] != bit[i2]) {
                return bit[i1] - bit[i2];
            } else {
                return i1 - i2;
            }
        });
        // 将排序后的 list 中元素存入数组 arr
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
```