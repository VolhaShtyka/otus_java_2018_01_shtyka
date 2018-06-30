package ru.otus.shtyka.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.shtyka.entity.User;

public class DBServiceHelper {
    private static Logger log = LoggerFactory.getLogger(DBServiceHelper.class);

    public static void someActions(DBService dbService) {
        User sidorov = new User("Sidorov", 22);
        dbService.save(sidorov);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dbService.load(User.class, sidorov.getId());

        dbService.load(User.class, sidorov.getId());
        dbService.save(new User("Ivanov", 98));
        dbService.load(User.class, sidorov.getId());
        dbService.load(User.class, sidorov.getId());
        dbService.load(User.class, 5);
    }
}
