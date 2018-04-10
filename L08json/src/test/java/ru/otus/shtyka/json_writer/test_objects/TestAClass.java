package ru.otus.shtyka.json_writer.test_objects;

import java.util.List;
import java.util.Objects;

public class TestAClass {
    private int a;
    private String b;
    private List<Double> c;
    private TestBClass d;

    public TestAClass(int a, String b, List<Double> c, TestBClass d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestAClass that = (TestAClass) o;
        return a == that.a &&
                Objects.equals(b, that.b) &&
                Objects.equals(c, that.c) &&
                Objects.equals(d, that.d);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d);
    }

    @Override
    public String toString() {
        return "TestAClass{" +
                "a=" + a +
                ", b='" + b + '\'' +
                ", c=" + c +
                ", d=" + d +
                '}';
    }
}
