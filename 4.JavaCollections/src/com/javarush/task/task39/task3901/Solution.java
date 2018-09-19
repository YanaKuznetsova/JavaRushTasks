package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/* 
Уникальные подстроки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please input your string: ");
        String s = bufferedReader.readLine();
        //String s = "ttttwt";
        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String string) {
        int result = 0;
        if (string != null && !string.equals("")) {
            Set<Character> letters = new HashSet<>();
            List<Integer> lengths = new ArrayList<>();
            int start = 0;
            while (start < string.length()){
                int end = start;
                while (end < string.length()) {
                    if (letters.add(string.charAt(end))) {
                        end++;
                    } else {
                        lengths.add(letters.size());
                        letters.clear();
                        start++;
                        break;
                    }
                }
            }
            result = Collections.max(lengths);
        }
        return result;
    }
}
