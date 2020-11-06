package com.leozhi.leetcode.topic1301_1400.topic1356;

/**
 * @author leozhi
 * 暴力
 * 通过 13ms
 */
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
                boolean judgeNum = counts[j - 1] == counts[j] && arr[j - 1] > arr[j];
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