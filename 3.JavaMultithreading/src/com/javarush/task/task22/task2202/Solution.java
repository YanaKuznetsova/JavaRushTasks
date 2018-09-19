package com.javarush.task.task22.task2202;

/* 
Найти подстроку
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getPartOfString("JavaRush - лучший сервис обучения Java."));
        System.out.println(getPartOfString("Амиго и Диего лучшие друзья!"));
        System.out.println(getPartOfString("Aaa bbb bbb  ccc!"));
        System.out.println(getPartOfString("Aaa bbb bbb ccc ddd eee fff!"));
        //System.out.println(getPartOfString("Aaa bbb bbb ccc!"));
    }

    public static String getPartOfString(String string) {
        if (string == null || string.isEmpty()) {
            throw new TooShortStringException();
        }
        String[] words = string.split(" ");
        if (words.length < 5) {
            throw new TooShortStringException();
        }
        StringBuilder sb = new StringBuilder();
        int index = 1;
        while (index < 4){
            sb.append(words[index++] + " ");
        }
        sb.append(words[index]);
        return sb.toString();
    }

    public static class TooShortStringException extends RuntimeException {

    }
}
