package ru.otus.shtyka.storage;

import ru.otus.shtyka.department.CashPoint;

public class Memento {
    private final CashPoint atm;

    Memento(final CashPoint atm) {
        this.atm = atm;
    }

    public CashPoint getSavedState() {
        return atm;
    }
}
