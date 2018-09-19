package com.javarush.task.task30.task3013;

/* 
Набираем код
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int number = Integer.MAX_VALUE - 133;
        System.out.println(Integer.toString(number, 2));

        String result = Integer.toString(solution.resetLowerBits(number), 2);
        System.out.println(result);

        number = 1;
        System.out.println(number + "\t"+ solution.resetLowerBits(number));

        number = 2;
        System.out.println(number + "\t"+ solution.resetLowerBits(number));
        System.out.println(Integer.toString(number, 2));
        result = Integer.toString(solution.resetLowerBits(number), 2);
        System.out.println(result);

        number = 4;
        System.out.println(number + "\t"+ solution.resetLowerBits(number));
        System.out.println(Integer.toString(number, 2));
        result = Integer.toString(solution.resetLowerBits(number), 2);
        System.out.println(result);

        number = 255;
        System.out.println(number + "\t"+ solution.resetLowerBits(number));
        System.out.println(Integer.toString(number, 2));
        result = Integer.toString(solution.resetLowerBits(number), 2);
        System.out.println(result);

        number = 3456;
        System.out.println(number + "\t"+ solution.resetLowerBits(number));
        System.out.println(Integer.toString(number, 2));
        result = Integer.toString(solution.resetLowerBits(number), 2);
        System.out.println(result);
    }

    public int resetLowerBits(int number) {
        number |= number >> 1;
        number |= number >> 2;
        number |= number >> 4;
        number |= number >> 8;
        number |= number >> 16;
        return (number & ~(number>>1));
    }
}