package ru.otus.shtyka;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        printObjectSize(new Object());
        printObjectSize(new AgentTester());
        printObjectSize(new AgentTester(2, "I`m test", 873635L));
        printObjectSize(1);
        printObjectSize("");
        printObjectSize("string");
        printObjectSize(new BigDecimal("999999999999999.999"));
        printObjectSize(new ArrayList<String>());
        printObjectSize(new Integer[100]);
    }

    private static void printObjectSize(Object obj) {
        System.out.println(String.format("%s: %s, size=%s", obj.getClass()
                .getSimpleName(), obj, AgentMemory.getSize(obj)));
    }
}

class AgentTester {
    private int number;//4 bytes
    private String name; //  12 bytes (char value[]; 4 bytes int offset; // 4 bytes int count; // 4 bytes)
    private Long id; //8 bytes

    AgentTester() {
    }

    AgentTester(int number, String name, Long id) {
        super();
        this.number = number;
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        if (name == null) {
            return "I`m empty";
        } else {
            return "AgentTester{" +
                    "number=" + number +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
