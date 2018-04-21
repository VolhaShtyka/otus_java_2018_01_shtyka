package ru.otus.shtyka;

import ru.otus.shtyka.base.DBService;
import ru.otus.shtyka.base.entity.Phone;
import ru.otus.shtyka.base.entity.User;
import ru.otus.shtyka.service.DBServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        User sidorov = new User("Sidorov", 22);
        User petrov = new User("Petrov", 38);

        petrov.getAddress().setStreet("Kurskaya");
        Phone phone = new Phone();
        phone.setNumber("9876");
        sidorov.addPhone(phone);
        dbService.save(sidorov);
        dbService.save(petrov);

        User user = (User) dbService.load(User.class, sidorov.getId());
        System.out.println(user);

        String userName = dbService.getUserNameById(petrov.getId());
        System.out.println(userName);

        List<User> dataSets = dbService.loadAll(User.class);
        for (User userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();
    }
}
