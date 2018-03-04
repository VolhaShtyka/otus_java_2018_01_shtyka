package ru.otus.shtyka.root;

public class TestAssert {
    public static void assertEquals(final Object expected, final Object actual) {
        if (!actual.equals(expected)) {
            throw new TestException("Expected: " + expected + ", but was: " + actual);
        }
    }
}
