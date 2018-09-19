package com.javarush.task.task39.task3904;

import java.util.Arrays;

/* 
Лестница
*/
public class Solution {
    private static int n = 70;
    private static long[] numOfRunups = new long[n + 1];
    static {
        numOfRunups[0] = 1;
        numOfRunups[1] = 1;
        numOfRunups[2] = 2;
        numOfRunups[3] = 4;
    }
    public static void main(String[] args) {
//        System.out.println("Number of possible runups for " + n + " stairs is: " + countPossibleRunups(n));
        System.out.println(countPossibleRunups(-1));
        System.out.println(countPossibleRunups(0));
        System.out.println(countPossibleRunups(1));
        System.out.println(countPossibleRunups(2));
        System.out.println(countPossibleRunups(3));
        System.out.println(countPossibleRunups(4));

    }

    public static long countPossibleRunups(int n) {
        long result = 0;
        if (n >= 0) {
            int index = 4;
            while (index <= n) {
                numOfRunups[index] = numOfRunups[index-1] + numOfRunups[index - 2]+ numOfRunups[index - 3];
                index++;
            }
            result = numOfRunups[n];
        }
        return result;
    }
}

