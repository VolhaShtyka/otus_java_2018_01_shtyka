package ru.otus.shtyka.storage;

import ru.otus.shtyka.department.CashPoint;

import java.util.HashMap;
import java.util.Map;

public class Caretaker implements Cloneable {
    private static final Map<CashPoint, Memento> savedStates = new HashMap<>();

    private Caretaker() {
    }

    public static void put(CashPoint atm) {
        savedStates.put(atm, new Memento(atm.copy()));
    }

    public static Memento get(CashPoint atm) {
        return savedStates.get(atm);
    }
}
