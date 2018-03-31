package ru.otus.shtyka.sum_strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AlgorithmUSD implements Algorithm {
    @Override
    public BigDecimal calculateWithCurrency(BigDecimal cashBalanceRUB) {
        return cashBalanceRUB.divide(CURRENCY.USD.rate, RoundingMode.HALF_DOWN);
    }

    @Override
    public BigDecimal convertToRub(BigDecimal cashBalance) {
        return cashBalance.multiply(CURRENCY.USD.rate);
    }

}