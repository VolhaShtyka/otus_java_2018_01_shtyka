package ru.otus.shtyka;

import ru.otus.shtyka.entity.Phone;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.service.DBService;
import ru.otus.shtyka.service.DBServiceImpl;
import ru.otus.shtyka.servlet.JettyServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        DBService dbService = new DBServiceImpl();
        JettyServer server = new JettyServer();
        server.run(dbService.getCacheEngine());
        User sidorov = new User("Sidorov", 22);
        User petrov = new User("Petrov", 38);

        petrov.getAddress().setStreet("Kurskaya");
        Phone phone = new Phone();
        phone.setNumber("9876");
        sidorov.addPhone(phone);
        dbService.save(sidorov);
        dbService.save(petrov);
        dbService.save(new User("Ivanov", 98));
        dbService.save(new User("Smith", 15));

        User user = (User) dbService.load(User.class, sidorov.getId());
        System.out.println(user);

        User userFromCache = (User) dbService.load(User.class, sidorov.getId());
        System.out.println(userFromCache);

        String userName = dbService.getUserNameById(petrov.getId());
        System.out.println(userName);

        List<User> dataSets = dbService.loadAll();
        for (User userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        System.out.println("Reading users from DB");
        System.out.println("To exit press \"q\"");
        System.out.println("Enter the required ID");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (!(input = reader.readLine()).equals("q")) {
            try {
                System.out.println(dbService.load(User.class, Integer.parseInt(input)));
            } catch (Exception e) {
                System.out.println("Could not find user with ID " + input);
            }
        }
        server.stop();
        dbService.shutdown();
    }
}
