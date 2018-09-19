package com.javarush.task.task30.task3002;

/* 
Осваиваем методы класса Integer
*/
public class Solution {

    public static void main(String[] args) {
        System.out.println(convertToDecimalSystem("0x16")); //22
        System.out.println(convertToDecimalSystem("012"));  //10
        System.out.println(convertToDecimalSystem("0b10")); //2
        System.out.println(convertToDecimalSystem("62"));   //62
    }

    public static String convertToDecimalSystem(String string) {
        String result = string;
        if (string.startsWith("0b")){
            result = String.valueOf(Integer.parseInt(string.substring(2), 2));
        } else if (string.startsWith("0x")){
            result = String.valueOf(Integer.parseInt(string.substring(2), 16));
        } else if (string.startsWith("0")){
            result = String.valueOf(Integer.parseInt(string.substring(1), 8));
        }
        return result;
    }
}
