package com.javarush.task.task22.task2209;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
Составить цепочку слов
*/
public class Solution {
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

        StringBuilder result = getLine(allWords);
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        StringBuilder result = new StringBuilder();
        if (words == null || words.length == 0) {
            return result;
        }
        if (words.length == 1 || words[0].equals("")) {
            return result.append(words[0]);
        }

        ArrayList<String> list = new ArrayList<>(Arrays.asList(words));
        list.remove(" ");

        while(!canMakeChain(list)) {
            Collections.shuffle(list);
        }
        for (String word: list){
            result.append(word).append(" ");
        }
        result.deleteCharAt(result.length()-1);
        return result;

//        boolean canAddWord = true;
//        sb.append(list.get(0));
//        list.remove(0);
//        while (canAddWord) {
//            String lastChar = sb.substring(sb.length()-1);
//            int i = 0;
//            boolean foundNewWord = false;
//            while (i < list.size()){
//                if (list.get(i).startsWith(lastChar.toUpperCase())){
//                    sb.append(" " + list.get(i));
//                    list.remove(i);
//                    foundNewWord = true;
//                    break;
//                } else {
//                    i++;
//                }
//            }
//            canAddWord = foundNewWord;
//        }

    }

    private static boolean canMakeChain(ArrayList<String> list) {
        for (int i = 0; i < list.size() - 1; i++){
            String currentWord = list.get(i).toLowerCase();
            String nextWord = list.get(i+1).toLowerCase();
            if (currentWord.charAt(currentWord.length() - 1) != nextWord.charAt(0)) {
                return false;
            }
        }
        return true;
    }

}
