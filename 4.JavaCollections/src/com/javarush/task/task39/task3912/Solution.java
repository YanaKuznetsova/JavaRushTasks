package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

public class Solution {
    public static void main(String[] args) {
        int[][] matrix = new int[][]
            {
                    {1, 1, 1, 1, 1},
                    {1, 0, 1, 0, 1},
                    {1, 0, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            };
        System.out.println(maxSquare(matrix));
    }

    public static int maxSquare(int[][] matrix) {
        int maxSideLength = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j ++) {
                if (i * j != 0) {
                    if (matrix[i][j] != 0) {
                        matrix[i][j] = Math.min(Math.min(matrix[i][j - 1], matrix[i - 1][j - 1]), matrix[i - 1][j]) + 1;
                    }
                    if (matrix[i][j] > maxSideLength) {
                        maxSideLength = matrix[i][j];
                    }
                }
            }
        }
        return maxSideLength * maxSideLength;
    }
}
