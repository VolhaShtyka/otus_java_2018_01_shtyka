package ru.otus.shtyka;

import static ru.otus.shtyka.root.TestClassLoader.loadClassByName;
import static ru.otus.shtyka.root.TestClassLoader.loadTestByPackage;

public class Main {
    public static void main(String[] args) {
        loadClassByName("ru.otus.shtyka.test.RabbitTest");
        loadTestByPackage("ru.otus.shtyka.test");
    }
}
