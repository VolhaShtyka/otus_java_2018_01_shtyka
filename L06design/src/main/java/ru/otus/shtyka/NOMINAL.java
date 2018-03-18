package ru.otus.shtyka;

public enum NOMINAL {
    ONE(1), FIFE(5), TEN(10), FIFTY(50), HUNDRED(100);

    public int getValue() {
        return value;
    }

    private int value;

    NOMINAL(final int value) {
        this.value = value;
    }
}
