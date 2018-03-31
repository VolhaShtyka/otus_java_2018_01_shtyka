package ru.otus.shtyka.sum_strategy;

import java.math.BigDecimal;

public interface Algorithm {
    BigDecimal calculateWithCurrency(BigDecimal cashBalance);

    BigDecimal convertToRub(BigDecimal cashBalance);
}
