package ru.otus.shtyka.json_writer.test_objects;

import java.util.List;
import java.util.Objects;

public class TestBClass {
    private int a;
    private String b;
    private List<Double> c;

    public TestBClass(int a, String b, List<Double> c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "TestBClass{" +
                "a=" + a +
                ", b='" + b + '\'' +
                ", c=" + c +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestBClass testClass = (TestBClass) o;
        return Objects.equals(a, testClass.a) &&
                Objects.equals(b, testClass.b) &&
                Objects.equals(c, testClass.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
