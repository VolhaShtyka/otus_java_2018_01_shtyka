package ru.otus.shtyka.json_writer.test_objects;

import java.util.Objects;

public class EmptyClass {
    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass();
    }
}
