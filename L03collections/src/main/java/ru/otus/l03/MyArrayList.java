package ru.otus.l03;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private final static int DEFAULT_SIZE = 20;

    private int size;
    private Object[] array;

    MyArrayList() {
        this.array = new Object[DEFAULT_SIZE];
    }

    MyArrayList(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Illegal capacity");
        }
        this.size = size;
        array = new Object[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public boolean add(T t) {
        checkCapacity(size);
        array[size++] = t;
        return true;
    }

    private void checkCapacity(final int size) {
        if (size + 1 > array.length) {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public void sort(Comparator<? super T> c) {
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j].hashCode() > array[j + 1].hashCode()) {
                    int tmp = array[j].hashCode();
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }

    @Override
    public void clear() {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        T oldEl = (T) array[index];
        array[index] = element;
        return oldEl;
    }

    @Override
    public void add(int index, T element) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public T remove(int index) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public int indexOf(Object o) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("This exception is provided by the implementation MyArrayList");
    }

    private class MyIterator implements Iterator<T> {
        private int index;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return (T) array[index++];
        }
    }

    @Override
    public String toString() {
        MyIterator iterator = new MyIterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n");
        }
        return sb.toString();
    }
}
