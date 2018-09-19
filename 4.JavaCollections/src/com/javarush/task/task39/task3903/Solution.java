package com.javarush.task.task39.task3903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Неравноценный обмен
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please type in a number: ");

        long number = Long.parseLong(reader.readLine());
        System.out.println("Please type in first index: ");
        int i = Integer.parseInt(reader.readLine());
        System.out.println("Please type in second index: ");
        int j = Integer.parseInt(reader.readLine());

        System.out.println("The result of swapping bits is " + swapBits(number, i, j));
//        System.out.println("10 = 1010, 1, 2 -> 1100 = 12; " + swapBits(10, 1, 2));
//        System.out.println("10 = 1010, 0, 2 -> 1010 = 10; " + swapBits(10, 0, 2));
//        System.out.println("10 = 1010, 0, 1 -> 1001 = 9; " + swapBits(10, 0, 1));

    }

    public static long swapBits(long number, int i, int j) {
        long result = number;
        long iByte = number >> i & 1;
        long jByte = number >> j & 1;

        if (iByte != jByte) {
            result = number ^ (1 << j);
            result = result ^ (1 << i);
        }

        return result;
    }
}
