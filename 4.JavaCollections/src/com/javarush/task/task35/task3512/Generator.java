package com.javarush.task.task35.task3512;

public class Generator<T> {
    private Class<T> myClass;

    Generator(Class<T> myClass) {
        this.myClass = myClass;
    }

    T newInstance() throws IllegalAccessException, InstantiationException {
        return myClass.newInstance();
    }
}
