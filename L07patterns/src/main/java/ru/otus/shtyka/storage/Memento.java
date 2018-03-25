package ru.otus.shtyka.storage;

import ru.otus.shtyka.department.ATMObserver;

public class Memento<T extends ATMObserver> {
    private final T atm;

    public Memento(final T atm) {
        this.atm = atm;
    }

    public T getSavedState() {
        return atm;
    }
}
