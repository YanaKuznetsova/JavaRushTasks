package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Waiter;

public class Restaurant {

    public static void main(String[] args) {
        Tablet myTablet = new Tablet(5);
        Cook myCook = new Cook("Amigo");
        Waiter myWaiter = new Waiter();

        myTablet.addObserver(myCook);
        myCook.addObserver(myWaiter);
        myTablet.createOrder();


    }

}
