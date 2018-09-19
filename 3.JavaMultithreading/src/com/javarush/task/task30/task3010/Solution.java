package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {
        try {
            String inputString = args[0];
            boolean canConvert = false;
            for (int i = 2; i < 37; i++){
                try {
                    new BigInteger(inputString, i);
                    System.out.println(i);
                    canConvert = true;
                    break;
                } catch (Exception ignored) {}
            }
            if (!canConvert) {
               System.out.println("incorrect");
           }
        } catch (Exception ignored) {}
    }
}

//            if (!inputString.matches("[0-9a-z]*")) {
//                System.out.println("incorrect");
//            } else {
//                int maxChar = 0;
//                for (int i = 0; i < inputString.length(); i++){
//                    if (inputString.charAt(i) > maxChar) {
//                        maxChar = inputString.charAt(i);
//                    }
//                }
//                if (maxChar < 50) {
//                    System.out.println("2");
//                }
//                else if (maxChar < 58) {
//                    System.out.println(maxChar - 47);
//                } else {
//                    System.out.println(maxChar - 86);
//                }
//            }