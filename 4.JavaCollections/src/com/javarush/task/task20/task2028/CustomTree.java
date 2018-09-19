package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String>
        implements Cloneable, Serializable {

    Entry<String> root;

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public void checkChildren() {
            if (leftChild != null) {
                availableToAddLeftChildren = false;
            } else {
                availableToAddLeftChildren = true;
            }
            if (rightChild != null) {
                availableToAddRightChildren = false;
            } else {
                availableToAddRightChildren = true;
            }
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }

    CustomTree(){
        root = new Entry("root");
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        int size = 0;
        Queue<Entry> queue = new LinkedList<>();
        Entry currentEntry;
        queue.add(root);
        while(!queue.isEmpty()) {
            currentEntry = queue.poll();
            size++;
            if (currentEntry.leftChild != null) {
                queue.add(currentEntry.leftChild);
            }
            if (currentEntry.rightChild != null) {
                queue.add(currentEntry.rightChild);
            }
        }
        return --size;
    }

    @Override
    public boolean add(String element) {
        if (element == null) {
            return false;
        }
        Entry currentEntry;
        Queue<Entry> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            currentEntry = queue.poll();
            if (currentEntry.isAvailableToAddChildren()) {
                if (currentEntry.availableToAddLeftChildren) {
                    currentEntry.leftChild = new Entry(element);
                    currentEntry.leftChild.parent = currentEntry;
                    currentEntry.checkChildren();
                    return true;
                } else if (currentEntry.availableToAddRightChildren) {
                    currentEntry.rightChild = new Entry(element);
                    currentEntry.rightChild.parent = currentEntry;
                    currentEntry.checkChildren();
                    return true;
                }
            }
            queue.add(currentEntry.leftChild);
            queue.add(currentEntry.rightChild);
        }
        return false;
    }

    public String getParent(String element) {
        if (element == null) {
            return null;
        }
        Entry currentEntry;
        Queue<Entry> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            currentEntry = queue.poll();
            if (currentEntry.elementName.equals(element)) {
                return currentEntry.parent.elementName;
            }
            if (currentEntry.leftChild != null) {
                queue.add(currentEntry.leftChild);
            }
            if (currentEntry.rightChild != null) {
                queue.add(currentEntry.rightChild);
            }
        }
        return null;
    }

    public boolean remove(Object element) {
        if (element == null || !(element instanceof String)) {
            throw new UnsupportedOperationException();
        }
        Entry currentEntry = root;
        Queue<Entry> queue = new LinkedList<>();
        boolean entryFound = false;
        queue.add(root);
        while (!queue.isEmpty()) {
            currentEntry = queue.poll();
            if (currentEntry.elementName.equals(element)) {
                entryFound = true;
                break;
            }
            if (currentEntry.leftChild != null) {
                queue.add(currentEntry.leftChild);
            }
            if (currentEntry.rightChild != null) {
                queue.add(currentEntry.rightChild);
            }
        }
        if (entryFound) {
            Entry parent = currentEntry.parent;
            if (parent.leftChild == currentEntry) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
            parent.checkChildren();
            if (parent == root && !root.isAvailableToAddChildren()) {
                root.availableToAddLeftChildren = true;
                root.availableToAddRightChildren = true;
            }
        }
        return entryFound;

    }

}
