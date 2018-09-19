package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;

public class StudentsDataBase {
    public static List<Student> students = new ArrayList<>();

    public static void addInfoAboutStudent(Student student) {
        students.add(student);
        printInfoAboutStudent(student);
    }

    public static void printInfoAboutStudent(Student student) {
        System.out.println("Имя: " + student.getName() + " Возраст: " + student.getAge());
    }

    public static void removeStudent(int index) {
        try {
            students.remove(index);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static void findDimaOrSasha() {
        for (int i = 0; i < students.size(); i++) {
            String name = students.get(i).getName();
            if (name.equals("Dima")) {
                System.out.println("Студент Dima есть в базе.");
                break;
            }
            else if (name.equals("Sasha")) {
                System.out.println("Студент Sasha есть в базе.");
                break;
            }
        }
    }
}