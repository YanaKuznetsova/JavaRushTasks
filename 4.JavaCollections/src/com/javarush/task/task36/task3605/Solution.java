package com.javarush.task.task36.task3605;

import java.io.*;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        FileReader reader = new FileReader(filename);
        TreeSet<String> set = new TreeSet<>();
        char[] nextChar = new char[1];
        while (reader.read(nextChar) > 0) {
            String next = Character.toString(nextChar[0]).toLowerCase();
            if (Character.isLetter(next.charAt(0))) {
                set.add(next);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i ++) {
            if (set.size() > 0) {
                stringBuilder.append(set.pollFirst());
            }
        }
        System.out.println(stringBuilder.toString());
        reader.close();

    }
}
