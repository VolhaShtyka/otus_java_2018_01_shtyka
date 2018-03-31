package ru.otus.shtyka.nominal_slot_composite;

import ru.otus.shtyka.sum_strategy.CURRENCY;

import java.math.BigDecimal;

interface SlotSum {
    BigDecimal getCashBalanceByCurrency(CURRENCY currency);

    BigDecimal getCashBalanceInRub();
}
