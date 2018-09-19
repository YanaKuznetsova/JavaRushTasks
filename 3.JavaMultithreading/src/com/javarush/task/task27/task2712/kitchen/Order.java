package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

import static com.javarush.task.task27.task2712.ConsoleHelper.getAllDishesForOrder;

public class Order {

    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        dishes = getAllDishesForOrder();
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }

    @Override
    public String toString(){
//        String result = "";
//        if (dishes.size() > 0) {
//            StringBuilder stringBuilder = new StringBuilder("Your order: [");
//            for (Dish dish: dishes) {
//                stringBuilder.append(dish + ", ");
//            }
//            stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
//            stringBuilder.append("] of ");
//            stringBuilder.append(tablet.toString());
//            result = stringBuilder.toString();
//        }
//        return result;

        return dishes.isEmpty() ? "" : "Your order: " + dishes + " of " + tablet;

    }

    public int getTotalCookingTime() {
        int time = 0;
        for (Dish dish: dishes) {
            time += dish.getDuration();
        }
        return time;
    }

}
