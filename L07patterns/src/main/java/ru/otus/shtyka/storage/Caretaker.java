package ru.otus.shtyka.storage;

import ru.otus.shtyka.department.ATMObserver;

import java.util.HashMap;
import java.util.Map;

public class Caretaker {
    private final Map<ATMObserver, Memento> savedStates = new HashMap<>();
    private static Caretaker caretaker;

    private Caretaker() {
    }

    public static Caretaker getInstance() {
        if (caretaker == null) {
            caretaker = new Caretaker();
        }
        return caretaker;
    }

    public void put(ATMObserver atm, Memento state) {
        savedStates.put(atm, state);
    }

    public <T extends ATMObserver> Memento get(T atm) {
        return savedStates.get(atm);
    }
}
