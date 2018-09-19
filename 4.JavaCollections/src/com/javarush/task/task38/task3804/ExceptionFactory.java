package com.javarush.task.task38.task3804;

public class ExceptionFactory {

    public static Throwable throwException(Enum typeOfException) {
        Throwable result = new IllegalArgumentException();

        if (typeOfException != null) {
            String temp = typeOfException.name().replace("_", " ").toLowerCase();
            StringBuilder stringBuilder = new StringBuilder(temp.substring(0,1).toUpperCase());
            String message = stringBuilder.append(temp.substring(1)).toString();

            if (typeOfException instanceof ExceptionApplicationMessage) {
                result = new Exception(message);
            } else if (typeOfException instanceof ExceptionDBMessage) {
                result = new RuntimeException(message);
            } else if (typeOfException instanceof ExceptionUserMessage) {
                result = new Error(message);
            }
        }
        return result;
    }

}
