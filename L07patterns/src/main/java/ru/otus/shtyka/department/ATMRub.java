package ru.otus.shtyka.department;

import ru.otus.shtyka.storage.Caretaker;
import ru.otus.shtyka.storage.Memento;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ATMRub implements ATMObserver {
    private String name;
    private BigDecimal cashBalance;

    public ATMRub(String name, BigDecimal cashBalance) throws CloneNotSupportedException {
        this.name = name;
        this.cashBalance = cashBalance;
        saveToMemento();
    }

    private void saveToMemento() throws CloneNotSupportedException {
        Caretaker.getInstance().put(this, new Memento<>((ATMRub) this.clone()));
        System.out.println("ATM Rub " + name + ": Saving " + this.cashBalance + " to Memento.");
    }

    public void increaseCashBalance(final BigDecimal sum) {
        cashBalance = cashBalance.add(sum);
        System.out.println("ATM Rub " + name + ": increase " + sum);
        printCashBalance();
    }

    public void decreaseCashBalance(final BigDecimal sum) {
        cashBalance = cashBalance.subtract(sum);
        System.out.println("ATM Rub " + name + ": decrease " + sum);
        printCashBalance();
    }

    @Override
    public void printCashBalance() {
        System.out.println("ATM Rub " + name + " cash balance: " + cashBalance);
    }

    @Override
    public BigDecimal getSum(CURRENCY currency) {
        switch (currency) {
            case RUB:
                return cashBalance;
            case USD:
                return cashBalance.divide(currency.rate, RoundingMode.HALF_DOWN);
            case EURO:
                return cashBalance.divide(currency.rate, RoundingMode.HALF_DOWN);
            default:
                throw new IllegalArgumentException("Unknown currency type");
        }
    }

    @Override
    public BigDecimal getSum() {
        return cashBalance;
    }

    @Override
    public void restoreFromMemento() {
        cashBalance = Caretaker.getInstance().get(this).getSavedState().getSum();
        System.out.println("ATM Rub " + name + ": State after restoring from Memento: " + cashBalance);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
