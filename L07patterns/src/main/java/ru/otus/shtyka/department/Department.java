package ru.otus.shtyka.department;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private static Department department;
    private List<ATMObserver> cashPoints = new ArrayList<>();

    private Department() {
    }

    public static Department getInstance() {
        if (department == null) {
            department = new Department();
        }
        return department;
    }

    public void register(ATMObserver atm) {
        cashPoints.add(atm);
    }

    public void restoreAllCashPointsFromMemento() {
        cashPoints.forEach(ATMObserver::restoreFromMemento);
    }

    public BigDecimal getSumAllCashPointsByCurrency(CURRENCY currency) {
        return cashPoints.stream().map(b -> b.getSum(currency)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
