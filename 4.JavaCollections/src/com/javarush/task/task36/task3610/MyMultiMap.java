package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int size = 0;
        for (Entry<K, List<V>> entry : map.entrySet()) {
            size += entry.getValue().size();
        }
        return size;
    }

    @Override
    public V put(K key, V value) {
        V result = null;
        if (map.containsKey(key)) {
            int size = map.get(key).size();
            result = map.get(key).get(size - 1);
            if (size == repeatCount) {
                map.get(key).remove(0);
            }
        } else {
            map.put(key, new ArrayList<V>());
        }
        map.get(key).add(value);
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = null;
        if (map.containsKey(key)) {
            if (map.get(key).size() > 0){
                result = map.get(key).remove(0);
                if (map.get(key).size() == 0) {
                    map.remove(key);
                }
            }
        }
        return result;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> result = new ArrayList<>();
        for (Entry<K, List<V>> entry: map.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        boolean result = false;
        for (Entry<K, List<V>> entry: map.entrySet()) {
            if (entry.getValue().contains(value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}