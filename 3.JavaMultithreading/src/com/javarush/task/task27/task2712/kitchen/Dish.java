package com.javarush.task.task27.task2712.kitchen;

import java.util.Arrays;

public enum Dish {

    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public static String allDishesToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Dish dish: Dish.values()) {
            stringBuilder.append(dish + ", ");
        }
        return stringBuilder.toString().trim().substring(0, stringBuilder.length()-2);
        //return Arrays.toString(Dish.values()).substring(1, Arrays.toString(Dish.values()).length()-1);
    }

    public int getDuration() {
        return duration;
    }

}
