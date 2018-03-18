package ru.otus.shtyka;

import java.util.ArrayList;
import java.util.List;

class NominalSlot {
    private final static int DEFAULT_BILLS_COUNT = 10;

    public NOMINAL getNominal() {
        return nominal;
    }

    public int getBillsCount() {
        return billsCount;
    }

    public void setUpBillsCount(int upCount) {
        this.billsCount = this.billsCount + upCount;
    }

    public void setDownBillsCount(int downCount) {
        this.billsCount = this.billsCount - downCount;
    }

    private NOMINAL nominal;
    private int billsCount;

    private NominalSlot(NOMINAL nominal, int billsCount) {
        this.nominal = nominal;
        this.billsCount = billsCount;
    }

    public static List<NominalSlot> getDefaultCountOfSlots() {
        List<NominalSlot> slots = new ArrayList<>();
        for (NOMINAL nominal : NOMINAL.values()) {
            slots.add(new NominalSlot(nominal, DEFAULT_BILLS_COUNT));
        }
        return slots;
    }
}
