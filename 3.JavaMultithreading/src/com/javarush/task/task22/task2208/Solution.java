package com.javarush.task.task22.task2208;

import java.util.Map;
import java.util.TreeMap;

/* 
Формируем WHERE
*/
public class Solution {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>();
        map.put("name", "Ivanov");
        map.put("country", "UK");
        map.put("city", "Kiev");
        map.put("age", null);
        System.out.println(getQuery(map));

    }
    public static String getQuery(Map<String, String> params) {
        if (params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry: params.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key!= null && value != null) {
                sb.append(key + " = '" + value + "' and ");
            }
        }
        if (sb.length() > 0) {
            int trimEnd = sb.lastIndexOf("and");
            sb.delete(trimEnd - 1, sb.length());
        }

        return sb.toString();
    }
}
