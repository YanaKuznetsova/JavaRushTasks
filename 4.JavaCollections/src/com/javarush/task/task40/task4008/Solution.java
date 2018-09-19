package com.javarush.task.task40.task4008;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/* 
Работа с Java 8 DateTime API
*/

public class Solution {
    public static void main(String[] args) {
        printDate("9.10.2017 5:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        MyCalendar myCalendar = new MyCalendar(date);
        myCalendar.print();
    }

    static class MyCalendar {
        private LocalDate localDate;
        private LocalTime localTime;
        private String dateAndTime;
        private String date = "";
        private String time = "";

        MyCalendar(String dateAndTime) {
            this.dateAndTime = dateAndTime;
        }

        void print(){
            splitDateAndTime();
            if (!date.equals("")) {
                writeDate();
            }
            if (!time.equals("")) {
                writeTime();
            }
        }

        void splitDateAndTime() {
            String[] parts = dateAndTime.split(" ");
            if (parts.length == 2) {
                this.date = parts[0];
                this.time = parts[1];
            } else if (parts[0].contains(".")){
                this.date = dateAndTime;
            } else {
                this.time = dateAndTime;
            }
        }

        void writeDate() {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d.M.yyyy"));
            System.out.println("День: " + localDate.getDayOfMonth());
            System.out.println("День недели: " + localDate.getDayOfWeek().getValue());
            System.out.println("День месяца: " + localDate.getDayOfMonth());
            System.out.println("День года: " + localDate.getDayOfYear());
            System.out.println("Неделя месяца: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth()));
            System.out.println("Неделя года: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfYear()));
            System.out.println("Месяц: " + localDate.getMonthValue());
            System.out.println("Год: " + localDate.getYear());
        }

        void writeTime() {
            localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:m:ss"));
            System.out.println("AM или PM: " + (localTime.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
            System.out.println("Часы: " + localTime.get(ChronoField.HOUR_OF_AMPM));
            System.out.println("Часы дня: " + localTime.getHour());
            System.out.println("Минуты: " + localTime.getMinute());
            System.out.println("Секунды: " + localTime.getSecond());
        }
    }

}
