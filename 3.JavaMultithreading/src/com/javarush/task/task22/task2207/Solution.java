package com.javarush.task.task22.task2207;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/* 
Обращенные слова
*/
public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String fileName = console.readLine();
        console.close();

        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        StringBuilder buffer = new StringBuilder();
        while (fileReader.ready()){
            buffer.append(fileReader.readLine() + " ");
        }
        fileReader.close();

        String[] allWords = buffer.toString().trim().split("\\s+");

        System.out.println();
        for (int i = 0; i < allWords.length; i++) {
            for (int j = i + 1; j < allWords.length; j++) {
                String reverseWord = new StringBuilder(allWords[j]).reverse().toString();
                if (allWords[i].equals(reverseWord)){
                    Pair pair = new Pair();
                    pair.first = allWords[i];
                    pair.second = allWords[j];
                    if (!result.contains(pair)) {
                        result.add(pair);
                    }
                }
            }
        }

        for (Pair pair: result) {
            System.out.println(pair.toString());
        }

    }

    public static class Pair {
        String first;
        String second;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return  first == null && second == null ? "" :
                    first == null && second != null ? second :
                    second == null && first != null ? first :
                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
