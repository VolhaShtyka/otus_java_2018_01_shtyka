package ru.otus.shtyka.sum_strategy;

import java.math.BigDecimal;

public class Converter implements Algorithm {
    private Algorithm algorithm;

    public Converter(CURRENCY currency) {
        this.algorithm = currency.algorithm;
    }

    public BigDecimal calculateWithCurrency(BigDecimal cashBalance) {
        if (algorithm == null) {
            throw new IllegalStateException();
        }
        return algorithm.calculateWithCurrency(cashBalance);
    }

    public BigDecimal convertToRub(BigDecimal cashBalance) {
        if (algorithm == null) {
            throw new IllegalStateException();
        }
        return algorithm.convertToRub(cashBalance);
    }
}
