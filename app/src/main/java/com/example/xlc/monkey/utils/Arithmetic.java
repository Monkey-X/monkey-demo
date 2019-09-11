package com.example.xlc.monkey.utils;

/**
 * @author:xlc
 * @date:2019/9/3
 * @descirbe:
 */
public class Arithmetic {

    public static void main(String[] args) {

    }


    //插入排序
    public void insterSort(int[] a) {

        int length = a.length;

        for (int i = 1; i < length; i++) {

            int temp = a[i];
            int j = i;

            while (j > 0 && temp < a[j - 1]) {
                a[j] = a[j - 1];
                j--;
            }

            if (j != i) {
                a[j] = temp;
            }

        }

    }

    //选择排序
    public void SelectionSort(int[] b) {

        int length = b.length;
        for (int i = 0; i < length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (b[j] < b[min]) {
                    min = j;
                }
            }

            if (i != min) {
                int temp = b[i];
                b[i] = b[min];
                b[min] = temp;
            }

        }
    }


    //希尔排序，插入排序的改进版，优先比较距离远的元素
    public void shellSort(int[] c) {
        int gap = 1;
        while (gap < c.length) {
            gap = gap * 3 + 1;
        }

        while (gap > 0) {
            for (int i = gap; i < c.length; i++) {
                int temp = c[i];
                int j = i - gap;
                while (j >= 0 && c[j] > temp) {
                    c[j + gap] = c[j];
                    j -= gap;
                }
                c[j + gap] = temp;
            }

            gap = (int) Math.floor(gap / 3);

        }
    }


}
