package com.javarush.task.task39.task3902;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Биты были биты
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please type in a number: ");

        long l = Long.parseLong(reader.readLine());
        String result = isWeightEven(l) ? "even" : "odd";
        System.out.println("Number of ones in a given number is " + result);
//
//        System.out.println("1 = 1 " + isWeightEven(1));
//        System.out.println("2 = 10 " + isWeightEven(2));
//        System.out.println("3 = 11 " + isWeightEven(3));
//        System.out.println("4 = 100 " + isWeightEven(4));
//        System.out.println("5 = 101 " + isWeightEven(5));
//        System.out.println("6 = 110 " + isWeightEven(6));
//        System.out.println("7 = 111 " + isWeightEven(7));
//        System.out.println("8 = 1000 " + isWeightEven(8));
//        System.out.println("9223372036854775807 " + isWeightEven(Long.MAX_VALUE));
    }

    public static boolean isWeightEven(long number) {
        int result = 0;
        for (int i = 0; i < 64; i++) {
            result += number ^ 1;
            number = number >> 1;
        }
        return result % 2 == 0;
    }

}
