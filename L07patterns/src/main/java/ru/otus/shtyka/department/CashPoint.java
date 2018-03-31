package ru.otus.shtyka.department;

import org.kamranzafar.commons.cloner.ObjectCloner;
import ru.otus.shtyka.nominal_slot_composite.NOMINAL;
import ru.otus.shtyka.nominal_slot_composite.NominalSlotRUB;
import ru.otus.shtyka.nominal_slot_composite.NominalSlotUSD;
import ru.otus.shtyka.nominal_slot_composite.NominalSlotsGroup;
import ru.otus.shtyka.storage.Caretaker;
import ru.otus.shtyka.sum_strategy.CURRENCY;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CashPoint {

    private String name;

    private NominalSlotsGroup slots;

    public CashPoint(String name, int countOfSlots) {
        this.name = name;
        this.slots = new NominalSlotsGroup();
        for (NOMINAL nominal : NOMINAL.values()) {
            slots.addSlot(new NominalSlotRUB(nominal, countOfSlots));
            slots.addSlot(new NominalSlotUSD(nominal, countOfSlots));
        }
        saveToMemento();
    }

    public CashPoint copy() {
        return (CashPoint) new ObjectCloner().deepClone(this);
    }

    private void saveToMemento() {
        Caretaker.put(this);
        System.out.println("ATM " + name + ": Saving " + getCashBalance() + " in RUB to Memento.");
    }

    public void restoreFromMemento() {
        this.slots = Caretaker.get(this).getSavedState().slots;
        System.out.println("ATM " + name + ": State after restoring from Memento: " + getCashBalance() + " in RUB");
    }

    public BigDecimal getCashBalance() {
        return slots.getCashBalanceInRub();
    }


    public BigDecimal getCashBalance(CURRENCY currency) {
        return slots.getCashBalanceByCurrency(currency);
    }

    public void getSumOfMinimumNumberOfBanknotes(final BigDecimal wantedSum, final CURRENCY currency) {
        if (wantedSum.compareTo(getCashBalance(currency)) > 0 ||
                wantedSum.intValue() < 0 ||
                wantedSum.setScale(0, RoundingMode.HALF_DOWN).compareTo(wantedSum) != 0) {
            System.out.println("It`s impossible to provide the requested amount: " + wantedSum + " in " + currency);
            return;
        }
        System.out.println("Opening balance " + name + ": " + getCashBalance() + " in RUB");
        System.out.println("Requested amount " + name + ": " + wantedSum + " " + currency + "\n");
        slots.getSum(wantedSum, currency);
        System.out.println("\nFinal balance " + name + ": " + getCashBalance() + " in RUB");
    }
}
