package com.javarush.task.task39.task3908;

import java.util.HashMap;
import java.util.Map;

/*
Возможен ли палиндром?
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isPalindromePermutation(""));
        System.out.println(isPalindromePermutation("aba"));
        System.out.println(isPalindromePermutation("abba"));
        System.out.println(isPalindromePermutation("abc"));
        System.out.println(isPalindromePermutation("ab bb ba"));
    }

    public static boolean isPalindromePermutation(String s) {
        boolean result = true;
        if (s != null || !s.equals("")) {
            String string = s.toLowerCase().replace(" ", "");
            Map<Character, Integer> numberOfCharOccurrences = new HashMap<>();
            for (int i = 0; i < string.length(); i++) {
                int index = 1;
                if (numberOfCharOccurrences.containsKey(string.charAt(i))) {
                    index = numberOfCharOccurrences.get(string.charAt(i)) + 1;
                }
                numberOfCharOccurrences.put(string.charAt(i), index);
            }
            int numOfOdds = 0;
            for (Map.Entry<Character, Integer> entry : numberOfCharOccurrences.entrySet()) {
                if (entry.getValue() % 2 != 0) {
                    numOfOdds++;
                }
                if (numOfOdds > 1) {
                    result = false;
                    break;
                }
            }

        }
        return result;
    }
}
