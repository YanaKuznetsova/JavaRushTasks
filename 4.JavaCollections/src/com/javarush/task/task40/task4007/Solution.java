package com.javarush.task.task40.task4007;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* 
Работа с датами
*/

public class Solution {
    public static void main(String[] args) {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        try {
            String pattern = getPattern(date);
            MyCalendar myCalendar = new MyCalendar(pattern, date);
            if (myCalendar.haveToWriteDate) {
                myCalendar.writeDate();
            }
            if (myCalendar.haveToWriteTime) {
                myCalendar.writeTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static String getPattern(String date) {
        String pattern;
        if (date.contains(":") && date.contains(".")) {
            pattern = "dd.MM.yyyy HH:mm:ss";
        } else if (date.contains(".")){
            pattern = "dd.MM.yyyy";
        } else {
            pattern = "HH:mm:ss";
        }
        return pattern;
    }

    static class MyCalendar {
        private boolean haveToWriteDate = false;
        private boolean haveToWriteTime = false;
        Calendar calendar;

        MyCalendar(String pattern, String date) throws ParseException {
            this.calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat(pattern).parse(date));
            if (pattern.contains(".")) {
                haveToWriteDate = true;
            }
            if (pattern.contains(":")) {
                haveToWriteTime = true;
            }
        }

        void writeDate() {
            System.out.println("День: " + calendar.get(Calendar.DATE));
            System.out.println("День недели: " + ((calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1));
            System.out.println("День месяца: " + calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("День года: " + calendar.get(Calendar.DAY_OF_YEAR));
            System.out.println("Неделя месяца: " + calendar.get(Calendar.WEEK_OF_MONTH));
            System.out.println("Неделя года: " + calendar.get(Calendar.WEEK_OF_YEAR));
            System.out.println("Месяц: " + (calendar.get(Calendar.MONTH) + 1));
            System.out.println("Год: " + calendar.get(Calendar.YEAR));
        }

        void writeTime() {
            System.out.println("AM или PM: " + (calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM"));
            System.out.println("Часы: " + calendar.get(Calendar.HOUR));
            System.out.println("Часы дня: " + calendar.get(Calendar.HOUR_OF_DAY));
            System.out.println("Минуты: " + calendar.get(Calendar.MINUTE));
            System.out.println("Секунды: " + calendar.get(Calendar.SECOND));        }
    }

}
