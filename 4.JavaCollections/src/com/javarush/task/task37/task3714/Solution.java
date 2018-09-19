package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

/* 
Древний Рим
*/
public class Solution {

    private static HashMap<String, Integer> romanToIntegerMap = new HashMap<>();
    static {
        romanToIntegerMap.put("I", 1);
        romanToIntegerMap.put("V", 5);
        romanToIntegerMap.put("X", 10);
        romanToIntegerMap.put("L", 50);
        romanToIntegerMap.put("C", 100);
        romanToIntegerMap.put("D", 500);
        romanToIntegerMap.put("M", 1000);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
//        System.out.println(romanToInteger("iii"));
//        System.out.println(romanToInteger("xliv"));
//        System.out.println(romanToInteger("MMDCCCLXViii"));
//        System.out.println(romanToInteger("MCMXCIX"));
    }

    public static int romanToInteger(String s) {
        int result = 0;
        int currentNumber = romanToIntegerMap.get(String.valueOf(s.toUpperCase().charAt(0)));
        for (int i = 0; i < s.length() - 1; i++) {
            int nextNumber = romanToIntegerMap.get(String.valueOf(s.toUpperCase().charAt(i+1)));
            if (currentNumber < nextNumber) {
                result -= currentNumber;
            } else {
                result+= currentNumber;
            }
            currentNumber = nextNumber;
        }
        return result + currentNumber;
    }

}
