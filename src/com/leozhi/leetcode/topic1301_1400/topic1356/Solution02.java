package com.leozhi.leetcode.topic1301_1400.topic1356;

import java.util.Arrays;

/**
 * @author leozhi
 * 巧妙合并元素二进制 1 的个数和元素值
 * 通过 3ms
 */
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