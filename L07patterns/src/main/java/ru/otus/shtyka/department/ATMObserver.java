package ru.otus.shtyka.department;

import java.math.BigDecimal;

/**
 * Created by tully.
 * <p>
 * Abstract observer in the Observer interface.
 */

public interface ATMObserver extends Cloneable {
    BigDecimal getSum();

    BigDecimal getSum(CURRENCY currency);

    void restoreFromMemento();

    void printCashBalance();
}
