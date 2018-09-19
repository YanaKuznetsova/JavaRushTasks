package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {
        Integer[] array = new Integer[5];
        array[0] = 13;
        array[1] = 8;
        array[2] = 15;
        array[3] = 5;
        array[4] = 17;
//        System.out.println(Arrays.toString(array));
//        System.out.println(Arrays.toString(sort(array)));
//        System.out.println("------------------");
        array = new Integer[6];
        array[0] = 13;
        array[1] = 8;
        array[2] = 15;
        array[3] = 5;
        array[4] = 17;
        array[5] = 25;
//        System.out.println(Arrays.toString(array));
 //       System.out.println(Arrays.toString(sort(array)));
    }

    public static Integer[] sort(Integer[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int length = array.length;
        int median;
        Arrays.sort(array);
        if (length % 2 == 0) {
            median = (array[length/2 - 1] + array[length/2])/2;
        } else {
            median = array[length/2];
        }
        Arrays.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (Math.abs(median - o1) != Math.abs(median - o2)) {
                    return Math.abs(median - o1) - Math.abs(median - o2);
                } else {
                    return o1 - o2;
                }
            }
        });

        return array;
    }
}
