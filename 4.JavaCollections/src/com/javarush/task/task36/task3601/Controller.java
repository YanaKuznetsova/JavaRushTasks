package com.javarush.task.task36.task3601;

import java.util.List;

public class Controller {

    private Model model;

    public Controller() {
        model = new Model();
    }

    public List<String> onDataListShow() {
        return model.getStringDataList();
    }

}
