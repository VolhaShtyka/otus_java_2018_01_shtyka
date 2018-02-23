package ru.otus.shtyka;

import java.lang.instrument.Instrumentation;

public class AgentMemory {
    private static volatile Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
        System.out.println("Hello! I`m java agent");

    }

    public static long getSize(Object obj) {
        if (instrumentation == null) {
            throw new IllegalStateException("Agent not initialised");
        }
        return instrumentation.getObjectSize(obj);
    }
}