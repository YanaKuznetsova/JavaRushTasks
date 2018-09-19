package com.javarush.task.task39.task3909;

/* 
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isOneEditAway("ab", "abb"));
        System.out.println(isOneEditAway("aba", "abb"));
        System.out.println(isOneEditAway("ab", "a"));
        System.out.println(isOneEditAway("ab", "ab"));
        System.out.println(isOneEditAway("ab", "abba"));
        System.out.println(isOneEditAway("", ""));
        System.out.println(isOneEditAway("ab", ""));
        System.out.println(isOneEditAway("", "a"));
        System.out.println(isOneEditAway("ab", null));
    }

    public static boolean isOneEditAway(String first, String second) {
//        System.out.print("-" + first + "- -" + second + "- ");

        boolean result = true;
        if (first == null || second == null) {
            result = false;
        } else if (first.equals("") && second.equals("")) {
            result = true;
        } else if (Math.abs(first.length() - second.length()) > 1){
            result = false;
        } else if (first.equals(second)) {
            result = true;
        } else if (first.equals("") || second.equals("")){
            result = true;
        } else {
            StringBuilder string1 = (first.length() >= second.length()) ? new StringBuilder(first) : new StringBuilder(second);
            StringBuilder string2 = (first.length() >= second.length()) ? new StringBuilder(second) : new StringBuilder(first);

            for (int i = 0; i < string2.length(); i++) {
                if (string1.charAt(i) != string2.charAt(i)) {
                    if (first.length() != second.length()) {
                        string1.deleteCharAt(i);
                    } else {
                        string1.deleteCharAt(i);
                        string2.deleteCharAt(i);
                    }
                    break;
                }
            }
            if (string1.length() > string2.length()) {
                string1.deleteCharAt(string1.length() - 1);
            }
            result = string1.toString().equals(string2.toString());
        }
        return result;
    }
}
