package com.leozhi.leetcode.topic1301_1400.topic1356;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leozhi
 * 递推预处理
 * 位运算 右移 >>
 * 位运算 位与 &
 * 通过 9ms
 */
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
