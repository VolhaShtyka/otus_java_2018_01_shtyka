package ru.otus.shtyka.test;

public class Rabbit {
    private String name;
    private boolean IS_JUMPING = true;

    Rabbit(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean canJump() {
        return IS_JUMPING;
    }

    public int calculateSpeed(final int time, final int distance) {
        return distance / time;
    }
}
