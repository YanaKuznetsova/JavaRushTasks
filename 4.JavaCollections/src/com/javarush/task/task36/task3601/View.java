package com.javarush.task.task36.task3601;

public class View {

    private Controller controller;

    public View() {
        controller = new Controller();
    }

    public void fireEventShowData() {
        System.out.println(controller.onDataListShow());
    }

}
