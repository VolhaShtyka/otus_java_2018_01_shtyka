package ru.otus.l03;

import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> myStringListEmpty = new MyArrayList<>();
        System.out.println(myStringListEmpty.size());
        System.out.println(Arrays.toString(myStringListEmpty.toArray()));

        MyArrayList<String> myStringListFix = new MyArrayList<>(3);
        System.out.println(myStringListFix.size());  // Должен быть ноль
        System.out.println(myStringListFix.isEmpty()); // Должно быть true

        MyArrayList<String> myStringList = new MyArrayList<>();
        myStringList.add("This is String.");
        System.out.println("Size myStringList: " + myStringList.size());
        System.out.println(Arrays.toString(myStringList.toArray()));
        System.out.println("myStringList: " + myStringList);

        MyArrayList<Integer> myIntegerList = new MyArrayList<>();
        int integerSizeList = 5;
        for (int i = 0; i < integerSizeList; i++) {
            myIntegerList.add(i);
        }
        Collections.addAll(myIntegerList, 6, 5, 9, 7);
        System.out.println("Size myIntegerList: " + myIntegerList.size());

        MyArrayList<Integer> myIntegerList2 = new MyArrayList<>(myIntegerList.size());
        boolean isEquals = Arrays.equals(myIntegerList.toArray(), myIntegerList2.toArray());
        System.out.println("Equals myIntegerList myIntegerList2: " + isEquals);

        Collections.reverse(myIntegerList2);
        System.out.println("Max value from myIntegerList2: " + Collections.max(myIntegerList2));
    }
}
