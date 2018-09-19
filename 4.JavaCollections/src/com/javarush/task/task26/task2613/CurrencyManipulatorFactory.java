package com.javarush.task.task26.task2613;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {}

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
        String codeUpperCase = currencyCode.toUpperCase();
        CurrencyManipulator result;
        if (map.containsKey(codeUpperCase)) {
            result = map.get(codeUpperCase);
        } else {
            result = new CurrencyManipulator(codeUpperCase);
            map.put(codeUpperCase, result);
        }
        return result;
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return map.values();
    }
}
