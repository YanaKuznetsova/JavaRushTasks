package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {

    private final static int length = 10;

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
        solution.createExpression(1234);
        solution.createExpression(1);
        solution.createExpression(2);
        solution.createExpression(3);
        solution.createExpression(4);
        solution.createExpression(3000);
    }

    public void createExpression3(int number) {
        StringBuilder result = new StringBuilder().append(number).append(" =");
        String[] formats = {"", " + %d", " - %d"};
        for (int i = number, tri = 1; 0 < i; i = ++i / 3, tri *= 3) {
            result.append(String.format(formats[i % 3], tri));
        }
        System.out.println(result);
    }

    public void createExpression(int number) {

        int[] ternaryRepresentation = convertToTernary(number);
        int[] symmetricTernaryRepresentation = new int[length];
        int counter = 0;
        int i = 0;
        while (i < length) {
            if (ternaryRepresentation[i] == 0 || ternaryRepresentation [i] == 1) {
                symmetricTernaryRepresentation[counter] = ternaryRepresentation[i];
                counter++;
                i++;
            } else {
                if (ternaryRepresentation[i] == 2) {
                    symmetricTernaryRepresentation[counter] = -1;
                } else {
                    symmetricTernaryRepresentation[counter] = 0;
                }
                counter++;
                i++;
                ternaryRepresentation[i] += 1;
            }
        }
        counter = 0;
        StringBuilder result = new StringBuilder(number + " =");
        int radix = 1;
        while (counter < length) {
            int value = symmetricTernaryRepresentation[counter] * radix;
            if (value != 0) {
                result.append(value > 0 ? " + " : " - ");
                result.append(Math.abs(value));
            }
            radix *= 3;
            counter++;
        }
        System.out.println(result);
    }

    private int[] convertToTernary (int number) {
        int[] ternaryRepresentation = new int[length];
        int counter = 0;
        while (number > 3) {
            ternaryRepresentation[counter] = number % 3;
            number = number / 3;
            counter++;
        }
        ternaryRepresentation[counter] = number;
        return ternaryRepresentation;
    }
}