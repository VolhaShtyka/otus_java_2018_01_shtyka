package ru.otus.shtyka;

import ru.otus.shtyka.base.DBService;
import ru.otus.shtyka.base.UsersDataSet;
import ru.otus.shtyka.connection.DBServiceTransactional;

public class Main {
    public static void main(String[] args) throws Exception {
        try (DBService dbService = new DBServiceTransactional()) {
            System.out.println(dbService.getMetaData());
            dbService.prepareTables(UsersDataSet.class);
            UsersDataSet petrov = new UsersDataSet("Petrov", 32);
            UsersDataSet sidorov = new UsersDataSet("Sidorov", 25);
            dbService.save(petrov);
            dbService.save(sidorov);
            long petrovId = petrov.getId();
            System.out.println(String.format("UserName with id = %d : %s", petrovId, dbService.getUserName(UsersDataSet.class.getSimpleName(), petrovId)));
            long sidorovId = sidorov.getId();
            UsersDataSet user = dbService.load(sidorovId, UsersDataSet.class);
            System.out.println(String.format("User with id = %d : %s", sidorovId, user));
            dbService.deleteTables(UsersDataSet.class);
        }
    }
}
