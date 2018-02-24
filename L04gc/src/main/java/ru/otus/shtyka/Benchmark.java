package ru.otus.shtyka;

import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {
    private volatile int size = 100;
    private volatile long millis = 500;

    private static final List<String> array = new ArrayList<>();

    void run() throws InterruptedException {

        while (true) {
            for (int i = 0; i < size; i++) {
                array.add(new String(new char[i]));
            }
            System.out.println("Created " + size + " objects.");
            for (int i = 0; i < size / 2; i++) {
                array.remove(i);
            }
            System.out.println("Removed " + size / 2 + " objects.");
            Thread.sleep(millis);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public long getSleepingTime() {
        return millis;
    }

    @Override
    public void sleep(long millis) {
        this.millis = millis;
    }
}
