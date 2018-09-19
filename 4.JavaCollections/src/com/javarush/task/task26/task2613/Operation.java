package com.javarush.task.task26.task2613;

public enum Operation {

    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        Operation result = null;
        if (i > 0 && i <= Operation.values().length) {
            result = Operation.values()[i];
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }

}
