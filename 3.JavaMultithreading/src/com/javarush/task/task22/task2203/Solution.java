package com.javarush.task.task22.task2203;

/* 
Между табуляциями
*/
public class Solution {
    public static String getPartOfString(String string) throws TooShortStringException {
        if (string == null || string.isEmpty()) {
            throw new TooShortStringException();
        }
//        String[] words = string.split("\t");
//        if (words.length < 3) {
//            throw new TooShortStringException();
//        }
//        return words[1];
        try{
            int startPosition = string.indexOf("\t") + 1;
            int endPosition = string.indexOf("\t", startPosition);
            return string.substring(startPosition, endPosition);
        } catch (StringIndexOutOfBoundsException e) {
            throw new TooShortStringException();
        }
    }

    public static class TooShortStringException extends Exception {
    }

    public static void main(String[] args) throws TooShortStringException {
        System.out.println(getPartOfString("\tJavaRush - лучший сервис \tобучения Java\t."));
        System.out.println(getPartOfString("\taaJavaRush - лучший сервис \t"));
        System.out.println(getPartOfString("!\tbbJavaRush - лучший сервис \tcc"));
        System.out.println(getPartOfString("!\tddJavaRush - лучший сервис "));
    }
}
