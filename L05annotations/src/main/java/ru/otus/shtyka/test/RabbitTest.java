package ru.otus.shtyka.test;

import ru.otus.shtyka.root.annotations.After;
import ru.otus.shtyka.root.annotations.Before;
import ru.otus.shtyka.root.annotations.Test;

import static ru.otus.shtyka.root.TestAssert.assertEquals;

@SuppressWarnings("unused")
public class RabbitTest {
    Rabbit rabbit;

    @Before
    public final void init() {
        rabbit = new Rabbit("Bob v." + (int) (Math.random() * 100));
        System.out.println("Create new Rabbit: " + rabbit.getName());
    }

    @Test
    public final void assertFailTest() {
        assertEquals(1, 2);
    }

    @Test
    public final void canJumpTest() {
        assertEquals(true, rabbit.canJump());
    }

    @Test
    public final void calculateSpeedTest() {
        assertEquals(10, rabbit.calculateSpeed(20, 200));
    }

    @After
    public final void finish() {
        System.out.println("Remove Rabbit: " + rabbit.getName());
        rabbit = null;
    }
}
