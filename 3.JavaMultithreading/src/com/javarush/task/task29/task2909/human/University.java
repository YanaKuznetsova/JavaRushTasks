package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;

public class University {//extends Student {

    private List<Student> students;
    private String name;
    private int age;

    public University(String name, int age) {
        this.name = name;
        this.age = age;
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student student: students) {
            if (student.getAverageGrade() == averageGrade){
                return student;
            }
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        double averageGrade = 0;
        Student bestStudent = null;
        for (Student student: students) {
            if (student.getAverageGrade() >= averageGrade){
                bestStudent = student;
                averageGrade = student.getAverageGrade();
            }
        }
        return bestStudent;
    }

    public Student getStudentWithMinAverageGrade() {
        double averageGrade = Double.MAX_VALUE;
        Student worstStudent = null;
        for (Student student: students) {
            if (student.getAverageGrade() <= averageGrade){
                worstStudent = student;
                averageGrade = student.getAverageGrade();
            }
        }
        return worstStudent;
    }

    public void expel(Student student) {
        students.remove(student);
    }
}