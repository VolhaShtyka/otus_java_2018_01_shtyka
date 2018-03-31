package ru.otus.shtyka.nominal_slot_composite;

import ru.otus.shtyka.sum_strategy.CURRENCY;

public class NominalSlotRUB extends NominalSlot {

    @Override
    public CURRENCY getDefaultCurrency() {
        return defaultCurrency;
    }

    private CURRENCY defaultCurrency = CURRENCY.RUB;

    public NominalSlotRUB(NOMINAL nominal, int billsCount) {
        super(nominal, billsCount);
    }
}
