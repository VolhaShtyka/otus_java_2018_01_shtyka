package ru.otus.shtyka.department;

import ru.otus.shtyka.storage.Caretaker;
import ru.otus.shtyka.storage.Memento;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ATMUsd implements ATMObserver {
    private String name;
    private BigDecimal cashBalance;

    public ATMUsd(String name, BigDecimal cashBalance) throws CloneNotSupportedException {
        this.name = name;
        this.cashBalance = cashBalance;
        saveToMemento();
    }

    private void saveToMemento() throws CloneNotSupportedException {
        Caretaker.getInstance().put(this, new Memento<>((ATMUsd) this.clone()));
        System.out.println("ATM USD " + name + ": Saving " + this.cashBalance + " to Memento.");
    }

    public void increaseCashBalance(final BigDecimal sum) {
        cashBalance = cashBalance.add(sum);
        System.out.println("ATM USD " + name + ": increase " + sum);
        printCashBalance();
    }

    public void decreaseCashBalance(final BigDecimal sum) {
        cashBalance = cashBalance.subtract(sum);
        System.out.println("ATM USD " + name + ": decrease " + sum);
        printCashBalance();
    }

    @Override
    public void printCashBalance() {
        System.out.println("ATM USD " + name + " cash balance: " + cashBalance);
    }

    @Override
    public BigDecimal getSum(CURRENCY currency) {
        switch (currency) {
            case RUB:
                return cashBalance.multiply(currency.rate);
            case USD:
                return cashBalance;
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
        System.out.println("ATM USD " + name + ": State after restoring from Memento: " + cashBalance);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
