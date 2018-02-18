package ru.otus.l03;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> myStringList = new MyArrayList<>();
        myStringList.add("This is String.");
        System.out.println("Size myStringList: " + myStringList.size());
        System.out.println("myStringList: " + myStringList);

        MyArrayList<Integer> myIntegerList = new MyArrayList<>();
        int integerSizeList = 5;
        for (int i = 0; i < integerSizeList; i++) {
            myIntegerList.add(i);
        }
        Collections.addAll(myIntegerList, 6, 5, 9, 7);
        System.out.println("Size myIntegerList: " + myIntegerList.size());

        MyArrayList<Integer> myIntegerList2 = new MyArrayList<>(myIntegerList.size());
        Collections.copy(myIntegerList2, myIntegerList);
        System.out.println("Size myIntegerList2: " + myIntegerList2.size());

        Collections.reverse(myIntegerList2);
        System.out.println("Max value from myIntegerList2: " + Collections.max(myIntegerList2));
    }
}
