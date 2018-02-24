package ru.otus.shtyka;

public interface BenchmarkMBean {
    int getSize();

    void setSize(int size);

    long getSleepingTime();

    void sleep(long sleep);
}
