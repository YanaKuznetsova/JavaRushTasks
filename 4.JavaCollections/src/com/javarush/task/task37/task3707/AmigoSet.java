package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet(){
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = (int) Math.max(16, collection.size()/.75f + 1);
        map = new HashMap<>(capacity);
        addAll(collection);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public boolean add(E e) {
        if (map.containsKey(e)) {
            return false;
        }
        map.put(e, PRESENT);
        return true;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Object clone(){
        try {
            AmigoSet<E> newSet = (AmigoSet<E>) super.clone();
            newSet.map = (HashMap<E, Object>) map.clone();
            return newSet;
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();

        int capacity = HashMapReflectionHelper.callHiddenMethod(map, "capacity");
        objectOutputStream.writeInt(capacity);

        float loadFactor = HashMapReflectionHelper.callHiddenMethod(map, "loadFactor");
        objectOutputStream.writeObject(loadFactor);

        int size = map.size();
        objectOutputStream.writeInt(size);

        for (E key: map.keySet()) {
            objectOutputStream.writeObject(key);
        }
    }

    private void readObject (ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int capacity = objectInputStream.readInt();
        float loadFactor = (float) objectInputStream.readObject();
        int size = objectInputStream.readInt();

        map = new HashMap<>(capacity, loadFactor);
        for (int i = 0; i < size; i++) {
            E key = (E) objectInputStream.readObject();
            map.put(key, PRESENT);
        }
    }

//    @Override
//    public Object[] toArray(Object[] a) {
//        return new Object[0];
//    }
}
