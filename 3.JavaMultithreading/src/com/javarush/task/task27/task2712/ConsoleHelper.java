package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.javarush.task.task27.task2712.kitchen.Dish.allDishesToString;

public class ConsoleHelper {

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }
    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> allSelectedDishes = new ArrayList<>();
        System.out.println(allDishesToString());
        writeMessage("Choose your dish or enter \"exit\":");
        while (true) {
            String selectedDish = readString();
            if (selectedDish.equalsIgnoreCase("exit")) {
                break;
            }
            if (selectedDish.isEmpty()) {
                writeMessage("Nothing selected");
            } else {
                boolean notFoundInMenu = true;
                for (Dish dish : Dish.values()) {
                    if (dish.toString().equalsIgnoreCase(selectedDish)) {
                        allSelectedDishes.add(dish);
                        notFoundInMenu = false;
                        break;
                    }
                }
                if (notFoundInMenu) {
                    writeMessage("No such dish in menu");
                }
            }

        }
        return allSelectedDishes;
    }

}
